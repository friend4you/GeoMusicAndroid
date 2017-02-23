package com.example.vlada.geomusicandroidclient.api;

public class ServiceGenerator {

    private static GeomusicService service;

    public static GeomusicService getGeomusicService() {
        if (service == null) {
            service = new GeomusicService();
        }
        return service;
    }
}
