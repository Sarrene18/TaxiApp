package com.example.apptaxi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PassengerRegistrationActivity extends AppCompatActivity

{
    private Button PassengerSignupButton;
    private Button PassengerSigninButton;
    private TextView PassengerSignupLink;
    private TextView PassengerStatus;
    private EditText PassengerEmail;
    private EditText PassengerPassword;


    private FirebaseAuth mAuth;
    private DatabaseReference PassengerDatabaseRef;
    private String onlinePassengerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_registration);

        mAuth = FirebaseAuth.getInstance();

        PassengerSignupButton = (Button) findViewById(R.id.passenger_signup_btn);
        PassengerSigninButton = (Button) findViewById(R.id.passenger_signin_btn);
        PassengerSignupLink = (TextView) findViewById(R.id.signup_passenger_link);
        PassengerStatus = (TextView) findViewById(R.id.passenger_status);
        PassengerEmail = (EditText) findViewById(R.id.passenger_email);
        PassengerPassword = (EditText) findViewById(R.id.passenger_password);

        PassengerSignupButton.setVisibility(View.INVISIBLE);
        PassengerSignupButton.setEnabled(false);

        PassengerSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                PassengerSigninButton.setVisibility(View.INVISIBLE);
                PassengerSignupLink.setVisibility(View.INVISIBLE);
                PassengerStatus.setText("Passenger Registration");

                PassengerSignupButton.setVisibility(View.VISIBLE);
                PassengerSignupButton.setEnabled(true);
            }
        });

        PassengerSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = PassengerEmail.getText().toString();
                String password = PassengerPassword.getText().toString();
                signUpPassenger(email,password);
            }
        });

        PassengerSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = PassengerEmail.getText().toString();
                String password = PassengerPassword.getText().toString();
                signInPassenger(email,password);
            }
        });
    }
    private void signInPassenger(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(PassengerRegistrationActivity.this, "Please input Email .....", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(PassengerRegistrationActivity.this, "Please input Password .....", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(PassengerRegistrationActivity.this,
                                        "Passenger Sign In Successful..", Toast.LENGTH_SHORT).show();
                                Intent passengerIntent = new Intent(PassengerRegistrationActivity.this,
                                        PassengerMapActivity.class);
                                startActivity(passengerIntent);
                            }
                            else
                            {
                                Toast.makeText(PassengerRegistrationActivity.this,
                                        "Passenger Sign In Unsuccessful..", Toast.LENGTH_SHORT).show();
                            }
                        }
            });
        }
    }
    private void signUpPassenger(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(PassengerRegistrationActivity.this, "Please input Email .....", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(PassengerRegistrationActivity.this, "Please input Password .....", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                onlinePassengerID = mAuth.getCurrentUser().getUid();
                                PassengerDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Passengers")
                                        .child(onlinePassengerID);
                                PassengerDatabaseRef.setValue(true);
                                Intent passengerIntent = new Intent(PassengerRegistrationActivity.this,
                                        PassengerMapActivity.class);
                                startActivity(passengerIntent);
                                Toast.makeText(PassengerRegistrationActivity.this,
                                        "Sign Up Successful..", Toast.LENGTH_SHORT).show();
                            }
                            else
                             {
                                Toast.makeText(PassengerRegistrationActivity.this,
                                        "Sign Up Unsuccessful..", Toast.LENGTH_SHORT).show();
                             }
                        }
            });

        }


    }

}