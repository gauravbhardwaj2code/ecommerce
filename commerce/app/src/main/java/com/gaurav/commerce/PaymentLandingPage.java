package com.gaurav.commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.commerce.http.ConfirmOrder;
import com.gaurav.commerce.usersession.UserSession;
import com.instamojo.android.Instamojo;

public class PaymentLandingPage extends AppCompatActivity implements Instamojo.InstamojoPaymentCallback{

    private static final String TAG =PaymentLandingPage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_landing_page);
    }


    @Override
    public void onInstamojoPaymentComplete(String orderID, String transactionID, String paymentID, String paymentStatus) {
        Log.d(TAG, "Payment complete");
        System.out.println("Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
                + ", Payment ID:" + paymentID + ", Status: " + paymentStatus);

        String result=null;
        try {
            UserSession userSession=new UserSession(this);
            result=new ConfirmOrder().execute(userSession.getOrderId(),transactionID).get();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(null!=result && result.equalsIgnoreCase("success")){
            Intent intent = new Intent(this, OrderPlaced.class);
            intent.putExtra("orderid","Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
                    + ", Payment ID:" + paymentID + ", Status: " + paymentStatus);
            this.startActivity(intent);
        }else {
            TextView textView=findViewById(R.id.message);
            textView.setText("Your payment is complete. But we are facing issuw while processing your order." +
                    "Contact Support Team");
            Intent intent = new Intent(this, Cart.class);
            this.startActivity(intent);
            this.finish();
        }

        //finish();*/
        //showToast("Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
        //      + ", Payment ID:" + paymentID + ", Status: " + paymentStatus);

    }

    @Override
    public void onPaymentCancelled() {
        Log.d(TAG, "Payment cancelled");
        showToast("Payment cancelled by user");

        Intent intent = new Intent(this, Cart.class);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public void onInitiatePaymentFailure(String errorMessage) {
        Log.d(TAG, "Initiate payment failed");
        showToast("Initiating payment failed. Error: " + errorMessage);
        Intent intent = new Intent(this, Cart.class);
        this.startActivity(intent);
        this.finish();
    }

    private void showToast(final String message) {

        TextView textView=findViewById(R.id.message);
        textView.setText(message);

    }
}
