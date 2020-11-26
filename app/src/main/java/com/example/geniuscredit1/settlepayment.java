package com.example.geniuscredit1;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settlepayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settlepayment extends Fragment {
    SearchView searchView;
    TextView searchgst;

    CardView cardviewviewdefaulter;

    CardView nodataavailable;

    static String submittedby;
    static String profileemail;
    String newsubittedby;

    String fetchedgstnumber,fetchedtradename,fetcheddueamount,fetchedlegalname,fetcheddefaultersubmitteddate,fetchedstate,fetchedaddress,fetchedlegalcase,fetchedduedate;
    TextView gstnumber,tradename,dueamount,legalname,defaultersubmitteddate,state,address,legalcase,duedate;
    String fetchedcity;
    Button done;
    TextView cancel;
    EditText settlementamount;
    EditText narration;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public settlepayment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settlepayment.
     */
    // TODO: Rename and change types and number of parameters
    public static settlepayment newInstance(String param1, String param2) {
        settlepayment fragment = new settlepayment();
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
        return inflater.inflate(R.layout.fragment_settlepayment, container, false);
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

        state=(TextView)view.findViewById(R.id.state);
        address=(TextView)view.findViewById(R.id.address);

        legalcase=(TextView)view.findViewById(R.id.legalcase);
        duedate=(TextView)view.findViewById(R.id.duedate);
        nodataavailable =(CardView)view.findViewById(R.id.cardviewnodataavailable);
        done=(Button)view.findViewById(R.id.settlementdone);
        cancel=(TextView)view.findViewById(R.id.cancel);
        nodataavailable.setVisibility(View.GONE);
        settlementamount=(EditText)view.findViewById(R.id.settlementamount);
        narration=(EditText)view.findViewById(R.id.narration);

       fetchinglegalname();
    }

    public void fetchinglegalname(){
        Query query = FirebaseDatabase.getInstance().getReference().child("Company").orderByChild("Email").equalTo(profileemail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    submittedby=model.getLegalName();
                }
               searchsettlement();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void searchsettlement(){
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

                            fetchedstate=model.getState();
                            fetchedaddress=model.getAddress();

                            fetchedlegalcase=model.getLegalCase();
                            fetchedduedate=model.getDueDate();
                            fetchedcity=model.getCity();
                            newsubittedby=model.getSubmittedBy();

                        }
                        if(fetchedgstnumber!=null){
                                if (newsubittedby.equals(submittedby)) {
                                    cardviewviewdefaulter.setVisibility(View.VISIBLE);
                                    gstnumber.setText(fetchedgstnumber);
                                    tradename.setText(fetchedtradename);
                                    dueamount.setText(fetcheddueamount);
                                    legalname.setText(fetchedlegalname);
                                    defaultersubmitteddate.setText(fetcheddefaultersubmitteddate);

                                    state.setText(fetchedstate);
                                    address.setText(fetchedaddress);

                                    legalcase.setText(fetchedlegalcase);
                                    duedate.setText(fetchedduedate);
                                    settlement();
                                }
                                else{
                                    Toast toast = Toast.makeText(getActivity(),"You have not reported to this Defaulter",Toast.LENGTH_LONG);
                                    toast.show();
                                }


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

    public void settlement(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardviewviewdefaulter.setVisibility(View.GONE);
                nodataavailable.setVisibility(View.VISIBLE);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(settlementamount.getText().length()!=0){

                    HashMap<String,Object> data = new HashMap<>();
                    data.put("GSTNumber",fetchedgstnumber);
                    data.put("SettlementAmount","₹" + settlementamount.getText().toString());
                    data.put("LegalName",fetchedlegalname);
                    data.put("State",fetchedstate);
                    data.put("City",fetchedcity);
                    data.put("Amount",fetcheddueamount);
                    data.put("DueDate",fetchedduedate);
                    data.put("DefaulterSubmittedDate",fetcheddefaultersubmitteddate);
                    data.put("LegalCase",fetchedlegalcase);
                    data.put("Address",fetchedaddress);
                    data.put("SubmittedBy",newsubittedby);
                    data.put("Narration",narration.getText().toString());
                    try{
                        FirebaseDatabase.getInstance().getReference().child("Settlement").child(submittedby).child(fetchedgstnumber).setValue(data);
                        FirebaseDatabase.getInstance().getReference().child("Defaulter").child(fetchedgstnumber).removeValue();
                        settlementamount.getText().clear();
                        cardviewviewdefaulter.setVisibility(View.GONE);
                        nodataavailable.setVisibility(View.VISIBLE);
                        Toast toast = Toast.makeText(getActivity(),"The Details has been Submitted",Toast.LENGTH_LONG);
                        toast.show();



                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
                else{
                    Toast toast = Toast.makeText(getActivity(),"Please enter Settlement Amount",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}