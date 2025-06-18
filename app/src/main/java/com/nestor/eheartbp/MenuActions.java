package com.nestor.eheartbp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

public class MenuActions {
    private static final String TAG = "MenuActions";

    public static boolean options(Activity act, MenuItem item) {
        // Verificar que la actividad no sea null y no esté destruida
        if (act == null || act.isFinishing() || act.isDestroyed()) {
            Log.w(TAG, "Activity is null, finishing, or destroyed");
            return false;
        }

        Intent intent = null;

        try {
            switch (item.getItemId()) {
                case R.id.get_pressure:
                    intent = new Intent(act, ObtainPressureActivity.class);
                    break;
                case R.id.record:
                    intent = new Intent(act, RecordsActivity.class);
                    break;
                case R.id.statistics:
                    intent = new Intent(act, StatisticsActivity.class);
                    break;
                case R.id.risk:
                    intent = new Intent(act, RiskPrediction.class);
                    break;
                case R.id.close_session:
                    intent = new Intent(act, SignInActivity.class);
                    // Limpiar el stack de actividades para el cierre de sesión
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    break;
                default:
                    // Delegar al parent si no es un item manejado
                    if (act.getParent() != null) {
                        return act.getParent().onOptionsItemSelected(item);
                    }
                    return false;
            }

            if (intent != null) {
                act.startActivity(intent);
                return true;
            }

        } catch (Exception e) {
            Log.e(TAG, "Error navigating to activity", e);
        }

        return false;
    }
}
