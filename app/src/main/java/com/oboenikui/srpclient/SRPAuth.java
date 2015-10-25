package com.oboenikui.srpclient;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.oboenikui.srp.OTP;

import android.net.Uri;
import android.util.Log;

public class SRPAuth {
    private String id;
    private String password;
    private String imatrix;
    private String userAgentString;
    private CookieStore cookieStore;
    private long otpSessionId;
    private String otpAlgo;
    private int otpCounter;
    private SRPAuthListener listener = new SRPAuthListener() {
        @Override
        public void onException() {
        }
    };
    private static final String SCHEME = "https";
    private static final String AUTH_DOMAIN = "www.srp.tohoku.ac.jp";
    private static final String RESQUERY = "candr";
    
    public SRPAuth(String id, String password, String imatrix, String userAgentString){
        this.id = id;
        this.password = password;
        this.imatrix = imatrix;
        this.userAgentString = userAgentString;
    }

    public void setListener(SRPAuthListener listener){
        this.listener = listener;
    }

    public boolean doAuth() throws ClientProtocolException, IOException{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpParams params = httpClient.getParams();
        params.setParameter(CoreProtocolPNames.USER_AGENT, userAgentString);
        try{
            if(passPhraseAuth(httpClient)){
                return imageMatrixAuth(httpClient);
            }
            return false;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
    
    public CookieStore getCookieStore(){
        return cookieStore;
    }
    private boolean passPhraseAuth(DefaultHttpClient httpClient) throws ClientProtocolException, IOException{

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME);
        builder.encodedAuthority(AUTH_DOMAIN);
        builder.path("/");
        builder.appendQueryParameter("twuser", id);
        builder.appendQueryParameter("twpassword", password);
        String url = builder.build().toString();
        HttpGet request = new HttpGet(url);
        String result = httpClient.execute(request, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse response)
                    throws ClientProtocolException, IOException {

                switch (response.getStatusLine().getStatusCode()) {
                case HttpStatus.SC_OK:
                    return EntityUtils.toString(response.getEntity(), "UTF-8");
                default:
                    return null;
                }
            }
        });
        if(result==null){
            listener.onException();
        } else {
            CookieStore cookieStore = httpClient.getCookieStore();
            if(cookieStore==null){
                listener.onException();
            } else {
                this.cookieStore = cookieStore;
                Log.d("result",result);
                otpAlgo = getOTPAlgorism(result);
                otpCounter = getOTPCounter(result);
                otpSessionId = getOTPSessionId(result);
                Log.d("(algo,counter,sessionid)","("+otpAlgo+","+otpCounter+","+otpSessionId+")");
                return true;
            }
        }
        return false;
    }

    private boolean imageMatrixAuth(DefaultHttpClient httpClient) throws ClientProtocolException, IOException{
        OTP otp = new OTP(otpAlgo);
        String resqueryValue = otp.generateOTP(otpCounter, otpSessionId, imatrix);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME);
        builder.encodedAuthority(AUTH_DOMAIN);
        builder.path("/");
        builder.appendQueryParameter(RESQUERY, resqueryValue);
        String url = builder.build().toString();
        url.replaceAll("%20", "+");
        HttpGet request = new HttpGet(url);
        return httpClient.execute(request, new ResponseHandler<Boolean>() {
            @Override
            public Boolean handleResponse(HttpResponse response)
                    throws ClientProtocolException, IOException {
                switch (response.getStatusLine().getStatusCode()) {
                case HttpStatus.SC_OK:

                    Log.d("result", EntityUtils.toString(response.getEntity(), "UTF-8"));
                    return true;
                default:
                    return false;
                }

            }
        });
    }

    private Cookie getJSessionIdCookie(List<Cookie> cookies){
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("JSESSIONID")){
                return cookie;
            }
        }
        return null;
    }
    
    private String getOTPAlgorism(String html){
        if(html.indexOf("OTP_algo")!=-1){
            return html.split("OTP_algo")[1].split("\"")[1];
        }
        return null;
    }
    

    private long getOTPSessionId(String html){
        if(html.indexOf("OTP_session")!=-1){
            return Long.parseLong(html.split("OTP_session")[1].split("=")[1].split(";")[0].trim());
        }
        return -1;
    }
    

    private int getOTPCounter(String html){
        if(html.indexOf("OTP_counter")!=-1){
            return Integer.parseInt(html.split("OTP_counter")[1].split("=")[1].split(";")[0].trim());
        }
        return -1;
    }
    
    public interface SRPAuthListener{
        public void onException();
    }
}
