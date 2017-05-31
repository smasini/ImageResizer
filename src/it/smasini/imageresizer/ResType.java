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

    public int percentage;
    public String folder;

    static {
        XXXHDPI.percentage = 100;
        XXHDPI.percentage = 75;
        XHDPI.percentage = 50;
        HDPI.percentage = 38;
        MDPI.percentage = 25;

        XXXHDPI.folder = "drawable-xxxhdpi";
        XXHDPI.folder = "drawable-xxhdpi";
        XHDPI.folder = "drawable-xhdpi";
        HDPI.folder = "drawable-hdpi";
        MDPI.folder = "drawable-mdpi";
    }
}
