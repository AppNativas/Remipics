package com.example.remipics.auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.remipics.DashPrincipal;
import com.example.remipics.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        AppCompatTextView backText = findViewById(R.id.back_text);
        backText.setOnClickListener(v -> onBackPressed());

        findViewById(R.id.rememberpass_text).setOnClickListener(v -> onRedirectionRemember());

        findViewById(R.id.login_send_button).setOnClickListener(v -> onRedirectionUserDashboard());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    protected void onRedirectionRemember() {
        // Redirect to the remember password activity
        startActivity(new Intent(this, RememberActivity.class));
    }

    protected void onRedirectionUserDashboard() {
        // Redirect to the user dashboard activity

        if(isUserLoggedIn()) {
            startActivity(new Intent(this, DashPrincipal.class));
        }else{
            showDialog("User not found");
        }

    }

    protected boolean isUserLoggedIn() {
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Add your logic to check if the user is logged in
        return !username.isEmpty() && !password.isEmpty();
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

}