package com.example.womensafetydevice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button continueMain;
    private Button addButton;
    private Button delButton;
//    private Button continueButton;
    private Button showContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        continueMain=findViewById(R.id.main_continue);
        addButton=findViewById(R.id.add_contact);
        delButton=findViewById(R.id.delete_contact);
//        continueButton=findViewById(R.id.continue_button);
        showContact=findViewById(R.id.show_contact);
        continueMain.setOnClickListener(this);
        addButton.setOnClickListener(this);
        delButton.setOnClickListener(this);
//        continueButton.setOnClickListener(this);
        showContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_continue:
                Intent i = new Intent(this, BTActivityMain.class);
                startActivity(i);
                break;
            case R.id.add_contact:
                Intent intentA = new Intent(getBaseContext(),AddContact.class);
                startActivity(intentA);
                break;
            case R.id.delete_contact:
                Intent intentD =new Intent(getBaseContext(),DeleteContact.class);
                startActivity(intentD);
                break;
            case R.id.show_contact:
                Intent intentS = new Intent(getBaseContext(),ShowActivity.class);
                startActivity(intentS);
                break;
        }
    }
}
