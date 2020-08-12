package com.example.womensafetydevice;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import android.telephony.SmsManager;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

import data.DatabaseHandler;
import data.GPSTracker;
import model.Contact;
public class MonitoringScreen2 extends Activity{
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;
    private String message;
    private String[] phoneNos;
    final int SEND_SMS_PERMISSION_REQUEST_CODE =1 ;
    private PackageManager MockPackageManager;
    private ArrayList<String> contactArrayList;
    private List<Contact> contactList;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_screen2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DatabaseHandler db = new DatabaseHandler(MonitoringScreen2.this);
        if (checkPermission(Manifest.permission.SEND_SMS)) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        List<Contact> contacts = db.getAllContacts();
        phoneNos = new String[contacts.size()];
            for (int i = 0; i < contacts.size(); i++) {
                phoneNos[i] = contacts.get(i).getPhoneNumber().toString();
            }
            gps = new GPSTracker(MonitoringScreen2.this);
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                message = "LAT:" + String.valueOf(latitude) + " LONG:" + String.valueOf(longitude);
                if (checkPermission(Manifest.permission.SEND_SMS)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    for(int i=0;i<phoneNos.length;i++){
                        smsManager.sendTextMessage(phoneNos[i].toString(), null, message, null, null);
                        try {
                            //set time in mili
                            Thread.sleep(3000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(this, "sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "NOPE", Toast.LENGTH_LONG).show();

                }

            } else {
                gps.showSettingsAlert();
            }

        }
    public boolean checkPermission(String permission){
        int check =ContextCompat.checkSelfPermission(this,permission);
        return (check==PackageManager.PERMISSION_GRANTED);
    }
}
