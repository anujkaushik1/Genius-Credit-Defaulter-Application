package com.example.geniuscredit1;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link viewdefaulter1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewdefaulter1 extends Fragment  {

    SearchView searchView;
    TextView searchgst;

    CardView cardviewviewdefaulter;

    CardView nodataavailable;
    Button done;

    boolean wBalance = false;
    static String submittedby;
    static String profileemail;
    static String profilegstnumber;
    String profilebalance;
    int totalamount;


            String fetchedgstnumber,fetchedtradename,fetcheddueamount,fetchedlegalname,fetcheddefaultersubmitteddate,fetchedbalance,fetchedstate,fetchedaddress,fetchednaturebusinessactivity,fetchedlegalcase,fetchedduedate;
    TextView gstnumber,tradename,dueamount,legalname,defaultersubmitteddate,balance,state,address,naturebusinessactivity,legalcase,duedate;

    HashMap<String,Object> data = new HashMap<>();
    int temp=0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public viewdefaulter1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewdefaulter1.
     */
    // TODO: Rename and change types and number of parameters
    public static viewdefaulter1 newInstance(String param1, String param2) {
        viewdefaulter1 fragment = new viewdefaulter1();
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
        return inflater.inflate(R.layout.fragment_viewdefaulter1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView=(SearchView)view.findViewById(R.id.searchviewdefaulter);
        searchgst=(TextView)view.findViewById(R.id.textviewsearchgst);
          cardviewviewdefaulter=(CardView) view.findViewById(R.id.cardviewviewdeffaulter);
            cardviewviewdefaulter.setVisibility(View.GONE);
            gstnumber=(TextView)view.findViewById(R.id.gstnumber);
            tradename =(TextView)view.findViewById(R.id.tradename);
            dueamount=(TextView) view.findViewById(R.id.dueamount);
            legalname=(TextView)view.findViewById(R.id.legalname);
            defaultersubmitteddate=(TextView)view.findViewById(R.id.defaultersubmitteddate);
            balance=(TextView)view.findViewById(R.id.balance);
            state=(TextView)view.findViewById(R.id.state);
            address=(TextView)view.findViewById(R.id.address);
            done=(Button)view.findViewById(R.id.done);
            naturebusinessactivity=(TextView)view.findViewById(R.id.naturebusiness);
            legalcase=(TextView)view.findViewById(R.id.legalcase);
            duedate=(TextView)view.findViewById(R.id.duedate);
            nodataavailable =(CardView)view.findViewById(R.id.cardviewnodataavailable);
            nodataavailable.setVisibility(View.GONE);

        final AlertDialog.Builder alert1 = new AlertDialog.Builder(getActivity());
        View view2 = getLayoutInflater().inflate(R.layout.loadingalertdialog, null);
        alert1.setView(view2);
        final AlertDialog alertDialog1 = alert1.create();
        alertDialog1.show();
        alertDialog1.setCanceledOnTouchOutside(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog1.dismiss();
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
                    profilegstnumber=model.getGSTNumber();
                }
                Log.e("called","data changed called" + wBalance);
                if(submittedby!=null && !wBalance){
                    wBalance = true;
                    walletbalance();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

  public void walletbalance(){

        Query query= FirebaseDatabase.getInstance().getReference().child("Wallet Balance").orderByChild("SubmittedBy").equalTo(submittedby);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    profilebalance = model.getBalance();
                }

                int testingamount = Integer.parseInt(profilebalance);
                try{
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    View view= getLayoutInflater().inflate(R.layout.walletbalancealert,null);

                    Button procced = (Button)view.findViewById(R.id.proceed);
                    TextView cancel = (TextView)view.findViewById(R.id.cancel);
                    alert.setView(view);
                    final AlertDialog alertDialog= alert.create();

                    alertDialog.setCanceledOnTouchOutside(false);
                    if(testingamount<=199){

                        if(getActivity()!=null){
                            alertDialog.show();

                            procced.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(getActivity(),walletbalancerazorpay.class);
                                    intent.putExtra("submittedby",submittedby);
                                    intent.putExtra("gstnumber",profilegstnumber);
                                    startActivity(intent);
                                    alertDialog.dismiss();
                                }
                            });

                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                    Navigation.findNavController(getView()).navigate(R.id.action_viewdefaulter1_to_nav_dashboard);

                                }
                            });

                        }
                        else{

                        }
                    }
                    else{
                        if(alertDialog.isShowing()){
                            System.out.println("yes");
                        }
                        else{
                            System.out.println("no");
                        }
                        alertDialog.dismiss();
                        searchviewdefaulter();
                    }
                } catch (Exception e) {
                   System.out.println(e.getMessage());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



  }



    public void searchviewdefaulter(){


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Query query = FirebaseDatabase.getInstance().getReference().child("Defaulter").orderByChild("GSTNumber").equalTo(s);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                                fetchedgstnumber=model.getGSTNumber();
                                fetchedtradename=model.getTradeName();
                                fetcheddueamount=model.getAmount();
                                fetchedlegalname=model.getLegalName();
                                fetcheddefaultersubmitteddate=model.getDefaulterSubmittedDate();
                                fetchedbalance=model.getAmount();
                                fetchedstate=model.getState();
                                fetchedaddress=model.getAddress();
                                fetchednaturebusinessactivity=model.getNatureBusinessActivity();
                                fetchedlegalcase=model.getLegalCase();
                                fetchedduedate=model.getDueDate();

                            }
                            if(fetchedgstnumber!=null){
                                cardviewviewdefaulter.setVisibility(View.VISIBLE);
                                gstnumber.setText(fetchedgstnumber);
                                tradename.setText(fetchedtradename);
                                dueamount.setText(fetcheddueamount);
                                legalname.setText(fetchedlegalname);
                                defaultersubmitteddate.setText(fetcheddefaultersubmitteddate);
                                balance.setText(fetchedbalance);
                                state.setText(fetchedstate);
                                address.setText(fetchedaddress);
                                naturebusinessactivity.setText(fetchednaturebusinessactivity);
                                legalcase.setText(fetchedlegalcase);
                                duedate.setText(fetchedduedate);

                                done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        wBalance = true;
                                        Log.e("clicked","clicked on done" + wBalance);

                                            totalamount = Integer.parseInt(profilebalance);
                                            totalamount=totalamount-200;
                                            if(totalamount<0){
                                                data.put("Balance","0");
                                                FirebaseDatabase.getInstance().getReference().child("Wallet Balance").child(profilegstnumber).updateChildren(data);

                                            }
                                            else{
                                                data.put("Balance",String.valueOf(totalamount));
                                                FirebaseDatabase.getInstance().getReference().child("Wallet Balance").child(profilegstnumber).updateChildren(data);

                                            }

                                            getActivity().onBackPressed();


                                    }
                                });

                            }
                            else{
                                nodataavailable.setVisibility(View.VISIBLE);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    searchView.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.corner1));
                    searchgst.setTextColor(Color.BLUE);
                    return false;
                }
            });


        }





}