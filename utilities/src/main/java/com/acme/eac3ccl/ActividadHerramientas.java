package com.acme.eac3ccl;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ActividadHerramientas extends AppCompatActivity implements ComunicaMenu, ManejaFlashCamara {

    private android.support.v4.app.Fragment[] misfragments;
    private CameraManager miCamara;
    private String idCamara;

    @Override
    @TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_herramientas);
        //La primera vez que carga la info lo hace desde el Bundle del Activity_main.

        misfragments = new Fragment[7];

        misfragments[0] = new Linterna();
        misfragments[1] = new Foto();
        misfragments[2] = new Animacion();
        misfragments[3] = new Batman();
        misfragments[4] = new Musica();
        misfragments[5] = new VideoPantalla();
        misfragments[6] = new Mapa();

        Bundle bundle = getIntent().getExtras();
        int botonpulsado = (int) bundle.getInt("BotonPulsado");

        opcionmenu(botonpulsado); //Lo pasamos al método que cambia el fragment.

        miCamara = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try{
            idCamara = miCamara.getCameraIdList()[0];
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void opcionmenu(int queboton) {
        //Esta interfaz viene a responder a la llamada que se hace desde la clase Menu.java...
        //Al tenerse que gestionar desde la activity implementando interfaz nos asegura el poder llamar a la función
        //...en concreto en la linea (ComunicaMenu) estaActividad).opcionmenu(queBoton)
        //La clase FragmentManager para gestionar cambios de fragment.

        android.support.v4.app.FragmentManager miManejador = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction miTransaccion = miManejador.beginTransaction();
        //Creación de los 4 fragments de menu (sin pulsar y cada uno de los tres pulsado = total 4)
        Bundle datos = new Bundle();
        datos.putInt("BOTONPULSADO", queboton);
        //Reemplazo de la zona inferior.
        miTransaccion.replace(R.id.herramientas, misfragments[queboton]);

        miTransaccion.commit();
    }

    @Override
    public void enciendeApaga(boolean estado) {
        try{
            if(estado){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ //Control versiones de Android
                    Toast.makeText(this, R.string.flashencendido, Toast.LENGTH_SHORT).show();
                    miCamara.setTorchMode(idCamara, true);
                }
            } else {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ //Control versiones de Android
                    Toast.makeText(this, R.string.flashapagado, Toast.LENGTH_SHORT).show();
                    miCamara.setTorchMode(idCamara, false);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void onBackPressed(){

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
