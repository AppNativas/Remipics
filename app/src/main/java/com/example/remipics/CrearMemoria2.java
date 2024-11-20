package com.example.remipics;

// Importaciones necesarias
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.Calendar;

public class CrearMemoria2 extends AppCompatActivity {

    private ImageView imageView;
    private PopupWindow popupWindow;

    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;

    private TextView locationTextView;
    private TextView enlaceFechaTextView;

    private Bitmap capturedImageBitmap;
    private Uri selectedImageUri;
    private String selectedDate;
    private String selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_memoria2);

        // Inicializar componentes de cámara
        imageView = findViewById(R.id.icon_img_m);
        imageView.setOnClickListener(v -> showModal());

        // Inicializar el TextView para la fecha
        enlaceFechaTextView = findViewById(R.id.enlace_fecha_m);
        enlaceFechaTextView.setOnClickListener(v -> showDatePicker());

        // Inicializar el TextView para la ubicación
        locationTextView = findViewById(R.id.enlace_ubi_m);
        locationTextView.setOnClickListener(v -> {
            Intent intent = new Intent(CrearMemoria2.this, LocationPickerActivity.class);
            startActivityForResult(intent, 200);
        });

        // Botón para guardar memoria
        Button saveButton = findViewById(R.id.btn_aceptar_cm);
        saveButton.setOnClickListener(v -> saveDataToJSON());
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            enlaceFechaTextView.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            selectedLocation = data.getStringExtra("direccion");
            locationTextView.setText(selectedLocation);
        }
    }

    private void showModal() {
        try {
            View modalView = LayoutInflater.from(this).inflate(R.layout.modal_opciones, null);
            popupWindow = new PopupWindow(modalView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAsDropDown(imageView, 0, 0);

            Button openCameraOption = modalView.findViewById(R.id.btnOpenCamera);
            openCameraOption.setOnClickListener(v -> {
                checkCameraPermission();
                popupWindow.dismiss();
            });

            Button openGalleryOption = modalView.findViewById(R.id.btnOpenGallery);
            openGalleryOption.setOnClickListener(v -> {
                checkGalleryPermission();
                popupWindow.dismiss();
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error al mostrar el modal: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            cameraActivityResultLauncher.launch(cameraIntent);
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryActivityResultLauncher.launch(galleryIntent);
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            openCamera();
        }
    }

    private void checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
        } else {
            openGallery();
        }
    }

    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    capturedImageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(capturedImageBitmap);
                }
            }
    );

    private final ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    imageView.setImageURI(selectedImageUri);
                }
            }
    );

    private void saveDataToJSON() {
        try {
            JSONObject memoryData = new JSONObject();
            memoryData.put("fecha", selectedDate);
            memoryData.put("ubicacion", selectedLocation);
            if (capturedImageBitmap != null) {
                memoryData.put("imagen", "captured_image_data"); // Puedes procesar la imagen según tu lógica
            } else if (selectedImageUri != null) {
                memoryData.put("imagen", selectedImageUri.toString());
            }

            JSONArray memoryList = new JSONArray();
            memoryList.put(memoryData);

            FileOutputStream fos = openFileOutput("memorias.json", MODE_PRIVATE);
            fos.write(memoryList.toString().getBytes());
            fos.close();

            Toast.makeText(this, "Memoria guardada exitosamente", Toast.LENGTH_SHORT).show();

            // Redirigir al DashMemoriaCreada
            Intent intent = new Intent(this, DashMemoriaCreada.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else if (requestCode == GALLERY_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
        }
    }
}
