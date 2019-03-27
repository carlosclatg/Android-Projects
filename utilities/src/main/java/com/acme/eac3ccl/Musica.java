package com.acme.eac3ccl;

import android.support.v4.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class Musica extends android.support.v4.app.Fragment {

    MediaPlayer musica;
    public Musica (){
        //Constructor clase
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() { //Si canviem de fragment es pari la música, ja que si tornem i torno a reproduir es fa un coro :S
        super.onPause();
        if(musica.isPlaying()) {
            musica.stop();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragmentmusica, container, false);
        musica = MediaPlayer.create(getActivity(),R.raw.ringtoneszelda);


        final ImageView imatge = (ImageView)fragmento.findViewById(R.id.botonmusica);
        imatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musica.isPlaying()){
                    musica.stop();
                    try {
                        musica.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), R.string.stopmusica, Toast.LENGTH_SHORT).show();
                } else {
                    musica.start();
                    Toast.makeText(getActivity(), R.string.repromusica, Toast.LENGTH_SHORT).show();
                }
            }
        });


        return fragmento;

    }
}


