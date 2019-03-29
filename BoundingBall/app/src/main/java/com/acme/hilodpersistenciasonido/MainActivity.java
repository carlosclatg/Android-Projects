package com.acme.hilodpersistenciasonido;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.acme.hilodpersistenciasonido.R.*;

public class MainActivity extends AppCompatActivity {

    private int record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
    }

    @Override
    protected void onActivityResult (int peticion, int codigo, Intent intent){
        if ((peticion != 1) || codigo != RESULT_OK){
        } else {
            int resultado = intent.getIntExtra("PUNTUACIÓN", 0);

            if (resultado > record){

                record = resultado;
                TextView caja= (TextView) findViewById(id.record);
                caja.setText("Record: " + resultado +"");
                guardarecord(); //Lo guardamos
                String puntos = "Nuevo Record! " + resultado +" botes";
                Toast mitoast = Toast.makeText(this, puntos, Toast.LENGTH_SHORT);
                mitoast.setGravity(Gravity.CENTER, 0, 0);
                mitoast.show();

            } else { //Si no hemos superado, simplemente mostramos en un Toast

                String puntos = "Felicidades has hecho " + resultado +" botes";
                Toast mitoast = Toast.makeText(this, puntos, Toast.LENGTH_SHORT);
                mitoast.setGravity(Gravity.CENTER, 0, 0);
                mitoast.show();
            }
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        leerecord();
    }

    private void leerecord() { //Método para leer del sharedpreferences el record anterior
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this); //Obtener el shared preferences por defecto
        record = datos.getInt("cuenta", 0); //El entero es el valor por defecto en caso que no encuente la clave.
        TextView caja= (TextView) findViewById(R.id.record);
        caja.setText("Record: " + record +"");

    }

    private void guardarecord(){
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this); //Obtener el shared preferences por defecto
        SharedPreferences.Editor miEditor  = datos.edit();  //Por la api pasamos por SharedPreferences.editor
        miEditor.putInt("cuenta", record);
        miEditor.apply(); //Guardamos en el objeto SharedPreferences la pareja clave valor.
    }


    public void ayuda (View vista){

        Intent  intencion = new Intent(this, AyudaActividad.class);
        startActivity(intencion);

    }


    public void dificultad(View vista){

        String dif = (String) ((Button) vista).getText();

        int dificultad = 1;

        if (dif.equals(getString(string.medio))) dificultad = 2;

        if (dif.equals(getString(string.dificil))) dificultad = 3;

        Intent in = new Intent(this, Gestion.class); //Llamamos al intent de la clase donde va

        in.putExtra("DIFICULTAD", dificultad); //Lo pasamos por el bundle

        startActivityForResult(in, 1);

    }

}


