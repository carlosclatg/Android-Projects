package com.acme.eac3ccl;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Foto extends Fragment {

    private static final int APP_CAMERA = 0;
    private static final int APPCAMERAREQUEST = 123;
    private ImageView quadrefoto;
    View fragmento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmento = inflater.inflate(R.layout.fragmentfoto, container, false);


        quadrefoto = fragmento.findViewById(R.id.ViewFoto);
        quadrefoto.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                fesFoto();
            }
        });
        return fragmento;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void fesFoto() {

        if(getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, APP_CAMERA);
        } else {
            String[] permissionRequest = {Manifest.permission.CAMERA};
            requestPermissions(permissionRequest,APPCAMERAREQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == APPCAMERAREQUEST){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, APP_CAMERA);
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.sinpermiso), Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            Toast.makeText(getContext(), getResources().getString(R.string.nophototaken), Toast.LENGTH_SHORT);
        } else {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            quadrefoto.setImageBitmap(bitmap);
        }

    }
}



