package user.example.com.javacalljs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebview();
    }


    private void initWebview(){
        webView=new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/test1.html");
    }

    class JavaCallJs{
        @JavascriptInterface
        public void showToast(){
            Toast.makeText(MainActivity.this,"我是java代码",Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void playVideo(String url,String title){
            Log.i("Tag8","url:"+url);
            Log.i("Tag8","title:"+title);
            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(url),"video/*");
            startActivity(intent);
        }

    }

    /**
     * Java 调用Js代码 (给html中js下的方法javaCallJs传参数)
     * @param v
     */
    public void JavaCallJs(View v){
        webView.loadUrl("javascript:javaCallJs('"+"一个帅比"+"')");
        setContentView(webView);
    }

    /**
     * Js 调用Java代码
     * @param v
     * 这里java这个参数很重要，起到一个连接作用，要和html中的window.xxx下面的对应起来
     */
    public void JsCallJava(View v){
        webView.addJavascriptInterface(new JavaCallJs(),"java");
        webView.loadUrl("file:///android_asset/test1.html");
        setContentView(webView);
    }
}
