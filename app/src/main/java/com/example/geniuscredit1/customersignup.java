package com.example.geniuscredit1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.util.HashMap;

public class customersignup extends AppCompatActivity {

    TextView tv1,tv2,tv3;
    EditText et1,et2,et3,et4,et5,et6,et7,et8,et9,et10,et11,et12,et13;
    CheckBox checkBox;
    CardView cardViewerror;
    TextView ok;

    //invalid details=

    TextView tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16;

    String aadhaar;
    String legalname;
    String statejurisdiction;
    String naturebuisnessactvity;
    String tradename;
    String state;
    String city;
    String address;
    String businesscategory;
    String email;
    String mobilenumber;
    String password;


    //FETCHED ====
    String fetchedaadhaar;
    String fetchedlegalname;
    String fetchedstatejuri;
    String fetchednaturebusiness;
    String fetchedtradename;
    String fetchedstate;
    String fetchedcity;
    String fetchedaddress;
    String fetchedbusinesscategory;
    String fetchedemail;
    String fetchedmobile;
    String fetchedpassword;

    private FirebaseAuth auth;

    String gstpattern="^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customersignup);
        tv1 = (TextView)findViewById(R.id.loginn);
        tv2 = (TextView)findViewById(R.id.signupp);
        tv3 = (TextView)findViewById(R.id.proceed1);
        et1 =(EditText)findViewById(R.id.aadhaar);
        et2 =(EditText)findViewById(R.id.legalname1);
        et3 =(EditText)findViewById(R.id.statejurisdiction1);
        et4 =(EditText)findViewById(R.id.naturebusiness1);
        et5 =(EditText)findViewById(R.id.tradename1);
        et6 =(EditText)findViewById(R.id.state1);
        et7 =(EditText)findViewById(R.id.city1);
        et8 =(EditText)findViewById(R.id.address1);
        et9 =(EditText)findViewById(R.id.businesscategoty1);
        et10 =(EditText)findViewById(R.id.email1);
        et11 =(EditText)findViewById(R.id.mobilenumber1);
        et12 =(EditText)findViewById(R.id.password1);

        checkBox =(CheckBox)findViewById(R.id.checkbox1);
        cardViewerror= (CardView)findViewById(R.id.cardviewerror1);
        cardViewerror.setVisibility(View.GONE);
        ok = (TextView)findViewById(R.id.ok1);

        //INVALID DETAILS=

