package com.example.abboud_tikinas;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ImageRotation {

    public static Image rotateClockwise(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        PixelReader pixelReader = image.getPixelReader();
        WritableImage rotatedImage = new WritableImage(height, width);
        PixelWriter pixelWriter = rotatedImage.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelWriter.setColor(y, width - x - 1, pixelReader.getColor(x, y));
            }
        }
        return rotatedImage;
    }

    public static Image rotateCounterClockwise(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        PixelReader pixelReader = image.getPixelReader();
        WritableImage rotatedImage = new WritableImage(height, width);
        PixelWriter pixelWriter = rotatedImage.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelWriter.setColor(height - y - 1, x, pixelReader.getColor(x, y));
            }
        }
        return rotatedImage;
    }
}
