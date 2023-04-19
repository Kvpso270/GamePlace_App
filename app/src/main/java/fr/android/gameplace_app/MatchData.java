package fr.android.gameplace_app;

import java.util.Date;

public class MatchData {
    private int id;
    private String date;
    private Date creationDate;
    private String location;
    private double latitude;
    private double longitude;
    private String streetName;
    private byte[] image;

    public MatchData(int id, String date, Date creationDate, String location, double latitude, double longitude, String streetName, byte[] image) {
        this.id = id;
        this.date = date;
        this.creationDate = creationDate;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetName = streetName;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

