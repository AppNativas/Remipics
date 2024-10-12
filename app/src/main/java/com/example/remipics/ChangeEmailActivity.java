package com.example.remipics;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ChangeEmailActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button changeEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);  // Asocia el XML a la actividad

        // Inicializa los elementos de la interfaz
        emailInput = findViewById(R.id.email_input);
        changeEmailButton = findViewById(R.id.change_email_button);

        // Listener para el botón de cambiar correo
        changeEmailButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();

            // Verifica si el campo de correo está vacío
            if (email.isEmpty()) {
                Toast.makeText(ChangeEmailActivity.this, "Por favor, ingresa un correo válido", Toast.LENGTH_SHORT).show();
            } else {
                // Lógica para cambiar el correo
                // Aquí puedes implementar la lógica para cambiar el correo en la base de datos o backend
                Toast.makeText(ChangeEmailActivity.this, "Correo cambiado a: " + email, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
