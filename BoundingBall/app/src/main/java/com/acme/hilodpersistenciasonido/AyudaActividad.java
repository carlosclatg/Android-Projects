package com.acme.hilodpersistenciasonido;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AyudaActividad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda_actividad);
    }


    public void volver (View vista){

        onBackPressed(); //Botón de atrás en Android
    }
}
