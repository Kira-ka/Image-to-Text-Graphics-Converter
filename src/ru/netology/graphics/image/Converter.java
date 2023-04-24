package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {

    private double maxRatio;
    private int maxWidth;
    private int maxHeight;

    private ColorSchema cl;


    public Converter(ColorSchema cl) {
        this.cl = cl;
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        checkMaxRatio(img.getWidth(), img.getHeight());
        Image scaledImage = newWidthAndNewHeight(img);
        BufferedImage bwImg = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        RenderedImage imageObject = bwImg;
        ImageIO.write(imageObject, "png", new File("out2.png"));
        return toString(bwImg);
    }


    public String toString(BufferedImage img) {
        WritableRaster bwRaster = img.getRaster();
        int sizeW = bwRaster.getWidth();
        int sizeH = bwRaster.getHeight();
        char array[][] = new char[sizeH][sizeW];
        for (int h = 0; h < sizeH; h++) {
            for (int w = 0; w < sizeW; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = this.cl.convert(color);
                array[h][w] = c;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            char[] tmp = array[i];
            for (int j = 0; j < tmp.length; j++) {
                sb
                        .append(tmp[j])
                        .append(tmp[j]);

            }
            sb
                    .append("\n");
        }
        return sb.toString();
    }


    public Image newWidthAndNewHeight(BufferedImage img) {
        int originalWidth = img.getWidth();
        int originalHeight = img.getHeight();
        int boundWidth = this.maxWidth;
        int boundHeight = this.maxHeight;
        int newWidth = originalWidth;
        int newHeight = originalHeight;
        if (boundWidth != 0 && originalWidth > boundWidth) {
            newWidth = boundWidth;
            newHeight = (newWidth * originalHeight) / originalWidth;
        }
        if (boundHeight != 0 && newHeight > boundHeight) {
            newHeight = boundHeight;
            newWidth = (newHeight * originalWidth) / originalHeight;
        }
        return img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
    }


    public boolean checkMaxRatio(int x, int y) throws BadImageSizeException {
        if (this.maxRatio != 0) {
            boolean ratio = (x / y) <= this.maxRatio;
            return ratio;
        } else if (this.maxRatio != 0) {
            boolean ratio = (x / y) > this.maxRatio;
            throw new BadImageSizeException((x / y), this.maxRatio);
        } else {
            return true;
        }

    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;

    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {

    }
}
