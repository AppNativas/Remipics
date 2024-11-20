package com.example.remipics;

import android.app.Application;
import com.google.android.libraries.places.api.Places;

public class Ubicacion extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializar Google Places con tu API Key
        Places.initialize(getApplicationContext(), "AIzaSyCPoBJ4aiKiMZbsoFM_mTo5vrVGBl-Dlw4");
    }

}
