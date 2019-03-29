package com.acme.hilodpersistenciasonido;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.WindowManager;


public class Partida extends android.support.v7.widget.AppCompatImageView {

    private int acel;
    private Bitmap pelota, fondo;
    private int tam_pantX, tam_pantY, posX, posY, velX, velY;
    private int tamPelota;
    boolean pelota_sube;

    public Partida(Context contexto, int nivel_dificultad){

        super(contexto);

        WindowManager manejador_ventana=(WindowManager) contexto.getSystemService(Context.WINDOW_SERVICE);
        //Interfaz WindowManager para obtener datos de la pantalla (tamaño, resolución...)
        //SE obtiene a través de la clase display

        Display pantalla=manejador_ventana.getDefaultDisplay();

        Point maneja_coord=new Point(); //Integra dos coordenadas

        pantalla.getSize(maneja_coord); //determina tamaño pantalla donde se ejecuta la app, argumento tipo Point.

        tam_pantX=maneja_coord.x; //Obtiene el ancho

        tam_pantY=maneja_coord.y; //Obtiene el alto


        //Construye un layout a través de código.

        BitmapDrawable dibujo_fondo=(BitmapDrawable) ContextCompat.getDrawable(contexto, R.drawable.paisaje_1); //ContextCompat es un helper para obtener datos del contexto.

        fondo=dibujo_fondo.getBitmap();// mirar en api getBitmap en clase BitmapDrawable. esto nos lleva a la siguiente instr.

        fondo=Bitmap.createScaledBitmap(fondo, tam_pantX, tam_pantY, false);//mirar en clase Bitmap


        BitmapDrawable objetoPelota=(BitmapDrawable)ContextCompat.getDrawable(contexto, R.drawable.pelota_1);

        pelota=objetoPelota.getBitmap();

        tamPelota=tam_pantY/3; //Hacemos la pelota en función del tamaño de la pantalla.

        pelota=Bitmap.createScaledBitmap(pelota, tamPelota, tamPelota, false);

        posX=tam_pantX/2-tamPelota/2;

        posY=0-tamPelota;

        acel=nivel_dificultad*(maneja_coord.y/400); //aceleración de la caída en f(dificultad)


    }

    public boolean toque(int x, int y){

        if(y<tam_pantY/3) return false;

        if(velY<=0) return false;

        if(x<posX || x> posX+tamPelota) return false;

        if(y<posY || y>posY+tamPelota) return false;

        velY=-velY;

        double desplX=x-(posX+tamPelota/2);

        desplX=desplX/(tamPelota/2)*velY/2;

        velX+=(int)desplX;

        return true;
    }


    public boolean movimientoBola(){

        if(posX<0-tamPelota){

            posY=0-tamPelota;

            velY=acel;
        }

        posX+=velX;

        posY+=velY;

        if(posY>=tam_pantY) return true; //devuelve true si el juego ha terminado.

        if(posX+tamPelota<0 || posX>tam_pantX) return true; //Si ha salido por los lados.

        if(velY<0) pelota_sube=true;

        if(velY>0 && pelota_sube){

            pelota_sube=false;

        }

        velY+=acel;

        return false;
    }

    protected void onDraw(Canvas lienzo){

        lienzo.drawBitmap(fondo, 0,0, null); //Pinta fondo

        lienzo.drawBitmap(pelota, posX, posY, null); //Pinta pelota


    }
}

