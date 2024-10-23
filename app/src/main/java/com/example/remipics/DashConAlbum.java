package com.example.remipics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashConAlbum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dash_con_album);

        View goMemory = findViewById(R.id.img_portada_album);
        ImageView btnCreateMemory = findViewById(R.id.icon_crear_album);

        btnCreateMemory.setOnClickListener(v -> {
            // Redirect to the create memory activity
            startActivity(new Intent(this, CrearAlbum.class));
        });

        goMemory.setOnClickListener(v -> {
            // Redirect to the create memory activity
            startActivity(new Intent(this, DashMemoria.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}