package com.example.geniuscredit1;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class incompleterequestrecyclerview extends RecyclerView.Adapter<incompleterequestrecyclerview.customholder> {

    private Context context;

  private  ArrayList<String> recyclersubmittedby;

    private   ArrayList<String> recyclergstnumber;

    private  ArrayList<String> recyclertradename;

    private   ArrayList<String> recycleruniqueid;

    private   ArrayList<String> recyclerdueamount;

    private  ArrayList<String> recyclerdefaultersubmitteddate;

    private   ArrayList<String> recyclerbalance;

    private  ArrayList<String> recyclerstatus;

    private  ArrayList<String> recycleradmincomment;

    private  ArrayList<String> recycleraddress;

    private   ArrayList<String> recyclercity;

    private  ArrayList<String> recyclerdate;

    private  ArrayList<String> recycleremail;

    private  ArrayList<String> recyclerlegalname;

    private  ArrayList<String> recyclermobilenumber;

    private  ArrayList<String> recyclernaturebusiness;

    private   ArrayList<String> recyclerstate;

    private ArrayList<String> recyclerstatejuri;

    private  ArrayList<String> recyclerlegalcase;

    private  ArrayList<String> recyclerduedate;

    private ArrayList<String> recyclerusergstnumber;

    private ArrayList<String> recyclerbusinesscategory;

    private ArrayList<String> recycleruserbusinesscategory;

    private ArrayList<String> recyclercurrentdate;

    HashMap<String,Object> defaulterdata= new HashMap<>();

    String totalbalance;
    int balance;


    public incompleterequestrecyclerview(Context context, ArrayList<String> recyclersubmittedby, ArrayList<String> recyclergstnumber, ArrayList<String> recyclertradename, ArrayList<String> recycleruniqueid, ArrayList<String> recyclerdueamount, ArrayList<String> recyclerdefaultersubmitteddate, ArrayList<String> recyclerbalance, ArrayList<String> recyclerstatus, ArrayList<String> recycleradmincomment, ArrayList<String> recycleraddress, ArrayList<String> recyclercity, ArrayList<String> recyclerdate, ArrayList<String> recycleremail, ArrayList<String> recyclerlegalname, ArrayList<String> recyclermobilenumber, ArrayList<String> recyclernaturebusiness, ArrayList<String> recyclerstate, ArrayList<String> recyclerstatejuri, ArrayList<String> recyclerlegalcase, ArrayList<String> recyclerduedate, ArrayList<String> recyclerusergstnumber, ArrayList<String> recyclerbusinesscategory, ArrayList<String> recycleruserbusinesscategory, ArrayList<String> recyclercurrentdate) {
        this.context = context;
        this.recyclersubmittedby = recyclersubmittedby;
        this.recyclergstnumber = recyclergstnumber;
        this.recyclertradename = recyclertradename;
        this.recycleruniqueid = recycleruniqueid;
        this.recyclerdueamount = recyclerdueamount;
        this.recyclerdefaultersubmitteddate = recyclerdefaultersubmitteddate;
        this.recyclerbalance = recyclerbalance;
        this.recyclerstatus = recyclerstatus;
        this.recycleradmincomment = recycleradmincomment;
        this.recycleraddress = recycleraddress;
        this.recyclercity = recyclercity;
        this.recyclerdate = recyclerdate;
        this.recycleremail = recycleremail;
        this.recyclerlegalname = recyclerlegalname;
        this.recyclermobilenumber = recyclermobilenumber;
        this.recyclernaturebusiness = recyclernaturebusiness;
        this.recyclerstate = recyclerstate;
        this.recyclerstatejuri = recyclerstatejuri;
        this.recyclerlegalcase = recyclerlegalcase;
        this.recyclerduedate = recyclerduedate;
        this.recyclerusergstnumber=recyclerusergstnumber;
        this.recyclerbusinesscategory=recyclerbusinesscategory;
        this.recycleruserbusinesscategory=recycleruserbusinesscategory;
        this.recyclercurrentdate=recyclercurrentdate;

    }


    @NonNull
    @Override
    public customholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.incompleterequestrecyclerview,parent,false);
        customholder ch = new customholder(v);
        return ch;
    }

    @Override
    public void onBindViewHolder(@NonNull customholder holder, int position) {

        // IN LAYOUT =
        final String setgstnumber = recyclergstnumber.get(position);
        String settradename=recyclertradename.get(position);
        String setuniqueid=recycleruniqueid.get(position);
        String setdueamount=recyclerdueamount.get(position);
        String setdefaulterdate=recyclerdefaultersubmitteddate.get(position);
        String setbalance=recyclerbalance.get(position);
        String setstatus=recyclerstatus.get(position);
        String setadmincomment=recycleradmincomment.get(position);
        final String setsubmittedby=recyclersubmittedby.get(position);
        String setaddress = recycleraddress.get(position);
        String setcity=recyclercity.get(position);
        String setduedate=recyclerduedate.get(position);
        String setemail=recycleremail.get(position);
        String setlegalcase=recyclerlegalcase.get(position);
        final String setlegalname=recyclerlegalname.get(position);
        String setmobilenumber=recyclermobilenumber.get(position);
        String setstate=recyclerstate.get(position);
        final String setusergstumber=recyclerusergstnumber.get(position);
        String setbusinesscategory=recyclerbusinesscategory.get(position);
        final String setuserbusinesscategory = recycleruserbusinesscategory.get(position);
        final String setcurrentdate = recyclercurrentdate.get(position);



        defaulterdata.put("Address",setaddress);
        defaulterdata.put("Amount",setdueamount);
        defaulterdata.put("City",setcity);
        defaulterdata.put("DefaulterSubmittedDate",setdefaulterdate);
        defaulterdata.put("DueDate",setduedate);
        defaulterdata.put("Email",setemail);
        defaulterdata.put("GSTNumber",setgstnumber);
        defaulterdata.put("LegalCase",setlegalcase);
        defaulterdata.put("LegalName",setlegalname);
        defaulterdata.put("MobileNumber",setmobilenumber);
        defaulterdata.put("State",setstate);
        defaulterdata.put("Status",setstatus);
        defaulterdata.put("SubmittedBy",setsubmittedby);
        defaulterdata.put("TradeName",settradename);
        defaulterdata.put("Udin",setuniqueid);
        defaulterdata.put("BusinessCategory",setbusinesscategory);

        if(setstatus.equals("completed") || setstatus.equals("Completed") || setstatus.equals("complete") || setstatus.equals("Complete")){
            FirebaseDatabase.getInstance().getReference().child("Incomplete Request").child(setsubmittedby).child(setgstnumber).removeValue();
            holder.incompletedot.setTextColor(Color.GREEN);



            FirebaseDatabase.getInstance().getReference().child("Defaulter").child(setgstnumber).setValue(defaulterdata);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    HashMap<String,Object> cirdata = new HashMap<>();
                    cirdata.put("SubmittedBy",setsubmittedby);
                    cirdata.put("GSTNumber",setusergstumber);
                    cirdata.put("LegalName",setlegalname);
                    cirdata.put("BusinessCategory",setuserbusinesscategory);

                    FirebaseDatabase.getInstance().getReference().child("CIR").child(setlegalname).setValue(cirdata);
                }
            },2000);

            HashMap<String,Object> approvedbalance= new HashMap<>();
            approvedbalance.put("LegalName",setlegalname);
            approvedbalance.put("SubmittedBy",setsubmittedby);
            approvedbalance.put("RegistrationDate",setcurrentdate);

            FirebaseDatabase.getInstance().getReference().child("Approved Balance").child(setsubmittedby).child(setgstnumber).setValue(approvedbalance);

        }
        else if(setstatus.equals("pending") || setstatus.equals("Pending") || setstatus.equals("Processing") || setstatus.equals("processing")){
            FirebaseDatabase.getInstance().getReference().child("Pending Request").child(setsubmittedby).child(setgstnumber).setValue(defaulterdata);
            System.out.println("HELLO SIR");
        }

        else if(setstatus.equals("Rejected") || setstatus.equals("rejected") || setstatus.equals("reject")){
            FirebaseDatabase.getInstance().getReference().child("Rejected Request").child(setsubmittedby).child(setgstnumber).setValue(defaulterdata);
        }

        //SETTING VALUES=
        holder.gstnumber.setText(setgstnumber);
        holder.tradename.setText(settradename);
        holder.uniqueid.setText(setuniqueid);
        holder.dueamount.setText(setdueamount);
        holder.defaultersubmitteddate.setText(setdefaulterdate);
        holder.balance.setText(setbalance);
        holder.admincomment.setText(setadmincomment);
        holder.status.setText(setstatus);






    }

    @Override
    public int getItemCount() {
        return recyclergstnumber.size();
    }

    public class customholder extends RecyclerView.ViewHolder {
        TextView gstnumber, tradename, uniqueid, dueamount, defaultersubmitteddate, balance, status, admincomment;
        TextView incompletedot;

        public customholder(@NonNull View itemView) {
            super(itemView);
            gstnumber = (TextView) itemView.findViewById(R.id.gstnumber);
            tradename = (TextView) itemView.findViewById(R.id.tradename);
            uniqueid = (TextView) itemView.findViewById(R.id.uniqueid);
            dueamount = (TextView) itemView.findViewById(R.id.dueamount);
            defaultersubmitteddate = (TextView) itemView.findViewById(R.id.defaultersubmitteddate);
            balance = (TextView) itemView.findViewById(R.id.balance);
            status = (TextView) itemView.findViewById(R.id.status);
            admincomment = (TextView) itemView.findViewById(R.id.admincomment);
            incompletedot=(TextView)itemView.findViewById(R.id.incompletedot);
        }
    }
}
