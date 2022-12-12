package com.example.pinkcal;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
//establecer el contexto, el nombre y la versión de la base de datos
public class BDSQLite extends SQLiteOpenHelper {
    //define una constante llamada DATABASE_VERSION que se establece en 1
    private static final int DATABASE_VERSION = 1;
  //DATABASE_NAME que se establece en "eventos.db"
    private static final String DATABASE_NAME = "eventos.db";
//utilizan para especificar la versión y el nombre de la base de datos.
    public BDSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//sentencia SQL que se utilizará para crear una tabla en una base de datos que almacenará información sobre eventos
    private String sql = "create table eventos(" +
            "idEventto int," +
            "nombreEvento varchar(40)," +
            "ubicacion varchar(60)," +
            "descripcion varchar(1000)," +
            "dia int," +
            "mes int," +
            "year int)";

    public BDSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
//se crea la base de datos por primera vez
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla para almacenar los eventos
        // ejecuta una sentencia SQL que crea una tabla llamada "eventos" con diferentes columnas para almacenar información sobre los eventos.
        db.execSQL("CREATE TABLE eventos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, ubicacion TEXT, descripcion TEXT, anio INTEGER, mes INTEGER, dia INTEGER)");
    }
// actualiza la base de datos cuando se cambia su versión
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}