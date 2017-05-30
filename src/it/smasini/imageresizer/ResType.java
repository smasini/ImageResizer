package it.smasini.imageresizer;

/**
 * Created by Simone on 30/05/17.
 */
public enum ResType {
    XXXHDPI,
    XXHDPI,
    XHDPI,
    HDPI,
    MDPI;

    public double percentage;

    static {
        XXXHDPI.percentage = 100;
        XXHDPI.percentage = 100;
        XHDPI.percentage = 100;
        HDPI.percentage = 100;
        MDPI.percentage = 100;
    }
}
