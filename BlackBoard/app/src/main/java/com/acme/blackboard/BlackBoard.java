package com.acme.blackboard;

import android.Manifest;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class BlackBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private HashMap<Integer, Integer> mColors= new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(BlackBoard.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                new AlertDialog.Builder(BlackBoard.this)
                        .setTitle("Grosos del pincel?")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String m_Text = input.getText().toString();
                                float strokewidht = Float.parseFloat(m_Text);
                                Board board = findViewById(R.id.board_view);
                                board.setStrokeWidth(strokewidht);
                            }
                        })
                        .show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mColors.put(R.id.pal_white, Color.WHITE);
        mColors.put(R.id.pal_blue, Color.BLUE);
        mColors.put(R.id.pal_green, Color.GREEN);
        mColors.put(R.id.pal_red, Color.RED);
        mColors.put(R.id.pal_yellow, Color.YELLOW);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.black_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Board board = (Board) findViewById(R.id.board_view);
        switch (item.getItemId()){
            case R.id.action_save:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    final EditText input = new EditText(this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    new AlertDialog.Builder(this)
                            .setTitle("Name of the photo")
                            .setView(input)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String m_Text = input.getText().toString();
                                            saveFile(m_Text);
                                        }
                                    })
                            .show();
                }
                break;
            case R.id.action_clean:
                board.clear(this);
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    private void saveFile(String nombre) {
        Board board = (Board) findViewById(R.id.board_view);
        Bitmap bitmap = board.getBitmap(); //SE recupera Bitmap
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        String nom = ("" + nombre +".png");
        File file = new File(dir, nom);
        OutputStream out;
        try{
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(BlackBoard.this, getResources().getString(R.string.savedOn) + dir, Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            e.printStackTrace();
            new AlertDialog.Builder(this).setMessage("ERROR: " + e.getLocalizedMessage())
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Board board = (Board) findViewById(R.id.board_view);
        if(id == R.id.pal_clean){
            board.setEraser();
        } else {
            board.setPaintColor(mColors.get(id));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
