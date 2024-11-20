package com.example.remipics.auth;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.remipics.DashPrincipal;
import com.example.remipics.R;
import com.example.remipics.controllers.ApiClient;
import com.example.remipics.entities.ApiResponse;
import com.example.remipics.entities.LoginRequest;
import com.example.remipics.entities.RegisterRequest;
import com.example.remipics.interfaces.ApiUserService;
import com.example.remipics.utils.DateConverter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ApiUserService apiUserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewSwitcher viewSwitcher = findViewById(R.id.viewSwitcher);
        TextInputEditText birthDateEditText = findViewById(R.id.birthDate);

        MaterialButton nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> viewSwitcher.showNext());

        MaterialButton registerButton = findViewById(R.id.register_send_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            EditText nameEditText = findViewById(R.id.firstName);
            EditText lastNameEditText = findViewById(R.id.lastName);
            EditText birthDateEditText = findViewById(R.id.birthDate);
            EditText emailEditText = findViewById(R.id.email);
            TextInputLayout passwordLayout = findViewById(R.id.passwordLayout);
            EditText passwordEditText = passwordLayout.getEditText();


            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String birthDate = birthDateEditText.getText().toString().trim();

                DateConverter dateConverter = new DateConverter();
                birthDate = dateConverter.convertDate(birthDate);

                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String sex = "N/A";
                String address = "N/A";

                RegisterRequest registerRequest = new RegisterRequest(email, name, lastName, birthDate,sex, password, address);
                apiUserService = ApiClient.getClient().create(ApiUserService.class);
                Call<ApiResponse> call = apiUserService.register(registerRequest);

                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse registerResponse = response.body();
                            //Log.d("LoginActivity", "Response: " + loginResponse.toString());
                            if (registerResponse.isSuccess()) {
                                nameEditText.getText().clear();
                                lastNameEditText.getText().clear();
                                birthDateEditText.getText().clear();
                                passwordEditText.getText().clear();
                                emailEditText.getText().clear();

                                String registerUser = response.body().getData().toString();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);;
                                Toast.makeText(RegisterActivity.this, "Register successful, por favor inicia sesion", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            } else {
                                showDialog("1. User not found");
                                Toast.makeText(RegisterActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        showDialog("2. User not found");
                        Toast.makeText(RegisterActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        birthDateEditText.setOnClickListener(v -> showDatePickerDialog());

        //#region button return
        AppCompatTextView backText = findViewById(R.id.back_text);
        backText.setOnClickListener(v -> onBackPressed());
        //endregion

    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    TextInputEditText birthDateEditText = findViewById(R.id.birthDate);
                    birthDateEditText.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    public void showDialog(String message) {
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