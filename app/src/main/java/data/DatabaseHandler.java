package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import com.varun.contactmanager.R;

import java.util.ArrayList;
import java.util.List;

import model.Contact;
import util.Util;

import static util.Util.DATABASE_NAME;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, Util.DATABASE_VERSION);
    }
    //we create our table here
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME+"("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"+Util.KEY_NAME + " TEXT,"
                + Util.KEY_PHONE_NUMBER +" TEXT"+")";
        db.execSQL(CREATE_CONTACT_TABLE);//creating our table
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE = String.valueOf("CROP TABLE IF EXISTS");
        db.execSQL(DROP_TABLE, new String[]{DATABASE_NAME});
        onCreate(db);
    }
    /*
       CRUD= Create,Read,Update,Delete
     */
    //Add contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME,contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());
        //Insert to row
        db.insert(Util.TABLE_NAME,null,values);
        Log.d("add","contact added");
        db.close();
    }
    public Contact getContact(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{ Util.KEY_ID,Util.KEY_NAME,Util.KEY_PHONE_NUMBER},Util.KEY_ID +"=?",new String[]{String.valueOf(id)},null,null,null);
        Contact contact=new Contact();
        if(cursor !=null) {
            cursor.moveToFirst();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));
        }
        return contact;
    }
    public List<Contact> getAllContacts(){
        List<Contact> contactList =new ArrayList<>();
        String selectAll="SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectAll,null);
        if(cursor.moveToFirst()){
            do {
                Contact contact=new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }
    public int updateContact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(Util.KEY_NAME,contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());
        return db.update(Util.TABLE_NAME,values,Util.KEY_ID+"=?",new String[]{ String.valueOf(contact.getId())});
    }
    public void deleteContact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Util.TABLE_NAME,Util.KEY_ID+"=?",new String[]{String.valueOf(contact.getId())});
    }

    public int getCount(){
        String countQuery = "SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);
        return cursor.getCount();
    }
}
