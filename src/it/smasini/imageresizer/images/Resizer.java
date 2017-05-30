package it.smasini.imageresizer.images;

import com.intellij.util.ui.UIUtil;
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

    private String imageFilePath;
    private boolean generateAllRes;

    private BufferedImage originalImage;
    private int originalImageType;
    private String filename, extension;

    public Resizer(String imageFilePath, boolean generateAllRes) {
        this.imageFilePath = imageFilePath;
        this.generateAllRes = generateAllRes;
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
    }

    public void scale(){
        if(generateAllRes){
            int originalWidth = originalImage.getWidth();
            int originalHeigth = originalImage.getHeight();

            //TODO for every type of res i need to calculatate the width and height
            scaleAndSave(originalWidth/2, originalHeigth/2, "/Users/Simone/Desktop/workspace/" + filename + "-resized." + extension);
        }
    }

    private void scaleAndSave(int width, int height, String destinationFilePath){
        writeImage(resizeImage(width, height), destinationFilePath);
    }

    private BufferedImage resizeImage(int width, int height){
        return getScaledInstance(width, height);
        //return Scalr.resize(originalImage, Scalr.Method.BALANCED, width, height);
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
        return getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_AREA_AVERAGING, true);
    }

    public BufferedImage getScaledInstance(int targetWidth, int targetHeight, Object hint){
        return getScaledInstance(targetWidth, targetHeight, hint, true);
    }

    public BufferedImage getScaledInstance(int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
        //int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
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
            //BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            if(hint!=null) {
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            }
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

}
