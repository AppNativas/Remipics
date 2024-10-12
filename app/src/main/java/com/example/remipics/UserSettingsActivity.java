package com.example.remipics;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserSettingsActivity extends AppCompatActivity {

    private FrameLayout profile_frame;
    private ImageView addIcon;
    private Button changePasswordButton;
    private Button changeEmailButton;
    private Button logoutButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        // Referencias a los elementos
        profile_frame = findViewById(R.id.profile_frame);
        addIcon = findViewById(R.id.add_icon);
        changePasswordButton = findViewById(R.id.change_password_button);
        changeEmailButton = findViewById(R.id.change_email_button);
        logoutButton = findViewById(R.id.logout_button);

        // Eventos de los botones
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para cambiar contraseña
                Toast.makeText(UserSettingsActivity.this, "Cambiar Contraseña", Toast.LENGTH_SHORT).show();
            }
        });

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para cambiar correo
                Toast.makeText(UserSettingsActivity.this, "Cambiar Correo", Toast.LENGTH_SHORT).show();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para cerrar sesión
                Toast.makeText(UserSettingsActivity.this, "Cerrar Sesión", Toast.LENGTH_SHORT).show();
            }
        });

        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para cambiar imagen de perfil
                Toast.makeText(UserSettingsActivity.this, "Cambiar Imagen de Perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

