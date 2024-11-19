package com.example.remipics;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CrearMemoria2 extends AppCompatActivity  {

    private ImageView imageView;
    private PopupWindow popupWindow;

    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;

    // Geolocalización
    private TextView locationTextView;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private static final int CODIGO_PERMISO_UBICACION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_memoria2);

        // Inicializar componentes de cámara
        imageView = findViewById(R.id.icon_img_m);
        imageView.setOnClickListener(v -> showModal());


        // Inicializar el TextView
        TextView locationTextView = findViewById(R.id.enlace_ubi_m);

        // Abrir LocationPickerActivity al hacer clic
        locationTextView.setOnClickListener(v -> {
            Intent intent = new Intent(CrearMemoria2.this, LocationPickerActivity.class);
            startActivityForResult(intent, 200); // Código de solicitud para la actividad
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            String direccion = data.getStringExtra("direccion");
            TextView locationTextView = findViewById(R.id.enlace_ubi_m);
            locationTextView.setText(direccion); // Mostrar la dirección seleccionada
        }
    }







    // Mostrar el modal para elegir entre cámara y galería
    private void showModal() {
        try {
            // Inflar el diseño del modal
            View modalView = LayoutInflater.from(this).inflate(R.layout.modal_opciones, null);

            // Inicializar el PopupWindow con las dimensiones necesarias
            popupWindow = new PopupWindow(modalView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

            // Mostrar el PopupWindow en la pantalla
            popupWindow.showAsDropDown(imageView, 0, 0);

            // Configurar el evento para el botón de la cámara
            Button openCameraOption = modalView.findViewById(R.id.btnOpenCamera);
            openCameraOption.setOnClickListener(v -> {
                checkCameraPermission(); // Verificar permisos antes de abrir la cámara
                popupWindow.dismiss(); // Cerrar el modal después de elegir
            });

            // Configurar el evento para el botón de la galería
            Button openGalleryOption = modalView.findViewById(R.id.btnOpenGallery);
            openGalleryOption.setOnClickListener(v -> {
                checkGalleryPermission(); // Verificar permisos antes de abrir la galería
                popupWindow.dismiss(); // Cerrar el modal después de elegir
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error al mostrar el modal: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Método para abrir la cámara
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            cameraActivityResultLauncher.launch(cameraIntent);
        }
    }

    // Método para abrir la galería
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryActivityResultLauncher.launch(galleryIntent);
    }

    // Verificar permisos para la cámara y abrirla si están concedidos
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            openCamera(); // Si los permisos están concedidos, abrir la cámara
        }
    }

    // Verificar permisos para la galería y abrirla si están concedidos
    private void checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
        } else {
            openGallery(); // Si los permisos están concedidos, abrir la galería
        }
    }

    // Manejar el resultado de la cámara
    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap); // Mostrar la imagen capturada en el ImageView
                }
            }
    );

    // Manejar el resultado de la galería
    private final ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    imageView.setImageURI(selectedImageUri); // Mostrar la imagen seleccionada en el ImageView
                }
            }
    );

    // Sobrescribir onRequestPermissionsResult para manejar la respuesta del usuario
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(); // Abrir la cámara si se concede el permiso
            } else {
                showPermissionDeniedMessage("Cámara"); // Mostrar mensaje de error
            }
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery(); // Abrir la galería si se concede el permiso
            } else {
                showPermissionDeniedMessage("Galería"); // Mostrar mensaje de error
            }
        }
    }

    // Método para mostrar mensaje si el permiso fue denegado
    private void showPermissionDeniedMessage(String feature) {
        Toast.makeText(this, "Permiso denegado para " + feature, Toast.LENGTH_SHORT).show();
    }


}