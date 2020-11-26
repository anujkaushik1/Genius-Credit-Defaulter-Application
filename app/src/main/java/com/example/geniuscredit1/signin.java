package com.example.geniuscredit1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity {
    TextView tv3;
    EditText et1,et2;
    private FirebaseAuth auth;
    TextView tv4;   //FORGOT PASSWORD

    Button tv1,tv2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        tv1 = (Button) findViewById(R.id.login);
        tv2=(Button) findViewById(R.id.signup);
        tv3 = (TextView)findViewById(R.id.loginbutton);

        et1=(EditText)findViewById(R.id.signinemail);
        et2 =(EditText)findViewById(R.id.signinpassword);

        tv4 = (TextView)findViewById(R.id.forgotpassword);

        auth= FirebaseAuth.getInstance();

        tv1.setBackgroundColor(Color.WHITE);
        tv1.setTextColor(Color.BLUE);

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(signin.this,signup.class);
            startActivity(intent);
            }
        });

        fetchingemailandpassword();
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotpassword();
            }
        });

    }

    public void fetchingemailandpassword(){
        tv3.setOnClickListener(new View.OnClickListener() {     //loginbutton
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(signin.this);
                View view1= getLayoutInflater().inflate(R.layout.loadingalertdialog,null);
                alert.setView(view1);
                final AlertDialog alertDialog= alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);

                
                if (et1.getText().length() != 0 && et2.getText().length() != 0){
                    auth.signInWithEmailAndPassword(et1.getText().toString(), et2.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            alertDialog.dismiss();
                            Toast toast = Toast.makeText(signin.this,
                                    "You have logged in",
                                    Toast.LENGTH_SHORT);
                            toast.show();

                            Intent intent = new Intent(signin.this,navigationdrawer.class);
                        intent.putExtra("temail",et1.getText().toString());
                        startActivity(intent);
                        finish();
                        FirebaseAuth.getInstance().getCurrentUser();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alertDialog.dismiss();
                            Toast toast = Toast.makeText(signin.this,
                                    "Wrong email id or password",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    });
                }
                else{
                    alertDialog.dismiss();
                    Toast toast = Toast.makeText(signin.this,
                            "Wrong email id or password",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
            }
        });
    }

    public void forgotpassword(){
        Intent intent = new Intent(signin.this,Forgotpassword.class);
        startActivity(intent);
    }

}