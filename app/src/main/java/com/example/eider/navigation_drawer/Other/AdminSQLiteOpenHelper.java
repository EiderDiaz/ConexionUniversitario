package com.example.eider.navigation_drawer.Other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.eider.navigation_drawer.Modelos.Usuario;

import java.util.ArrayList;

/**
 * Created by Eider on 07/12/2017.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String NOMBREBD = "sesionfb.db";
    private static final int VERSIONBD = 1;
    //constantes campos tabla sesion
    private final static String tblsesion = "tblsesion";
    public final static String nombre = "nombre";
    private final static String apellido = "apellido";
    private final static String correo = "correo";
    private final static String fb_id = "if_id";

    public AdminSQLiteOpenHelper(Context context) {
        super(context, NOMBREBD, null, VERSIONBD);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CrearTablaSesionFB =
                "create table " + tblsesion + "("
                        + nombre + " TEXT NOT NULL, "
                        + apellido + " TEXT NOT NULL, "
                        + correo + " TEXT NOT NULL, "
                        + fb_id + " TEXT NOT NULL);";

        db.execSQL(CrearTablaSesionFB);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tblsesion);
        onCreate(sqLiteDatabase);
    }
    
    public void resetDataBase(Context context)  {
        try{
            // Drop older table if existed
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS "+tblsesion);
            onCreate(db);
        }catch (Exception e){
            Toast.makeText(context, "error :C"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
  

    public void GuardarDatosSession (Usuario usuario, Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nombre,usuario.getNombre());
        values.put(apellido,usuario.getNombre());
        values.put(correo,usuario.getCorreo());
        values.put(fb_id,usuario.getFb_id());
        long err =db.insert(tblsesion,null,values);
        db.close();
        if (err == -1)Toast.makeText(context, "error en la insercion", Toast.LENGTH_SHORT).show();
       // else Toast.makeText(context, "datos insertados exitosamente ", Toast.LENGTH_SHORT).show();

    }

    public Usuario consultarDatoSession(Context context){
        String selectQuery = "SELECT  * FROM " + tblsesion;
        Usuario usuario = new Usuario();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor fila = db.rawQuery(selectQuery, null);
        if (fila.moveToFirst()) {
            do {
               usuario =new Usuario(fila.getString(0), fila.getString(1),fila.getString(2),fila.getString(3));
            }while (fila.moveToNext());
        }
        db.close();
        return usuario;
    }
}
