package filtres.abboud_tikinas;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class BlackWhiteFilter implements ImageFilter {
    @Override
    public Image apply(Image image) {
        WritableImage result = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        PixelWriter writer = result.getPixelWriter();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = image.getPixelReader().getColor(x, y);
                double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                writer.setColor(x, y, Color.color(gray, gray, gray));
            }
        }
        return result;
    }
}
