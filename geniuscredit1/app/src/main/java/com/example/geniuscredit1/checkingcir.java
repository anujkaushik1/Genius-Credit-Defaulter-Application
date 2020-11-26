package com.example.geniuscredit1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link checkingcir#newInstance} factory method to
 * create an instance of this fragment.
 */
public class checkingcir extends Fragment {


    static String submittedby;

    CardView nodatavailable;
    PDFView pdfView;

    File file;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public checkingcir() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment checkingcir.
     */
    // TODO: Rename and change types and number of parameters
    public static checkingcir newInstance(String param1, String param2) {
        checkingcir fragment = new checkingcir();
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
        submittedby = navigationdrawer.getmydata();
        return inflater.inflate(R.layout.fragment_checkingcir, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nodatavailable=(CardView)view.findViewById(R.id.cardviewnodataavailable);
        pdfView=(PDFView)view.findViewById(R.id.pdfView);
    webview();
    }
    public void webview(){

        file= new File("/storage/emulated/0/cirdocument.pdf");
        if(file.exists()){
            pdfView.fromFile(file).load();
            nodatavailable.setVisibility(View.GONE);
        }
        else{
            nodatavailable.setVisibility(View.VISIBLE);
        }

    }
}