package com.example.repaso_examen;

import android.content.Intent;
import android.os.Bundle;

import com.example.repaso_examen.configuraciones.Constants;
import com.example.repaso_examen.modelos.Inmueble;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


import com.example.repaso_examen.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //1: Creamos la clase Inmueble
    //1.01: Creamos la clase constantes
    //2: Creamos el ArrayList de Inmueble y el Intent
    private ArrayList<Inmueble> inmueblesList;
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addInmuebleLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //3: Inicializamos el ArrayList
        inmueblesList = new ArrayList<>();

        setSupportActionBar(binding.toolbar);

        //TODO: DESPUÉS DE HACER EL ADDINMUEBLE HAY QUE CONFIGURAR ESTO Y PONERLE NÚMERO
        //5: Inicializamos el launcher
        inicializaLaunchers();

        //4: Configuramos el botón
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
                            if (result.getData().getExtras().getSerializable(Constants.INMUEBLE) != null) {
                                Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable(Constants.INMUEBLE);
                                inmueblesList.add(inmueble);
                                //muestraInmuebleContenido();
                            }
                        }
                    }
                }
        );
    }

}