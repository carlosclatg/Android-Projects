package com.acme.eac1_calvo_l;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class SegonaPantalla  extends AppCompatActivity {
    int numintents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_segonapantalla);
        numintents = 0;
        //Recuperem les dades pasades en el intent
        Bundle datos = getIntent().getExtras();
        final int min = datos.getInt("from");
        final int max = datos.getInt("to");

        //Calculem el random i fem final per a que perduri i passi al mètode onClick
        Random rand = new Random();
        final int randomNum = rand.nextInt((max - min) + 1) + min;
        //Numero intents
        Button boton = findViewById(R.id.buttoncomprovar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Considero que cada click es un intent, independentement de la validacio de dades
                numintents ++;

                EditText editText = findViewById(R.id.editcomprova);
                if (editText.getText().toString().isEmpty()) { //Cas EditText buit

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.campsincomplerts), Toast.LENGTH_SHORT).show();
                    return;
                }

                int valor = Integer.parseInt(editText.getText().toString());
                if((valor < min)||(valor > max)){ //valor fora de rang

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.intervalbad), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(valor == randomNum){ //Valor correcte
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.felicitats), Toast.LENGTH_SHORT).show();

                    //text a mostrar concatenat
                    TextView textView = findViewById(R.id.textviewfinal);
                    String texto = getResources().getString(R.string.textintents).concat(" " + numintents + "").concat(" intents");
                    textView.setText(texto);

                    //deshabilitem botons
                    view.setEnabled(false);
                    editText.setEnabled(false);

                } else { //Torna a provar

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.tornaaprovar), Toast.LENGTH_SHORT).show();
                    editText.setText("");

                }

            }
        });


        //buttonenrere
        Button botoenrere = (Button) findViewById(R.id.buttonenrere);
        botoenrere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SegonaPantalla.super.onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //Només es pot tornar enrere amb el IMageButton, onBackPressed no fa res
    }

}
