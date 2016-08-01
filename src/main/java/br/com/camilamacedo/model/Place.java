package br.com.camilamacedo.model;

import lombok.Data;

public @Data class Place {

    private static final String CSV_DIVISOR = ";";

    private String _id;
    private String name;
    private String type;
    private GeoPosition geo_position;

    @Override
    public String toString() {
        return
                 this._id + CSV_DIVISOR
                 + this.name + CSV_DIVISOR
                 + this.type + CSV_DIVISOR
                 + this.geo_position.getLatitude() + CSV_DIVISOR
                 + this.geo_position.getLongitude() ;
    }
}

