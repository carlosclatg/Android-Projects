package com.acme.eac2_calvo_l;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityAfegir extends AppCompatActivity {
    Spinner spinner;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegirllibre);
        setTitle(getResources().getString(R.string.TitolInsertar));
        //Afegim l'spinner

        spinner = (Spinner) findViewById(R.id.spinnertipus);
        String[] arrayvalors = {"Tapa Dura", "De butxaca"};
        adapter  = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayvalors);
        spinner.setAdapter(adapter);


        //Recuperem botons
        Button buttonCancelar = findViewById(R.id.cancelar);
        Button buttonInserir = findViewById(R.id.inserirllibre);




        //Accions dels botons
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recuperem dades i verifiquem que no estan buides.
                EditText titol = findViewById(R.id.editTitol);
                if(titol.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.faltendades), Toast.LENGTH_SHORT).show();
                    return;}
                EditText autor = findViewById(R.id.editAutor);
                if(autor.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.faltendades), Toast.LENGTH_SHORT).show();
                    return;}
                EditText editorial = findViewById(R.id.editEditorial);
                if(editorial.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.faltendades), Toast.LENGTH_SHORT).show();
                    return;}
                EditText preu = findViewById(R.id.editPreu);
                if(preu.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.faltendades), Toast.LENGTH_SHORT).show();
                    return;}
                //Spinner
                String tipus = spinner.getSelectedItem().toString();
                if(tipus == null){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.faltendades), Toast.LENGTH_SHORT).show();
                    return;
                }




                //Arribem fins aqui sii totes les dades estan completes
                Llibre llibre = new Llibre(titol.getText().toString(),autor.getText().toString(),editorial.getText().toString(),tipus,preu.getText().toString());
                try {
                    MainActivity.mDbHelper.open();
                    MainActivity.mDbHelper.insertItem(llibre);
                    MainActivity.mDbHelper.close();
                    onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
