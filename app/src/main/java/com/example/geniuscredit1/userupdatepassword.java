package com.example.geniuscredit1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userupdatepassword extends AppCompatActivity {

    EditText oldpassword,newpassword,confirmpassword;
    Button updatepassword;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userupdatepassword);
        oldpassword=(EditText)findViewById(R.id.oldpassword);
        newpassword=(EditText)findViewById(R.id.newpassword);
        confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        updatepassword=(Button)findViewById(R.id.updatepassword);
        auth=FirebaseAuth.getInstance();
        updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changepassword();
            }
        });


    }
    public void changepassword(){
        if(oldpassword.length()==0 && newpassword.length()==0&& confirmpassword.length()==0){
            Toast toast = Toast.makeText(userupdatepassword.this,"Please fill all the fields",Toast.LENGTH_LONG);
            toast.show();

        }
        else{
            if(newpassword.getText().toString().equals(confirmpassword.getText().toString()) && oldpassword.length()!=0 && newpassword.length()!=0&& confirmpassword.length()!=0 ){

                final FirebaseUser user= auth.getCurrentUser();
                user.updatePassword(newpassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast toast = Toast.makeText(userupdatepassword.this,"Password has been changed",Toast.LENGTH_LONG);
                        toast.show();
                        userupdatepassword.super.onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                     Toast toast = Toast.makeText(userupdatepassword.this,"Please enter correct old password",Toast.LENGTH_LONG);
                     toast.show();
                    }
                });

            }
            else{
                Toast toast = Toast.makeText(userupdatepassword.this,"New password and confirm password must be same",Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}