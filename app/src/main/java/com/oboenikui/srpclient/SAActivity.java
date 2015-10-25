package com.oboenikui.srpclient;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.cookie.Cookie;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SAActivity extends Activity {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                final String id = ((EditText)SAActivity.this.findViewById(R.id.editText1)).getText().toString();
                final String pass = ((EditText)SAActivity.this.findViewById(R.id.editText2)).getText().toString();
                final String imt = ((EditText)SAActivity.this.findViewById(R.id.editText3)).getText().toString();

                WebView webView = new WebView(SAActivity.this);
                WebSettings webSettings = webView.getSettings();
                final String userAgentString = webSettings.getUserAgentString();
                new Thread(new Runnable() {
                    
                    @Override
                    public void run() {

                        SRPAuth auth = new SRPAuth(id,pass,imt,userAgentString);
                        try {
                            auth.doAuth();
                            final List<Cookie> cookies = auth.getCookieStore().getCookies();
                            handler.post(new Runnable() {
                                
                                @Override
                                public void run() {
                                    String cookieString="";
                                    for(Cookie cookie:cookies){
                                        cookieString += cookie.getName()+":"+cookie.getValue();
                                    }
                                    ((TextView)SAActivity.this.findViewById(R.id.textView)).setText(cookieString);
                                }
                            });
                        } catch (ClientProtocolException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
