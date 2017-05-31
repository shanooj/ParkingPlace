package com.karbaros.parkingplace.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shanu on 29-May-17.
 */

public class ParkSpot {

    private String psId;
    private List<HashMap<String, Object>> locationPoints;
    private String spotName;


    public ParkSpot() {
    }

   /* public ParkSpot(String psId, List<LatLng> locationPoints, String spotName) {
        this.psId = psId;
        this.locationPoints = locationPoints;
        this.spotName = spotName;
    }*/

    public String getPsId() {
        return psId;
    }

    public void setPsId(String psId) {
        this.psId = psId;
    }

    public List<HashMap<String, Object>> getLocationPoints() {
        return locationPoints;
    }

    public void setLocationPoints(List<HashMap<String, Object>> locationPoints) {
        this.locationPoints = locationPoints;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }
}
