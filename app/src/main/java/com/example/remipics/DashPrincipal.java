package com.example.remipics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.remipics.auth.HomeActivity;
import com.example.remipics.auth.LoginActivity;

public class DashPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dash_principal);

        ImageView btnCreateMemory = findViewById(R.id.icon_crear_album);

        btnCreateMemory.setOnClickListener(v -> {
            // Redirect to the create memory activity
            startActivity(new Intent(this, CrearMemoria2.class));
        });

        // Get the Intent that started this activity
        Intent intent = getIntent();
        // Extract the data from the Intent
        String data = intent.getStringExtra("token");
        // Use the data
        if (data != null) {
            Log.d("MainActivity", "Received data: " + data);
            Toast.makeText(this, "Received data: " + data, Toast.LENGTH_SHORT).show();
        } else {
            Intent intentFaild = new Intent(DashPrincipal.this, HomeActivity.class);
            Log.d("MainActivity", "No data received");
            startActivity(intentFaild);

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}