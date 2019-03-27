package com.acme.eac2_calvo_l;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityCerca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycerca);
        setTitle(getResources().getString(R.string.TitolCercar));
        //Obtenim els tres botons i el EditText
        Button buttonCerca = findViewById(R.id.botonbuscar);
        Button buttonNetejar = findViewById(R.id.botonnetejar);
        Button buttonTornar = findViewById(R.id.botontornar);


        //Accions dels botons
        buttonCerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cerca de text
                EditText editTitol = findViewById(R.id.editTitol);
                MainActivity.mDbHelper.open();
                Cursor cursor = MainActivity.mDbHelper.getItembyTitle(editTitol.getText().toString());
                if( cursor.moveToFirst() ){ //Si ha trobat
                    String title = cursor.getString(cursor.getColumnIndex("TITOL"));
                    String autor = cursor.getString(cursor.getColumnIndex("AUTOR"));
                    String editorial = cursor.getString(cursor.getColumnIndex("EDITORIAL"));
                    String tipus = cursor.getString(cursor.getColumnIndex("TIPUSTAPA"));
                    String preu = cursor.getString(cursor.getColumnIndex("PREU"));
                    cursor.close();
                    MainActivity.mDbHelper.close();
                    Intent i = new Intent(getApplicationContext(), Activitydetall_llibre.class);
                    i.putExtra("titol", title);
                    i.putExtra("autor", autor);
                    i.putExtra("editorial", editorial);
                    i.putExtra("tipus", tipus);
                    i.putExtra("preu", preu);
                    startActivity(i);
                } else {
                    MainActivity.mDbHelper.close();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.notrobat), Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonNetejar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Neteja Text
                EditText editTitol = findViewById(R.id.editTitol);
                editTitol.setText("");
            }
        });

        buttonTornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Tornar enrere
                onBackPressed();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText editTitol = findViewById(R.id.editTitol);
        editTitol.setText(""); //Per tal d'esborrar el text

    }
}
