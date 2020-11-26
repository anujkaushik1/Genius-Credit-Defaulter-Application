package com.example.geniuscredit1;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link reportdefaulterproceed2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reportdefaulterproceed2 extends Fragment {

        EditText udin;
        Button uploadledger,uploadcacertificate;
        TextView caertificateformat;
        CheckBox checkbox;
        Button submit;
        TextView cancel;

    static String gstnumber;
    static String submittedby;

        TextView requireudin,requireledger,requirecaertificate;

        Uri pdfuri;  //url to store urls for local storage    >> CA CERTFICATE
        Uri pdfuri1;  // >> LEDGER


    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public reportdefaulterproceed2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reportdefaulterproceed2.
     */
    // TODO: Rename and change types and number of parameters
    public static reportdefaulterproceed2 newInstance(String param1, String param2) {
        reportdefaulterproceed2 fragment = new reportdefaulterproceed2();
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
        navigationdrawer navigationdrawer= (com.example.geniuscredit1.navigationdrawer)getActivity();
        submittedby=navigationdrawer.getmydata();
        return inflater.inflate(R.layout.fragment_reportdefaulterproceed2, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        udin = (EditText)view.findViewById(R.id.udin);
      uploadledger=(Button)view.findViewById(R.id.uploadledger);
      uploadcacertificate=(Button)view.findViewById(R.id.uploadcacertficate);
      caertificateformat=(TextView)view.findViewById(R.id.cacertficateformat);
      checkbox=(CheckBox)view.findViewById(R.id.checkbox);
      submit=(Button)view.findViewById(R.id.summit);
      cancel=(TextView)view.findViewById(R.id.cancel);

      requirecaertificate=(TextView)view.findViewById(R.id.requirecacertificate);
      requireledger=(TextView)view.findViewById(R.id.requireledger);
      requireudin=(TextView)view.findViewById(R.id.requireudin);

      requireudin.setVisibility(View.INVISIBLE);
      requireledger.setVisibility(View.INVISIBLE);
      requirecaertificate.setVisibility(View.INVISIBLE);

      storage = FirebaseStorage.getInstance();
      database=FirebaseDatabase.getInstance();


   submit.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
           View view1= getLayoutInflater().inflate(R.layout.signupalertdialog,null);
           TextView ok = (TextView)view1.findViewById(R.id.ok);
           alert.setView(view1);
           if(checkbox.isChecked()){
                   if (udin.getText().length() != 0) {
                       requireudin.setVisibility(View.INVISIBLE);
                   } else {
                       requireudin.setVisibility(View.VISIBLE);
                   }
                   if(pdfuri!=null){
                       requirecaertificate.setVisibility(View.INVISIBLE);
                   }
                   else{
                       requirecaertificate.setVisibility(View.VISIBLE);
                   }
                   if(pdfuri1!=null){
                       requireledger.setVisibility(View.INVISIBLE);
                   }
                   else{
                       requireledger.setVisibility(View.VISIBLE);
                   }

                   if(udin.getText().length()!=0 && pdfuri!=null && pdfuri1!=null) {
                            upfloadfilecacacertificate(pdfuri);
                            uploadfileledger(pdfuri1);
                                nextfragement();
                        }
           }
           else{
            final AlertDialog alertDialog= alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
           }
       }
   });
   uploadcacertificate.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           permissionstorage();
       }
   });
   uploadledger.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           permissionstorage();
       }
   });

        Bundle bundle=getArguments();
        if(bundle!=null) {
            gstnumber = bundle.getString("gstnumber");

        }

   cacertificateformat();

    }
    public  void cacertificateformat(){
        caertificateformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),reportdefaulterwebview.class);
                startActivity(intent);
            }
        });
    }


  public void permissionstorage(){

            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
               selectpdf();
            }
            else{
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
            }



  }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast toast = Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG);
            toast.show();
            selectpdf();
        }
        else{
            Toast toast = Toast.makeText(getActivity(),"Permission is Denied!",Toast.LENGTH_LONG);
            toast.show();
        }

    }


    public void selectpdf(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(intent,86);
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {    //file selected hui ya nai
        if(requestCode==86 && data!=null){
        pdfuri=data.getData();   //return uri of selectedfile
                if(pdfuri!=null){
                    Toast toast = Toast.makeText(getActivity(),"File is Selected",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getActivity(),"Please Select a CA Certificate",Toast.LENGTH_LONG);
                    toast.show();
                }
        pdfuri1=data.getData();
                if(pdfuri1!=null){
                    Toast toast = Toast.makeText(getActivity(),"File is Selected",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getActivity(),"Please Select a Ledger",Toast.LENGTH_LONG);
                    toast.show();
                }
        }
        else{
         Toast toast = Toast.makeText(getActivity(),"Please Select a File",Toast.LENGTH_LONG);
         toast.show();
        }

    }

        public  void upfloadfilecacacertificate(Uri pdfuri){

            if(pdfuri!=null){


                StorageReference storageReference = storage.getReference();
                storageReference.child("CA Certficate").child(udin.getText().toString()).putFile(pdfuri).
                        addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                requirecaertificate.setVisibility(View.INVISIBLE);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast = Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG);
                        toast.show();
                        requirecaertificate.setVisibility(View.VISIBLE);

                    }
                });
                requirecaertificate.setVisibility(View.INVISIBLE);
            }
            else{
                Toast toast = Toast.makeText(getActivity(),"Please Select a File",Toast.LENGTH_LONG);
                toast.show();
                requirecaertificate.setVisibility(View.VISIBLE);
            }

        }

        public void uploadfileledger(Uri pdfuri1){
            if(pdfuri1!=null){


                StorageReference storageReference = storage.getReference();
                storageReference.child("Ledger").child(udin.getText().toString()).putFile(pdfuri1).
                        addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                requireledger.setVisibility(View.INVISIBLE);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast = Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG);
                        toast.show();
                        requireledger.setVisibility(View.VISIBLE);

                    }
                });
                requireledger.setVisibility(View.INVISIBLE);
            }
            else{
                Toast toast = Toast.makeText(getActivity(),"Please Select a File",Toast.LENGTH_LONG);
                toast.show();
                requireledger.setVisibility(View.VISIBLE);
            }

        }



        public void nextfragement(){
        String currentdate;
        final Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            currentdate=simpleDateFormat.format(cal.getTime());


            HashMap<String,Object> anuj = new HashMap<>();
            anuj.put("Udin",udin.getText().toString());
            anuj.put("DefaulterSubmittedDate",currentdate);

            FirebaseDatabase.getInstance().getReference().child("Incomplete Request").child(submittedby).child(gstnumber).updateChildren(anuj);

        Toast toast = Toast.makeText(getActivity(),"The Details has been submitted",Toast.LENGTH_LONG);
        toast.show();
            Navigation.findNavController(getView()).navigate(R.id.action_reportdefaulterproceed2_to_viewdefaulter);


        }

    }

