package com.acme.eac2_calvo_l;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ActivityModificar extends AppCompatActivity {
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymodificar);
        //Recuperem les dades
        Bundle b = getIntent().getExtras();
        String titol = b.getString("titol");
        MainActivity.mDbHelper.open();
        Cursor cursor = MainActivity.mDbHelper.getItembyTitle(titol);

        if( cursor.moveToFirst() ) { //Si ha trobat
            String autor = cursor.getString(cursor.getColumnIndex("AUTOR"));
            String editorial = cursor.getString(cursor.getColumnIndex("EDITORIAL"));
            String tipus = cursor.getString(cursor.getColumnIndex("TIPUSTAPA"));
            String preu = cursor.getString(cursor.getColumnIndex("PREU"));
            cursor.close();
            //titol
            EditText modTitol = findViewById(R.id.modTitol);
            modTitol.setText(titol);
            //autor
            EditText modAutor = findViewById(R.id.modAutor);
            modAutor.setText(autor);
            //editorial
            EditText modEditorial = findViewById(R.id.modEditorial);
            modEditorial.setText(editorial);
            //Spinner
            Spinner modTipus = findViewById(R.id.modspinnertipus);
            String[] arrayvalors = {"Tapa Dura", "De butxaca"};
            adapter  = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayvalors);
            modTipus.setAdapter(adapter);
            modTipus.setSelection(adapter.getPosition(tipus));
            //preu
            EditText modPreu = findViewById(R.id.modPreu);
            modPreu.setText(preu);
        }
        MainActivity.mDbHelper.close();



        //assignem accions als botons
        Button bmodificar = findViewById(R.id.modllibre);
        Button bcancelar = findViewById(R.id.modcancelar);

        //boto modificar
        bmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText modTitol = findViewById(R.id.modTitol);
                EditText modAutor = findViewById(R.id.modAutor);
                EditText modEditorial = findViewById(R.id.modEditorial);
                Spinner modTipus = findViewById(R.id.modspinnertipus);
                String[] arrayvalors = {"Tapa Dura", "De butxaca"};
                adapter  = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayvalors);
                modTipus.setAdapter(adapter);
                EditText modPreu = findViewById(R.id.modPreu);
                String titol = modTitol.getText().toString();
                String autor = modAutor.getText().toString();
                String editorial = modEditorial.getText().toString();
                String tipus = modTipus.getSelectedItem().toString();
                String preu = modPreu.getText().toString();
                try{
                    MainActivity.mDbHelper.open();
                    MainActivity.mDbHelper.updateItem(titol, autor, editorial,tipus,preu);
                    MainActivity.mDbHelper.close();
                    //Tornem a la pantalla principal
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        //boto cancelar
        bcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
