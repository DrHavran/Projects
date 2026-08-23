package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Draw draw = new Draw();

        Scene scene = new Scene(draw.getRoot(), Settings.screenWidth, Settings.screenHeight);

        primaryStage.setTitle("K-Means");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
