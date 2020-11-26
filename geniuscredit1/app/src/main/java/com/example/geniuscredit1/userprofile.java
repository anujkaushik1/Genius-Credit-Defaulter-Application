package com.example.geniuscredit1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class userprofile extends AppCompatActivity {
    String fetchedlegalname;
    String fetchedstatejuridiction;
    String fetchednaturebusiness;
    String fetchedtradename;
    String fetchedstate;
    String fetchedcity;
    String fetchedaddress;
    String fetchedbusinesscategory;
    String fetchedemail;
    String fetchedmobile;

    String firebasegstnumber;
    TextView profilename;


    static String submittedby;
    static String profileemail;
    TextView legalname,statejuri,naturebusiness,tradename,state,city,address;

    static String fetchedgstnumber;

    CardView cardView;
    TextView requiredemail,requiredmobile;
    TextView email,mobilenumber;
    Button done;
    TextView searchgst;
    String currentdate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        legalname =(TextView)findViewById(R.id.legalname);
        statejuri =(TextView)findViewById(R.id.statejurisdiction);
        naturebusiness =(TextView)findViewById(R.id.naturebusiness);
        tradename =(TextView)findViewById(R.id.tradename);
        state =(TextView)findViewById(R.id.state);
        address=(TextView)findViewById(R.id.address);

        city =(TextView)findViewById(R.id.city);

        mobilenumber =(TextView)findViewById(R.id.reportdefaultermobilenumber);
        email = (TextView)findViewById(R.id.reportdefaulterenail);
        done =(Button)findViewById(R.id.reportdefaulterdone);
        profilename=(TextView)findViewById(R.id.profilename);
       firebasefetching();
    }

    public void firebasefetching(){
        Intent intent=getIntent();
     submittedby=intent.getStringExtra("submittedby");

        Query query= FirebaseDatabase.getInstance().getReference().child("Company").orderByChild("LegalName").equalTo(submittedby);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);

                    fetchedlegalname=model.getLegalName();
                    fetchedstatejuridiction=model.getStateJurisdiction();
                    fetchednaturebusiness=model.getNatureBusinessActivity();
                    fetchedtradename=model.getTradeName();
                    fetchedcity=model.getCity();
                    fetchedstate=model.getState();
                    fetchedaddress=model.getAddress();
                    fetchedemail=model.getEmail();
                    fetchedmobile=model.getMobileNumber();


                }
                profilename.setText(fetchedlegalname);
                legalname.setText(fetchedlegalname);
                statejuri.setText(fetchedstatejuridiction);
                naturebusiness.setText(fetchednaturebusiness);
                tradename.setText(fetchedtradename);
                city.setText(fetchedcity);
                state.setText(fetchedstate);
                address.setText(fetchedaddress);
                email.setText(fetchedemail);
                mobilenumber.setText(fetchedmobile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}