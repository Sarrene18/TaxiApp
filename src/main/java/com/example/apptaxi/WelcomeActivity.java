package com.example.apptaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private Button WelcomeDriverButton;
    private Button WelcomePassengerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        WelcomeDriverButton = (Button) findViewById(R.id.join_driver_btn);
        WelcomePassengerButton = (Button) findViewById(R.id.join_passenger_btn);

        WelcomePassengerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent LoginRegPassengerIntent = new Intent(WelcomeActivity.this, PassengerRegistrationActivity.class);
                startActivity(LoginRegPassengerIntent);
                
            }
        });

        WelcomeDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginRegDriverIntent = new Intent(WelcomeActivity.this, DriverRegistrationActivity.class);
                startActivity(LoginRegDriverIntent);

            }
        });
    }


}