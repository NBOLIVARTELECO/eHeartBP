package com.nestor.eheartbp;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.nestor.eheartbp.Globals.diastolica;
import static com.nestor.eheartbp.Globals.pul;
import static com.nestor.eheartbp.Globals.sistolica;
import static com.nestor.eheartbp.Globals.tiempo_toma;
import static com.nestor.eheartbp.Globals.pulso;
import static com.nestor.eheartbp.Globals.time_stamp;

public class RecordsActivity extends AppCompatActivity {
    private static final String TAG = "RecordsActivity";

    private RecyclerView recyclerView;
    private RecordsAdapter adapter;
    private List<RecordItem> recordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records2);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        initializeViews();
        setupRecyclerView();
        loadRecords();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView == null) {
            Log.e(TAG, "RecyclerView not found in layout");
        }
    }

    private void setupRecyclerView() {
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recordsList = new ArrayList<>();
            adapter = new RecordsAdapter(recordsList);
            recyclerView.setAdapter(adapter);
        }
    }

    private void loadRecords() {
        try {
            recordsList.clear();
            
            for (int i = 0; i < pulso.size(); i++) {
                String date = formatDate(tiempo_toma.get(i));
                String systolic = sistolica.get(i);
                String diastolic = diastolica.get(i);
                String pulse = pulso.get(i);
                
                RecordItem record = new RecordItem(date, systolic, diastolic, pulse);
                recordsList.add(record);
            }
            
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error loading records", e);
        }
    }

    private String formatDate(String timestamp) {
        try {
            long time = Long.parseLong(timestamp);
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(time);
            date.add(Calendar.MONTH, 1);
            
            return String.format("%d/%d", 
                date.get(Calendar.DAY_OF_MONTH), 
                date.get(Calendar.MONTH));
        } catch (Exception e) {
            Log.e(TAG, "Error formatting date", e);
            return "N/A";
        }
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
}
