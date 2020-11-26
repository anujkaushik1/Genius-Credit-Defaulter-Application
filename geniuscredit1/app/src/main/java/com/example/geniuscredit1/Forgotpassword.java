package com.example.geniuscredit1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Forgotpassword extends AppCompatActivity {
    FirebaseAuth auth;
    EditText editText;
    TextView textView;
    FirebaseAuthSettings firebaseAuthSettings;

    String phonenumber;
    String matchingemail;

    String codesent;
    Query query;


    String fetchedemail;

    String fetchedpassword;

    //OLD AND NEW PASSWORD=
    CardView cardView1;
    TextView textView2;
    TextView textView3;  // ok new and old password
    TextView textView4; //EMAIL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        auth = FirebaseAuth.getInstance();
        editText = (EditText) findViewById(R.id.forgotedittext);
        textView = (TextView) findViewById(R.id.forgotsubmitbutton);





        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Company").orderByChild("Email").equalTo(editText.getText().toString());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                          signupmodel model = dataSnapshot1.getValue(signupmodel.class);
                          fetchedemail = model.getEmail();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if (editText.getText().toString().equals(fetchedemail)) {


                    auth.sendPasswordResetEmail(editText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                          Toast toast = Toast.makeText(Forgotpassword.this,
                                    "Reset Link sent to your Email",
                                    Toast.LENGTH_SHORT);

                            editText.getText().clear();
                            toast.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                         Toast toast = Toast.makeText(Forgotpassword.this,
                                    "Please enter valid email address",
                                    Toast.LENGTH_LONG);
                            editText.getText().clear();

                            toast.show();
                        }
                    });
                }

              else{
                    Toast toast = Toast.makeText(Forgotpassword.this,
                            "Please enter registered Email ",
                            Toast.LENGTH_LONG);

                    toast.show();
                }


            }
        });


    }




}



