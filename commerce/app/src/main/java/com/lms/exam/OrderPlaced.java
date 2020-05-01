package com.lms.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lms.exam.activities.HomePageWithBottomNavigation;
import com.lms.exam.http.ConfirmOrder;
import com.lms.exam.networksync.CheckInternetConnection;
import com.lms.exam.usersession.UserSession;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderPlaced extends AppCompatActivity {

    @BindView(R.id.orderid)
    TextView orderidview;
    private String orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        ButterKnife.bind(this);

        //check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        String paymentStatus = getIntent().getStringExtra("paymentStatus");
        if (paymentStatus.equalsIgnoreCase("credit")) {
            try {
                UserSession userSession = new UserSession(this);
                new ConfirmOrder().execute(userSession.getOrderId(), getIntent().getStringExtra("transactionId"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            TextView textView = findViewById(R.id.message);
            textView.setText("Payment Failed.");
        }

        Button button = findViewById(R.id.go_to_mycourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderPlaced.this, HomePageWithBottomNavigation.class);
                startActivity(intent);
                finish();
            }
        });


        initialize();
    }

    private void initialize() {
        orderid = getIntent().getStringExtra("orderid");
        orderidview.setText(orderid);
    }

    public void finishActivity(View view) {
        Intent intent = new Intent(this, HomePageWithBottomNavigation.class);
        startActivity(intent);
        finish();
    }
}
