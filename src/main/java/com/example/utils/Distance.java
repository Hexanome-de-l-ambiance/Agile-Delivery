package com.example.utils;

/**
 * Classe utilitaire pour calculer la distance entre deux points
 */
public class Distance {

    /**
     * Rayon de la Terre en kilomètres
     */
    private static final double R = 6371.01;


    /**
     *
     * @param lat1 latitude du point 1
     * @param lon1 longitude du point 1
     * @param lat2 latitude du point 2
     * @param lon2 longitude du point 2
     * @return La distance entre le point 1 et 2 en mètres en utilisant la formule de Haversine
     */
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        // Conversion en radians
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        // formule de Haversine
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c * 1000;
    }
}
