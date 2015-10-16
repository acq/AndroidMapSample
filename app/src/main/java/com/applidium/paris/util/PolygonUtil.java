package com.applidium.paris.util;

public class PolygonUtil {

    // via http://stackoverflow.com/a/2922778
    public static boolean insidePolygon(double[][] vertices, double x, double y) {
        int i, j;
        boolean c = false;
        for (i = 0, j = vertices.length - 1; i < vertices.length; j = i++) {
            double vix = vertices[i][0];
            double viy = vertices[i][1];
            double vjx = vertices[j][0];
            double vjy = vertices[j][1];
            if (((viy > y) != (vjy > y)) && (x < (vjx - vix) * (y - viy) / (vjy - viy) + vix)) {
                c = !c;
            }
        }
        return c;
    }
}
