package com.example.geniuscredit1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link invoices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class invoices extends Fragment {
  static  String profileemail;
   static String submittedby;
String userbalance;
    Bitmap bmp, bmpscaled;
    String gstnumber,tradename,legalname,address;
    String path;
    PDFView pdfView;
    File file;
    CardView nodatavailable;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public invoices() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment invoices.
     */
    // TODO: Rename and change types and number of parameters
    public static invoices newInstance(String param1, String param2) {
        invoices fragment = new invoices();
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
        return inflater.inflate(R.layout.fragment_invoices, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nodatavailable=(CardView)view.findViewById(R.id.cardviewnodataavailable);
        pdfView=(PDFView)view.findViewById(R.id.pdfView);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.geniuscredit);
        bmpscaled = Bitmap.createScaledBitmap(bmp, 100, 100, false);
        ActivityCompat.requestPermissions(getActivity() ,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        nodatavailable.setVisibility(View.VISIBLE);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

       firebasefetching();

    }
    public void firebasefetching(){
        Query query = FirebaseDatabase.getInstance().getReference().child("Company").orderByChild("Email").equalTo(profileemail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    userbalance=model.getPayment();
                    gstnumber= model.getGSTNumber();
                    tradename=model.getTradeName();
                    legalname=model.getLegalName();
                    address=model.getAddress();

                }
                if(userbalance.equals("Yes")){
                    nodatavailable.setVisibility(View.GONE);
                    invoicegenerate();
                }
                else{
                    nodatavailable.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void invoicegenerate(){
        PdfDocument mypdfdocument = new PdfDocument();
        Paint mypaint = new Paint();
        Paint titlepaint = new Paint();
        Paint boldtitlepaint = new Paint();

        PdfDocument.PageInfo mypageinfo1 = new PdfDocument.PageInfo.Builder(1200, 1100, 1).create();
        PdfDocument.Page mypage1 = mypdfdocument.startPage(mypageinfo1);
        Canvas canvas = mypage1.getCanvas();

        canvas.drawBitmap(bmpscaled, 0, 0, mypaint);

        titlepaint.setColor(Color.BLACK);
        titlepaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlepaint.setTextSize(10f);

        canvas.drawText("Tax Invoice", 270, 50, titlepaint);
        canvas.drawText("(Original For Recipent)", 260, 70, titlepaint);

        mypaint.setColor(Color.BLACK);
        mypaint.setTextSize(8f);
        mypaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("GSTIN of Customer :"+ gstnumber, 220, 150, mypaint);
        canvas.drawText("Trade Name :"  + tradename, 240, 180, mypaint);
        canvas.drawText("Legal Name :" + legalname, 240, 210, mypaint);
        canvas.drawText("Address :" + address, 250, 240, mypaint);


        mypaint.setStyle(Paint.Style.STROKE);
        mypaint.setTextSize(8f);
        Rect rect = new Rect(20, 280, 800, 355);
        canvas.drawRect(rect, mypaint);
        mypaint.setTextAlign(Paint.Align.LEFT);
        mypaint.setStyle(Paint.Style.FILL);
        int width = rect.width();
        int height = rect.height();
        canvas.drawText("Description", 35, 290, mypaint);
        canvas.drawText("Amount ", 115, 290, mypaint);
        canvas.drawText("Discount Amount", 160, 290, mypaint);
        canvas.drawText("Taxable Amount", 275, 290, mypaint);
        canvas.drawText("IGST", 365, 290, mypaint);
        canvas.drawText("CGST", 475, 290, mypaint);
        canvas.drawText("SGST/UGST", 565, 290, mypaint);
        canvas.drawText("Total(incl Taxes)", 665, 290, mypaint);

        canvas.drawLine(20, 300, 800, 300, mypaint);

        canvas.drawText("Registration Fee", 25, 310, mypaint);
        canvas.drawText("5000 ", 115, 310, mypaint);
        canvas.drawText("500", 180, 310, mypaint);
       canvas.drawText("4500", 275, 310, mypaint);
        canvas.drawText("Tax = 18% , Amount= 810", 350, 310, mypaint);
        canvas.drawText("- -", 475, 310, mypaint);
        canvas.drawText("- -", 565, 310, mypaint);
        canvas.drawText("5310", 695, 310, mypaint);

        canvas.drawLine(20, 320, 800, 320, mypaint);

        mypaint.setColor(Color.BLACK);
        mypaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mypaint.setTextSize(8f);

        canvas.drawText("Grand Total", 35, 340, mypaint);
      canvas.drawText("5000 ", 115, 340, mypaint);
        canvas.drawText("500", 180, 340, mypaint);
        canvas.drawText("4500", 275, 340, mypaint);
        canvas.drawText(" Amount= 810", 370, 340, mypaint);
        canvas.drawText("- -", 475, 340, mypaint);
        canvas.drawText("- -", 565, 340, mypaint);
        canvas.drawText("5310", 695, 340, mypaint);

        canvas.drawLine(100, 280, 100, 355, mypaint);
        canvas.drawLine(150, 280, 150, 355, mypaint);
        canvas.drawLine(240, 280, 240, 355, mypaint);
        canvas.drawLine(345, 280, 345, 355, mypaint);
        canvas.drawLine(455, 280, 455, 355, mypaint);
        canvas.drawLine(555, 280, 555, 355, mypaint);
        canvas.drawLine(655, 280,  655, 355, mypaint);

        mypaint.setTextSize(10.5f);
        canvas.drawText("This is a computer generated invoice and does not require any signature.", 20, 380, mypaint);


        mypdfdocument.finishPage(mypage1);
        path = Environment.getExternalStorageDirectory().getPath();

        try {
            File file = new File(path, "/invoices.pdf");
            System.out.println(path);

            mypdfdocument.writeTo(new FileOutputStream(file));

            mypdfdocument.close();



        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        fetchinginvoice();


    }
    
    public void fetchinginvoice(){
        file= new File("/storage/emulated/0/invoices.pdf");
        if(file.exists()){

            pdfView.fromFile(file).load();
            nodatavailable.setVisibility(View.GONE);
        }
        else{
            nodatavailable.setVisibility(View.VISIBLE);
        }

    }
}
