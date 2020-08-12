package com.example.womensafetydevice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Contact;

public class AddContact extends AppCompatActivity implements View.OnClickListener {
    private EditText nameBox;
    private EditText phoneBox;
    private Button send;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        nameBox=findViewById(R.id.enter_name);
        phoneBox=findViewById(R.id.enter_phone);
        send=findViewById(R.id.send);
        back=findViewById(R.id.back);
        send.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send:
                final DatabaseHandler db=new DatabaseHandler(this);
                String name=nameBox.getText().toString();
                String phone=phoneBox.getText().toString();
                Contact contact=new Contact();
                contact.setName(name);
                contact.setPhoneNumber(phone);
                db.addContact(contact);
                Toast.makeText(AddContact.this,"Contact is added",Toast.LENGTH_SHORT)
                        .show();
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
