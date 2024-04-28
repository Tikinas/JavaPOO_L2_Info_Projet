package com.example.abboud_tikinas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


import filtres.abboud_tikinas.*;
public class MainApplication extends Application {

    private ImageTagsInfo imageTagsInfo;
    private ImageView imageView;
    private ImageView securedImageView;
    private PasswordField passwordField;

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        imageView = new ImageView();
        //bouton pour selctioner le fichier de l'image
        Button openButton = new Button("Ouvrir une image");

        openButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                imageView.setFitHeight(200);
                imageView.setFitWidth(300);

                imageTagsInfo = new ImageTagsInfo();
            }
        });

        // menu déroulant pour les filtres
        Menu filterMenu = new Menu("Appliquer un filtre");
        MenuItem RGBSwapItem = new MenuItem("RGBSwap");
        MenuItem blackWhiteItem = new MenuItem("Black & White");
        MenuItem sepiaItem = new MenuItem("Sepia");
        MenuItem sobelItem = new MenuItem("Sobel");
        filterMenu.getItems().addAll(RGBSwapItem, blackWhiteItem, sepiaItem, sobelItem);

        // sélection d'un filtre dans le menu
        RGBSwapItem.setOnAction(e -> applyFilter(new RGBSwapFilter()));
        blackWhiteItem.setOnAction(e -> applyFilter(new BlackWhiteFilter()));
        sepiaItem.setOnAction(e -> applyFilter(new SepiaFilter()));
        sobelItem.setOnAction(e -> applyFilter(new SobelFilter()));

        // champ de saisie pour le tag
        TextField tagField = new TextField();

        // bouton pour ajouter le tag à l'image
        Button addTagButton = new Button("Ajouter le tag");

        addTagButton.setOnAction(e -> {
            String tag = tagField.getText();
            imageTagsInfo.addTag(tag);
            //System.out.println("Tags de l'image : " + imageTagsInfo.getTags());
            try {
                // Enregistrez les tags dans le fichier JSON
                imageTagsInfo.saveToJson("image_tags_info");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        // menu déroulant pour les filtres
        Menu rotationMenu = new Menu("Rotation");
        MenuItem rotationLeft = new MenuItem("Rotation à gauche");
        MenuItem rotationRight = new MenuItem("Rotation à droite");
        rotationMenu.getItems().addAll(rotationLeft, rotationRight);

        //bouton de rotation vers la gauche
        rotationLeft.setOnAction(e -> {
            if (imageView.getImage() != null) {
                // Effectuer une rotation de l'image à gauche
                Image rotatedImage = ImageRotation.rotateCounterClockwise(imageView.getImage());
                imageView.setImage(rotatedImage);
            }
        });

        // bouton de rotation vers la droite
        rotationRight.setOnAction(e -> {
            if (imageView.getImage() != null) {
                // Effectuer une rotation de l'image a droite
                Image rotatedImage = ImageRotation.rotateClockwise(imageView.getImage());
                imageView.setImage(rotatedImage);
            }
        });

        // menu déroulant pour les symetries
        Menu symetrieMenu = new Menu("Symetrie");
        MenuItem symetrieh = new MenuItem("Symetrie horizontal");
        MenuItem symetriev = new MenuItem("Symetrie vertical");
        symetrieMenu.getItems().addAll(symetrieh, symetriev);

        // bouton de sypmetrie  horizontal
        symetrieh.setOnAction(e -> {
            if (imageView.getImage() != null) {
                // Effectuer une symetrie horizontal de l'image
                Image imageSymetries = ImageSymetries.horizontalSymmetry(imageView.getImage());
                imageView.setImage(imageSymetries);
            }
        });

        // bouton de symetries vertical
        symetriev.setOnAction(e -> {
            if (imageView.getImage() != null) {
                // Effectuer une symetrie vertical de l'image
                Image imageSymetries = ImageSymetries.verticalSymetry(imageView.getImage());
                imageView.setImage(imageSymetries);
            }
        });

        // Créez les champs de saisie pour le filtre et le mot de passe
        TextField filterField = new TextField();
        filterField.setPromptText("Filtre");

        passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        // Créez le bouton pour sécuriser l'image
        Button secureButton = new Button("Sécuriser");
        secureButton.setOnAction(e -> {
            try {
                secureImage();
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Créez une nouvelle ImageView pour afficher l'image sécurisée
        securedImageView = new ImageView();
        securedImageView.setFitWidth(300);
        securedImageView.setFitHeight(200);




        MenuBar filterBar = new MenuBar(filterMenu);
        MenuBar rotationBar = new MenuBar(rotationMenu);
        MenuBar symetriesBar = new MenuBar(symetrieMenu);
        HBox menuAndButtonBox = new HBox(openButton,filterBar,rotationBar,symetriesBar);


        // Ajoutez l'ImageView, le label et les boutons à un VBox
        VBox vbox = new VBox(menuAndButtonBox, imageView, l, tagField, addTagButton);
        VBox SecurityBox = new VBox(filterField, passwordField,secureButton);
        SecurityBox.setPadding(new Insets(25, 15, 5, 15));
        VBox encryptedBox = new VBox(securedImageView);
        encryptedBox.setPadding(new Insets(25, 15, 5, 15));
        HBox hbox = new HBox();
        hbox.getChildren().addAll(vbox, SecurityBox, encryptedBox);
        Scene scene = new Scene(hbox, 910, 480);
        stage.setScene(scene);
        stage.show();
    }

    // Méthode pour appliquer un filtre à l'image
    private void applyFilter(ImageFilter filter) {
        if (imageView.getImage() != null) {
            Image filteredImage = filter.apply(imageView.getImage());
            imageView.setImage(filteredImage);
            imageTagsInfo.addTransformation(new ImageTagsInfo.Transformation(filter.getClass().getSimpleName())); // Ajoutez la transformation à l'imageTagsInfo
            //System.out.println("Tags de l'image : " + imageTagsInfo.getTransformations()); // Affichez les tags
        }
    }
    // Méthode pour sécuriser l'image
    private void secureImage() throws NoSuchAlgorithmException, IOException {
        // Récupérez l'image d'origine
        Image originalImage = imageView.getImage();
        if (originalImage != null) {
            // Appelez la méthode pour sécuriser l'image en utilisant le mot de passe fourni
            Image securedImage = ImageSecurity.encryptImage(originalImage, passwordField.getText());

            // j'essaie d'enregistrer l'image générée apres l'encryptement mais ça ne marche pas j'ai essayé de toutes les façons d'ajouter la dependance de swing.SwingFXUtils dans pomp.xml et module-info
            /*File outputFile = new File("secured_image.png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(securedImage, null), "png", outputFile);
                System.out.println("Image sécurisée enregistrée avec succès.");
            } catch (IOException ex) {
                System.err.println("Erreur lors de l'enregistrement de l'image sécurisée : " + ex.getMessage());
            }*/

            // Affichez l'image sécurisée dans l'ImageView dédiée
            securedImageView.setImage(securedImage);
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
