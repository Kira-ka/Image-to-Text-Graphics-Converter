package ru.netology.graphics;

import ru.netology.graphics.image.*;
import ru.netology.graphics.server.GServer;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        ColorSchema cl = new ColorSchema();
        TextGraphicsConverter converter = new Converter(cl);
        GServer server = new GServer(converter);
        server.start();
    }
}
