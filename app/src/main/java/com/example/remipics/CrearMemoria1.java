package com.example.remipics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CrearMemoria1 extends AppCompatActivity {

    private EditText tituloEditText;
    private EditText descripcionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_memoria1);

        // Inicializar los campos
        tituloEditText = findViewById(R.id.label_nom_m);
        descripcionEditText = findViewById(R.id.label_desc);

        // Configurar el botón para avanzar
        ImageView avanzarButton = findViewById(R.id.icon_avan);
        avanzarButton.setOnClickListener(v -> validarCamposYAvanzar());
    }

    private void validarCamposYAvanzar() {
        String titulo = tituloEditText.getText().toString().trim();
        String descripcion = descripcionEditText.getText().toString().trim();

        if (titulo.isEmpty()) {
            Toast.makeText(this, "El título es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        if (descripcion.isEmpty()) {
            Toast.makeText(this, "La descripción es obligatoria", Toast.LENGTH_SHORT).show();
            return;
        }

        // Si los campos están completos, redirigir a CrearMemoria2
        Intent intent = new Intent(CrearMemoria1.this, CrearMemoria2.class);
        startActivity(intent);
    }
}
