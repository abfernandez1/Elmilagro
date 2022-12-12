package com.example.pinkcal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_event extends AppCompatActivity implements View.OnClickListener {
    private EditText nombreEvento, ubicacionEvento, descripcion;
    private Button agregar, salir;
    int dia = 0, mes = 0, anio = 0;
    String NombreEven = "Sin datos", Ubicacion = " Sin datos", Descripcion = " Sin datos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        nombreEvento = (EditText) findViewById(R.id.NombreEvento);
        ubicacionEvento = (EditText) findViewById(R.id.UbicacionEvento);
        descripcion = (EditText) findViewById(R.id.Descripcion);

        Bundle bundle = getIntent().getExtras();
        dia = bundle.getInt("dia");
        mes = bundle.getInt("mes");
        anio = bundle.getInt("anio");

        agregar = (Button) findViewById(R.id.Guardar);
        salir = (Button) findViewById(R.id.Salir);

        agregar.setOnClickListener(this);
        salir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == agregar.getId()) {
            //Guarrdar datos de los datos
            BDSQLite bd = new BDSQLite(getApplication(), "Agenda", null, 1);
            SQLiteDatabase db = bd.getWritableDatabase();

            //Obtener los valores ingresados en los campos de texto
            String nombreEven = nombreEvento.getText().toString();
            String ubicacion = ubicacionEvento.getText().toString();
            String descrip = descripcion.getText().toString();

            //Comprobar si se han ingresado valores en los campos de texto
            if (nombreEven.isEmpty() || ubicacion.isEmpty()) {
                //Mostrar un mensaje de error si faltan datos
                Toast.makeText(getApplication(), "Ingresa un nombre de evento, una ubicación y descripcion", Toast.LENGTH_SHORT).show();
                return;
            }

            String sql = "INSERT INTO eventos" + "(nombreEvento, ubicacion, descripcion, dia, mes, year)" + "values('" + nombreEven + "','" + ubicacion + "','" + descrip + "','" + dia + "','" + mes + "','" + anio + "')";
            try {
                db.execSQL(sql);

                //Limpiar los campos de texto después de guardar los datos
                nombreEvento.setText("");
                ubicacionEvento.setText("");
                descripcion.setText("");
            } catch (Exception e) {
                Toast.makeText(getApplication(), "Error " + e.getMessage(), Toast.
                        LENGTH_SHORT).show();
            }
        } else {
            this.finish();
            return;
        }
    }
}

