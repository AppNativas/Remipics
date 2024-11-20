package com.example.remipics.auth;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.remipics.DashPrincipal;
import com.example.remipics.R;
import com.example.remipics.controllers.ApiClient;
import com.example.remipics.entities.ApiResponse;
import com.example.remipics.entities.LoginRequest;
import com.example.remipics.interfaces.ApiUserService;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LoginActivity extends AppCompatActivity {

    private ApiUserService apiUserService;
    private static final String TAG = "InicioDeSesion";
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private ImageButton button;
    private static final int REQ_ONE_TAP = 2;
    private boolean showOneTapUI = true;

    private int intentosFailedLogin = 0;
    private static final int MAX_INTENTOS = 5;
    private static final long TIEMPO_BLOQUEO = 30 * 60 * 1000; // 30 minutos en milisegundos

    private ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        AppCompatTextView backText = findViewById(R.id.back_text);
        backText.setOnClickListener(v -> onBackPressed());

        findViewById(R.id.rememberpass_text).setOnClickListener(v -> onRedirectionRemember());


        Button btnLogin = findViewById(R.id.login_send_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            EditText usernameEditText = findViewById(R.id.username);
            EditText passwordEditText = findViewById(R.id.password);
            @Override
            public void onClick(View v) {
                    String username = usernameEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();

                    // Add your logic to check if the user is logged in
                    //return !username.isEmpty() && !password.isEmpty();

                    LoginRequest loginRequest = new LoginRequest(username, password);
                    apiUserService = ApiClient.getClient().create(ApiUserService.class);
                    Call<ApiResponse> call = apiUserService.login(loginRequest);

                    call.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ApiResponse loginResponse = response.body();
                                //Log.d("LoginActivity", "Response: " + loginResponse.toString());
                                if (loginResponse.isSuccess()) {
                                    usernameEditText.getText().clear();
                                    passwordEditText.getText().clear();
                                    String tokenInter = response.body().getData().toString();

                                    Intent intent = new Intent(LoginActivity.this, DashPrincipal.class);
                                    intent.putExtra("token", tokenInter);

                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                } else {
                                    showDialog("1. User not found");
                                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            showDialog("2. User not found");
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicializarVistas();
        configurarGoogleSignIn();
        configurarActivityResultLauncher();
        configurarBotonLogin();
    }

    protected void onRedirectionRemember() {
        // Redirect to the remember password activity
        startActivity(new Intent(this, RememberActivity.class));
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


    /*Google*/

    private void inicializarVistas() {
        button = findViewById(R.id.imageButton2);
        Log.d(TAG, "Vistas inicializadas");
    }

    private void configurarGoogleSignIn() {

        Log.d("Key:" , getString(R.string.web_client_id));

        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .setAutoSelectEnabled(true)
                .build();
        Log.d(TAG, "Google Sign-In configurado");
    }

    private void configurarActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d(TAG, "ActivityResult recibido: " + result.getResultCode());
                        Log.d(TAG, "More info: " + result.toString());
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            try {
                                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                                String idToken = credential.getGoogleIdToken();
                                if (idToken != null) {
                                    String email = credential.getId();
                                    Log.d(TAG, "Google Sign-In exitoso para: " + email);
                                    Toast.makeText(getApplicationContext(), "Email: " + email, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.w(TAG, "No se pudo obtener ID Token");
                                }
                            } catch (ApiException e) {
                                Log.e(TAG, "Error al obtener credenciales: " + e.getStatusCode(), e);
                                manejarErrorLogin(e);
                            }
                        } else {
                            Log.w(TAG, "Google Sign-In cancelado");
                        }
                        button.setEnabled(true);
                    }
                });
        Log.d(TAG, "ActivityResultLauncher configurado");
    }

    private void configurarBotonLogin() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Botón de inicio de sesión presionado");
                if (intentosFailedLogin >= MAX_INTENTOS) {
                    Toast.makeText(LoginActivity.this, "Has alcanzado el límite de intentos. Por favor, espera antes de intentar de nuevo.", Toast.LENGTH_LONG).show();
                    return;
                }
                iniciarProcesoLogin();
            }
        });
    }

    private void iniciarProcesoLogin() {
        Log.d(TAG, "Iniciando proceso de login");
        button.setEnabled(false);

        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(LoginActivity.this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        Log.d(TAG, "beginSignIn exitoso");
                        IntentSenderRequest intentSenderRequest =
                                new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                        activityResultLauncher.launch(intentSenderRequest);
                    }
                })
                .addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "beginSignIn falló", e);
                        button.setEnabled(true);
                        manejarErrorLogin(e);
                    }
                });
    }

    private void manejarErrorLogin(Exception e) {
        intentosFailedLogin++;
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            if (apiException.getStatusCode() == CommonStatusCodes.CANCELED) {
                Log.d(TAG, "One-tap dialog was closed.");
                // Don't re-prompt immediately after the user cancels.
                showOneTapUI = false;
            } else {
                Log.e(TAG, "Error de inicio de sesión: " + apiException.getStatusCode());
                Toast.makeText(LoginActivity.this, "Error al iniciar sesión: " + getErrorMessage(apiException.getStatusCode()), Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(TAG, "Error no específico: " + e.getMessage(), e);
            Toast.makeText(LoginActivity.this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (intentosFailedLogin >= MAX_INTENTOS) {
            Log.w(TAG, "Máximo de intentos alcanzado");
            Toast.makeText(LoginActivity.this, "Has alcanzado el límite de intentos fallidos. Inténtalo de nuevo más tarde.", Toast.LENGTH_LONG).show();
            button.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    button.setEnabled(true);
                    intentosFailedLogin = 0;
                    Log.d(TAG, "Reinicio de intentos de login");
                }
            }, TIEMPO_BLOQUEO);
        }
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            Log.e(TAG, "Código de error detallado: " + apiException.getStatusCode());
            Log.e(TAG, "Mensaje de error detallado: " + apiException.getMessage());
        }
    }

    private String getErrorMessage(int statusCode) {
        switch (statusCode) {
            case CommonStatusCodes.API_NOT_CONNECTED:
                return "API no conectada";
            case CommonStatusCodes.DEVELOPER_ERROR:
                return "Error de desarrollador. Verifica la configuración.";
            case CommonStatusCodes.ERROR:
                return "Error desconocido";
            case CommonStatusCodes.INTERNAL_ERROR:
                return "Error interno de Google Play Services";
            case CommonStatusCodes.INVALID_ACCOUNT:
                return "Cuenta inválida";
            case CommonStatusCodes.SIGN_IN_REQUIRED:
                return "Se requiere iniciar sesión";
            default:
                return "Error " + statusCode;
        }
    }


}