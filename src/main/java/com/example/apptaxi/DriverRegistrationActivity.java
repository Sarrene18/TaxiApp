package com.example.apptaxi;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverRegistrationActivity extends AppCompatActivity {
    private Button DriverSignupButton;
    private Button DriverSigninButton;
    private TextView DriverSignupLink;
    private TextView DriverStatus;
    private EditText DriverEmail;
    private EditText DriverPassword;



    private FirebaseAuth mAuth;
    private DatabaseReference DriverDatabaseRef;
    private String onlineDriverID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        mAuth = FirebaseAuth.getInstance();


        DriverSignupButton = (Button) findViewById(R.id.driver_signup_btn);
        DriverSigninButton = (Button) findViewById(R.id.driver_signin_btn);
        DriverSignupLink = (TextView) findViewById(R.id.signup_driver_link);
        DriverStatus = (TextView) findViewById(R.id.driver_status);
        DriverEmail = (EditText) findViewById(R.id.driver_email);
        DriverPassword = (EditText) findViewById(R.id.driver_password);


        DriverSignupButton.setVisibility(View.INVISIBLE);
        DriverSignupButton.setEnabled(false);

        DriverSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverSigninButton.setVisibility(View.INVISIBLE);
                DriverSignupLink.setVisibility(View.INVISIBLE);
                DriverStatus.setText("Driver Registration");

                DriverSignupButton.setVisibility(View.VISIBLE);
                DriverSignupButton.setEnabled(true);
            }
        });

        DriverSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = DriverEmail.getText().toString();
                String password = DriverPassword.getText().toString();

                signUpDriver(email,password);

            }
        });

        DriverSigninButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = DriverEmail.getText().toString();
                String password = DriverPassword.getText().toString();

                signInDriver(email,password);
            }
        });



    }


    private void signInDriver(String email, String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverRegistrationActivity.this, "Please input Email .....", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverRegistrationActivity.this, "Please input Password .....", Toast.LENGTH_SHORT).show();
        }
        else
         {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(DriverRegistrationActivity.this, "Driver Sign In Successful..", Toast.LENGTH_SHORT).show();

                                Intent driverIntent = new Intent(DriverRegistrationActivity.this, DriverMapActivity.class);
                                startActivity(driverIntent);

                            }
                            else
                            {
                                Toast.makeText(DriverRegistrationActivity.this, "Driver Sign In Unsuccessful..", Toast.LENGTH_SHORT).show();

                            }
                        }
            });
         }

    }


    private void signUpDriver(String email, String password) {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverRegistrationActivity.this, "Please input Email .....", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverRegistrationActivity.this, "Please input Password .....", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                onlineDriverID = mAuth.getCurrentUser().getUid();
                                DriverDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(onlineDriverID);
                                DriverDatabaseRef.setValue(true);

                                Intent driverIntent = new Intent(DriverRegistrationActivity.this, DriverMapActivity.class);
                                startActivity(driverIntent);

                                Toast.makeText(DriverRegistrationActivity.this, "Driver Sign Up Successful..", Toast.LENGTH_SHORT).show();
                            }
                            else
                             {
                                Toast.makeText(DriverRegistrationActivity.this, "Driver Sign Up Unsuccessful..", Toast.LENGTH_SHORT).show();

                             }
                        }
            });
        }


    }
}
