package com.gmail.xiifwat.salattimes.library.Georesponse;

public class Geometry {
    private Location location;
    private String location_type;
    private Area viewport;
    private Area bounds;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public Area getViewport() {
        return viewport;
    }

    public void setViewport(Area viewport) {
        this.viewport = viewport;
    }

    public Area getBounds() {
        return bounds;
    }

    public void setBounds(Area bounds) {
        this.bounds = bounds;
    }
}


