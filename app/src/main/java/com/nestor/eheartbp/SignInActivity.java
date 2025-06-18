package com.nestor.eheartbp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.ProxyFileDescriptorCallback;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignInActivity";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private Button botonregistro, button_login;
    private EditText login_pass, login_mail;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progresdialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        
        initializeViews();
        setupFirebaseAuth();
        setupEmailAutocomplete();
    }

    private void initializeViews() {
        button_login = findViewById(R.id.button_login);
        botonregistro = findViewById(R.id.botonregistro);
        login_pass = findViewById(R.id.login_pass);
        login_mail = findViewById(R.id.login_mail);
        progresdialog = new ProgressDialog(this);

        botonregistro.setOnClickListener(this);
        button_login.setOnClickListener(this);
    }

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.i(TAG, "Sesión iniciada con email: " + user.getEmail());
                } else {
                    Log.i(TAG, "Sesión cerrada");
                }
            }
        };
    }

    private void setupEmailAutocomplete() {
        try {
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            Map<String, ?> allEntries = prefs.getAll();

            List<String> emailList = new ArrayList<>();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                emailList.add(entry.getKey());
            }

            String[] savedEmails = emailList.toArray(new String[0]);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_item, savedEmails);
            
            AutoCompleteTextView actv = findViewById(R.id.login_mail);
            if (actv != null) {
                actv.setThreshold(1);
                actv.setAdapter(adapter);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting up email autocomplete", e);
        }
    }

    private void registrar(String email, String pass) {
        if (!ValidationUtils.isValidEmail(email)) {
            showToast(ValidationUtils.getEmailErrorMessage());
            progresdialog.dismiss();
            return;
        }

        if (!ValidationUtils.isValidPassword(pass)) {
            showToast(ValidationUtils.getPasswordErrorMessage());
            progresdialog.dismiss();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "Usuario creado correctamente");
                        showToast("Usuario creado correctamente");
                    } else {
                        Log.e(TAG, "Error al crear usuario: " + task.getException().getMessage());
                        showToast("No se pudo crear el usuario. Verifique los datos.");
                    }
                    progresdialog.dismiss();
                }
            });
    }

    private void iniciar_sesion(String email, String pass) {
        if (!ValidationUtils.isValidEmail(email)) {
            showToast(ValidationUtils.getEmailErrorMessage());
            progresdialog.dismiss();
            return;
        }

        if (!ValidationUtils.isValidPassword(pass)) {
            showToast(ValidationUtils.getPasswordErrorMessage());
            progresdialog.dismiss();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "Sesión iniciada correctamente");
                        saveEmailToPreferences(email);
                        acceso();
                    } else {
                        String errorString = task.getException().getMessage();
                        Log.e(TAG, "Error al iniciar sesión: " + errorString);
                        
                        if (errorString != null && errorString.contains("network error")) {
                            showToast("No hay conexión a internet.");
                        } else {
                            showToast("Credenciales incorrectas o usuario no registrado.");
                        }
                    }
                    progresdialog.dismiss();
                }
            });
    }

    private void saveEmailToPreferences(String email) {
        try {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(email, "email");
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "Error saving email to preferences", e);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void acceso() {
        if (!isFinishing() && !isDestroyed()) {
            startActivity(new Intent(this, ObtainPressureActivity.class));
        }
    }

    private void showToast(String text) {
        if (!isFinishing() && !isDestroyed()) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_login) {
            handleLoginClick();
        } else if (view.getId() == R.id.botonregistro) {
            handleRegisterClick();
        }
    }

    private void handleLoginClick() {
        String emailInicio = login_mail.getText().toString().trim();
        String passInicio = login_pass.getText().toString().trim();

        if (TextUtils.isEmpty(emailInicio)) {
            showToast("Se debe ingresar un email");
            return;
        }
        if (TextUtils.isEmpty(passInicio)) {
            showToast("Se debe ingresar una contraseña");
            return;
        }

        progresdialog.setMessage("Iniciando sesión...");
        progresdialog.show();
        iniciar_sesion(emailInicio, passInicio);
    }

    private void handleRegisterClick() {
        String emailReg = login_mail.getText().toString().trim();
        String passReg = login_pass.getText().toString().trim();

        if (TextUtils.isEmpty(emailReg)) {
            showToast("Se debe ingresar un email");
            return;
        }
        if (TextUtils.isEmpty(passReg)) {
            showToast("Se debe ingresar una contraseña");
            return;
        }

        progresdialog.setMessage("Realizando registro...");
        progresdialog.show();
        registrar(emailReg, passReg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
