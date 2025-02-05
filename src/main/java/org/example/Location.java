package org.example;

import java.util.Objects;

public class Location {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private double todayTemp;
    private double tomorrowTemp;
    private double dayAfterTomorrowTemp;

    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getTodayTemp() { return todayTemp; }
    public double getTomorrowTemp() { return tomorrowTemp; }
    public double getDayAfterTomorrowTemp() { return dayAfterTomorrowTemp; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;
        return Double.compare(location.latitude, latitude) == 0 &&
                Double.compare(location.longitude, longitude) == 0 &&
                Double.compare(location.todayTemp, todayTemp) == 0 &&
                Double.compare(location.tomorrowTemp, tomorrowTemp) == 0 &&
                Double.compare(location.dayAfterTomorrowTemp, dayAfterTomorrowTemp) == 0 &&
                Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, todayTemp, tomorrowTemp, dayAfterTomorrowTemp);
    }
}
