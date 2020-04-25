package com.gaurav.commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gaurav.commerce.activities.HomePageWithBottomNavigation;
import com.gaurav.commerce.http.FetchPaymentLink;
import com.gaurav.commerce.network.JavaScriptInterface;

import java.util.concurrent.ExecutionException;

public class PaymentWebView extends AppCompatActivity {

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_web_view);

        Intent intent=getIntent();
        String orderId=intent.getStringExtra("orderId");

        String url="https://api.instamojo.com/v2/gateway/orders/"+orderId+"/checkout-options/";

        try {
            String paymentUrl=new FetchPaymentLink().execute(url).get();
            mWebview = findViewById(R.id.webView);
            mWebview.setWebViewClient(new MyBrowser());
            mWebview.getSettings().setLoadsImagesAutomatically(true);
            mWebview.getSettings().setJavaScriptEnabled(true);
            mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            mWebview.addJavascriptInterface(new JavaScriptInterface(this), "AndroidScriptInterface");
           // mWebview.loadUrl("http://192.168.0.156/build/index.html");
            mWebview.loadUrl(paymentUrl);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, Cart.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
class MyBrowser extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
