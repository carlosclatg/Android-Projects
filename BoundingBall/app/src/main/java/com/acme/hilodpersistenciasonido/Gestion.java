package com.acme.hilodpersistenciasonido;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class Gestion extends AppCompatActivity {
    private Partida partida;
    private int dificultad;
    private int botes;
    private int FPS = 30 ; //En temas de animación hay frames, que es la actualización fps.
    private Handler temporizador; //Manejador de hilos
    private MediaPlayer golpeo;
    private MediaPlayer musica;

    private Runnable elhilo = new Runnable() { //Interfaz runnable, implementa método run.
        @Override
        public void run() {
            if (partida.movimientoBola() == true){ //Caso que se de final de la partida
                fin(); //Acaba la partida
            } else { //La pelota está en la pantalla. La pelota se mueve frame a frame.
                partida.invalidate();//Elimina el contenido  de ImageView y llama de nuevo a onDraw.
                temporizador.postDelayed(elhilo, 20); //Intervalo de actualización de la pantalla
            }
        }
    };

    public boolean onTouchEvent(MotionEvent evento){

        int x = (int) evento.getX();
        int y = (int) evento.getY();

        if (partida.toque(x,y)){//Ejecuta evento toque
            botes ++;
            golpeo.start();
        }



        return false;

    }

    public void fin(){

        Intent in = new Intent();

        in.putExtra("PUNTUACIÓN", botes);

        setResult(RESULT_OK, in); //Debemos indicar que el resultado es OK

        //https://developer.android.com/training/basics/intents/result?hl=es-419

        //PAra pasar un Intent con los datos que se deben guardar.

        temporizador.removeCallbacks(elhilo);

        finish();

        musica.release();
    }



    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        botes = 0;

        Bundle extras = getIntent().getExtras();

        dificultad = extras.getInt("DIFICULTAD");

        partida = new Partida(getApplicationContext(), dificultad);

        setContentView(partida);

        temporizador=new Handler(); // La clase Handler permite tener un hilo de ejecución.

        temporizador.postDelayed(elhilo,2000);
        //Se le pasa un objeto que implemete la interfaz Runnable y le pasamos el delay

        musica = MediaPlayer.create(this,R.raw.ringtoneszelda);

        musica.start();

        golpeo = MediaPlayer.create(this,R.raw.mariobrosvida);
    }








}
