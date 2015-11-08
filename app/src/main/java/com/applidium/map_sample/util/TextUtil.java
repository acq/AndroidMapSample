package com.applidium.map_sample.util;

public class TextUtil {
    public static String humanReadableDistance(double distance) {
        if (distance < 1000) {
            return String.format("%.0fm", distance);
        } else if (distance < 10000) {
            return String.format("%.1fkm", distance / 1000);
        } else {
            return String.format("%.0fkm", distance / 1000);
        }
    }
}
