package com.example.geniuscredit1;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link reportdefaulterproceed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reportdefaulterproceed extends Fragment {
    EditText amount,description;
    TextView duedate;
    RadioButton yes,no;
    Button done;
    RadioGroup radioGroup;
    static String gstnumber;
    TextView requiredamount,requiredate,requiredescription,requirelegalcase;

    static String submittedby;

    DatePickerDialog.OnDateSetListener listener;
    int day,month,year;
    String date;
    final Calendar cal = Calendar.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public reportdefaulterproceed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reportdefaulterproceed.
     */
    // TODO: Rename and change types and number of parameters
    public static reportdefaulterproceed newInstance(String param1, String param2) {
        reportdefaulterproceed fragment = new reportdefaulterproceed();
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
        return inflater.inflate(R.layout.fragment_reportdefaulterproceed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        amount =(EditText)view.findViewById(R.id.reportdefaulteramount);
        description =(EditText)view.findViewById(R.id.reportdefaulterdescription);
        duedate =(TextView)view.findViewById(R.id.reportdefaulterproceedcalender);
        yes =(RadioButton)view.findViewById(R.id.yes);
        no =(RadioButton)view.findViewById(R.id.no);
        done = (Button)view.findViewById(R.id.reportdefaulterdone);


        requiredamount=(TextView)view.findViewById(R.id.requiredamount);
        requiredate=(TextView)view.findViewById(R.id.requiredate);
        requiredescription=(TextView)view.findViewById(R.id.requiredescription);
        requirelegalcase=(TextView)view.findViewById(R.id.requirelegalcase);
        radioGroup=(RadioGroup)view.findViewById(R.id.radiogroup);

        requiredescription.setVisibility(View.INVISIBLE);
        requiredate.setVisibility(View.INVISIBLE);
        requiredamount.setVisibility(View.INVISIBLE);
        requirelegalcase.setVisibility(View.INVISIBLE);
        enterdate();

        done();
        Bundle bundle=getArguments();
        if(bundle!=null) {
            gstnumber = bundle.getString("gstnumber");

        }
    }

    public void enterdate(){
        duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_DeviceDefault_Light,listener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
                datePickerDialog.show();
            }
        });
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                date = year + "/" + month + "/" + day;
                duedate.setText(date);

            }
        };
    }

    public void done(){
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> anuj = new HashMap<>();
                if(amount.getText().length()>=5){
                    requiredamount.setVisibility(View.INVISIBLE);
                }
                else{
                    requiredamount.setVisibility(View.VISIBLE);
                }
                if(duedate.getText().length()!=0){
                    requiredate.setVisibility(View.INVISIBLE);
                }
                else{
                    requiredate.setVisibility(View.VISIBLE);
                }
                if(description.getText().length()!=0){
                    requiredescription.setVisibility(View.INVISIBLE);
                }
                else{
                    requiredescription.setVisibility(View.VISIBLE);
                }
                if(yes.isChecked() || no.isChecked()){
                    requirelegalcase.setVisibility(View.INVISIBLE);

                }
                else{
                    requirelegalcase.setVisibility(View.VISIBLE);
                }
                if (amount.getText().length() > 4 && duedate.getText().length() != 0 && description.getText().length() != 0 && yes.isChecked() || no.isChecked()) {

                    if (yes.isChecked()){
                        anuj.put("LegalCase","Yes");
                    }
                    else if(no.isChecked()){
                        anuj.put("LegalCase","No");
                    }

                    anuj.put("Amount","₹" + amount.getText().toString());
                    anuj.put("DueDate",duedate.getText().toString());
                    anuj.put("SubmittedBy",submittedby);

                    FirebaseDatabase.getInstance().getReference().child("Incomplete Request").child(submittedby).child(gstnumber).updateChildren(anuj);


                    reportdefaulterproceed2 reportdefaulterproceed2 = new reportdefaulterproceed2();
                    Bundle bundle = new Bundle();
                    bundle.putString("gstnumber",gstnumber);
                    reportdefaulterproceed2.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(getId(),reportdefaulterproceed2).commit();

                   Navigation.findNavController(view).navigate(R.id.action_reportdefaulterproceed_to_reportdefaulterproceed2);

                }

            }
        });

    }

}