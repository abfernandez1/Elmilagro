package com.example.pinkcal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewEventosActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private SQLiteDatabase db;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    int dia=0, mes=0, anio=0;
    String nombreEvento, ubicacion, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_eventos);

        listView = (ListView) findViewById(R.id.ListaEventos);
        listView.setOnItemLongClickListener(this);

        Bundle bundle = getIntent().getExtras();
        dia = bundle.getInt("dia");
        mes = bundle.getInt("mes");
        anio = bundle.getInt("anio");

        BDSQLite bd = new BDSQLite(getApplicationContext(), "Agenda", null, 1);
        db = bd.getReadableDatabase();

        String sql = "select * from eventos where dia='" + dia + "' and mes='" + mes + "' and year='" + anio + "'";
        Cursor c;

        String nombreEvento, ubicacion, descripcion;
        try {
            c = db.rawQuery(sql, null);
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
            if (c.moveToFirst()) {
                do {
                    nombreEvento = c.getString(1);
                    ubicacion = c.getString(2);
                    descripcion = c.getString(3);

                    arrayAdapter.add(nombreEvento + ", " + ubicacion + ", " + descripcion);
                } while (c.moveToNext());
                listView.setAdapter(arrayAdapter);
            } else {
                this.finish();
            }
        } catch (Exception ex) {
            Toast.makeText(getApplication(), "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

        private void eliminar(String dato){
        String []datos = dato.split(", ");
        String sql="delete from eventos where nombreEvento='"+datos[0]+"' and ubicacion='"+datos[1]+"' and descripcion='"+datos[2]+"'";

        try {
            arrayAdapter.remove(dato);
            listView.setAdapter(arrayAdapter);
            db.execSQL(sql);
            Toast.makeText(getApplication(), "Evento Eliminado", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Error: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[2];
        items[0]="Eliminar Evento";
        items[1]="Cancelar";
        builder.setTitle("Eliminar Evento")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            //Eliminar Evento
                            eliminar(parent.getItemAtPosition(position).toString());
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }
}