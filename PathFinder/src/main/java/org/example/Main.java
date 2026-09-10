package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Logic logic = new Logic();

        logic.loadFile("House");
        logic.drawMap();

        Scene scene = new Scene(logic.getRoot(), GlobalSettings.screenWidth, GlobalSettings.screenHeight);
        primaryStage.setTitle("Map");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
