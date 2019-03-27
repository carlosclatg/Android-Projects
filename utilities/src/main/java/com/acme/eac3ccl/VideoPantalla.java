package com.acme.eac3ccl;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import java.io.IOException;

public class VideoPantalla extends android.support.v4.app.Fragment {

    private VideoView video;

    public VideoPantalla() {
    }

    @Override
    public void onPause() {
        super.onPause();
        if(video.isPlaying()){
            video.stopPlayback();
        }
        return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragmentvideo, container, false);
        Uri urivideo = Uri.parse("android.resource://com.acme.eac3ccl/raw/videoska");
        video = (VideoView) fragmento.findViewById(R.id.videofrag) ;
        video.setVideoURI(urivideo);
        video.requestFocus();

        Button botonplay = fragmento.findViewById(R.id.buttonplay);
        Button botonstop = fragmento.findViewById(R.id.buttonstop);
        Button botonpause = fragmento.findViewById(R.id.buttonpause);


        botonplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(video.isPlaying()){
                    //NO fa res si està reproduint
                } else {
                    video.start();
                }

            }
        });

        botonpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(video.isPlaying()){
                    video.pause();
                }

            }
        });

        botonstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(video.isPlaying()){
                    video.stopPlayback();
                    video.resume();
                } else {
                    video.resume();
                }

            }
        });

        return fragmento;


    }
}
