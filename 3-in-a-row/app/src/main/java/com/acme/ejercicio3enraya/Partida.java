package com.acme.ejercicio3enraya;

import java.util.Random;

public class Partida {

    public final int dificultad;
    private int jugador;
    private int[] casillas; //Array para casillas ocupadas
    private final int [][] COMBINACIONES ={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};



    public Partida (int dific){

        this.dificultad = dific;

        jugador = 1;

        casillas=new int[9];

        for (int i=0; i<9;i++){
            casillas[i] = 0; //casillas vacías
        }
    }

    public int ia(){

        int casilla;

        casilla=dosEnRaya(2); //dos en Raya devuelve -1 si no hay posibilidad de ganar.

        if (casilla !=-1) return casilla; //si hay posibilidad de ganar, devuelve casill clave para la máquina

        if (dificultad > 0){ //En caso de que sea dificultad normal o dificil, mira si hay posibilidad de que el jugador humano gane,
            casilla=dosEnRaya(1);

            if(casilla !=-1) return casilla;
        }

        if (dificultad == 2){
            if(casillas[0] == 0) return 0;
            if(casillas[2] == 0) return 2;
            if(casillas[6] == 0) return 6;
            if(casillas[8] == 0) return 8;//Si no hay posibilidad de 3 en raya marca una esquina (para que no le gane el humano)
        }
        Random casilla_azar = new Random();

        casilla = casilla_azar.nextInt(9);

        return casilla;  //Random de 0 a 9 exclusive para que rellene la casilla.

    }


    public int dosEnRaya(int jugador_en_turno){ //Método que devuelve la casilla clave en caso de haber 2 en raya. Si no devuelve -1

        int casilla = -1; //La casilla clave para evitar perder por parte de IA

        int cuantaslleva = 0;

        if(primeracasillaesquina(jugador_en_turno))return 4;

        for (int i = 0; i<COMBINACIONES.length; i++) { //Para cada posibilidad (0,1,2)...

            for (int pos : COMBINACIONES[i]) { //Recorremos el interior cada vector (0,1,2), (3,4,5)....

                if(casillas[pos] == jugador_en_turno){
                    cuantaslleva ++; //Si lleva 2 en una fila.
                }

                if(casillas[pos] == 0){
                    casilla = pos; //Si la que falta es 0, es que está vacía.
                }
            }
            if (cuantaslleva == 2 && casilla!=-1){
                return casilla;
            }
            casilla = -1;
            cuantaslleva = 0;
        }
        return -1;
    }

    public int turno(){

        boolean empate = true;
        boolean ultimomovimiento = true;

        for (int i = 0; i<COMBINACIONES.length; i++){
            for (int pos: COMBINACIONES[i]){ //Recorremos cada vector (0,1,2), (3,4,5)....

                if(casillas[pos]!= jugador) {
                    ultimomovimiento = false;
                }


                if(casillas[pos]==0){ // Si en el array de casillas hay un 0 es que quedan casillas por rellenar.
                    empate = false;
                }
            } //cierre for anidado

            if(ultimomovimiento){
                return jugador;
            }
            ultimomovimiento = true;
        }//cierre for principal

        if (empate) {
            return 3;
        }



        jugador ++;
        if (jugador > 2) {
            jugador = 1;
        }
        //Empate devuelve 3
        //Gana J2 devuelve 2
        //Gana J1 devuelve 1
        //Empate devuelve 0

        return 0;
    }


    public boolean compruebacasilla(int casilla){

        if (casillas[casilla] != 0){
            return false;
        } else {
            casillas[casilla] = this.jugador;
            return true;
        }
    }

    public boolean primeracasillaesquina(int jugador_en_turno){
        if(jugador_en_turno == 1){
            int suma = 0;
            for (int i = 0; i < casillas.length; i++){
                suma = suma + casillas [i];
            }
            if (suma == 1 && casillas [0] == 1) return true;
            if (suma == 1 && casillas [2] == 1) return true;
            if (suma == 1 && casillas [6] == 1) return true;
            if (suma == 1 && casillas [8] == 1) return true;
        }
        return false;
    }

















    //GETTER Y SETTERS
    public void setJugador(int jugador) {
        this.jugador = jugador;
    }

    public int getJugador() {
        return this.jugador;
    }
}
