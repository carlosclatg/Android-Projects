package com.acme.eac1_calvo_l;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int SEGON = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Botó d'esborrar amb el seu listener corresponent
        Button bdelete = (Button) findViewById(R.id.buttondelete);
        bdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edittext del from
                EditText from = (EditText) findViewById(R.id.edittextfrom);
                from.setText("");
                //edittext del to
                EditText to = (EditText) findViewById(R.id.edittextto);
                to.setText("");
            }
        });


        //Botó de check per passar a l'altre activity
        Button bcheck = (Button) findViewById(R.id.buttoncheck);
        bcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Algun dels camps buits
                EditText from = (EditText) findViewById(R.id.edittextfrom);
                EditText to = (EditText) findViewById(R.id.edittextto);

                //Cas algun dels dos buits
                if ((from.getText().toString().isEmpty() || to.getText().toString().isEmpty())){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.warning1), Toast.LENGTH_SHORT).show();
                    return;
                }

                //Cas from < to
                if(Integer.parseInt(from.getText().toString()) >= Integer.parseInt(to.getText().toString())){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.warning2), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), SegonaPantalla.class);
                intent.putExtra("from", Integer.parseInt(from.getText().toString()));
                intent.putExtra("to", Integer.parseInt(to.getText().toString()));
                startActivityForResult(intent, SEGON);
            }
        });
    }

    @Override
    protected void onStart() {  //Per posar els indicadors a 0 quan tornem a la activity des de l'altre activity
        super.onStart();
        EditText from = (EditText) findViewById(R.id.edittextfrom);
        from.setText("");
        EditText to = (EditText) findViewById(R.id.edittextto);
        to.setText("");

    }
}
