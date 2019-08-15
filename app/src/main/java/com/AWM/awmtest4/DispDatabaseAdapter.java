package com.AWM.awmtest4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DispDatabaseAdapter {

    static final String DATABASE_NAME = "database.db";
    String ok="OK";
    static final int DATABASE_VERSION = 1;
    public  static String getIP="";
    public  static String getCorreo="";
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table REGISTRO( ID integer primary key autoincrement,NOMBRE  text,IP  text,MACADD text,TOPICMQTT text,IDCONFIGURACION text); ";
    static final String DATABASE_CREATE_Usuario = "create table REGISTRO_USUARIO( NOMBRE  text,ID_USUARIO text primary key, CORREO text,CONTRASENA text,TELEFONO text,MISDISPOSITIVOS text); ";

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

    // Metodo para cerrar la base de datos
    public void close() {
        db.close();
    }

    // method returns an Instance of the Database
    public  SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    /*Metodo para insertar data en la tabla Dispositivo*/

    public String insertEntry(String id, String Nombre, String ip, String macAdd, String topicMQTT, String idConfiguracion) {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("NOMBRE", Nombre);
            newValues.put("IP", ip);
            newValues.put("MACADD", macAdd);
            newValues.put("TOPICMQTT", topicMQTT);
            newValues.put("IDCONFIGURACION", idConfiguracion);


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

/*Metodo para insertar data en la tabla usuario*/

    public String insertEntryUsuario(String nombre,String id,String correo,String contrasena,String telefono, ArrayList<Dispositivo>misDisp) {
        try {

            /*Crear objeto JSON para poder convertir el Arraylist en String */

            JSONObject json = new JSONObject();
            json.put("Dispositivos", new JSONArray(misDisp));
            String arrayList = json.toString();

           /*
           Para convertir el objeto JSON a arraylist (proceso inverso)

           JSONObject json = new JSONObject(stringreadfromsqlite);
           ArrayList items = json.optJSONArray("uniqueArrays");
           */


            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("NOMBRE", nombre);
            newValues.put("CORREO", correo);
            newValues.put("CONTRASENA", contrasena);
            newValues.put("TELEFONO", telefono);
            newValues.put("MISDISPOSITIVOS", arrayList);
            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result=db.insert("REGISTRO_USUARIO", null, newValues);
            System.out.print(result);
            Toast.makeText(context, "Usuario Registrado", Toast.LENGTH_LONG).show();
        }catch(Exception ex) {
            System.out.println("Exceptions " +ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }

    /*Dado el Nombre de un dispositivo lo elimina de la tabla de dispositivo*/

    public int deleteEntry(String Nombre) {
        String where="NOMBRE=?";
        int numberOFEntriesDeleted= db.delete("REGISTRO", where, new String[]{Nombre}) ;
        Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    /*Dado el ID de un usuario lo elimina de la tabla de registro*/

    public int deleteEntryUsuario(String correo) {
        String where="correo=?";
        int numberOFEntriesDeleted= db.delete("REGISTRO_USUARIO", where, new String[]{correo}) ;
        Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }


    /*Dado el nombre de un dispositivo devuelve la IP */

    public String getSinlgeEntry(String Nombre) {

        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("REGISTRO", null, "NOMBRE=?", new String[]{Nombre}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return "NOT EXIST";
        cursor.moveToFirst();
        getIP= cursor.getString(cursor.getColumnIndex("IP"));
        return getIP;
    }

/*Dado el nombre de un usuario devuelve su correo */

    public String getSinlgeEntryUsuario(String idUsuario) {

        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("REGISTRO_USUARIO", null, "correo=?", new String[]{idUsuario}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return "NOT EXIST";
        cursor.moveToFirst();
        getCorreo= cursor.getString(cursor.getColumnIndex("CORREO"));
        return getCorreo;
    }

    /*Actualiza el nombre y la IP de un dispositivo ya registrado*/

    public void  updateEntry(String Nombre,String IP) {
        //  create object of ContentValues
        ContentValues updatedValues = new ContentValues();
        // Assign values for each Column.
        updatedValues.put("NOMBRE", Nombre);
        updatedValues.put("IP", IP);
        String where="NOMBRE = ?";
        db.update("REGISTRO",updatedValues, where, new String[]{Nombre});
    }

    /*Actualiza el nombre y la IP de un dispositivo ya registrado*/

    public void  updateEntryUsuario(String Nombre,String ID,String Correo, String Contrasena, String Telefono) {
        //  create object of ContentValues
        ContentValues updatedValues = new ContentValues();
        // Assign values for each Column.
        updatedValues.put("NOMBRE", Nombre);
        updatedValues.put("CORREO", Correo);
        updatedValues.put("CONTRASENA", Contrasena);
        updatedValues.put("TELEFONO", Telefono);

        String where="correo = ?";
        db.update("REGISTRO_USUARIO",updatedValues, where, new String[]{ID});
    }
}
