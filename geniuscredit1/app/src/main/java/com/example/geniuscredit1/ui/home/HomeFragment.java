package com.example.geniuscredit1.ui.home;

import android.app.AlertDialog;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.example.geniuscredit1.R;
import com.example.geniuscredit1.navigationdrawer;
import com.example.geniuscredit1.signupmodel;
import com.example.geniuscredit1.viewdefaulter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class HomeFragment extends Fragment  {
    CardView reportdefaulter, incompleterequest;

    CardView pendingcardview;

    TextView noofincompleterequestdashboard,noofpendingrequestdashoard,noofrejectedrequestdashoard,noofsettlementdashboard;
    TextView noofbalance;
    TextView noofcirdashboard;
    TextView rupeesign;
    ProgressBar progressBar;

    String status;

    CardView viewdefaulter;
    CardView viewcir;

    CardView viewsettlement;
    CardView walletbalance;
    CardView rejectedrequest;
    CardView rqmrequest;

        String currentdate;
    static String profileemail;
    static String submittedby;

    String fetchedgstnumber;
    String updatedgstnumber;

    int temp=0;

    static String hello;

    String noofincompleterequest,noofpendingrequest,noofrejectedrequest,nofsettlemet,noofcir;
    ArrayList<String> noofincompleterequestlist= new ArrayList<>();
    ArrayList<String> noofpendingrequestlist=new ArrayList<>();
    ArrayList<String> noofrejectedrequestlist= new ArrayList<>();
    ArrayList<String > noofsettlementlist=new ArrayList<>();
    ArrayList<String> noofcirlist=new ArrayList<>();

    ArrayList<String> recyclerlegalname = new ArrayList<>();
    String profilegstnumber;
    String legalname;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        navigationdrawer navigationdrawer = (com.example.geniuscredit1.navigationdrawer) getActivity();
       profileemail=navigationdrawer.getemail();


        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reportdefaulter = (CardView) view.findViewById(R.id.reportdefaulter);
        incompleterequest = (CardView) view.findViewById(R.id.incompleterequest);

        pendingcardview = (CardView) view.findViewById(R.id.pendingcardview);
        viewdefaulter = (CardView) view.findViewById(R.id.viewdefaulter);
        viewcir = (CardView) view.findViewById(R.id.viewcir);
        viewsettlement = (CardView) view.findViewById(R.id.viewsettlement);
        walletbalance = (CardView) view.findViewById(R.id.walletbalance);
        rejectedrequest=(CardView)view.findViewById(R.id.rejectedrequest);
        rqmrequest=(CardView)view.findViewById(R.id.rqmrequest);
        noofpendingrequestdashoard=(TextView)view.findViewById(R.id.noofpendingrequest);
        noofrejectedrequestdashoard=(TextView)view.findViewById(R.id.noofrejectedrequest);
        noofsettlementdashboard=(TextView)view.findViewById(R.id.noofviewsettlement);
        noofcirdashboard=(TextView)view.findViewById(R.id.noofcir);
        noofbalance=(TextView)view.findViewById(R.id.noodbalance);
        rupeesign=(TextView)view.findViewById(R.id.rupeesign);

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        View view1 = getLayoutInflater().inflate(R.layout.loadingalertdialog, null);
        alert.setView(view1);

  viewdefaulter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_viewdefaulter1);
      }
  });


        noofincompleterequestdashboard = (TextView) view.findViewById(R.id.noofincompleterequest);




      incompleterequest.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_viewdefaulter);
          }
      });


