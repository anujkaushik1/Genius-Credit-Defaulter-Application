package com.example.geniuscredit1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;

public class signuppayment extends AppCompatActivity implements PaymentResultListener {

    Button proceedtopay;
    String usergstnumber;
    TextView cancel;
    HashMap<String,Object> paymentdata=new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppayment);

        proceedtopay = (Button) findViewById(R.id.proceedtopay);
        cancel=(TextView)findViewById(R.id.cancel);

        Intent intent = getIntent();
        usergstnumber = intent.getStringExtra("gstnumber");
        System.out.println(usergstnumber);
        proceedtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startpayment();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(signuppayment.this,signin.class);
                startActivity(intent1);
            }
        });


    }
    public void startpayment(){
        Checkout checkout= new Checkout();

        try{
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");


            options.put("currency", "INR");
            options.put("amount", "100");//pass amount in currency subunits

            checkout.open(signuppayment.this,options);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast toast = Toast.makeText(signuppayment.this,"Payment Successful",Toast.LENGTH_LONG);
        toast.show();
        paymentdata.put("Balance","5000");
        FirebaseDatabase.getInstance().getReference().child("Company").child(usergstnumber).updateChildren(paymentdata);
        FirebaseDatabase.getInstance().getReference().child("Wallet Balance").child(usergstnumber).updateChildren(paymentdata);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(signuppayment.this,signin.class);
                startActivity(intent);
            }
        },2000);

        System.out.println(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast toast= Toast.makeText(signuppayment.this,"Error ! Please try again",Toast.LENGTH_LONG);
        toast.show();

    }
}