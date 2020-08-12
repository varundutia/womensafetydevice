package com.example.womensafetydevice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import data.DatabaseHandler;
import model.Contact;

public class ShowActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        listView = findViewById(R.id.listView);
        back= findViewById(R.id.back_main);
        DatabaseHandler db = new DatabaseHandler(ShowActivity.this);
        List<Contact> contacts = db.getAllContacts();
        if (contacts != null) {
            contactArrayList = new ArrayList<String>();
            for (Contact contact : contacts) {
                contactArrayList.add(contact.getName());
                Log.d("contacts", String.valueOf(contact));
            }
            arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    contactArrayList
            );
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("List", contactArrayList.get(i));
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}