        tv4 = (TextView)findViewById(R.id.requiredemail1);
        tv4.setVisibility(View.GONE);
        tv5 = (TextView)findViewById(R.id.requiredpassword1);
        tv5.setVisibility(View.GONE);
        tv6 = (TextView)findViewById(R.id.requiredlegalname1);
        tv6.setVisibility(View.GONE);
        tv7 = (TextView)findViewById(R.id.requiredstatejuris1);
        tv7.setVisibility(View.GONE);
        tv8 = (TextView)findViewById(R.id.requirenature1);
        tv8.setVisibility(View.GONE);
        tv9 = (TextView)findViewById(R.id.requiredtrade1);
        tv9.setVisibility(View.GONE);
        tv10 = (TextView)findViewById(R.id.requiredstate1);
        tv10.setVisibility(View.GONE);
        tv11 = (TextView)findViewById(R.id.requiredcity1);
        tv11.setVisibility(View.GONE);
        tv12 = (TextView)findViewById(R.id.requiredaddress1);
        tv12.setVisibility(View.GONE);
        tv13 = (TextView)findViewById(R.id.requiredbusinesscategory1);
        tv13.setVisibility(View.GONE);
        tv14 = (TextView)findViewById(R.id.requiredmobile1);
        tv14.setVisibility(View.GONE);



        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        auth = FirebaseAuth.getInstance();





        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customersignup.this, signin.class);
                startActivity(intent);
            }
        });


        System.out.println("normal success");
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(checkBox.isChecked()) {
                    cardViewerror.setVisibility(View.GONE);
                    alldetails();
                }
                else{
                    cardViewerror.setVisibility(View.VISIBLE);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cardViewerror.setVisibility(View.GONE);
                        }
                    });
                }

            }
        });




    }
    public void alldetails(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        aadhaar = et1.getText().toString();
        legalname = et2.getText().toString();
        statejurisdiction = et3.getText().toString();
        naturebuisnessactvity = et4.getText().toString();
        tradename = et5.getText().toString();
        state = et6.getText().toString();
        city = et7.getText().toString();
        address = et8.getText().toString();
        businesscategory = et9.getText().toString();
        email = et10.getText().toString();
        mobilenumber = et11.getText().toString();
        password = et12.getText().toString();


        HashMap<String,Object> alldata = new HashMap<>();
        if(aadhaar.length()!=0){
            alldata.put("AadhaarNumber",aadhaar);
        }
        else{
            Toast toast = Toast.makeText(customersignup.this,
                    "Please enter valid Aadhaar number",
                    Toast.LENGTH_LONG);

            toast.show();
        }

        if(legalname.length()!=0) {
            alldata.put("LegalName", legalname);
            tv6.setVisibility(View.GONE);
        }
        else{
            tv6.setVisibility(View.VISIBLE);
        }
        if(statejurisdiction.length()!=0){
            alldata.put("StateJurisdiction",statejurisdiction);
            tv7.setVisibility(View.GONE);

        }
        else{
            tv7.setVisibility(View.VISIBLE);
        }
        if(naturebuisnessactvity.length()!=0) {
            alldata.put("NatureBusinessActivity", naturebuisnessactvity);
            tv8.setVisibility(View.GONE);
        }
        else{
            tv8.setVisibility(View.VISIBLE);
        }
        if(tradename.length()!=0) {
            alldata.put("TradeName", tradename);
            tv9.setVisibility(View.GONE);
        }
        else{
            tv9.setVisibility(View.VISIBLE);
        }
        if(state.length()!=0) {
            alldata.put("State", state);
            tv10.setVisibility(View.GONE);
        }
        else{
            tv10.setVisibility(View.VISIBLE);
        }
        if(city.length()!=0) {
            alldata.put("City", city);
            tv11.setVisibility(View.GONE);
        }
        else{
            tv11.setVisibility(View.VISIBLE);
        }
        if(address.length()!=0) {
            alldata.put("Address", address);
            tv12.setVisibility(View.GONE);
        }
        else{
            tv12.setVisibility(View.VISIBLE);
        }
        if(businesscategory.length()!=0) {
            alldata.put("BusinessCategory", businesscategory);
            tv13.setVisibility(View.GONE);
        }
        else{
            tv13.setVisibility(View.VISIBLE);
        }
        if(email.length()!=0 && email.matches(emailPattern)){
            tv4.setVisibility(View.GONE);
            alldata.put("Email",email);
            emailandpassword();

        }
        else{
            tv4.setVisibility(View.VISIBLE);
        }
        if(mobilenumber.length()!=0){
            alldata.put("MobileNumber",mobilenumber);
            tv14.setVisibility(View.GONE);
        }
        else{
            tv14.setVisibility(View.VISIBLE);
        }
        if(password.length()!=0){
            alldata.put("Password",password);
            tv5.setVisibility(View.GONE);
        }
        else {
            tv5.setVisibility(View.VISIBLE);
        }

        if(aadhaar.length()!=0){
            FirebaseDatabase.getInstance().getReference().child("Customer").child(aadhaar).updateChildren(alldata);
            fetching();

        }
        else{
            Toast toast = Toast.makeText(customersignup.this,
                    "Please enter valid Aadhaar number",
                    Toast.LENGTH_LONG);

            toast.show();
        }
    }

    public void emailandpassword(){
        if(password.length()!=0) {
            auth.createUserWithEmailAndPassword(et10.getText().toString(), et12.getText().toString()).addOnCompleteListener(customersignup.this, new OnCompleteListener<AuthResult>() {
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

    public void fetching(){


        Query query =  FirebaseDatabase.getInstance().getReference().child("Customer").orderByChild("AadhaarNumber").equalTo(aadhaar);

        query.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    customersignupmodel model = dataSnapshot1.getValue(customersignupmodel.class);
                    fetchedaadhaar= model.getAadhaarNumber();
                    fetchedlegalname=  model.getLegalName();
                    fetchedstatejuri= model.getStateJurisdiction();
                    fetchednaturebusiness= model.getNatureBusinessActivity();
                    fetchedtradename= model.getTradeName();
                    fetchedstate= model.getState();
                    fetchedcity= model.getCity();
                    fetchedaddress= model.getAddress();
                    fetchedbusinesscategory=  model.getBusinessCategory();
                    fetchedemail= model.getEmail();
                    fetchedmobile= model.getMobileNumber();
                    fetchedpassword = model.getPassword();

                }
                confirmation();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void confirmation(){
        if(aadhaar.equals(fetchedaadhaar)){
            et2.setText(fetchedlegalname);
            et3.setText(fetchedstatejuri);
            et4.setText(fetchednaturebusiness);
            et5.setText(fetchedtradename);
            et6.setText(fetchedstate);
            et7.setText(fetchedcity);
            et8.setText(fetchedaddress);
            et9.setText(fetchedbusinesscategory);
            et10.setText(fetchedemail);
            et11.setText(mobilenumber);

        }


    }


}




