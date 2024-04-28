package filtres.abboud_tikinas;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class SepiaFilter implements ImageFilter {
    @Override
    public Image apply(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage sepiaImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = sepiaImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                double r = color.getRed();
                double g = color.getGreen();
                double b = color.getBlue();

                double tr = (0.393 * r + 0.769 * g + 0.189 * b);
                double tg = (0.349 * r + 0.686 * g + 0.168 * b);
                double tb = (0.272 * r + 0.534 * g + 0.131 * b);

                tr = Math.min(tr, 1);
                tg = Math.min(tg, 1);
                tb = Math.min(tb, 1);

                Color sepiaColor = new Color(tr, tg, tb, color.getOpacity());
                pixelWriter.setColor(x, y, sepiaColor);
            }
        }

        return sepiaImage;
    }
}
