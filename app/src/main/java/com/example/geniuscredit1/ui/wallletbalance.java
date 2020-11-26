package com.example.geniuscredit1.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geniuscredit1.R;
import com.example.geniuscredit1.navigationdrawer;
import com.example.geniuscredit1.signupmodel;
import com.example.geniuscredit1.walletbalancerecyclerview;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link wallletbalance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class wallletbalance extends Fragment {
    CardView nodataavailable;
    RecyclerView recyclerView;
    static String profileemail;
    static String submittedby;
    static String profilegstnumber;

    String firebasedate,firebaseparticular,firebasebalance;

    ArrayList<String> date=new ArrayList<>();
    ArrayList<String> particular=new ArrayList<>();
    ArrayList<String> sno=new ArrayList<>();
    ArrayList<String> balance=new ArrayList<>();

    String legalname;
    String currentdate;
    int amount;
    String updatedbalance;
    int updatedamount;

    ArrayList<String> recyclerlegalname = new ArrayList<>();
    HashMap<String,Object> walletbalancedata = new HashMap<>();



   int checking,checking1;
    int newupdatedamount;
String list;
int finalvalue;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public wallletbalance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment wallletbalance.
     */
    // TODO: Rename and change types and number of parameters
    public static wallletbalance newInstance(String param1, String param2) {
        wallletbalance fragment = new wallletbalance();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navigationdrawer navigationdrawer = (com.example.geniuscredit1.navigationdrawer) getActivity();
        profileemail=navigationdrawer.getemail();
        return inflater.inflate(R.layout.fragment_wallletbalance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        nodataavailable=(CardView)view.findViewById(R.id.cardviewnodataavailable);
legalname();
    }

    public void legalname(){
        try{

        } catch (Exception e) {
            e.printStackTrace();
        }
        Query query = FirebaseDatabase.getInstance().getReference().child("Company").orderByChild("Email").equalTo(profileemail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    submittedby=model.getLegalName();
                    profilegstnumber=model.getGSTNumber();
                }
                if(submittedby!=null){
                    nodataavailable.setVisibility(View.GONE);
                   firebasefetching();
                    System.out.println(profilegstnumber);
                }
                else{

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void firebasefetching(){
        if(particular.size()!=0 ){

            particular.clear();

        }
        if(balance.size()!=0){
            balance.clear();
        }
        if(sno.size()!=0){
            sno.clear();
        }
        if(date.size()!=0){
            date.clear();
        }
        checking1=0;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Approved Balance").child(submittedby);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    legalname=model.getLegalName();
                    currentdate=model.getRegistrationDate();

                    particular.add(legalname);
                    amount = particular.size()*1000;
                    balance.add(String.valueOf(amount));
                    sno.add(String.valueOf(particular.size()));
                    date.add(currentdate);



                }
                if(legalname!=null){

                    sendingdatatorecyclerview();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void sendingdatatorecyclerview(){

        if(newupdatedamount==0){
       /*     walletbalancedata.put("Balance",String.valueOf(amount));
            walletbalancedata.put("SubmittedBy",submittedby);
            FirebaseDatabase.getInstance().getReference().child("Wallet Balance").child(profilegstnumber).setValue(walletbalancedata);  */
        }
        else{

          /*  walletbalancedata.put("Balance",String.valueOf(newupdatedamount));
            walletbalancedata.put("SubmittedBy",submittedby);
            FirebaseDatabase.getInstance().getReference().child("Wallet Balance").child(profilegstnumber).setValue(walletbalancedata);  */
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new walletbalancerecyclerview(getActivity(),particular,balance,sno,date));
    }

}