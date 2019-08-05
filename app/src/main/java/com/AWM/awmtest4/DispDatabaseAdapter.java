package com.AWM.awmtest4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DispDatabaseAdapter {

    static final String DATABASE_NAME = "database.db";
    String ok="OK";
    static final int DATABASE_VERSION = 1;
    public  static String getIP="";
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table REGISTRO( ID integer primary key autoincrement,NOMBRE  text,IP  text,MACADD text,TOPICMQTT text); ";

    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private static DataBaseHelper dbHelper;

    public  DispDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method to openthe Database
    public  DispDatabaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();        return this;
    }

    // Method to close the Database
    public void close() {
        db.close();
    }

    // method returns an Instance of the Database
    public  SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public String insertEntry(String id,String Nombre,String ip,String macAdd,String topicMQTT) {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("NOMBRE", Nombre);
            newValues.put("IP", ip);
            newValues.put("MACADD", macAdd);
            newValues.put("TOPICMQTT", topicMQTT);
            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result=db.insert("REGISTRO", null, newValues);
            System.out.print(result);
            Toast.makeText(context, "Dispositivo Registrado", Toast.LENGTH_LONG).show();
        }catch(Exception ex) {
            System.out.println("Exceptions " +ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }

    public int deleteEntry(String Nombre) {
        String where="NOMBRE=?";
        int numberOFEntriesDeleted= db.delete("REGISTRO", where, new String[]{Nombre}) ;
        Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    // method to get the ip  of dispositivo
    public String getSinlgeEntry(String Nombre) {

        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("REGISTRO", null, "NOMBRE=?", new String[]{Nombre}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return "NOT EXIST";
        cursor.moveToFirst();
        getIP= cursor.getString(cursor.getColumnIndex("IP"));
        return getIP;
    }

    public void  updateEntry(String Nombre,String IP) {
        //  create object of ContentValues
        ContentValues updatedValues = new ContentValues();
        // Assign values for each Column.
        updatedValues.put("NOMBRE", Nombre);
        updatedValues.put("IP", IP);
        String where="NOMBRE = ?";
        db.update("REGISTRO",updatedValues, where, new String[]{Nombre});
    }
}
