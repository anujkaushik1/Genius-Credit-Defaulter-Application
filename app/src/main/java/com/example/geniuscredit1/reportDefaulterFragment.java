package com.example.geniuscredit1;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link reportDefaulterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reportDefaulterFragment extends Fragment {
    SearchView searchView;
    String fetchedlegalname;
    String fetchedstatejuridiction;
    String fetchednaturebusiness;
    String fetchedtradename;
    String fetchedstate;
    String fetchedcity;
    String fetchedaddress;
    String fetchedbusinesscategory;

    String firebasegstnumber;

    static String submittedby;
    static String profileemail;

    public static final String key = "FvIc1DZBJhOigWB8eMVGiFRir9w1";


    TextView legalname, statejuri, naturebusiness, tradename, state, city, address;

    static String fetchedgstnumber;

    CardView cardView;
    TextView requiredemail, requiredmobile;
    EditText email, mobilenumber;
    Button done;
    TextView searchgst;
    String currentdate;

    int time = 1;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public reportDefaulterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reportDefaulterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static reportDefaulterFragment newInstance(String param1, String param2) {
        reportDefaulterFragment fragment = new reportDefaulterFragment();
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

        profileemail = navigationdrawer.getemail();


        return inflater.inflate(R.layout.fragment_report_defaulter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = (SearchView) view.findViewById(R.id.searchreportdefaulter);
        legalname = (TextView) view.findViewById(R.id.legalname);
        statejuri = (TextView) view.findViewById(R.id.statejurisdiction);
        naturebusiness = (TextView) view.findViewById(R.id.naturebusiness);
        tradename = (TextView) view.findViewById(R.id.tradename);
        state = (TextView) view.findViewById(R.id.state);
        address = (TextView) view.findViewById(R.id.address);
        cardView = (CardView) view.findViewById(R.id.cardviewreportdeffaulter);
        city = (TextView) view.findViewById(R.id.city);
        requiredemail = (TextView) view.findViewById(R.id.requiredemail);
        requiredmobile = (TextView) view.findViewById(R.id.requiredmobile);
        mobilenumber = (EditText) view.findViewById(R.id.reportdefaultermobilenumber);
        email = (EditText) view.findViewById(R.id.reportdefaulterenail);
        done = (Button) view.findViewById(R.id.reportdefaulterdone);
        searchgst = (TextView) view.findViewById(R.id.textviewsearchgst);



        cardView.setVisibility(View.GONE);

        alertdialog();


    }

    public void alertdialog() {

        try {
            Query query = FirebaseDatabase.getInstance().getReference().child("Company").orderByChild("Email").equalTo(profileemail);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                        currentdate = model.getRegistrationDate();
                        submittedby = model.getLegalName();
                    }
                    System.out.println(currentdate);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
                    calendar.add(Calendar.DAY_OF_MONTH, 2);
                    String newdate = simpleDateFormat.format(calendar.getTime());
                    try {
                        Date checkcurrentdate = simpleDateFormat.parse(currentdate);
                        Date checknewcurrentdate = simpleDateFormat.parse(newdate);

                        if (checkcurrentdate.compareTo(checknewcurrentdate) < 0) {
                            gstapidetails();
                        } else if (checkcurrentdate.compareTo(checknewcurrentdate) > 0) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            View view = getLayoutInflater().inflate(R.layout.days30dialogbox, null);

                            Button button = (Button) view.findViewById(R.id.done);
                            alert.setView(view);
                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Navigation.findNavController(view).navigate(R.id.action_reportDefaulterFragment_to_nav_dashboard);

                                }
                            });


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void gstapidetails() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {


                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.loadingalertdialog, null);
                alert.setView(view);

                final AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);


                final Call<GSTJson> alldetails = GSTapi.getservice().getdata(s, key);
                alldetails.enqueue(new Callback<GSTJson>() {
                    @Override
                    public void onResponse(Call<GSTJson> call, Response<GSTJson> response) {
                        try {
                            if (response.isSuccessful()) {
                                alertDialog.dismiss();
                                GSTJson gstdata = response.body();
                                TaxpayerInfo taxpayerInfo = gstdata.getTaxpayerInfo();
                                Pradr pradr = taxpayerInfo.getPradr();
                                Addr addr = pradr.getAddr();

                                fetchedbusinesscategory = taxpayerInfo.getNba().get(1);
                                fetchedgstnumber = taxpayerInfo.getGstin();
                                fetchedlegalname = taxpayerInfo.getLgnm();
                                fetchedstatejuridiction = taxpayerInfo.getStj();

                                fetchednaturebusiness = taxpayerInfo.getCtb();

                                fetchedtradename = taxpayerInfo.getTradeNam();

                                fetchedstate = addr.getStcd();

                                fetchedcity = addr.getDst();

                                fetchedaddress = addr.getBno() + "," + addr.getSt() + "," + addr.getLoc() + "," + addr.getDst() + "," + addr.getPncd();
                                cardView.setVisibility(View.VISIBLE);
                                legalname.setText(fetchedlegalname);
                                statejuri.setText(fetchedstatejuridiction);
                                naturebusiness.setText(fetchednaturebusiness);
                                tradename.setText(fetchedtradename);
                                state.setText(fetchedstate);
                                city.setText(fetchedcity);
                                address.setText(fetchedaddress);
                                emailandpassword();
                                firebasegstnumber = s;
                            }


                        } catch (Exception e) {
                            Toast toast = Toast.makeText(getActivity(),
                                    "Please enter valid GST number",
                                    Toast.LENGTH_LONG);

                            toast.show();
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GSTJson> call, Throwable t) {
                        System.out.println(t.getMessage());

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

    }



    public void emailandpassword() {


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (email.getText().length() != 0 && mobilenumber.getText().length() == 10) {

                    reportdefaulterproceed reportdefaulterproceed = new reportdefaulterproceed();
                    Bundle bundle = new Bundle();
                    bundle.putString("gstnumber", fetchedgstnumber);

                    reportdefaulterproceed.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(getId(), reportdefaulterproceed).commit();
                    final HashMap<String, Object> anuj = new HashMap<>();

                    anuj.put("GSTNumber", fetchedgstnumber);
                    anuj.put("Status", "Incomplete");
                    anuj.put("LegalName", fetchedlegalname);
                    anuj.put("StateJuridiction", fetchedstatejuridiction);
                    anuj.put("NatureBusinessActivity", fetchednaturebusiness);
                    anuj.put("TradeName", fetchedtradename);
                    anuj.put("State", fetchedstate);
                    anuj.put("City", fetchedcity);
                    anuj.put("Address", fetchedaddress);
                    anuj.put("Email", email.getText().toString());
                    anuj.put("MobileNumber", mobilenumber.getText().toString());
                    anuj.put("AdminComment", "");
                    anuj.put("SubmittedBy", submittedby);
                    anuj.put("BusinessCategory", fetchedbusinesscategory);
                    System.out.println(fetchedbusinesscategory);
                    anuj.put("NatureBusinessActivity", fetchednaturebusiness);
                    FirebaseDatabase.getInstance().getReference().child("Incomplete Request").child(submittedby).child(fetchedgstnumber).setValue(anuj);
                    requiredemail.setVisibility(View.GONE);
                    requiredmobile.setVisibility(View.GONE);
                    Navigation.findNavController(view).navigate(R.id.action_reportDefaulterFragment_to_reportdefaulterproceed2);

                }

            }

        });
    }


}


