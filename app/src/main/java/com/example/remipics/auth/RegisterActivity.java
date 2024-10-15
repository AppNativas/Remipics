package com.example.remipics.auth;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.remipics.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

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
        registerButton.setOnClickListener(v -> {
            // Handle registration logic here
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
}