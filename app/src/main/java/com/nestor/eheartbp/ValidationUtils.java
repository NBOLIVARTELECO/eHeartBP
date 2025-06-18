package com.nestor.eheartbp;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUtils {
    
    /**
     * Valida si el email tiene un formato correcto
     */
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    
    /**
     * Valida si la contraseña cumple con los requisitos mínimos
     */
    public static boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }
    
    /**
     * Valida si los valores de presión arterial son lógicos
     */
    public static boolean isValidBloodPressure(int systolic, int diastolic) {
        return systolic > 0 && diastolic > 0 && systolic > diastolic && 
               systolic <= 300 && diastolic <= 200;
    }
    
    /**
     * Valida si el pulso está en un rango razonable
     */
    public static boolean isValidPulse(int pulse) {
        return pulse > 0 && pulse <= 200;
    }
    
    /**
     * Valida si el timestamp es válido
     */
    public static boolean isValidTimestamp(long timestamp) {
        return timestamp > 0 && timestamp <= System.currentTimeMillis();
    }
    
    /**
     * Valida si una cadena no está vacía
     */
    public static boolean isNotEmpty(String text) {
        return !TextUtils.isEmpty(text);
    }
    
    /**
     * Valida si un número está en un rango específico
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
    
    /**
     * Obtiene un mensaje de error para email inválido
     */
    public static String getEmailErrorMessage() {
        return "Por favor ingrese un email válido";
    }
    
    /**
     * Obtiene un mensaje de error para contraseña inválida
     */
    public static String getPasswordErrorMessage() {
        return "La contraseña debe tener al menos 6 caracteres";
    }
    
    /**
     * Obtiene un mensaje de error para presión arterial inválida
     */
    public static String getBloodPressureErrorMessage() {
        return "Los valores de presión arterial no son válidos";
    }
    
    /**
     * Obtiene un mensaje de error para pulso inválido
     */
    public static String getPulseErrorMessage() {
        return "El valor del pulso no es válido";
    }
} 