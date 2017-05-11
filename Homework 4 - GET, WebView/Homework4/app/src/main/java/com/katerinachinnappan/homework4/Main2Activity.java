package com.katerinachinnappan.homework4;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.webkit.JavascriptInterface;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main2Activity extends ActionBarActivity {

    private static final String LOG_TAG = "lv-ex";
    WebView myWebView;
    String url1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();

        if(extras == null) {
            finish(); // No idea what else to do
        } else {
            url1 = getIntent().getExtras().getString("url");
            Log.d(LOG_TAG, "Received urllll: " + url1);
            myWebView = (WebView) findViewById(R.id.webview);
            //myWebView.setWebViewClient(new WebViewClient());
            myWebView.setWebViewClient(new MyWebViewClient());
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            // Binds the Javascript interface
            myWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
            //String str = "https://dzone.com/articles/android-tutorial-how-parse";
            //Log.d(LOG_TAG, "Received str: " + str);
            TextView myTextView = (TextView) findViewById(R.id.url);
            myTextView.setText(getIntent().getExtras().getString("url"));
            myWebView.loadUrl(url1);
        }
    }


    public class JavaScriptInterface {
        Context mContext; // Having the context is useful for lots of things,
        // like accessing preferences.

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void myFunction(String args) {
            final String myArgs = args;
            Log.i(LOG_TAG, "I am in the javascript call.");
            runOnUiThread(new Runnable() {
                public void run() {
                    //Button v = (Button) findViewById(R.id.button1);
                   // v.setText(myArgs);
                }
            });

        }
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           //url1 = url1.substring(4);
            Log.d(LOG_TAG, "Received url1 web client: " + url1);
            Log.d(LOG_TAG, "Received url web client: " + url);

            //display in internal webview if url mathces/contains
            if (url.contains(url1)) {
                Log.d(LOG_TAG, "web false");
                return false;
            } //otherwise ciao
            else{
                // Otherwise, the link is not for a page on my site,
// so launch another Activity that handles URLs
                Log.d(LOG_TAG, "web true");
                Log.d(LOG_TAG, "ciao bambina");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack(); // Go to previous page
            return true;
        }
        // Use this as else part
        return super.onKeyDown(keyCode, event);
    }
}
