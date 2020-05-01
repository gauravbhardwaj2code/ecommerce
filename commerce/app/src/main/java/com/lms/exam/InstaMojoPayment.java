package com.lms.exam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lms.exam.http.CreateOrder;

import java.util.concurrent.ExecutionException;


public class InstaMojoPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insta_mojo_payment);

        try {
            createOrderOnServer();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createOrderOnServer() throws ExecutionException, InterruptedException {
        new CreateOrder(this).execute();
    }

    public void initiateSDKPayment(String orderID) {

        if (orderID == null || orderID.isEmpty()) {
            Toast.makeText(getBaseContext(), "Error while initiate payment! Try again Later", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Cart.class);
            intent.putExtra("orderId", orderID);
            startActivity(intent);

        } else {
            Intent intent = new Intent(this, PaymentWebView.class);
            intent.putExtra("orderId", orderID);
            startActivity(intent);
        }
    }


}