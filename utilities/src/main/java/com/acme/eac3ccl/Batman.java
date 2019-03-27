package com.acme.eac3ccl;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class Batman extends android.support.v4.app.Fragment {

    public Batman (){
        //Constructor clase
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragmentbatman, container, false);

        final ImageView imatge = (ImageView)fragmento.findViewById(R.id.animaciobatman);
        final Animation animacio = AnimationUtils.loadAnimation(getActivity(), R.anim.batmananimation);

        imatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imatge.startAnimation(animacio);
            }
        });


        return fragmento;

    }
}
