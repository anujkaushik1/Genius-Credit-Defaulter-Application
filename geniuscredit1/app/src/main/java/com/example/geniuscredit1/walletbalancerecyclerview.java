package com.example.geniuscredit1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class walletbalancerecyclerview extends RecyclerView.Adapter<walletbalancerecyclerview.customholder> {

    private Context context;

    public walletbalancerecyclerview(Context context, ArrayList<String> recyclerparticular, ArrayList<String> recyclerbalance, ArrayList<String> recyclersno, ArrayList<String> recyclerdate) {
        this.context = context;
        this.recyclerparticular = recyclerparticular;
        this.recyclerbalance = recyclerbalance;
        this.recyclersno = recyclersno;
        this.recyclerdate = recyclerdate;
    }

    private ArrayList<String> recyclerparticular= new ArrayList<>();
    private ArrayList<String> recyclerbalance = new ArrayList<>();
    private ArrayList<String> recyclersno = new ArrayList<>();
    private ArrayList<String> recyclerdate = new ArrayList<>();



    @NonNull
    @Override
    public customholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.walletbalancerecyclerview,parent,false);
        customholder ch = new customholder(view);
        return ch;
    }

    @Override
    public void onBindViewHolder(@NonNull customholder holder, int position) {
    String setparticular=recyclerparticular.get(position);
        String setbalance=recyclerbalance.get(position);
        String setsno=recyclersno.get(position);
        String setdate=recyclerdate.get(position);

        holder.layoutparticular.setText(setparticular);
        holder.layoutbalance.setText(setbalance);
        holder.layoutsno.setText(setsno);
        holder.layoutdate.setText(setdate);
    }

    @Override
    public int getItemCount() {
        return recyclerparticular.size();
    }

    public class customholder extends RecyclerView.ViewHolder {
        TextView layoutdate,layoutparticular,layoutbalance,layoutsno;

        public customholder(@NonNull View itemView) {
            super(itemView);
            layoutdate=(TextView)itemView.findViewById(R.id.date);
            layoutparticular=(TextView)itemView.findViewById(R.id.particular);
            layoutsno=(TextView)itemView.findViewById(R.id.sno);
            layoutbalance=(TextView)itemView.findViewById(R.id.balance);
        }
    }
}
