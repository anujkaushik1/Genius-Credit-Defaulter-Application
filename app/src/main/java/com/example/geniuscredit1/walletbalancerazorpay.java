package com.example.geniuscredit1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;

public class walletbalancerazorpay extends AppCompatActivity implements PaymentResultListener {
  int temp=0;
  HashMap<String,Object> data = new HashMap<>();
    String submittedby;
    String walletbalance;
    String profilegstnumber;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walletbalancerazorpay);

        Intent intent= getIntent();
        submittedby=intent.getStringExtra("submittedby");
        profilegstnumber=intent.getStringExtra("gstnumber");

                new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Query query = FirebaseDatabase.getInstance().getReference().child("Wallet Balance").orderByChild("SubmittedBy").equalTo(submittedby);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                            signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                            walletbalance=model.getBalance();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        },1500);
        startpayment();

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

            checkout.open(walletbalancerazorpay.this,options);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast toast = Toast.makeText(walletbalancerazorpay.this,"Payment Successful",Toast.LENGTH_SHORT);
        toast.show();
        amount= Integer.parseInt(walletbalance);
        amount = amount+200;

        data.put("Balance",String.valueOf(amount));
        FirebaseDatabase.getInstance().getReference().child("Wallet Balance").child(profilegstnumber).updateChildren(data);
        super.onBackPressed();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast toast = Toast.makeText(walletbalancerazorpay.this,"Error ! Please try again",Toast.LENGTH_SHORT);
        toast.show();
        super.onBackPressed();
    }

}