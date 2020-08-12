package com.example.womensafetydevice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import data.DatabaseHandler;
import model.Contact;

public class DeleteContact extends AppCompatActivity {
    private ListView contactList;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);
        contactList=findViewById(R.id.contact_list);
        back=findViewById(R.id.back_1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final DatabaseHandler db=new DatabaseHandler(this);
        final List<Contact> contacts = db.getAllContacts();
        if(contacts!=null) {
            final ArrayList<String> contactArrayList = new ArrayList<String>();
            for (Contact contact : contacts) {
                contactArrayList.add(contact.getName());
            }
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    contactArrayList
            );
            contactList.setAdapter(arrayAdapter);
            contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Contact deleteC = contacts.get(i);
                    db.deleteContact(deleteC);
                    finish();
                }
            });
        }else{
            finish();
        }
    }
}

