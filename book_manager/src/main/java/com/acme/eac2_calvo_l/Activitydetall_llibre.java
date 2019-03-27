package com.acme.eac2_calvo_l;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activitydetall_llibre extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydetall_llibre);
        //Carreguem dades
        carregadades();
        Button button = findViewById(R.id.detalltornar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregadades();
        Button button = findViewById(R.id.detalltornar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void carregadades() {
        Bundle bundle = getIntent().getExtras();
        TextView detallTitol = findViewById(R.id.detallTitol);
        TextView detallAutor = findViewById(R.id.detallAutor);
        TextView detallEditorial = findViewById(R.id.detallEditorial);
        TextView detallTipus = findViewById(R.id.detallTipus);
        TextView detallPreu = findViewById(R.id.detallpreu);
        detallTitol.setText(bundle.getString("titol"));
        detallAutor.setText(bundle.getString("autor"));
        detallEditorial.setText(bundle.getString("editorial"));
        detallTipus.setText(bundle.getString("tipus"));
        detallPreu.setText(bundle.getString("preu"));
    }

    //PARTE DE MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menudetall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId(); //Almacenamos el id pulsado
        if (id == R.id.eliminarllibre){ //Inserir llibre
            Bundle bundle = getIntent().getExtras();
            String titol = bundle.getString("titol");
            eliminarllibre(titol);
            return true;
        }
        if (id == R.id.modificarllibre) { //Cercar llibre
            modificarllibre();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void modificarllibre() {
        //Recupero el titol per carregarlo a l'activty modificar
        TextView detallTitol = findViewById(R.id.detallTitol);
        TextView detallAutor = findViewById(R.id.detallAutor);
        TextView detallEditorial = findViewById(R.id.detallEditorial);
        TextView detallTipus = findViewById(R.id.detallTipus);
        TextView detallPreu = findViewById(R.id.detallpreu);
        String titol = detallTitol.getText().toString();
        String autor = detallAutor.getText().toString();
        String editorial = detallEditorial.getText().toString();
        String tipus = detallTipus.getText().toString();
        String preu = detallPreu.getText().toString();
        Intent intent = new Intent(this, ActivityModificar.class);
        intent.putExtra("titol", titol );
        intent.putExtra("autor", autor);
        intent.putExtra("editorial", editorial);
        intent.putExtra("tipus", tipus);
        intent.putExtra("preu", preu);
        startActivity(intent);
    }

    private void eliminarllibre(final String titol) {
        //Alert Dialog confirmant si vol realment elimnar
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Alerta!");
        alertDialog.setMessage("Estàs segur que vols esborrar el llibre " + titol +"?");
        alertDialog.setNegativeButton ("Cancelar", null);
        alertDialog.setPositiveButton ("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    MainActivity.mDbHelper.open();
                    MainActivity.mDbHelper.delete(titol);
                    MainActivity.mDbHelper.close();
                    finish(); //Tornar
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialog.show();
    }
}
