package com.nestor.eheartbp;

public class RecordItem {
    private String date;
    private String systolic;
    private String diastolic;
    private String pulse;

    public RecordItem(String date, String systolic, String diastolic, String pulse) {
        this.date = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    @Override
    public String toString() {
        return "RecordItem{" +
                "date='" + date + '\'' +
                ", systolic='" + systolic + '\'' +
                ", diastolic='" + diastolic + '\'' +
                ", pulse='" + pulse + '\'' +
                '}';
    }
} 