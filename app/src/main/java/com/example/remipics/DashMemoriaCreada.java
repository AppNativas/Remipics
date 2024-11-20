package com.example.remipics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DashMemoriaCreada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_memoria_creada);

        // Inicializar el ImageView
        ImageView iconCrearMemoria = findViewById(R.id.icon_crear_memoria);

        // Configurar el evento onClick para redirigir a CrearMemoria1
        iconCrearMemoria.setOnClickListener(v -> {
            Intent intent = new Intent(DashMemoriaCreada.this, CrearMemoria1.class);
            startActivity(intent);
        });
    }
}
