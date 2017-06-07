// ImageUtils.java
package com.latlab.common.imageutils;



import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TekComImageUtils
{

    public static BufferedImage makeReflectedImage(Image image, int gap,
            int fadeHeight,
            float topOpacity)
    {
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage biReflectedImage;
        biReflectedImage = new BufferedImage(width, height * 2 + gap,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dRI = biReflectedImage.createGraphics();
        g2dRI.drawImage(image, 0, 0, null);
        g2dRI.translate(0, 2 * height + gap);
        g2dRI.scale(1, -1);
        BufferedImage biReflection;
        biReflection = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dR = biReflection.createGraphics();
        g2dR.drawImage(image, 0, 0, null);
        g2dR.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        GradientPaint gp;
        gp = new GradientPaint(0, height * fadeHeight, new Color(0.0f, 0.0f,
                0.0f, 0.0f), 0, height, new Color(0.0f, 0.0f,
                0.0f, topOpacity));
        g2dR.setPaint(gp);
        g2dR.fillRect(0, 0, width, height);
        g2dR.dispose();

        g2dRI.drawImage(biReflection, 0, 0, null);
        g2dRI.dispose();

        return biReflectedImage;
    }

    public static byte[] convertImageFileToBytes(File imageFile)
    {
        if(imageFile == null)
            return null;

        byte[] imageBytes = null;
        try
        {
            FileInputStream fis = new FileInputStream(imageFile);

            imageBytes = new byte[(int) imageFile.length()];

            fis.read(imageBytes);
        } catch (Exception ex)
        {
            Logger.getLogger(TekComImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imageBytes;
    }

    public static BufferedImage convertImageToBytes(byte[] imageBytes)
    {
//        byte[] imageBytes = null;
        BufferedImage imggg = null;
        try
        {
//            new BufferedImage(width, height, imageType).

            if (imageBytes == null)
            {
                return null;
            }

            Image img = Toolkit.getDefaultToolkit().createImage(imageBytes);

            imggg = new BufferedImage(120, 150, BufferedImage.TYPE_INT_RGB);

            imggg.createGraphics().drawImage(img, 0, 0, null);


        } catch (Exception ex)
        {
            Logger.getLogger(TekComImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imggg;
    }

    public static Image convertByteToImage(byte imageBytes[])
    {
        return new ImageIcon(imageBytes).getImage();
    }

    public static BufferedImage convertByteToBufferedImage(byte imageBytes[])
    {
        if(imageBytes == null)
            return null;
        BufferedImage bufferedImage = null;


        Image temImage =  new ImageIcon(imageBytes).getImage();
        
        bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = bufferedImage.createGraphics();

        graphics2D.drawImage(temImage, 0, 0, null);

        graphics2D.dispose();
        
        
        return bufferedImage;

    }

    public static BufferedImage byteToBufferedImage(byte[]  byteArray)
    {
        if(byteArray == null)
            return null;
        
        BufferedImage image = null;



        InputStream in = new ByteArrayInputStream(byteArray);
        try
        {
            image = javax.imageio.ImageIO.read(in);
        } catch (IOException ex)
        {
            Logger.getLogger(TekComImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return image;
    }

    public static BufferedImage convertFileToImage(File imageFile)
    {

        if (imageFile == null)
        {
            return null;
        }

        if (imageFile.isDirectory())
        {
            return null;
        }

        try
        {
            return ImageIO.read(imageFile);
        } catch (IOException ex)
        {

            return null;
        }
    }

    private byte[] getByteFromBufferedImage(BufferedImage bufferedImage)
    {
        byte[] imageByte = null;

        WritableRaster writableRaster = bufferedImage.getRaster();
        DataBufferByte dataBufferByte = (DataBufferByte) writableRaster.getDataBuffer();

        imageByte = dataBufferByte.getData();

        return imageByte;
    }

    public static byte[] bufferedImageToByteArray(BufferedImage img)
    {

        if(true)
        {
            System.out.println("code commented out");
            return null;
        }
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        
        
        return os.toByteArray();

    }


    public static BufferedImage produceRenderedImage(byte[] dataBuffer, int width, int height)
    {
        DataBuffer dBuffer = new DataBufferByte(dataBuffer, width * height);
        WritableRaster wr = Raster.createInterleavedRaster(dBuffer, width, height, width, 1, new int[]
                {
                    0
                }, null);
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorModel cm = new ComponentColorModel(cs, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        BufferedImage bi = new BufferedImage(cm, wr, false, null);

        return bi;
    }

    public static BufferedImage resizeBufferedImage(BufferedImage image, int width, int height)
    {
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static BufferedImage convertImageToBufferedImage(Image image)
    {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getWidth(null), BufferedImage.TYPE_INT_RGB);

        bufferedImage.createGraphics().drawImage(image, 0, 0,null);

        return bufferedImage;

    }

    
}

