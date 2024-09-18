package com.example.remipics;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button changePasswordButton;
    private TextView passwordRequirements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password); // Enlaza el layout XML

        // Inicializa los componentes
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        changePasswordButton = findViewById(R.id.change_password_button);
        passwordRequirements = findViewById(R.id.password_requirements);

        // Acción del botón "Cambiar Contraseña"
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndChangePassword();
            }
        });
    }

    private void validateAndChangePassword() {
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        // Verifica si los campos están vacíos
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor, ingrese la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Por favor, confirme la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica si las contraseñas coinciden
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica la longitud de la contraseña (mínimo 8 caracteres)
        if (password.length() < 8) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        // Puedes agregar validaciones adicionales aquí (letras, números, caracteres especiales)

        // Si todas las validaciones son correctas, muestra un mensaje de éxito
        Toast.makeText(this, "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show();

        // Aquí puedes añadir la lógica para cambiar la contraseña, por ejemplo, llamando a una API
    }
}
