package com.example.repaso_examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.repaso_examen.configuraciones.Constants;
import com.example.repaso_examen.databinding.ActivityEditInmuebleBinding;
import com.example.repaso_examen.modelos.Inmueble;

public class EditInmuebleActivity extends AppCompatActivity {

    //16: Creamos el binding
    private ActivityEditInmuebleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //16.01: Inflamos el binding TODO: PREGUNTAR EN CLASE
        binding = ActivityEditInmuebleBinding.inflate(getLayoutInflater());
        //16.02: Aquí decimos de dónde ha de coger los IDs
        setContentView(binding.getRoot());

        //16.03: Creamos el Intent, el Bundle y configuramos el Binding
        Intent intentDelMain = getIntent();
        Bundle bundle = intentDelMain.getExtras();
        Inmueble inmueble = (Inmueble) bundle.getSerializable(Constants.INMUEBLE);

        binding.txtDireccionEditInmueble.setText(inmueble.getDireccion());
        binding.txtNumeroEditInmueble.setText(String.valueOf(inmueble.getNumero()));
        binding.txtCPEditInmueble.setText(inmueble.getCp());
        binding.txtProvinciaEditInmueble.setText(inmueble.getProvincia());
        binding.txtCiudadEditInmueble.setText(inmueble.getCiudad());
        binding.rbValoracionEditInmueble.setRating(inmueble.getValoracion());

        //17: Configuramos el botón guardarInmueble
        binding.btnModificarEditInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //17.01: Creamos la clase para actualizar el inmueble que queremos modificar
                Inmueble inmuebleUpdated = crearInmueble();
                if (inmuebleUpdated != null) {
                    Intent intent = new Intent();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable(Constants.INMUEBLE, inmuebleUpdated);
                    int posicion = bundle.getInt(Constants.POSICION);
                    bundle1.putInt(Constants.POSICION, posicion);
                    intent.putExtras(bundle1);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private Inmueble crearInmueble() {
        if (
            binding.txtDireccionEditInmueble.getText().toString().isEmpty() ||
            binding.txtCiudadEditInmueble.getText().toString().isEmpty() ||
            binding.txtCPEditInmueble.getText().toString().isEmpty() ||
            binding.txtNumeroEditInmueble.getText().toString().isEmpty() ||
            binding.txtProvinciaEditInmueble.getText().toString().isEmpty()
        ) {
            return null;
        } else {

            return new Inmueble(
                    binding.txtDireccionEditInmueble.getText().toString(),
                    Integer.parseInt(binding.txtNumeroEditInmueble.getText().toString()),
                    binding.txtCPEditInmueble.getText().toString(),
                    binding.txtCiudadEditInmueble.getText().toString(),
                    binding.txtProvinciaEditInmueble.getText().toString(),
                    binding.rbValoracionEditInmueble.getRating()
            );
        }
    }
}