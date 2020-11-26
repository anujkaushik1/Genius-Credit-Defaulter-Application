package com.example.geniuscredit1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geniuscredit1.ui.wallletbalance;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class navigationdrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;

    String fetchedname;
    String fetchedgstnumber;

    String fetchedbusinesscategory;

    FirebaseAuth auth;
    String profileemail;




    List<String> items;
    Map<String,List<String>> submenuitems;

        int noofincompleterequest;


        int temp;
        int updatedtemp=temp-1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        auth=FirebaseAuth.getInstance();


         navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_invoices, R.id.reportdefaulterwebview,R.id.reportDefaulterFragment,R.id.viewdefaulter,R.id.settlepayment2,R.id.settlementhistory,R.id.viewcir2,R.id.checkingcir,R.id.nav_refer,R.id.nav_rate,R.id.nav_Support,R.id.logout,R.id.wallletbalance)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        firebase();

       // navigationView.setItemIconTintList(null);



    }




    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void firebase(){

        Intent intent = getIntent();
         profileemail = intent.getStringExtra("temail");
         System.out.println(profileemail);
        Query query = FirebaseDatabase.getInstance().getReference().child("Company").orderByChild("Email").equalTo(profileemail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                  signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                  fetchedname = model.getLegalName();
                  fetchedgstnumber=model.getGSTNumber();
                  fetchedbusinesscategory=model.getBusinessCategory();
              }
            System.out.println(fetchedname);

              System.out.println("hii=" +fetchedname);
                View view = navigationView.getHeaderView(0); //  View view = navigationView.inflateHeaderView(R.layout.navigationheadermain);
                TextView textView= (TextView)view.findViewById(R.id.hello);
                ImageView imageView =(ImageView)view.findViewById(R.id.imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(navigationdrawer.this,userprofile.class);
                        intent1.putExtra("submittedby",fetchedname);
                        startActivity(intent1);
                    }
                });
                textView.setText(fetchedname);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            System.out.println(databaseError.getMessage());
            }
        });
        filldata();

getgstnumber();
getemail();


    }

    public void filldata(){
        items= new ArrayList<>();
        submenuitems= new HashMap<>();
        items.add("Defaulter");

        List<String> defaulter = new ArrayList<>();
        defaulter.add("Report Defaulter");
        defaulter.add("View Defaulter");

        submenuitems.put(items.get(0),defaulter);

    }

    public String getemail(){
        return profileemail;
    }

    public String getmydata(){
        return fetchedname;

    }
    public String getgstnumber(){
        return fetchedgstnumber;
    }

    public String getbusinesscategory(){
        return fetchedbusinesscategory;
    }





    }




