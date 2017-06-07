package com.latlab.common.imageutils;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Edwin
 */
public  class ImageResource
{
    

    public static InputStream imageStream = null;

    private static Image resourceImage = null;
    private static Icon resourceIcon = null;

    public static Dimension SIZE_16x16 = new Dimension(16, 16);
    public static Dimension SIZE_23x23 = new Dimension(23, 23);
    public static Dimension SIZE_48x48 = new Dimension(48, 48);
    public static Dimension SIZE_800x700 = new Dimension(800, 600);

//    private ImageResource() {
//    }



    public static Image getResourceImage(String imageName)
    {
        resourceImage = null;

        if(imageName == null)
            return null;

        InputStream inputStream = null;

        try
        {
            try
            {
                ImageResource ingRes  = new ImageResource();

                inputStream = ingRes.getClass().getResourceAsStream(imageName);

//                inputStream = ImageResource.class.getResourceAsStream(imageName);
            } catch (Exception e)
            {
                System.out.println("error getting stream for imageResouce");
                e.printStackTrace();
            }

            if(inputStream == null)
                return null;
            
            resourceImage = ImageIO.read(inputStream);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return resourceImage;
    }
    
    public static Icon getResourceIcon(String imageName)
    {
        resourceIcon = null;
        
        resourceImage = getResourceImage(imageName);

        if(resourceImage == null)
            return null;
        
        resourceIcon = new ImageIcon(resourceImage);
        
        return resourceIcon;
    }

    public static Icon getResourceIcon(String imageName, Dimension size)
    {
        resourceIcon = null;

        resourceImage = getResourceImage(imageName,size);

        if(resourceImage == null)
            return null;

        resourceIcon = new ImageIcon(resourceImage);

        return resourceIcon;
    }

    public static Image getResourceImage(String imageName, Dimension size)
    {

        resourceImage = getResourceImage(imageName);

        if(resourceImage == null)
            return null;

        resourceImage = TekComImageUtils.resizeBufferedImage((BufferedImage) resourceImage, size.width, size.height);


        return resourceImage;
    }


    private static ImageResource imageResource = null;

    public static ImageResource getInstance()
    {
        if(imageResource == null)
            imageResource = new ImageResource();

        return imageResource;
    }






    public static String saveJPGImage(Image img, String path, String imageName)
    {
        return saveImage(img, path, imageName, ImgExtenstion.JPG);
    }

    public static String saveJPGImage(byte[] img, String path, String imageName)
    {
        return saveImage(img, path, imageName, ImgExtenstion.JPG);
    }


    public static String saveImage(byte[] imageBytes, String path, String imageName, ImgExtenstion ie )
    {
        try {
            
            new File(path).mkdirs();
            
            File f = new File(path, imageName+"."+ie.getExt());
            System.out.println("writing .....   " + f.getAbsolutePath());
            
            f.createNewFile();
            FileImageOutputStream fios = new FileImageOutputStream(f);
            fios.write(imageBytes, 0, imageBytes.length);
            fios.close();

            return f.getAbsolutePath();
        } catch (Exception e)
        {
            Logger.getLogger(ImageResource.class.getName()).log(Level.INFO, e.getMessage());
        }

        return null;
    }
    public static String saveImage(Image img, String path, String imageName, ImgExtenstion extention)
    {
        try {
            BufferedImage bi = convertImageToBufferedImage(img);

            System.out.println("creating file for saveing  ...." + path + " image name " + imageName);
            File f = new File(path, imageName+"."+extention.getExt());
            f.mkdirs();
            ImageIO.write(bi,extention.getExt() , f);



            return f.getAbsolutePath();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return null;
    }

    public static void main(String[] args) {

        try {

            System.out.println(System.getenv());

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.exit(0);
    }


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

    public byte[] getByteFromBufferedImage(BufferedImage bufferedImage)
    {
        byte[] imageByte = null;

        WritableRaster writableRaster = bufferedImage.getRaster();
        DataBufferByte dataBufferByte = (DataBufferByte) writableRaster.getDataBuffer();

        imageByte = dataBufferByte.getData();

        return imageByte;
    }

    public static byte[] bufferedImageToByteArray(BufferedImage img) throws IOException
    {
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            return  baos.toByteArray();
        }
        
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
    
    public static BufferedImage resizeImage(String fileName, int width, int height)
    {
        BufferedImage bi = convertFileToImage(new File(fileName));
        return resizeBufferedImage(bi, width, height);
    }
    
    public static BufferedImage resizeImage(String fileName, Dimension newDimension)
    {
        BufferedImage bi = convertFileToImage(new File(fileName));
        return resizeBufferedImage(bi, newDimension.width, newDimension.height);
    }
    
    
    public static boolean resizeFileImage(String fileName, int width, int height)
    {
        return resizeFileImage(fileName, new Dimension(width, height));
    }
    
    
    public static boolean resizeFileImage(String fileName, Dimension newDimension)
    {
        
        try
        {
            BufferedImage bi = resizeImage(fileName, newDimension);
            byte[] imageBytes = bufferedImageToByteArray(bi);
            FileImageOutputStream fios = new FileImageOutputStream(new File(fileName));
            fios.write(bufferedImageToByteArray(bi), 0, imageBytes.length);
            fios.close();
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return false;
            
    }

    public static BufferedImage convertImageToBufferedImage(Image image)
    {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getWidth(null), BufferedImage.TYPE_INT_RGB);

        bufferedImage.createGraphics().drawImage(image, 0, 0,null);

        return bufferedImage;

    }
}
