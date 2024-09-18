package com.example.remipics.components.molecule;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.remipics.R;

public class PrivacyPolicyDialog extends Dialog {

    public PrivacyPolicyDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_privacy_policy, null);
        setContentView(view);

        // Configurar el contenido del diálogo si es necesario
        TextView title = findViewById(R.id.privacy_policy_title);
        TextView text = findViewById(R.id.privacy_policy_text);
        title.setText("Privacidad y Términos");
        text.setText("Maecenas adipiscing ante non diam sodales hendrerit.");
    }
}
