package com.example.geniuscredit1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup extends AppCompatActivity {

    TextView tv3;
    EditText et1,et2,et3,et4,et5,et6,et7,et8,et9,et10,et11,et12,et13;
    CheckBox checkBox;


    LinearLayout linearLayout;
    Button tv1,tv2;
    //invalid details=

    TextView tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16;



    //FETCHED ====

    String fetchedlegalname;

    String fetchednaturebusiness;
    String fetchedtradename;
    String fetchedstate;
    String fetchedcity;
    String fetchedaddress;
    String fetchedstatejuridiction;

    private FirebaseAuth auth;
    HashMap<String,Object> alldata = new HashMap<>();

    public static final String key = "S9FWEAa0oOgu7pLBEyX2jJwhRdA3";     //FvIc1DZBJhOigWB8eMVGiFRir9w1
    String gstnumber;
    String fetchedgst;

    String gstpattern="^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";
    HashMap<String,Object> walletbalancedata=new HashMap<>();

    AlertDialog.Builder alert1;
    AlertDialog alertDialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tv1 = (Button) findViewById(R.id.loginn);
        tv2 = (Button) findViewById(R.id.signupp);
        tv3 = (TextView)findViewById(R.id.proceed);
        et1 =(EditText)findViewById(R.id.gstnumber);
        et2 =(EditText)findViewById(R.id.legalname);
        et3 =(EditText)findViewById(R.id.statejurisdiction);
        et4 =(EditText)findViewById(R.id.naturebusiness);
        et5 =(EditText)findViewById(R.id.tradename);
        et6 =(EditText)findViewById(R.id.state);
        et7 =(EditText)findViewById(R.id.city);
        et8 =(EditText)findViewById(R.id.address);
        et9 =(EditText)findViewById(R.id.businesscategoty);
        et10 =(EditText)findViewById(R.id.email);
        et11 =(EditText)findViewById(R.id.mobilenumber);
        et12 =(EditText)findViewById(R.id.password);
        gstnumber = et1.getText().toString();
        linearLayout =(LinearLayout)findViewById(R.id.linearlayoutsignup);

        checkBox =(CheckBox)findViewById(R.id.checkbox);


        tv2.setBackgroundColor(Color.WHITE);
        tv2.setTextColor(Color.BLUE);
        //INVALID DETAILS=

        tv4 = (TextView)findViewById(R.id.requiredemail);
        tv4.setVisibility(View.GONE);
        tv5 = (TextView)findViewById(R.id.requiredpassword);
        tv5.setVisibility(View.GONE);
        tv6 = (TextView)findViewById(R.id.requiredlegalname);
        tv6.setVisibility(View.GONE);
        tv7 = (TextView)findViewById(R.id.requiredstatejuris);
        tv7.setVisibility(View.GONE);
        tv8 = (TextView)findViewById(R.id.requirenature);
        tv8.setVisibility(View.GONE);
        tv9 = (TextView)findViewById(R.id.requiredtrade);
        tv9.setVisibility(View.GONE);
        tv10 = (TextView)findViewById(R.id.requiredstate);
        tv10.setVisibility(View.GONE);
        tv11 = (TextView)findViewById(R.id.requiredcity);
        tv11.setVisibility(View.GONE);
        tv12 = (TextView)findViewById(R.id.requiredaddress);
        tv12.setVisibility(View.GONE);
        tv13 = (TextView)findViewById(R.id.requiredbusinesscategory);
        tv13.setVisibility(View.GONE);
        tv14 = (TextView)findViewById(R.id.requiredmobile);
        tv14.setVisibility(View.GONE);
    alert1= new AlertDialog.Builder(signup.this);
        View view2= getLayoutInflater().inflate(R.layout.loadingalertdialog,null);
        alert1.setView(view2);
    alertDialog1=alert1.create();



        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        auth = FirebaseAuth.getInstance();





        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, signin.class);
                startActivity(intent);
            }
        });


        System.out.println("normal success");
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(signup.this);
                View view1 = getLayoutInflater().inflate(R.layout.signupalertdialog,null);

               TextView ok = (TextView)view1.findViewById(R.id.ok);
               alert.setView(view1);




                if(checkBox.isChecked()) {

                  alertDialog1.show();
                  alertDialog1.setCanceledOnTouchOutside(false);


                    gstverification();
                }
                else{

                final AlertDialog alertDialog = alert.create();

                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                }

            }
        });


    }
    public void gstverification(){
        if(et1.getText().toString().length()>=15){
            gstapidetails();
        }
        else{
            tv4.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);
          tv6.setVisibility(View.VISIBLE);
          tv7.setVisibility(View.VISIBLE);
          tv8.setVisibility(View.VISIBLE);
          tv9.setVisibility(View.VISIBLE);
          tv10.setVisibility(View.VISIBLE);
          tv11.setVisibility(View.VISIBLE);
          tv12.setVisibility(View.VISIBLE);
          tv13.setVisibility(View.VISIBLE);
          tv14.setVisibility(View.VISIBLE);


            Toast toast = Toast.makeText(signup.this,
                    "Please enter valid GST number",
                    Toast.LENGTH_LONG);

            toast.show();
        }
    }


    public void gstapidetails(){

        final Call<GSTJson> alldetails = GSTapi.getservice().getdata(et1.getText().toString(),key);
        alldetails.enqueue(new Callback<GSTJson>() {
            @Override
            public void onResponse(Call<GSTJson> call, Response<GSTJson> response) {
                try{
                    if(response.isSuccessful()) {
                       alertDialog1.dismiss();
                        GSTJson gstdata = response.body();
                        TaxpayerInfo taxpayerInfo = gstdata.getTaxpayerInfo();
                        Pradr pradr = taxpayerInfo.getPradr();
                        Addr addr = pradr.getAddr();

                       fetchedlegalname= taxpayerInfo.getLgnm();
                        fetchedstatejuridiction = taxpayerInfo.getStj();

                        fetchednaturebusiness = taxpayerInfo.getCtb();

                        fetchedtradename = taxpayerInfo.getTradeNam();

                        fetchedstate = addr.getStcd();

                        fetchedcity = addr.getDst();

                        fetchedaddress = addr.getBno() + "," + addr.getSt() + "," + addr.getLoc() + "," + addr.getDst() + "," + addr.getPncd();
                        et2.setText(fetchedlegalname);
                        et3.setText(fetchedstatejuridiction);
                        et4.setText(fetchednaturebusiness);
                        et5.setText(fetchedtradename);
                        et6.setText(fetchedstate);
                        et7.setText(fetchedcity);
                        et8.setText(fetchedaddress);
                    alldetails();
                    }


                } catch (Exception e) {
                    Toast toast = Toast.makeText(signup.this,
                            "Please enter valid GST number",
                            Toast.LENGTH_LONG);

                    toast.show();
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GSTJson> call, Throwable t) {
            System.out.println(t.getMessage());

            }
        });


    }


    public void alldetails(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        if(et1.getText().toString().length()!=0){
            alldata.put("GSTNumber",et1.getText().toString());
            Calendar calendar= Calendar.getInstance();
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd MMMM yyyy");
         String  currentdate=simpleDateFormat.format(calendar.getTime());
            alldata.put("RegistrationDate",currentdate);
        }
        else{
            Toast toast = Toast.makeText(signup.this,
                    "Please enter valid GST number",
                    Toast.LENGTH_LONG);

            toast.show();
        }

       if(et2.getText().toString().length()!=0) {
            alldata.put("LegalName", fetchedlegalname);
            tv6.setVisibility(View.GONE);
        }
        else{
            tv6.setVisibility(View.VISIBLE);
        }
        if(et3.getText().toString().length()!=0){
            alldata.put("StateJurisdiction",fetchedstatejuridiction);
            tv7.setVisibility(View.GONE);

        }
        else{
            tv7.setVisibility(View.VISIBLE);
        }
        if(et4.getText().toString().length()!=0) {
            alldata.put("NatureBusinessActivity", fetchednaturebusiness);
            tv8.setVisibility(View.GONE);
        }
        else{
            tv8.setVisibility(View.VISIBLE);
        }
        if(et5.getText().toString().length()!=0) {
            alldata.put("TradeName", fetchedtradename);
            tv9.setVisibility(View.GONE);
        }
        else{
            tv9.setVisibility(View.VISIBLE);
        }
        if(et6.getText().toString().length()!=0) {
            alldata.put("State", fetchedstate);
            tv10.setVisibility(View.GONE);
        }
        else{
            tv10.setVisibility(View.VISIBLE);
        }
        if(et7.getText().toString().length()!=0) {
            alldata.put("City", fetchedcity);
            tv11.setVisibility(View.GONE);
        }
        else{
            tv11.setVisibility(View.VISIBLE);
        }
        if(et8.getText().toString().length()!=0) {
            alldata.put("Address", fetchedaddress);
            tv12.setVisibility(View.GONE);
        }
        else{
            tv12.setVisibility(View.VISIBLE);
        }
        if(et9.getText().toString().length()!=0) {
            alldata.put("BusinessCategory",et9.getText().toString());
            tv13.setVisibility(View.GONE);

        }
        else{
            tv13.setVisibility(View.VISIBLE);
        }
        if(et10.getText().toString().length()!=0 && et10.getText().toString().matches(emailPattern)){
            tv4.setVisibility(View.GONE);
            alldata.put("Email",et10.getText().toString());
            emailandpassword();

        }
        else{
            tv4.setVisibility(View.VISIBLE);
        }
        if(et11.getText().toString().length()>=10){
            alldata.put("MobileNumber",et11.getText().toString());
            tv14.setVisibility(View.GONE);
        }
        else{
            tv14.setVisibility(View.VISIBLE);
        }
        if(et12.getText().toString().length()!=0){
            alldata.put("Password",et12.getText().toString());
            tv5.setVisibility(View.GONE);
        }
        else {
            tv5.setVisibility(View.VISIBLE);
        }
        alldata.put("Balance","0");
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd MMMM yyyy");
        String registrationdate = simpleDateFormat.format(calendar.getTime());

        walletbalancedata.put("Balance","0");
        walletbalancedata.put("SubmittedBy",fetchedlegalname);
        walletbalancedata.put("RegistrationDate",registrationdate);

        firebaseinserting();
        }
    public void firebaseinserting() {
        if (et1.getText().toString().length() != 0 && et2.getText().toString().length() != 0 && et3.getText().toString().length() != 0 && et4.getText().toString().length() != 0 && et5.getText().toString().length() != 0 && et6.getText().toString().length() != 0 && et7.getText().toString().length() != 0 && et8.getText().toString().length() != 0 && et9.getText().toString().length() != 0 && et10.getText().toString().length() != 0 && et11.getText().toString().length() != 0 && et12.getText().toString().length() != 0) {

                FirebaseDatabase.getInstance().getReference().child("Company").child(et1.getText().toString()).updateChildren(alldata);
            FirebaseDatabase.getInstance().getReference().child("Wallet Balance").child(et1.getText().toString()).updateChildren(walletbalancedata);

                Toast toast = Toast.makeText(signup.this,"Details have successfully filled",Toast.LENGTH_SHORT);
                toast.show();

                Intent intent= new Intent(signup.this,signuppayment.class);

                intent.putExtra("gstnumber",et1.getText().toString());
                startActivity(intent);

        }
        else{
            Toast toast = Toast.makeText(signup.this,
                    "Please fill all the details",
                    Toast.LENGTH_LONG);

            toast.show();
        }
    }


    public void emailandpassword(){
        if(et12.getText().toString().length()!=0) {
            auth.createUserWithEmailAndPassword(et10.getText().toString(), et12.getText().toString()).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        System.out.println("yeeeeeee");
                        tv4.setVisibility(View.GONE);
                        tv5.setVisibility(View.GONE);
                    }

                }
            });
        }
        else{
            tv5.setVisibility(View.VISIBLE);
        }


    }


    }


