import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;

public class Photoshop extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    static Scene scene;
    Slider luminosite = new Slider(-1, 1, 0);
    Slider contraste = new Slider(-1, 1, 0);
    Slider teinte = new Slider(-1, 1, 0);
    Slider saturation = new Slider(-1, 1, 0);
    Label texteEnBas = new Label("Bienvenue dans le modificateur d'images!");
    final ImageView imageView = new ImageView();
    Image image1 = new Image("image1.jpg");
    Image image2 = new Image("image2.jpg");
    Image image3 = new Image("image3.jpg");


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Photoshop gratuit");


        try {
            Image image = new Image("default.jpg");
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(500);


        MenuBar menuBar = new MenuBar();
        Menu menuFichier = creationMenu(0)[0];
        Menu menuActions = creationMenu(1)[0];
        menuBar.getMenus().addAll(menuFichier,menuActions);

        Rectangle rectangle = new Rectangle(0, 0, 0, 0);
        rectangle.setFill(Color.LIGHTGRAY);
        rectangle.widthProperty().bind(primaryStage.widthProperty());
        rectangle.heightProperty().bind(texteEnBas.heightProperty());

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        StackPane stackPane = new StackPane(rectangle, texteEnBas);
        stackPane.setAlignment(Pos.CENTER_LEFT);
        borderPane.setBottom(stackPane);
        stackPane.setPadding(new Insets(200, 0, 0, 0));


        Label label1 = new Label("Luminosité");
        Tooltip tooltip = new Tooltip("Rend l'image plus claire ou plus sombre");
        luminosite.setTooltip(tooltip);
        Label label2 = new Label("Contraste");
        Tooltip tooltip1 = new Tooltip("Diminue ou augmente la différence entre les couleurs");
        contraste.setTooltip(tooltip1);
        Label label3 = new Label("Teinte");
        Tooltip tooltip2 = new Tooltip("Change la teinte(couleur) de l'image");
        teinte.setTooltip(tooltip2);
        Label label4 = new Label("Saturation");
        Tooltip tooltip3 = new Tooltip("Diminue ou augmente l'intensité des couleurs");
        saturation.setTooltip(tooltip3);

        VBox vBox = new VBox(label1, luminosite, label2, contraste, label3, teinte, label4, saturation);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(imageView, vBox);
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(hBox);


        ColorAdjust colorAdjust = new ColorAdjust();
        imageView.setEffect(colorAdjust);

        luminosite.valueProperty().addListener((observable, oldValue, newValue) -> {
            colorAdjust.brightnessProperty().setValue(luminosite.getValue());
        });
        contraste.valueProperty().addListener((observable, oldValue, newValue) -> {
            colorAdjust.contrastProperty().setValue(contraste.getValue());
        });
        teinte.valueProperty().addListener((observable, oldValue, newValue) -> {
            colorAdjust.hueProperty().setValue(teinte.getValue());
        });
        saturation.valueProperty().addListener((observable, oldValue, newValue) -> {
            colorAdjust.saturationProperty().setValue(saturation.getValue());
        });

        ContextMenu contextMenu = new ContextMenu(creationMenu(2));

        scene = new Scene(new Group());
        scene.setOnContextMenuRequested(event -> {
            contextMenu.show(scene.getWindow(), event.getScreenX(), event.getScreenY());
        });
        scene.setRoot(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();


    }

    public Menu[] creationMenu(int state) {
        MenuItem menuItem = new MenuItem("Image #1");
        MenuItem menuItem1 = new MenuItem("Image #2");
        MenuItem menuItem2 = new MenuItem("Image #3");
        MenuItem menuItem3 = new MenuItem("Réinitialiser");
        Menu menu = new Menu("Charger une image");
        Menu menu1 = new Menu("Actions");
        Menu menu2 = new Menu("Fichiers");
        menu1.getItems().addAll(menuItem3);
        menu.getItems().addAll(menuItem, menuItem1, menuItem2);
        menu2.getItems().addAll(menu);

        menuItem.setOnAction(event -> {
            imageView.setImage(image1);
            texteEnBas.setText("Image 1 chargée");
        });
        menuItem1.setOnAction(event -> {
            imageView.setImage(image2);
            texteEnBas.setText("Image 2 chargée");
        });
        menuItem2.setOnAction(event -> {
            imageView.setImage(image3);
            texteEnBas.setText("Image 3 chargée");
        });
        menuItem3.setOnAction(event -> {
            texteEnBas.setText("Réinitialisation des ajustements de couleurs");
            luminosite.setValue(0);
            contraste.setValue(0);
            teinte.setValue(0);
            saturation.setValue(0);
        });

        if (state == 0) {
            Menu[] menus = {menu2};
            return menus;
        } else if (state == 1) {
            Menu[] menus = {menu1};
            return menus;
        } else {
            Menu[] menus = {menu1, menu};
            return menus;

        }


    }
}
