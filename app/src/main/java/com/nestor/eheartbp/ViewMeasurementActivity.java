package com.nestor.eheartbp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nestor.eheartbp.objetos.FirebaseReferences;
import com.nestor.eheartbp.objetos.Usuario;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.nestor.eheartbp.Globals.dia;
import static com.nestor.eheartbp.Globals.pul;
import static com.nestor.eheartbp.Globals.sys;

public class ViewMeasurementActivity extends AppCompatActivity {
    private static final String TAG = "ViewMeasurementActivity";

    private TextView editSys;
    private TextView editPul;
    private TextView editDia;
    private TextView fecha;
    private TextView time;
    private TextView time_stamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_measurement);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        
        initializeViews();
        updateMeasurementDisplay();
    }

    private void initializeViews() {
        editPul = findViewById(R.id.editPul);
        editSys = findViewById(R.id.editSys);
        editDia = findViewById(R.id.editDia);
        fecha = findViewById(R.id.textView4);
        time = findViewById(R.id.textView5);
        time_stamp = findViewById(R.id.textView7);
    }

    private void updateMeasurementDisplay() {
        if (!isFinishing() && !isDestroyed()) {
            // Actualizar valores de presión arterial
            updateBloodPressureValues();
            
            // Actualizar información de fecha y hora
            updateTimestampDisplay();
            updateCurrentTimeDisplay();
        }
    }

    private void updateBloodPressureValues() {
        try {
            editPul.setText(String.valueOf(pul));
            editDia.setText(String.valueOf(dia));
            editSys.setText(String.valueOf(sys));
        } catch (Exception e) {
            Log.e(TAG, "Error updating blood pressure values", e);
        }
    }

    private void updateTimestampDisplay() {
        try {
            if (Globals.time_stamp > 0) {
                Calendar date = Calendar.getInstance();
                date.setTimeInMillis(Globals.time_stamp);
                date.add(Calendar.MONTH, 1);
                
                String timestamp = String.format("%d/%d/%d %s:%s",
                    date.get(Calendar.DAY_OF_MONTH),
                    date.get(Calendar.MONTH),
                    date.get(Calendar.YEAR),
                    formatTime(date.get(Calendar.HOUR)),
                    formatTime(date.get(Calendar.MINUTE)));
                    
                time_stamp.setText(timestamp);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error updating timestamp display", e);
        }
    }

    private void updateCurrentTimeDisplay() {
        try {
            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            
            int dia = today.monthDay;
            int mes = today.month + 1; // Sumar 1 porque los meses van de 0-11
            int ano = today.year;
            int hora = today.hour;
            int min = today.minute;

            fecha.setText(String.format("%d / %d / %d", mes, dia, ano));
            time.setText(String.format("%s : %s  Ultima visualización", 
                formatTime(hora), formatTime(min)));
        } catch (Exception e) {
            Log.e(TAG, "Error updating current time display", e);
        }
    }

    private String formatTime(int time) {
        return time >= 10 ? String.valueOf(time) : "0" + time;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.e_heart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuActions.options(this, item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
