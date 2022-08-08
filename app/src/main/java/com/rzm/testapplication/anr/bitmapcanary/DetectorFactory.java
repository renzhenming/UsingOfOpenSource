package com.rzm.testapplication.anr.bitmapcanary;

import java.util.HashMap;


public class DetectorFactory {

    public static final int DETECT_TYPE_BACKGROUND = 1;
    public static final int DETECT_TYPE_IMAGESRC = 2;
    private static HashMap<Integer, Detector> detectorCache = new HashMap<>();

    public static Detector getDetector(int detectType) {
        if (detectorCache.containsKey(detectType)) {
            return detectorCache.get(detectType);
        } else {
            return produceDetector(detectType);
        }
    }

    private static Detector produceDetector(int detectType) {
        if (DETECT_TYPE_BACKGROUND == detectType) {
            return new BackgroundDetecotor();
        } else if (DETECT_TYPE_IMAGESRC == detectType) {
            return new ImagesrcDetector();
        } else {//todo checkbox detector、progress detecor ... adding furtue
            throw new IllegalArgumentException("detectType not support " + detectType);
        }
    }
}
