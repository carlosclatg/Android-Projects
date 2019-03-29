package com.acme.fragmenttest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // num Fragment
    int mStackPosition = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botón de añadir fragments
        Button button = (Button)findViewById(R.id.newFragment);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addFragment();
            }
        });

        if (savedInstanceState == null) {
            // añadir el primer fragment
            Fragment newFragment = SimpleFragment.newInstance(mStackPosition);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragmentShow, newFragment).commit();
        } else {
            mStackPosition = savedInstanceState.getInt("position");
        }
        //botón para mostrar dialogo AlertDialog
        Button buttonDialog = (Button)findViewById(R.id.showDialog);
        buttonDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog();
            }
        });
        //botón para mostrar dialogo Fragment
        Button buttonDialogF = (Button)findViewById(R.id.showDialogF);
        buttonDialogF.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialogF();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    void addFragment() {
        mStackPosition++;
        // Instanciamos nuevo Fragment
        Fragment newFragment = SimpleFragment.newInstance(mStackPosition);
        // Se añade el Fragment a la actividad
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentShow, newFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // añadimos la transacción a la pila, conservamos histórico.
        ft.addToBackStack(null);
        ft.commit();
    }










    //CLASE DE FRAGMENT.
        public static class SimpleFragment extends Fragment {

            //Datos de número de instancias creadas
            static int mNum;
            //El método new Instance crea una una nueva instancia de la classe SimpleFragment
            //https://www.tutorialspoint.com/java/lang/class_newinstance.htm
            static SimpleFragment newInstance(int number) {
                SimpleFragment f = new SimpleFragment();
                // Mantenemos el número para usarlo en cualquier momento.
                Bundle args = new Bundle();
                args.putInt("num", number);
                f.setArguments(args);
                return f;
            }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // obtenemos el número que se había pasado como argumento en
            // la creación de la instancia
            mNum = getArguments().getInt("num");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //View para el fragment en cuestión
            View v = null;
            // dependiendo de si es par o impar mostramos distintos layouts
            if (mNum % 2 == 0){
                v = inflater.inflate(R.layout.layout1, container, false);
                //El id no se puede repetir pq siempre hablamos de este layout particular.
                View tv = v.findViewById(R.id.text);
                //informamos el número de Fragment
                ((TextView)tv).setText("Fragmento número #" + mNum);
            }
            else{
                v = inflater.inflate(R.layout.layout2, container, false);
                //El id no se puede repetir pq siempre hablamos de este layout particular.
                View tv = v.findViewById(R.id.text2);
                //informamos el número de Fragment
                ((TextView)tv).setText("Fragmento número #" + mNum);
            }
            return v;
        }
    }

    void showDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setIcon(R.mipmap.ic_launcher);
                b.setTitle("Selecciona acción a realizar");
                b.setPositiveButton("Nuevo",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                addFragment();
                            }
                        }
                );
                b.setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // no hacer nada
                            }
                        }
                );
                b.setNeutralButton("Atrás",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getFragmentManager().popBackStack();
                            }
                        }
                );
                b.create().show();
    }
    void showDialogF() {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(
                "Cadena de ejemplo como parámetro");
        newFragment.show(getFragmentManager(), "dialog");
    }







    //CLASE DE DIALOGFRAGMENT
    public static class MyAlertDialogFragment extends DialogFragment {
        public static MyAlertDialogFragment newInstance(String valor) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            // podemos aprovechar para pasar parámetros mediante bundle
	/*
	Bundle bundle = new Bundle();
	bundle("clave", valor);
	frag.setArguments(bundle);
	*/
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = (LayoutInflater) 	getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

            ViewGroup dlgview = (ViewGroup) inflater.inflate(
                    R.layout.dialog, null);
            // botón nuevo Fragment
            Button buttonShow = (Button)dlgview.findViewById(R.id.newFrag);
            buttonShow.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ((MainActivity)getActivity() ).addFragment();
                }
            });
            // botón cancelar
            Button buttonCancel = (Button)dlgview.findViewById(R.id.cancel);
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
            // botón ir a Fragment anterior
            Button buttonBack = (Button)dlgview.findViewById(R.id.back);
            buttonBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });

            // asignar el dialog a la vista
            return new AlertDialog.Builder(getActivity()).setView(dlgview).create();
        }
    }

}
