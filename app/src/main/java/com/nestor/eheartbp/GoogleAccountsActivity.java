package com.nestor.eheartbp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GoogleAccountsActivity extends Activity {
    private static final String TAG = "GoogleAccountsActivity";
    private Button accountButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_window);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        // Configurar el tamaño de la ventana popup
        setupPopupWindow();
        
        // Inicializar vistas
        initializeViews();
    }

    private void setupPopupWindow() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.7));
    }

    private void initializeViews() {
        accountButton = findViewById(R.id.accountButton);
        if (accountButton == null) {
            Log.e(TAG, "Account button not found in layout");
        }
    }

    public void login2(View view) {
        if (accountButton != null) {
            accountButton.setBackgroundColor(Color.argb(30, 0, 0, 0));
        }

        try {
            // Navegación directa - startActivity ya es thread-safe
            Intent intent = new Intent(GoogleAccountsActivity.this, ObtainPressureActivity.class);
            startActivity(intent);
            finish(); // Cerrar la actividad popup después de navegar
        } catch (Exception e) {
            Log.e(TAG, "Error navigating to ObtainPressureActivity", e);
        }
    }

    @Override
    public void onBackPressed() {
        // Manejar el botón de retroceso para cerrar el popup
        finish();
    }
}
