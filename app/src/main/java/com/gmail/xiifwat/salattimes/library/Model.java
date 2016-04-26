package com.gmail.xiifwat.salattimes.library;

public class Model {

    private String fajrTime;
    private String sunriseTime;
    private String duhrTime;
    private String asrTime;
    private String maghribTime;
    private String ishaaTime;

    private String dateEnglish;
    private String dateArabic;

    private boolean isFajrAlarmSet;
    private boolean isSunriseAlarmSet;
    private boolean isDuhrAlarmSet;
    private boolean isAsrAlarmSet;
    private boolean isMaghribAlarmSet;
    private boolean isIshaaAlarmSet;

    public Model(String fajrTime, String sunriseTime, String duhrTime, String asrTime,
                 String maghribTime, String ishaaTime, String dateEnglish, String dateArabic) {
        this.fajrTime = fajrTime;
        this.sunriseTime = sunriseTime;
        this.duhrTime = duhrTime;
        this.asrTime = asrTime;
        this.maghribTime = maghribTime;
        this.ishaaTime = ishaaTime;
        this.dateEnglish = dateEnglish;
        this.dateArabic = dateArabic;
    }

    public String getFajrTime() {
        return fajrTime;
    }

    public void setFajrTime(String fajrTime) {
        this.fajrTime = fajrTime;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public String getDuhrTime() {
        return duhrTime;
    }

    public void setDuhrTime(String duhrTime) {
        this.duhrTime = duhrTime;
    }

    public String getAsrTime() {
        return asrTime;
    }

    public void setAsrTime(String asrTime) {
        this.asrTime = asrTime;
    }

    public String getMaghribTime() {
        return maghribTime;
    }

    public void setMaghribTime(String maghribTime) {
        this.maghribTime = maghribTime;
    }

    public String getIshaaTime() {
        return ishaaTime;
    }

    public void setIshaaTime(String ishaaTime) {
        this.ishaaTime = ishaaTime;
    }

    public String getDateEnglish() {
        return dateEnglish;
    }

    public void setDateEnglish(String dateEnglish) {
        this.dateEnglish = dateEnglish;
    }

    public String getDateArabic() {
        return dateArabic;
    }

    public void setDateArabic(String dateArabic) {
        this.dateArabic = dateArabic;
    }

    public boolean isFajrAlarmSet() {
        return isFajrAlarmSet;
    }

    public void setIsFajrAlarmSet(boolean isFajrAlarmSet) {
        this.isFajrAlarmSet = isFajrAlarmSet;
    }

    public boolean isSunriseAlarmSet() {
        return isSunriseAlarmSet;
    }

    public void setIsSunriseAlarmSet(boolean isSunriseAlarmSet) {
        this.isSunriseAlarmSet = isSunriseAlarmSet;
    }

    public boolean isDuhrAlarmSet() {
        return isDuhrAlarmSet;
    }

    public void setIsDuhrAlarmSet(boolean isDuhrAlarmSet) {
        this.isDuhrAlarmSet = isDuhrAlarmSet;
    }

    public boolean isAsrAlarmSet() {
        return isAsrAlarmSet;
    }

    public void setIsAsrAlarmSet(boolean isAsrAlarmSet) {
        this.isAsrAlarmSet = isAsrAlarmSet;
    }

    public boolean isMaghribAlarmSet() {
        return isMaghribAlarmSet;
    }

    public void setIsMaghribAlarmSet(boolean isMaghribAlarmSet) {
        this.isMaghribAlarmSet = isMaghribAlarmSet;
    }

    public boolean isIshaaAlarmSet() {
        return isIshaaAlarmSet;
    }

    public void setIsIshaaAlarmSet(boolean isIshaaAlarmSet) {
        this.isIshaaAlarmSet = isIshaaAlarmSet;
    }
}
