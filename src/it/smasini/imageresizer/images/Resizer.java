package it.smasini.imageresizer.images;

import com.intellij.util.ui.UIUtil;
import it.smasini.imageresizer.ResType;
import it.smasini.imageresizer.Utility;
import it.smasini.imageresizer.files.Renamer;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by Simone on 30/05/17.
 */
public class Resizer {

    private String imageFilePath, resFilePath;
    private boolean generateAllRes;
    private Renamer renamer;

    private BufferedImage originalImage;
    private int originalImageType;
    private String filename, extension;

    public Resizer(String imageFilePath, boolean generateAllRes, Renamer renamer) {
        this.imageFilePath = imageFilePath;
        this.generateAllRes = generateAllRes;
        this.renamer = renamer;
        //TODO cercare di ottenere questa cartella dinamicamente dal progetto in corso, oppure farla selezionare all'utente
        resFilePath = "/Users/Simone/Desktop/workspace/";
        getOriginalImage();
    }


    private void getOriginalImage(){
        try {
            originalImage = ImageIO.read(new File(imageFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        originalImageType = (originalImage.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        //originalImageType = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        String[] arr = imageFilePath.split("/");
        filename = arr[arr.length-1];
        String[] arr2 = filename.split("\\.");
        extension = arr2[arr2.length-1];
        filename = filename.substring(0, filename.indexOf("." + extension));
        renamer.setFileName(filename);
        filename = renamer.rename();
    }

    public void scale(){
        if(generateAllRes){
            int originalWidth = originalImage.getWidth();
            int originalHeigth = originalImage.getHeight();

            for(ResType resType : ResType.values()){
                int w = (int) Utility.getPercentage(resType.percentage, originalWidth);
                int h = (int) Utility.getPercentage(resType.percentage, originalHeigth);

                String dest = resFilePath + resType.folder;
                Utility.createFolderIfNeeded(dest);
                dest = dest + "/" + filename + "." + extension;
                scaleAndSave(w, h, dest);
            }
        }else{
            //TODO prendere dei dati tipo la percentuale o la dimensione precisa per poter scalare una singola immagine e metterla in una cartella scelta dall'utente
        }
    }

    private void scaleAndSave(int width, int height, String destinationFilePath){
        writeImage(resizeImage(width, height), destinationFilePath);
    }

    private BufferedImage resizeImage(int width, int height){
        //return getScaledInstance(width, height);
        return Scalr.resize(originalImage, Scalr.Method.ULTRA_QUALITY, width, height);
        /*BufferedImage resizedImage = UIUtil.createImage(width, height, originalImageType);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;*/
    }

    private void writeImage(BufferedImage image, String destinationFilePath){
        try {
            ImageIO.write(image, "png", new File(destinationFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public BufferedImage getScaledInstance(int targetWidth, int targetHeight){
        return getScaledInstance(targetWidth, targetHeight, null, true);
    }

    public BufferedImage getScaledInstance(int targetWidth, int targetHeight, Object hint){
        return getScaledInstance(targetWidth, targetHeight, hint, true);
    }

    public BufferedImage getScaledInstance(int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
        BufferedImage ret = originalImage;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = originalImage.getWidth();
            h = originalImage.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 4;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 4;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = UIUtil.createImage(w, h, originalImageType);
            Graphics2D g2 = tmp.createGraphics();
            if(hint!=null) {
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            }
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

}
