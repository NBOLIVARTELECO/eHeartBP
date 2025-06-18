package com.nestor.eheartbp.objetos;

public class Usuario {
    private int diastolica;
    private int sistolica;
    private int pulso;
    private long timestamp;

    public Usuario() {
        // Constructor por defecto
    }

    public Usuario(int diastolica, int sistolica, int pulso) {
        this.diastolica = diastolica;
        this.sistolica = sistolica;
        this.pulso = pulso;
    }

    public Usuario(int diastolica, int sistolica, int pulso, long timestamp) {
        this.diastolica = diastolica;
        this.sistolica = sistolica;
        this.pulso = pulso;
        this.timestamp = timestamp;
    }

    // Getters
    public int getDiastolica() {
        return diastolica;
    }

    public int getSistolica() {
        return sistolica;
    }

    public int getPulso() {
        return pulso;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setDiastolica(int diastolica) {
        this.diastolica = diastolica;
    }

    public void setSistolica(int sistolica) {
        this.sistolica = sistolica;
    }

    public void setPulso(int pulso) {
        this.pulso = pulso;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "diastolica=" + diastolica +
                ", sistolica=" + sistolica +
                ", pulso=" + pulso +
                ", timestamp=" + timestamp +
                '}';
    }
}
