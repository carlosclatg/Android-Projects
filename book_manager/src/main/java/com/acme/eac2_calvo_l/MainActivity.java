package com.acme.eac2_calvo_l;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_ITEM = 1;
    public static final int EDIT_ITEM = 2;
    public static final int SHOW_ITEM = 3;

    public static DataBaseHelper mDbHelper;
    public RecyclerView mRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Abrir la BD
        try {
            filldata();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void filldata() throws SQLException {
        mDbHelper = new DataBaseHelper(MainActivity.this);
        mDbHelper.open();
        Cursor itemCursor = mDbHelper.getItems();
        //ResultList fem la consulta a la BD de tots els llibres i els obtenim

        ArrayList<Llibre> resultList = new ArrayList<Llibre>();
        //procesamos resultado
        while (itemCursor.moveToNext()){
            //REcollim de cadascun dels llibres
            String titol = itemCursor.getString(itemCursor.getColumnIndex(DataBaseHelper.SL_TITOL));
            String autor = itemCursor.getString(itemCursor.getColumnIndex(DataBaseHelper.SL_AUTOR));
            String editorial = itemCursor.getString(itemCursor.getColumnIndex(DataBaseHelper.SL_EDITORIAL));
            String tipus = itemCursor.getString(itemCursor.getColumnIndex(DataBaseHelper.SL_TIPUSTAPA));
            String preu = itemCursor.getString(itemCursor.getColumnIndex(DataBaseHelper.SL_PREU));
            Llibre item = new Llibre(titol,autor,editorial,tipus,preu);
            resultList.add(item);

        }

        //CAS BD buida
        if(resultList == null){
            return;
        }
        itemCursor.close();

        mDbHelper.close();
        mRecView = (RecyclerView) findViewById(R.id.rView);
        final ItemAdapter adapter = new ItemAdapter(this, resultList);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mRecView.getChildAdapterPosition(view);
                Llibre llibre = adapter.getItemAt(position);
                String titol = llibre.getTitol();
                String autor = llibre.getAutors();
                String editorial = llibre.getEditorial();
                String tipus = llibre.getTipus();
                String preu = llibre.getPreu();
                Intent i = new Intent(MainActivity.this, Activitydetall_llibre.class);
                i.putExtra("titol", titol);
                i.putExtra("autor", autor);
                i.putExtra("editorial", editorial);
                i.putExtra("tipus", tipus);
                i.putExtra("preu", preu);
                startActivity(i);
            }
        });
        adapter.notifyDataSetChanged();
        mRecView.setAdapter(adapter);
        mRecView.setLayoutManager(new LinearLayoutManager(this));
        mRecView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    protected void onResume() {
        super.onResume();
        try{
            filldata();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //PARTE DE MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId(); //Almacenamos el id pulsado
        if (id == R.id.add){ //Inserir llibre
            Intent i = new Intent(MainActivity.this, ActivityAfegir.class);
            startActivity(i);
        }
        if (id == R.id.search) { //Cercar llibre
            Intent i = new Intent(MainActivity.this, ActivityCerca.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
