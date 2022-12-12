package com.example.pinkcal;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

public class EventosActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener{

    private CalendarView calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);

        calendario = (CalendarView) findViewById(R.id.calendarView);
        calendario.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence [] items = new CharSequence[3];
        items[0]="Agregar Evento";
        items[1]="Ver Eventos";
        items[2]="Cancelar";

        final int dia, mes, anio;
        anio = year;
        mes = month+1;
        dia = dayOfMonth;

        builder.setTitle("Seleccione que desea hacer")
                .setItems(items, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i==0){
                            //Agregar Eventos
                            Intent intent = new Intent(getApplication(), Add_event.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("nombreEvento", "Periodo ");
                            bundle.putString("ubicacion", "Monte grande");
                            bundle.putString("descripcion", "malhumor");
                            bundle.putInt("dia", dia);
                            bundle.putInt("mes", mes);
                            bundle.putInt("anio", anio);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else if(i==1){
                            //ver Eventos
                            Intent intent = new Intent(getApplication(), ViewEventosActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("nombreEvento", "Periodo ");
                            bundle.putString("ubicacion", "Monte grande");
                            bundle.putString("descripcion", "malhumor");
                            bundle.putInt("anio", anio);
                            bundle.putInt("mes", mes);
                            bundle.putInt("dia", dia);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else{
                            //salir del metodo
                            return;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}