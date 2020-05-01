package com.lms.exam.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.lms.exam.OrderPlaced;

/**
 * JavaScript interface to transfer control from Webview to Application.
 */
public class JavaScriptInterface {

    private Activity activity;

    public static final String PAYMENT_BUNDLE = "payment_bundle";
    public static final String ORDER_ID = "orderId";
    public static final String ORDER = "order";
    public static final int REQUEST_CODE = 9;
    public static final String URL = "url";
    public static final String MERCHANT_ID = "merchantId";
    public static final String POST_DATA = "postData";
    public static final String TRANSACTION_ID = "transactionID";
    public static final String PAYMENT_ID = "paymentID";
    public static final String PAYMENT_STATUS = "paymentStatus";
    public static final int PENDING_PAYMENT = 2;
    public static final String KEY_CODE = "code";
    public static final String KEY_MESSGE = "message";


    public JavaScriptInterface(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void onTransactionComplete(String orderID, String transactionID, String paymentID, String paymentStatus) {
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, orderID);
        bundle.putString(TRANSACTION_ID, transactionID);
        bundle.putString(PAYMENT_ID, paymentID);
        bundle.putString(PAYMENT_STATUS, paymentStatus);

        Intent intent = new Intent(activity, OrderPlaced.class);
        intent.putExtra("paymentStatus", paymentStatus);
        intent.putExtra("transactionId", transactionID);
        intent.putExtra("orderid", orderID);
        activity.startActivity(intent);
        activity.finish();
    }

    @JavascriptInterface
    public int getSDKVersionCode() {
        return 19;
    }
}