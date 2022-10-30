package com.example.repaso_examen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.repaso_examen.databinding.ActivityAddInmuebleBinding;
import com.example.repaso_examen.modelos.Inmueble;

public class AddInmuebleActivity extends AppCompatActivity {

    //5: Creamos el binding para que reconozca los IDs
    private ActivityAddInmuebleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inmueble);

        //5.01: Instanciamos el binding
        binding = ActivityAddInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //6: Configuramos el botón Cancelar
        binding.btnCancelarAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        //7: Configuramos el botón Crear
        binding.btnCrearAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //8: Creamos un inmueble a partir de los datos introducidos en el diseño
                Inmueble inmueble = crearInmueble();
            }
        });
    }

    //8.01: Instanciamos las IDs
    private Inmueble crearInmueble() {
        if (
            binding.txtDireccionAddInmueble.getText().toString().isEmpty() ||
            binding.txtNumeroAddInmueble.getText().toString().isEmpty() ||
            binding.txtCPAddInmueble.getText().toString().isEmpty() ||
            binding.txtProvinciaAddInmueble.getText().toString().isEmpty() ||
            binding.txtCiudadAddInmueble.getText().toString().isEmpty()
        )
        return null;

        return new Inmueble(
                binding.txtDireccionAddInmueble.getText().toString(),
                Integer.parseInt(binding.txtNumeroAddInmueble.getText().toString()),
                binding.txtCiudadAddInmueble.getText().toString(),
                binding.txtProvinciaAddInmueble.getText().toString(),
                binding.txtCPAddInmueble.getText().toString(),
                binding.rbValoracionAddInmueble.getRating()
        );
    }
}