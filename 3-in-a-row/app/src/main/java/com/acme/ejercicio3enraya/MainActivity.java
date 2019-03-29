package com.acme.ejercicio3enraya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int jugadores; //Para saber si son 1 o 2 jugadores
    private int[] CASILLAS; //Para identificar las casillas
    private Partida partida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Incializamos el array de casillas que identifica cada casilla y almacena el array

        CASILLAS = new int[9];
        CASILLAS [0] = R.id.a1;
        CASILLAS [1] = R.id.a2;
        CASILLAS [2] = R.id.a3;
        CASILLAS [3] = R.id.b1;
        CASILLAS [4] = R.id.b2;
        CASILLAS [5] = R.id.b3;
        CASILLAS [6] = R.id.c1;
        CASILLAS [7] = R.id.c2;
        CASILLAS [8] = R.id.c3;

    }

    public void aJugar (View vista) { //el argumento vista es el botón que hemos pulsado.

        //Lo primero es resetar el tablero de la anterior ocasión.
        ImageView imagen;

        for (int cadacasilla: CASILLAS){

            imagen = (ImageView) findViewById(cadacasilla);
            imagen.setImageResource(R.drawable.casilla); // A cada elemento le asignamos la casilla en blanco.
        }



        jugadores = 1;
        if (vista.getId() == R.id.dosjug){
            jugadores = 2;
        }

        RadioGroup configdificultad = (RadioGroup) findViewById(R.id.configd);
        int id = configdificultad.getCheckedRadioButtonId();
        int dificultad = 0;
        if (id == R.id.normal){
            dificultad = 1;
        } else if (id == R.id.dificil){
            dificultad = 2;
        }


        partida = new Partida (dificultad);


        //Inhabilitamos botones de la izquierda

        ((Button) findViewById(R.id.unjug)).setEnabled(false);
        ((Button) findViewById(R.id.dosjug)).setEnabled(false);
        ((RadioGroup) findViewById(R.id.configd)).setAlpha(0); //Lo hacemos transparente

    }


    public void toque (View mivista) { //Metodo para click en las casillas.
        // Este método ha de recibir la casilla donde hemos pulsado como parámetro --> View!!!
        // Cada casilla está en R.id.a1,a2,a3,b1.... CASILLAS[0] [1]...

        if (partida == null){
            return;
        } //Esto previene que si no hemos empezado la partida (sel jug y dif), las casillas no muestren el toast!!


        int casilla = 0;
        for (int i =0; i<9; i++){//Método para saber qué casilla hemos pulsado.
            if (CASILLAS[i] == mivista.getId()){
                casilla = i;
                break; // Para salir del buckle si lo hemos encontrado.
            }
        }

        //Creamos un mensaje emergente Toast para ver los datos de las variables
        /*Toast toast = Toast.makeText(this, "Has pulsado la casilla " + casilla + "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0, 0);
        toast.show();*/
        if(!partida.compruebacasilla(casilla)){
            return; //Sale del flujo de ejecución del método si la casilla ya está pulsada!!!
        }

        marca(casilla); //Hasta aquí respuesta a nuestra pulsación

        int resultado = partida.turno();

        if(resultado > 0){
            termina(resultado);
            return; //Salimos porque partida = null y se para el programa
        }

        casilla = partida.ia();

        while (partida.compruebacasilla(casilla)!=true){
            casilla = partida.ia();
        }


        marca(casilla);//Para que el programa llame al random para que obtenga una casilla

        resultado = partida.turno();

        if(resultado > 0){
            termina(resultado);
        }
    }

    private void termina (int resultado){

        String mensaje;
        if(resultado == 1 || resultado == 2){

            mensaje = "Ha ganado el jugador " + resultado +"";
        } else {
            mensaje = "Tablas";
        }

        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0, 0);
        toast.show();

        partida = null; //Borramos partida

        //AQuí habilitamos los botones

        ((Button) findViewById(R.id.unjug)).setEnabled(true);
        ((Button) findViewById(R.id.dosjug)).setEnabled(true);
        ((RadioGroup) findViewById(R.id.configd)).setAlpha(1); //Lo hacemos transparente




    }

    private void marca (int casilla){ //Método para pintar las casillas, como parámetro la casilla a pintar.

        ImageView imagen;

        imagen = (ImageView) findViewById(CASILLAS[casilla]);

        if(partida.getJugador()==1){
            imagen.setImageResource(R.drawable.circulo);

        } else {
            imagen.setImageResource(R.drawable.aspa);

        }

    }



}
