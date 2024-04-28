package filtres.abboud_tikinas;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class SobelFilter implements ImageFilter {
    @Override
    public Image apply(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage sobelImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = sobelImage.getPixelWriter();

        int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                double pixelX = 0;
                double pixelY = 0;

                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        Color color = pixelReader.getColor(x + dx, y + dy);
                        double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

                        pixelX += sobelX[dy + 1][dx + 1] * gray;
                        pixelY += sobelY[dy + 1][dx + 1] * gray;
                    }
                }

                double val = Math.sqrt(pixelX * pixelX + pixelY * pixelY);

                val = Math.min(val, 1);

                Color sobelColor = new Color(val, val, val, 1);
                pixelWriter.setColor(x, y, sobelColor);
            }
        }

        return sobelImage;
    }
}
