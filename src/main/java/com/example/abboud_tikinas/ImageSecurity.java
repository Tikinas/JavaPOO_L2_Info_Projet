package com.example.abboud_tikinas;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ImageSecurity {
    private static final int NUM_PIXELS_TO_SWAP = 5;
    private static final String HASH_ALGORITHM = "SHA-256";

    public static Image encryptImage(Image image, String password) throws NoSuchAlgorithmException {
        // Convertir le mot de passe en une graine pour le générateur de nombres aléatoires
        byte[] seed = getHash(password.getBytes());

        // Initialiser le générateur de nombres aléatoires avec la graine
        SecureRandom random = new SecureRandom(seed);

        // Créer une image modifiable pour stocker les pixels mélangés
        WritableImage encryptedImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());

        // Parcourir chaque pixel de l'image
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                // Générer une position aléatoire pour échanger des pixels
                int randomX = random.nextInt((int) image.getWidth());
                int randomY = random.nextInt((int) image.getHeight());
                // Échanger les pixels à la position courante et la position aléatoire
                for (int i = 0; i < NUM_PIXELS_TO_SWAP; i++) {
                    int pixel1 = (int) image.getPixelReader().getArgb(x, y);
                    int pixel2 = (int) image.getPixelReader().getArgb(randomX, randomY);
                    encryptedImage.getPixelWriter().setArgb(x, y, pixel2);
                    encryptedImage.getPixelWriter().setArgb(randomX, randomY, pixel1);
                }
            }
        }

        return encryptedImage;
    }

    private static byte[] getHash(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        digest.update(input);
        return digest.digest();
    }
}
