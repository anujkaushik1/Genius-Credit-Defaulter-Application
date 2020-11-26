package com.example.geniuscredit1;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link viewdefaulter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewdefaulter extends Fragment {




    static String submittedby;
    String newsubmittedby;

    static String usergstnumber;
    static String userbusinesscategory;

    String fetchedgstnumber,fetchedtradename,fetcheduniqueid,fetcheddueamount,fetcheddefaultersubmitteddate,fetchedbalance,fetchedstatus,fetchedadmincomment;
    String businesscategory;


    String address,city,date,email,legalname,mobilenumber,naturebusiness,state,statejuri,legalcase,duedate;


    CardView nodataavailable;

    RecyclerView recyclerView;

    String curretdate;

    String userbalance;


    int incompleterequest=0;
    int temp=0;

    //ARRAY LISTS=
    ArrayList<String> recyclersubmittedby= new ArrayList<>();
    ArrayList<String> recyclergstnumber = new ArrayList<>();
    ArrayList<String> recyclertradename = new ArrayList<>();
    ArrayList<String> recycleruniqueid = new ArrayList<>();
    ArrayList<String> recyclerdueamount = new ArrayList<>();
    ArrayList<String> recyclerdefaultersubmitteddate = new ArrayList<>();
    ArrayList<String> recyclerbalance = new ArrayList<>();
    ArrayList<String> recyclerstatus = new ArrayList<>();
    ArrayList<String> recycleradmincomment = new ArrayList<>();
    ArrayList<String> recycleraddress = new ArrayList<>();
    ArrayList<String> recyclercity = new ArrayList<>();
    ArrayList<String> recyclerdate  = new ArrayList<>();
    ArrayList<String> recycleremail = new ArrayList<>();
    ArrayList<String> recyclerlegalname = new ArrayList<>();
    ArrayList<String> recyclermobilenumber = new ArrayList<>();
    ArrayList<String> recyclernaturebusiness = new ArrayList<>();
    ArrayList<String> recyclerstate = new ArrayList<>();
    ArrayList<String> recyclerstatejuri = new ArrayList<>();
    ArrayList<String> recyclerlegalcase = new ArrayList<>();
    ArrayList<String> recyclerduedate = new ArrayList<>();
    ArrayList<String> recyclerusergstnumber= new ArrayList<>();
    ArrayList<String> recyclerbusinesscategory=new ArrayList<>();
    ArrayList<String> recycleruserbusinesscategory=new ArrayList<>();
    ArrayList<String> reycleruserbalance= new ArrayList<>();
    ArrayList<String> recyclercurrentdate= new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public viewdefaulter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewdefaulter.
     */
    // TODO: Rename and change types and number of parameters
    public static viewdefaulter newInstance(String param1, String param2) {
        viewdefaulter fragment = new viewdefaulter();
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
        // Inflate the layout for this fragment
        navigationdrawer navigationdrawer =(com.example.geniuscredit1.navigationdrawer)getActivity();
        submittedby = navigationdrawer.getmydata();
        usergstnumber=navigationdrawer.getgstnumber();
        userbusinesscategory=navigationdrawer.getbusinesscategory();

        return inflater.inflate(R.layout.fragment_viewdefaulter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nodataavailable=(CardView) view.findViewById(R.id.cardviewnodataavailable);
        nodataavailable.setVisibility(View.VISIBLE);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        View view1 = getLayoutInflater().inflate(R.layout.loadingalertdialog, null);
        alert.setView(view1);


        final AlertDialog alertDialog = alert.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                try {
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Incomplete Request").child(submittedby);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                                newsubmittedby = model.getSubmittedBy();
                                fetchedgstnumber=model.getGSTNumber();


                                fetchedtradename=model.getTradeName();
                                fetcheduniqueid=model.getUdin();
                                fetcheddueamount=model.getAmount();
                                fetcheddefaultersubmitteddate=model.getDefaulterSubmittedDate();
                                fetchedbalance=model.getAmount();
                                fetchedstatus=model.getStatus();
                                fetchedadmincomment=model.getAdminComment();

                                address=model.getAddress();
                                city=model.getCity();
                                date=model.getDefaulterSubmittedDate();
                                email=model.getEmail();
                                legalname=model.getLegalName();
                                mobilenumber=model.getMobileNumber();
                                naturebusiness=model.getBusinessCategory();
                                state=model.getState();
                                statejuri=model.getStateJurisdiction();
                                legalcase=model.getLegalCase();
                                duedate=model.getDueDate();
                                businesscategory=model.getBusinessCategory();
                                recyclersubmittedby.add(newsubmittedby);
                                recyclerbusinesscategory.add(businesscategory);
                                recyclergstnumber.add(fetchedgstnumber);

                                recyclertradename.add(fetchedtradename);

                                recycleruniqueid.add(fetcheduniqueid);

                                recyclerdueamount.add(fetcheddueamount);

                                recyclerdefaultersubmitteddate.add(fetcheddefaultersubmitteddate);

                                recyclerbalance.add(fetchedbalance);

                                recyclerstatus.add(fetchedstatus);

                                recycleradmincomment.add(fetchedadmincomment);

                                recycleraddress.add(address);

                                recyclercity.add(city);

                                recyclerdate.add(date);

                                recycleremail.add(email);

                                recyclerlegalname.add(legalname);

                                recyclermobilenumber.add(mobilenumber);

                                recyclernaturebusiness.add(naturebusiness);

                                recyclerstate.add(state);

                                recyclerstatejuri.add(statejuri);

                                recyclerlegalcase.add(legalcase);

                                recyclerduedate.add(duedate);

                                recyclerusergstnumber.add(usergstnumber);
                                recycleruserbusinesscategory.add(userbusinesscategory);
                                System.out.println(fetchedstatus);

                            }




                            if(newsubmittedby!=null){
                                nodataavailable.setVisibility(View.GONE);
                                Calendar calendar= Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd MMMM yyyy");
                                curretdate= simpleDateFormat.format(calendar.getTime());
                                recyclercurrentdate.add(curretdate);
                                sendingdatatorecyclerview();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }, 1500);





    }
    public void sendingdatatorecyclerview(){

        System.out.println(usergstnumber);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new incompleterequestrecyclerview(getActivity(),recyclersubmittedby,recyclergstnumber,recyclertradename,recycleruniqueid,recyclerdueamount,recyclerdefaultersubmitteddate,recyclerbalance,recyclerstatus,recycleradmincomment,recycleraddress,recyclercity,recyclerdate,recycleremail,recyclerlegalname,recyclermobilenumber,recyclernaturebusiness,recyclerstate,recyclerstatejuri,recyclerlegalcase,recyclerduedate,recyclerusergstnumber,recyclerbusinesscategory,recycleruserbusinesscategory,recyclercurrentdate));

    }

}