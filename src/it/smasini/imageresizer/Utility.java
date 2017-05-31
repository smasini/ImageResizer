package it.smasini.imageresizer;

import java.io.File;

/**
 * Created by Simone on 29/05/17.
 */
public class Utility {

    /**
     * Returns a proportion (n out of a total) as a percentage, in a float.
     */
    public static float getPercentage(int n, int total) {
        float proportion = ((float) n) * ((float) total);
        return proportion / 100;
    }

    public static void createFolderIfNeeded(String folderPath){
        File file = new File(folderPath);
        if(!file.exists()){
            file.mkdir();
        }
    }

}