pendingcardview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_pendingrequest);
    }
});


   viewsettlement.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_settlepayment2);
       }
   });

     viewcir.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_viewcir2);
         }
     });

        rejectedrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_rejectedrequest2);
            }
        });
            reportdefaulter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final AlertDialog alertDialog= alert.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                            Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_reportDefaulterFragment);


                        }
                    },2000);

                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    legalname();
                }
            },1800);

            rqmrequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_rqmrequest2);
                }
            });

            walletbalance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final AlertDialog alertDialog= alert.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                            Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_wallletbalance);
                        }
                    },1500);
                }
            });

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
                  profilegstnumber= model.getGSTNumber();
              }

              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      firebasefetching();
                  }
              },1500);

              firebasenumberfetching();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }

    public void firebasefetching(){
        if(recyclerlegalname.size()!=0){
            recyclerlegalname.clear();
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Approved Balance").child(submittedby);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    legalname=model.getLegalName();


                    recyclerlegalname.add(legalname);

                }
                if(recyclerlegalname.size()!=0){
                    updatingwalletbalance();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void updatingwalletbalance(){

        int amount = recyclerlegalname.size() *1000;
        System.out.println(amount);

        HashMap<String,Object> walletbalancedata = new HashMap<>();
        walletbalancedata.put("Balance",String.valueOf(amount));
        FirebaseDatabase.getInstance().getReference().child("Wallet Balance").child(profilegstnumber).setValue(walletbalancedata);

        rupeesign.setVisibility(View.INVISIBLE);
        noofbalance.setText("₹"+" "+String.valueOf(amount));

    }



    public void firebasenumberfetching(){


        if(noofincompleterequestlist.size()!=0){
            noofincompleterequestlist.clear();
        }
        Query query = FirebaseDatabase.getInstance().getReference().child("Incomplete Request").child(submittedby);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                        noofincompleterequest= model.getGSTNumber();
                        noofincompleterequestlist.add(noofincompleterequest);

                }
                try{
                    noofincompleterequestdashboard.setText(String.valueOf(noofincompleterequestlist.size()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(noofpendingrequestlist.size()!=0){
            noofpendingrequestlist.clear();
        }
        Query query1 = FirebaseDatabase.getInstance().getReference().child("Pending Request").child(submittedby);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    noofpendingrequest=model.getGSTNumber();
                  noofpendingrequestlist.add(noofpendingrequest);

                }

                try{
                    noofpendingrequestdashoard.setText(String.valueOf(noofpendingrequestlist.size()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(noofrejectedrequestlist.size()!=0){
            noofrejectedrequestlist.clear();
        }
        Query query2 = FirebaseDatabase.getInstance().getReference().child("Rejected Request").child(submittedby);
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    noofrejectedrequest=model.getGSTNumber();
                    noofrejectedrequestlist.add(noofrejectedrequest);
                }
              try{
                  noofrejectedrequestdashoard.setText(String.valueOf(noofrejectedrequestlist.size()));
              } catch (Exception e) {
                  System.out.println(e.getMessage());
              }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(noofsettlementlist.size()!=0){
            noofsettlementlist.clear();
        }
        Query query3 = FirebaseDatabase.getInstance().getReference().child("Settlement").child(submittedby);
        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    nofsettlemet=model.getGSTNumber();
                    noofsettlementlist.add(nofsettlemet);
                }
                System.out.println(noofsettlementlist);

            try{
                noofsettlementdashboard.setText(String.valueOf(noofsettlementlist.size()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(noofcirlist.size()!=0){
            noofcirlist.clear();
        }
        Query query4 = FirebaseDatabase.getInstance().getReference().child("Total CIR").child(submittedby);
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                    noofcir=model.getGSTNumber();
                    noofcirlist.add(noofcir);
                }
                System.out.println(noofsettlementlist);

                try{
                    noofcirdashboard.setText(String.valueOf(noofcirlist.size()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    }



    /*   SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd MMMM yyyy");
                currentdate="21 October 2020";
                String cehckoutput = "22 October 2020";


                try {
                    Date datecurrent = simpleDateFormat.parse(currentdate);
                    Date check =simpleDateFormat.parse(cehckoutput);

                    if(datecurrent.compareTo(check)<=0){
                        Toast toast = Toast.makeText(getActivity(),"current date is less than check",Toast.LENGTH_LONG);
                        toast.show();

                    }
                    else if(datecurrent.compareTo(check)>=0){
                        Toast toast = Toast.makeText(getActivity(),"current date is more than check",Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } */


