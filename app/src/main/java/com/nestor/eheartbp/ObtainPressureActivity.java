package com.nestor.eheartbp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nestor.eheartbp.objetos.FirebaseReferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.nestor.eheartbp.Globals.dia;
import static com.nestor.eheartbp.Globals.pul;
import static com.nestor.eheartbp.Globals.sys;
import static com.nestor.eheartbp.Globals.time_stamp;
import static com.nestor.eheartbp.Globals.pulso;
import static com.nestor.eheartbp.Globals.diastolica;
import static com.nestor.eheartbp.Globals.sistolica;
import static com.nestor.eheartbp.Globals.tiempo_toma;

public class ObtainPressureActivity extends AppCompatActivity {
    private static final String TAG = "ObtainPressureActivity";
    private static final int TOTAL_FIREBASE_CALLS = 4;
    
    private Button obtainButton;
    private int completedCalls = 0;
    private boolean dataReady = false;
    private boolean isObtainingData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtain_pressure);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        
        initializeViews();
    }

    private void initializeViews() {
        obtainButton = findViewById(R.id.obtainButton);
        if (obtainButton == null) {
            Log.e(TAG, "Obtain button not found in layout");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.e_heart_menu, menu);
        return true;
    }

    private void showToast(String text) {
        if (!isFinishing() && !isDestroyed()) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuActions.options(this, item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    public void obtain(View view) {
        if (isObtainingData) {
            showToast("Ya se están obteniendo datos...");
            return;
        }

        isObtainingData = true;
        completedCalls = 0;
        dataReady = false;

        if (obtainButton != null) {
            obtainButton.setBackgroundColor(Color.GRAY);
            obtainButton.setText("Cargando...");
        }

        // Limpiar listas antes de obtener nuevos datos
        clearGlobalLists();

        // Obtener datos de Firebase
        obtainDataFromFirebase();
    }

    private void clearGlobalLists() {
        try {
            pulso.clear();
            diastolica.clear();
            sistolica.clear();
            tiempo_toma.clear();
        } catch (Exception e) {
            Log.e(TAG, "Error clearing global lists", e);
        }
    }

    private void obtainDataFromFirebase() {
        DatabaseReference datos = FirebaseDatabase.getInstance().getReference();

        // Obtener datos de pulso
        datos.child("Datos/Usuario_1/Pulso")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    processPulseData(dataSnapshot);
                    checkAllDataReady();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    handleFirebaseError(databaseError, "pulso");
                }
            });

        // Obtener datos de diastólica
        datos.child("Datos/Usuario_1/Diastolica")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    processDiastolicData(dataSnapshot);
                    checkAllDataReady();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    handleFirebaseError(databaseError, "diastólica");
                }
            });

        // Obtener datos de sistólica
        datos.child("Datos/Usuario_1/Sistolica")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    processSystolicData(dataSnapshot);
                    checkAllDataReady();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    handleFirebaseError(databaseError, "sistólica");
                }
            });

        // Obtener datos de tiempo
        datos.child("Datos/Usuario_1/Tiempo")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    processTimeData(dataSnapshot);
                    checkAllDataReady();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    handleFirebaseError(databaseError, "tiempo");
                }
            });
    }

    private void processPulseData(DataSnapshot dataSnapshot) {
        try {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String value = snapshot.getValue().toString();
                pulso.add(value);
            }
            
            if (!pulso.isEmpty()) {
                pul = Integer.parseInt(pulso.get(pulso.size() - 1));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing pulse data", e);
        }
    }

    private void processDiastolicData(DataSnapshot dataSnapshot) {
        try {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String value = snapshot.getValue().toString();
                diastolica.add(value);
            }
            
            if (!diastolica.isEmpty()) {
                dia = Integer.parseInt(diastolica.get(diastolica.size() - 1));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing diastolic data", e);
        }
    }

    private void processSystolicData(DataSnapshot dataSnapshot) {
        try {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String value = snapshot.getValue().toString();
                sistolica.add(value);
            }
            
            if (!sistolica.isEmpty()) {
                sys = Integer.parseInt(sistolica.get(sistolica.size() - 1));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing systolic data", e);
        }
    }

    private void processTimeData(DataSnapshot dataSnapshot) {
        try {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String value = snapshot.getValue().toString();
                tiempo_toma.add(value);
            }
            
            if (!tiempo_toma.isEmpty()) {
                time_stamp = Long.parseLong(tiempo_toma.get(tiempo_toma.size() - 1));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing time data", e);
        }
    }

    private void handleFirebaseError(DatabaseError databaseError, String dataType) {
        Log.e(TAG, "Error obteniendo datos de " + dataType + ": " + databaseError.getMessage());
        showToast("Error al obtener los datos de " + dataType);
        checkAllDataReady();
    }

    private void checkAllDataReady() {
        completedCalls++;
        
        if (completedCalls >= TOTAL_FIREBASE_CALLS && !dataReady) {
            dataReady = true;
            isObtainingData = false;
            navigateToMeasurementView();
        }
    }

    private void navigateToMeasurementView() {
        if (!isFinishing() && !isDestroyed()) {
            try {
                Intent intent = new Intent(ObtainPressureActivity.this, ViewMeasurementActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, "Error navigating to ViewMeasurementActivity", e);
                showToast("Error al mostrar la medición");
            }
        }
    }
}
