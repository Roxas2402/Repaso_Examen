package com.example.repaso_examen;

import android.content.Intent;
import android.os.Bundle;

import com.example.repaso_examen.configuraciones.Constants;
import com.example.repaso_examen.modelos.Inmueble;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.repaso_examen.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //1: Creamos la clase Inmueble
    //1.01: Creamos la clase constantes
    //2: Creamos el ArrayList de Inmueble y el Intent
    private ArrayList<Inmueble> inmueblesList;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addInmuebleLauncher;
    //12+1: Creamos el Intent de editar inmueble
    private ActivityResultLauncher<Intent> editInmuebleLauncher;
    //14: Creamos la actividad EditInmuebleActivity (con su respectiva clase) y la personalizamos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //3: Inicializamos el ArrayList
        inmueblesList = new ArrayList<>();

        setSupportActionBar(binding.toolbar);

        //10: Crear la actividad en la que se muestra el inmueble creado (inmueble_view_model.xml) y borramos la clase que se crea
        //11: Inicializamos el launcher
        inicializaLaunchers();

        //4: Creamos la actividad AddInmuebleActivity
        //4.01: Configuramos el botón que nos llevará a la actividad AddInmuebleActivity
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInmuebleLauncher.launch(new Intent(MainActivity.this, AddInmuebleActivity.class));
            }
        });
    }

    private void inicializaLaunchers() {
        addInmuebleLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null) {
                                if (result.getData().getExtras() != null) {
                                    if (result.getData().getExtras().getSerializable(Constants.INMUEBLE) != null) {
                                        //Extraigo el inmueble
                                        Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable(Constants.INMUEBLE);
                                        //Agrego el inmueble
                                        inmueblesList.add(inmueble);
                                        //Muestro los inmuebles
                                        //12: Mostrará los datos que hay en el bundle
                                        muestraInmueblesContenido();
                                    } else {
                                        Toast.makeText(MainActivity.this, "NO HAY DATOS", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "NO HAY BUNDLE EN EL INTENT", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "NO HAY INTENT", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "VENTANA CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

        );

        //15: Configuramos el launcher de EditActivity
        //15.01: Creamos la variable final POSICION, para saber la posición en la que está el inmueble (podemos haber creado 100 inmuebles)
        //TODO: 15.02: Crear el evento de Click dentro de muestraInmueblesContenido()
        editInmuebleLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null && result.getData().getExtras() != null) {
                                Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable(Constants.INMUEBLE);
                                int posicion = result.getData().getExtras().getInt(Constants.POSICION);
                                inmueblesList.set(posicion, inmueble);
                                muestraInmueblesContenido();
                            }
                        }
                    }
                }
        );
    }

    private void muestraInmueblesContenido() {
        //12.01: Creamos el ScrollView en el content_main.xml y renombramos la cosa esa rara en el activity_main.xml
        binding.contentMain.contenedor.removeAllViews();

        //Por cada inmueble guardado en la lista, crearemos una "vista" con los datos del inmueble
        for (int i = 0; i < inmueblesList.size(); i++) {
            Inmueble inmueble = inmueblesList.get(i);

            View inmuebleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.inmueble_view_model, null);
            TextView lblDireccion = inmuebleView.findViewById(R.id.lblDireccionInmuebleModel);
            TextView lblNumero = inmuebleView.findViewById(R.id.lblNumeroInmuebleModel);
            TextView lblCiudad = inmuebleView.findViewById(R.id.lblCiudadInmuebleModel);
            TextView lblProvincia = inmuebleView.findViewById(R.id.lblProvinciaInmuebleModel);
            RatingBar rbValoracion = inmuebleView.findViewById(R.id.rbValoracionInmuebleModel);

            //12.02: Mostramos el contenido
            lblDireccion.setText(inmueble.getDireccion());
            lblNumero.setText(String.valueOf(inmueble.getNumero()));
            lblCiudad.setText(inmueble.getCiudad());
            lblProvincia.setText(inmueble.getProvincia());
            rbValoracion.setRating(inmueble.getValoracion());

            //15.02: Creamos el evento de Click
            int finalI = i;
            inmuebleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, EditInmuebleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.INMUEBLE, inmueble);
                    bundle.putInt(Constants.POSICION, finalI);
                    intent.putExtras(bundle);
                    editInmuebleLauncher.launch(intent);
                }
            });

            binding.contentMain.contenedor.addView(inmuebleView);

        }
    }

}