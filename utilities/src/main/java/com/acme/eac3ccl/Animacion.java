package com.acme.eac3ccl;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Animacion extends android.support.v4.app.Fragment {

    public Animacion (){
       //Constructor clase
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragment_animacio, container, false);

        final ImageView imatge = (ImageView) fragmento.findViewById(R.id.animacio);
        final AnimationDrawable animacio = (AnimationDrawable) imatge.getDrawable();
        imatge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(animacio.isRunning()){
                    animacio.stop();
                } else {
                    animacio.start();
                }

            }
        });


        return fragmento;

    }
}
