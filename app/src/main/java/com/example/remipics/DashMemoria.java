package com.example.remipics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashMemoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dash_memoria);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener el nombre del álbum desde el Intent
        String albumName = getIntent().getStringExtra("albumName");
        if (albumName == null || albumName.isEmpty()) {
            albumName = "Sin título";
        }

        // Actualizar el TextView con el nombre del álbum
        TextView tituloAlbum = findViewById(R.id.titulo_album);
        tituloAlbum.setText(albumName);

        // Configurar redirección al CrearMemoria2
        ImageView iconCrearMemoria = findViewById(R.id.icon_crear_memoria);
        iconCrearMemoria.setOnClickListener(v -> {
            Intent intent = new Intent(DashMemoria.this, CrearMemoria1.class);
            startActivity(intent);
        });
    }
}
