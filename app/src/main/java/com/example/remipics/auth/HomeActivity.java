package com.example.remipics.auth;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.remipics.R;
import com.example.remipics.components.molecule.PrivacyPolicyDialog;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_home);

            //#region Filter backgroundImage
            // In HomeActivity.java, add the following code in the onCreate method
            ImageView backgroundImage = findViewById(R.id.backgroundImage);

            // Create a color filter to adjust contrast, exposure, and temperature
            ColorMatrix colorMatrix = new ColorMatrix();

            // Adjust contrast (1.0 = original, < 1.0 = less contrast, > 1.0 = more contrast)
            float contrast = 0.8f;
            colorMatrix.set(new float[]{
                    contrast, 0, 0, 0, 0,
                    0, contrast, 0, 0, 0,
                    0, 0, contrast, 0, 0,
                    0, 0, 0, 1, 0
            });

            // Adjust exposure (1.0 = original, < 1.0 = less exposure, > 1.0 = more exposure)
            float exposure = 0.9f;
            ColorMatrix exposureMatrix = new ColorMatrix();
            exposureMatrix.setScale(exposure, exposure, exposure, 1);
            colorMatrix.postConcat(exposureMatrix);

            // Adjust temperature (1.0 = original, < 1.0 = cooler, > 1.0 = warmer)
            float temperature = 0.9f;
            ColorMatrix temperatureMatrix = new ColorMatrix();
            temperatureMatrix.set(new float[]{
                    temperature, 0, 0, 0, 0,
                    0, 1, 0, 0, 0,
                    0, 0, 1 / temperature, 0, 0,
                    0, 0, 0, 1, 0
            });
            colorMatrix.postConcat(temperatureMatrix);

            // Apply the color filter to the ImageView
            backgroundImage.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

            //#endregion


            //#region Dialog privacyPolicyDialog
            findViewById(R.id.privacy_policy_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrivacyPolicyDialog dialog = new PrivacyPolicyDialog(HomeActivity.this);
                    dialog.show();
                }
            });
            // #endregion


            //Init
            EdgeToEdge.enable(this);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }
    }
}