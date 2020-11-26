package com.example.geniuscredit1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link viewcir#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewcir extends Fragment {

    //PLEASE FIX BUSINESS CATEGORY AND LEGAL CASE THING

    SearchView searchView;
    TextView searchgst;

    CardView cardviewviewdefaulter;

    CardView nodataavailable;

    static String submittedby;

    String fetchedgstnumber, fetchedtradename, fetcheddueamount, fetchedlegalname, fetcheddefaultersubmitteddate, fetchedbalance, fetchedstate, fetchedaddress, fetchednaturebusinessactivity, fetchedlegalcase, fetchedduedate;
    TextView gstnumber, tradename, dueamount, legalname, defaultersubmitteddate, balance, state, address, naturebusinessactivity, legalcase, duedate;
    String fetchedbusinesscategory;
    Button requestreport;
    TextView cancel;

    String cirlegalname, cirgstnumber,cirbusinesscategory;
    ArrayList<String> listcirleglname = new ArrayList<>();
    ArrayList<String> listcirgstnumber = new ArrayList<>();
    ArrayList<String> listbusinesscategory=new ArrayList<>();
    //PDF=
    Bitmap bmp, bmpscaled;
    Bitmap bmpchart,bmpscaledchart;
    int pagewidth = 250;

    String path;

    //STORAGE=
    FirebaseStorage storage;

    String  downloadurl;
    static String useremail;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public viewcir() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewcir.
     */
    // TODO: Rename and change types and number of parameters
    public static viewcir newInstance(String param1, String param2) {
        viewcir fragment = new viewcir();
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
        useremail=navigationdrawer.getemail();
        return inflater.inflate(R.layout.fragment_viewcir, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = (SearchView) view.findViewById(R.id.searchviewdefaulter);
        searchgst = (TextView) view.findViewById(R.id.textviewsearchgst);
        cardviewviewdefaulter = (CardView) view.findViewById(R.id.cardviewviewdeffaulter);
        cardviewviewdefaulter.setVisibility(View.GONE);
        gstnumber = (TextView) view.findViewById(R.id.gstnumber);
        tradename = (TextView) view.findViewById(R.id.tradename);
        dueamount = (TextView) view.findViewById(R.id.dueamount);
        legalname = (TextView) view.findViewById(R.id.legalname);
        defaultersubmitteddate = (TextView) view.findViewById(R.id.defaultersubmitteddate);
        balance = (TextView) view.findViewById(R.id.balance);
        state = (TextView) view.findViewById(R.id.state);
        address = (TextView) view.findViewById(R.id.address);
        naturebusinessactivity = (TextView) view.findViewById(R.id.naturebusiness);
        legalcase = (TextView) view.findViewById(R.id.legalcase);
        duedate = (TextView) view.findViewById(R.id.duedate);
        nodataavailable = (CardView) view.findViewById(R.id.cardviewnodataavailable);
        nodataavailable.setVisibility(View.GONE);
        requestreport = (Button) view.findViewById(R.id.requestreport);
        cancel = (TextView) view.findViewById(R.id.cancel);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.geniuscredit);
        bmpscaled = Bitmap.createScaledBitmap(bmp, 100, 100, false);

        bmpchart = BitmapFactory.decodeResource(getResources(),R.drawable.chart);
        bmpscaledchart = Bitmap.createScaledBitmap(bmpchart, 200, 140, false);

        storage = FirebaseStorage.getInstance();

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
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
               searchdefaulter();
            }
        }, 1500);

    }

    public void searchdefaulter() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Defaulter").orderByChild("GSTNumber").equalTo(s);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                            fetchedgstnumber = model.getGSTNumber();
                            fetchedtradename = model.getTradeName();
                            fetcheddueamount = model.getAmount();
                            fetchedlegalname = model.getLegalName();
                            fetcheddefaultersubmitteddate = model.getDefaulterSubmittedDate();
                            fetchedbalance = model.getAmount();
                            fetchedstate = model.getState();
                            fetchedaddress = model.getAddress();
                            fetchednaturebusinessactivity = model.getNatureBusinessActivity();
                            fetchedlegalcase = model.getLegalCase();
                            fetchedduedate = model.getDueDate();
                            fetchedbusinesscategory=model.getBusinessCategory();

                        }
                        if (fetchedgstnumber != null) {
                            System.out.println(fetchedlegalname);
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
                            getcirdata();

                            requestreport.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    View view1 = getLayoutInflater().inflate(R.layout.paymentcircompleted, null);

                                    Button done = (Button) view1.findViewById(R.id.done);
                                    alert.setView(view1);

                                    final AlertDialog alertDialog = alert.create();
                                    alertDialog.show();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    done.setOnClickListener(new View.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                        @Override
                                        public void onClick(View view) {
                                            alertDialog.dismiss();
                                            cardviewviewdefaulter.setVisibility(View.GONE);
                                            createpdf();
                                        }
                                    });
                                }
                            });

                        } else {
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
                searchView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.corner1));
                searchgst.setTextColor(Color.BLUE);
                return false;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardviewviewdefaulter.setVisibility(View.GONE);
            }
        });
    }

    public void getcirdata() {
        System.out.println(fetchedlegalname);
        Query query = FirebaseDatabase.getInstance().getReference().child("CIR").orderByChild("LegalName").equalTo(fetchedlegalname);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    cirgstnumber=model.getGSTNumber();
                    cirlegalname=model.getSubmittedBy();
                    cirbusinesscategory=model.getBusinessCategory();

                    listcirleglname.add(cirlegalname);
                    listcirgstnumber.add(cirgstnumber);
                  //  listbusinesscategory.add(cirbusinesscategory);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createpdf() {
        PdfDocument mypdfdocument = new PdfDocument();
        Paint mypaint = new Paint();
        Paint titlepaint = new Paint();
        Paint boldtitlepaint = new Paint();

        PdfDocument.PageInfo mypageinfo1 = new PdfDocument.PageInfo.Builder(1200, 1100, 1).create();
        PdfDocument.Page mypage1 = mypdfdocument.startPage(mypageinfo1);
        Canvas canvas = mypage1.getCanvas();

        canvas.drawBitmap(bmpscaled, 0, 0, mypaint);
        canvas.drawBitmap(bmpscaledchart,20,340,mypaint);

        titlepaint.setTextAlign(Paint.Align.RIGHT);
        titlepaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        titlepaint.setTextSize(10f);
        canvas.drawText("Credit Information Report", 200, 120, titlepaint);


        mypaint.setColor(Color.BLACK);
        mypaint.setTextSize(8f);
        mypaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Legal Name" + "         " + fetchedlegalname, 250, 150, mypaint);
        canvas.drawText("Trade Name" + "         " + fetchedtradename, 240, 180, mypaint);
        canvas.drawText("Address" + "                " + fetchedaddress, 275, 210, mypaint);
        canvas.drawText("GST Number" + "         " + fetchedgstnumber, 162, 240, mypaint);
        canvas.drawText("Constitution of Business" + "         " + fetchednaturebusinessactivity, 155, 270, mypaint);

        boldtitlepaint.setColor(Color.BLACK);
        boldtitlepaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        boldtitlepaint.setTextSize(10f);
        canvas.drawText("GENIUS CREDIT SCORE-890", 16, 300, boldtitlepaint);

        canvas.drawText("Genius Credit is summarized in the form of Credit Score which ranges from 300-900", 364, 325, mypaint);

        canvas.drawText("Score Measures", 300, 360, boldtitlepaint);
        canvas.drawText("Score Range", 550, 360, boldtitlepaint);

        canvas.drawText("1." + " " + "Excellent", 352, 380, mypaint);
        canvas.drawText("         " + "2." + " " + "Very Good", 357, 400, mypaint);
        canvas.drawText("  " + "3." + " " + "Good", 337, 420, mypaint);
        canvas.drawText("  " + "4." + " " + "Normal", 342, 440, mypaint);
        canvas.drawText("  " + "5." + " " + "Bad", 327, 460, mypaint);
        canvas.drawText("  " + "6." + " " + "Very Bad", 347, 480, mypaint);


        canvas.drawText("<800>=900", 600, 380, mypaint);
        canvas.drawText("<700>=800", 600, 400, mypaint);
        canvas.drawText("<600>=700", 600, 420, mypaint);
        canvas.drawText("<500>=600", 600, 440, mypaint);
        canvas.drawText("<400>=500", 600, 460, mypaint);
        canvas.drawText("<300>=400", 600, 480, mypaint);

        canvas.drawText("At the time of declaration as defaulter,10 points will be deducted from his credit score.If default continues after 90 days then deduction of 10 points" +
                " will also continue for every 90 days from your credit score", 900, 520, mypaint);

        canvas.drawText("REPORT SUMMARY", 16, 560, boldtitlepaint);

        canvas.drawText("Credit Account Summary", 150, 590, titlepaint);
        canvas.drawText("Current Bal. Amt. Summary", 350, 590, titlepaint);
        canvas.drawText("Defaulter Ageing", 550, 590, titlepaint);
        canvas.drawText("Settle with Creditors ", 750, 590, titlepaint);

        canvas.drawText("Total no. of Account" + "     " + "1", 140, 620, mypaint);
        canvas.drawText("Settled Account" + "     " + "0", 140, 640, mypaint);
        canvas.drawText("Non Settled Defaulter" + "     " + "1", 140, 660, mypaint);
    /*    if(fetchedlegalcase.equals("yes") || fetchedlegalcase.equals("Yes") ){
            canvas.drawText("Legal Issue"+"      "+"1" ,360,700,mypaint);
        }
        else{

            canvas.drawText("Legal Issue"+"      "+"0" ,660,700,mypaint);
        } */
        canvas.drawText("Legal Issue" + "     " + "1", 140, 680, mypaint);

        canvas.drawText("Total Default Amount" + "     " + fetcheddueamount, 340, 620, mypaint);
        canvas.drawText("Settle Amount" + "     " + "1", 340, 640, mypaint);
        canvas.drawText("Write-off amount" + "     " + "0", 340, 660, mypaint);
        canvas.drawText("Current Balance" + "     " + fetcheddueamount, 340, 680, mypaint);


        canvas.drawText("Last 90 days" + "     " + "1", 540, 620, mypaint);
        canvas.drawText("Last 60 days" + "     " + "--", 540, 640, mypaint);
        canvas.drawText("Last 60 days" + "     " + "--", 540, 660, mypaint);
        canvas.drawText("Last 60 days" + "     " + "--", 540, 680, mypaint);

        canvas.drawText("Last 90 days Settlement" + "     " + "--", 770, 620, mypaint);
        canvas.drawText("Last 60 days Settlement" + "     " + "--", 770, 640, mypaint);
        canvas.drawText("Last 60 days Settlement" + "     " + "--", 770, 660, mypaint);
        canvas.drawText("Last 60 days Settlement" + "     " + "--", 770, 680, mypaint);

        canvas.drawText("SUMMARY : CREDIT ACCOUNT INFORMATION", 16, 720, boldtitlepaint);

        canvas.drawText("This section display summary of all your reported credit accounts found in defaulter database.", 410, 745, mypaint);

        mypaint.setStyle(Paint.Style.STROKE);
        mypaint.setTextSize(8f);
        Rect rect = new Rect(20, 780, 1000, 835);
        canvas.drawRect(rect, mypaint);
        mypaint.setTextAlign(Paint.Align.LEFT);
        mypaint.setStyle(Paint.Style.FILL);
        int width = rect.width();
        int height = rect.height();
        System.out.println(width);
        System.out.println(height);
        canvas.drawText("S.No.", 35, 790, mypaint);
        canvas.drawText("Creditor's Name ", 100, 790, mypaint);
        canvas.drawText("Business Category", 210, 790, mypaint);
        canvas.drawText("GST NO.", 315, 790, mypaint);
        canvas.drawText("Due Date", 415, 790, mypaint);
        canvas.drawText("Total Amount", 515, 790, mypaint);
        canvas.drawText("Settle Amount", 615, 790, mypaint);
        canvas.drawText("Write Off", 715, 790, mypaint);
        canvas.drawText("Status in Court", 815, 790, mypaint);
        canvas.drawText("Fully Settled", 915, 790, mypaint);

        canvas.drawLine(60, 780, 60, 835, mypaint);
        canvas.drawLine(210, 780, 210, 835, mypaint);
        canvas.drawLine(310, 780, 310, 835, mypaint);
        canvas.drawLine(400, 780, 400, 835, mypaint);
        canvas.drawLine(510, 780, 510, 835, mypaint);
        canvas.drawLine(600, 780, 600, 835, mypaint);
        canvas.drawLine(685, 780, 685, 835, mypaint);
        canvas.drawLine(785, 780, 785, 835, mypaint);
        canvas.drawLine(900, 780, 900, 835, mypaint);


        canvas.drawLine(20, 800, 1000, 800, mypaint);

        canvas.drawText("1", 35, 810, mypaint);
        canvas.drawText(cirlegalname,60,810,mypaint);
        canvas.drawText(fetchedbusinesscategory,225,810,mypaint);

     //   canvas.drawText(listbusinesscategory.get(0),315,810,mypaint);
        canvas.drawText(cirgstnumber,313,810,mypaint);

     //  canvas.drawText(fetchedduedate,415,810,mypaint);
     //   canvas.drawText(fetcheddueamount,515,810,mypaint);
       canvas.drawText("No",715,810,mypaint);
     //   canvas.drawText(fetchedlegalcase,815,810,mypaint);

        canvas.drawText("DISCLAIMER", 16, 880, boldtitlepaint);
        canvas.drawText("1. Information in CIR is updated prior to 2 days from the CIR generation date . ", 20, 910, mypaint);
        canvas.drawText("2. This section has information based on the details provided to CREDITQ is exactlty same the information provided by our members/subscribers on CREDITQ platform . ", 20, 935, mypaint);

        canvas.drawText("* Signature not required computer generated copy. ", 20, 970, mypaint);

        mypdfdocument.finishPage(mypage1);

         path = Environment.getExternalStorageDirectory().getPath();

        try {
            File file = new File(path, "/cirdocument.pdf");
            System.out.println(path);
            
            mypdfdocument.writeTo(new FileOutputStream(file));

            mypdfdocument.close();
            sendingdatatostorage();


        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }
    public void sendingdatatostorage(){
        Uri uri =Uri.fromFile(new File(path+"/cirdocument.pdf"));
        final StorageReference storageReference = storage.getReference().child("CIR").child(submittedby);

        UploadTask uploadTask = storageReference.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                  downloadurl=uri.toString();
                HashMap<String,Object> data=new HashMap<>();
                data.put("SubmittedBy",submittedby);
                 FirebaseDatabase.getInstance().getReference().child("Total CIR").child(submittedby).child(fetchedgstnumber).setValue(data);
                    sendingemail();



                }
            });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toast = Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

    public void sendingemail() {
        final String username = "geniuscredit123@gmail.com";
        final String password = "anujk2000";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        try {
            Message message1 = new MimeMessage(session);
            message1.setFrom(new InternetAddress(username));
            message1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(useremail));
            message1.setSubject("View CIR Report");
            message1.setText(downloadurl);


            Transport.send(message1);


        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);


        }

    }



}