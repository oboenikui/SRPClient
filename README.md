# SRP Client
SRP OTPを用いたクライアントのサンプルです．

## Usage
表示されたTextViewの左から順に東北大ID，パスワード，イメージマトリクスを入力します．
イメージマトリクスの文字列は，以下の表を参考にして下さい．

|画像|名前|画像|名前|
|:--:|:--:|:--:|:--:|
|![notebook](https://dl.dropboxusercontent.com/u/89996208/srp_img/d01.gif)|d01.gif|![okame](https://dl.dropboxusercontent.com/u/89996208/srp_img/d14.gif)|d14.gif|
|![camera](https://dl.dropboxusercontent.com/u/89996208/srp_img/d02.gif)|d02.gif|![oni](https://dl.dropboxusercontent.com/u/89996208/srp_img/d15.gif)|d15.gif|
|![chair](https://dl.dropboxusercontent.com/u/89996208/srp_img/d03.gif)|d03.gif|![paint](https://dl.dropboxusercontent.com/u/89996208/srp_img/d16.gif)|d16.gif|
|![cup](https://dl.dropboxusercontent.com/u/89996208/srp_img/d04.gif)|d04.gif|![pallet](https://dl.dropboxusercontent.com/u/89996208/srp_img/d17.gif)|d17.gif|
|![fan](https://dl.dropboxusercontent.com/u/89996208/srp_img/d05.gif)|d05.gif|![pen](https://dl.dropboxusercontent.com/u/89996208/srp_img/d18.gif)|d18.gif|
|![extinguisher](https://dl.dropboxusercontent.com/u/89996208/srp_img/d06.gif)|d06.gif|![pencil](https://dl.dropboxusercontent.com/u/89996208/srp_img/d19.gif)|d19.gif|
|![scissor](https://dl.dropboxusercontent.com/u/89996208/srp_img/d07.gif)|d07.gif|![post](https://dl.dropboxusercontent.com/u/89996208/srp_img/d20.gif)|d20.gif|
|![hyottoko](https://dl.dropboxusercontent.com/u/89996208/srp_img/d08.gif)|d08.gif|![shoes](https://dl.dropboxusercontent.com/u/89996208/srp_img/d21.gif)|d21.gif|
|![kabuto](https://dl.dropboxusercontent.com/u/89996208/srp_img/d09.gif)|d09.gif|![light](https://dl.dropboxusercontent.com/u/89996208/srp_img/d22.gif)|d22.gif|
|![fish](https://dl.dropboxusercontent.com/u/89996208/srp_img/d10.gif)|d10.gif|![sake](https://dl.dropboxusercontent.com/u/89996208/srp_img/d23.gif)|d23.gif|
|![lamp](https://dl.dropboxusercontent.com/u/89996208/srp_img/d11.gif)|d11.gif|![usagimochi](https://dl.dropboxusercontent.com/u/89996208/srp_img/d24.gif)|d24.gif|
|![moon](https://dl.dropboxusercontent.com/u/89996208/srp_img/d12.gif)|d12.gif|![violin](https://dl.dropboxusercontent.com/u/89996208/srp_img/d25.gif)|d25.gif|
|![hat](https://dl.dropboxusercontent.com/u/89996208/srp_img/d13.gif)|d13.gif| | |

もしイメージマトリクス認証が
![notebook](https://dl.dropboxusercontent.com/u/89996208/srp_img/d01.gif)→![camera](https://dl.dropboxusercontent.com/u/89996208/srp_img/d02.gif)→![chair](https://dl.dropboxusercontent.com/u/89996208/srp_img/d03.gif)
の順であるなら，入力するイメージマトリクスの文字列は「d01.gifd02.gifd03.gif」となります．

## License
SRP OTP is licensed under the terms of the [GNU General Public License](https://www.gnu.org/copyleft/gpl.html), with the "library exception" which permits its use as a library in conjunction with non-Free software:
> "As a special exception, the copyright holders of this library give you permission to link this library with independent modules to produce an executable, regardless of the license terms of these independent modules, and to copy and distribute the resulting executable under terms of your choice, provided that you also meet, for each linked independent module, the terms and conditions of the license of that module. An independent module is a module which is not derived from or based on this library. If you modify this library, you may extend this exception to your version of the library, but you are not obligated to do so. If you do not wish to do so, delete this exception statement from your version."

The effect of that license is similar to using the LGPL, except that static linking is permitted. GPL with that exception is sometimes called the Guile License, because the Guile implementation of Scheme (for embedding) uses this license.

このライブラリにはGPLライセンス下で提供されているgnu-crypto, srp-otpを含みます．