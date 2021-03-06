package com.example.geniuscredit1;

import android.app.AlertDialog;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pendingrequest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pendingrequest extends Fragment {




    static String submittedby;
    String newsubmittedby;
        static String profileemail;

    String fetchedgstnumber,fetchedtradename,fetcheduniqueid,fetcheddueamount,fetcheddefaultersubmitteddate,fetchedbalance,fetchedstatus,fetchedadmincomment;



    String address,city,date,email,legalname,mobilenumber,naturebusiness,state,statejuri,legalcase,duedate;


    CardView nodataavailable;
    RecyclerView recyclerView;
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




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pendingrequest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pendingrequest.
     */
    // TODO: Rename and change types and number of parameters
    public static pendingrequest newInstance(String param1, String param2) {
        pendingrequest fragment = new pendingrequest();
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
        navigationdrawer navigationdrawer =(com.example.geniuscredit1.navigationdrawer)getActivity();
        profileemail=navigationdrawer.getemail();
        return inflater.inflate(R.layout.fragment_pendingrequest, container, false);
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
                legalname();
            }
        }, 1500);


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
                }
             pendingrequest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
        public void pendingrequest(){
            try {
                DatabaseReference query = FirebaseDatabase.getInstance().getReference().child("Pending Request").child(submittedby);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
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
                            recyclersubmittedby.add(newsubmittedby);

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
                            if(newsubmittedby!=null){
                                nodataavailable.setVisibility(View.GONE);

                                sendingdatatorecyclerview();
                            }


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast toast = Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_LONG);
                        toast.show();
                        System.out.println(databaseError.getMessage());
                    }
                });





            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }

        public void sendingdatatorecyclerview(){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new pendingrecyclerview(getActivity(),recyclersubmittedby,recyclergstnumber,recyclertradename,recycleruniqueid,recyclerdueamount,recyclerdefaultersubmitteddate,recyclerbalance,recyclerstatus,recycleradmincomment,recycleraddress,recyclercity,recyclerdate,recycleremail,recyclerlegalname,recyclermobilenumber,recyclernaturebusiness,recyclerstate,recyclerstatejuri,recyclerlegalcase,recyclerduedate));


        }



}