package com.acme.eac3ccl;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Linterna extends android.support.v4.app.Fragment {

    private ImageView botonCamara;
    private boolean encendida; //Por defecto es false.

    public Linterna() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragment_linterna, container, false);

        botonCamara = (ImageView) fragmento.findViewById(R.id.linterna); //Boton imagen linterna.

        if(encendida){
            botonCamara.setImageResource(R.drawable.linterna);
        }

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        botonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View imagen) {

                if(encendida) {

                    botonApagaFlash();

                } else {

                    botonEnciendeFlash();
                }

            }
        });

        return fragmento;

    }

    public void botonEnciendeFlash() {

        botonCamara.setImageResource(R.drawable.linternaneg);

        encendida = true;

        Activity estaActividad = getActivity(); //Para gestionar desde linterna, pq el acceso a Camara se debe hacer desde una Activity
        ((ManejaFlashCamara) estaActividad).enciendeApaga(encendida);

    }

    public void botonApagaFlash() {

        botonCamara.setImageResource(R.drawable.linterna);

        encendida = false;

        Activity estaActividad = getActivity(); //Para gestionar desde linterna, pq el acceso a Camara se debe hacer desde una Activity
        ((ManejaFlashCamara) estaActividad).enciendeApaga(encendida);

    }


    @Override
    public void onPause() {
        super.onPause();
        botonApagaFlash(); //Apaguem flash si ens movem de Fragment.
    }
}
