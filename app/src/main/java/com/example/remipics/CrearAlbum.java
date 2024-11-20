package com.example.remipics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CrearAlbum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_album);

        Button btnCreateMemory = findViewById(R.id.btn_aceptar_ca);
        EditText albumNameInput = findViewById(R.id.label_nom_album);

        btnCreateMemory.setOnClickListener(v -> {
            String albumName = albumNameInput.getText().toString().trim();
            if (!albumName.isEmpty()) {
                Intent intent = new Intent(this, DashConAlbum.class);
                intent.putExtra("ALBUM_NAME", albumName);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Por favor, ingresa un nombre para el álbum", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnCancelar = findViewById(R.id.btn_cancelar_ca);
        btnCancelar.setOnClickListener(v -> {
            startActivity(new Intent(this, DashPrincipal.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}