package com.acme.eac3ccl;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Menu extends android.support.v4.app.Fragment {

    private final int[] BOTONESMENU = {R.id.linterna, R.id.camera, R.id.animationf,R.id.batman, R.id.music, R.id.video, R.id.mapa }; //array de id ImageButton
    //private int [] BOTONESILUMINADOS = {R.drawable.linterna2, R.drawable.musica2, R.drawable.nivel2}; //Array de imágenes
    private int boton; //Boton pulsado

    public Menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Método inflate de la clase LayoutInflater : https://developer.android.com/reference/android/view/LayoutInflater.html#inflate(org.xmlpull.v1.XmlPullParser,%20android.view.ViewGroup,%20boolean)
        //La clase LayoutInflater resumiendo lo que hace es cargar el fragmento correspondiente en su contenedor padre (actividad)
        View mimenu = inflater.inflate(R.layout.fragmentmenu, container, false);


        boton = -1; //Si no incializamos valor por defecto = 0;
        if(getArguments() != null) { //Para proteger la primera vez que viene de Main Activity
            boton = getArguments().getInt("BOTONPULSADO"); //Recuperamos boton pulsado del bundle
        }


        ImageButton botonmenu;

        for (int i = 0; i< BOTONESMENU.length; i++){

            botonmenu = (ImageButton) mimenu.findViewById(BOTONESMENU[i]);

            if(boton==i) { //Seleccionamos el botón a iluminar.
                //botonmenu.setImageResource(BOTONESILUMINADOS[i]); //Para el botón dado cambiamos la src
            }
            //Añadimos los listeners a cada uno de los botones.
            final int  queBoton = i; //lo hacemos final para que entre en los listenners

            botonmenu.setOnClickListener(new View.OnClickListener() { //Listener
                @Override
                public void onClick(View view) {
                    Activity estaActividad = getActivity(); //Obtenemos la activity de la que venimos. Actividad Herramientas
                    //Si estamos en el MainActivity simplemente invoca al método opcionmenu y nos pasa a la ActivityHerramientas.
                    ((ComunicaMenu) estaActividad).opcionmenu(queBoton); // Casting a un objeto de tipo ComunicaMenu
                }
            });
        }
        return mimenu;
    }
    //Lo primero que necesitamos saber es qué actividad nos encontramos
}



