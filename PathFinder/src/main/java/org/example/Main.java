package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Logic logic = new Logic();

        logic.loadFile("Street");
        logic.drawMap();

        //logic.findPath("66973468", "693323283", true); //Street
        //logic.findConnectedNodes("688027"); // Street
        logic.findCenter("688027"); //Street

        Scene scene = new Scene(logic.getRoot(), GlobalSettings.screenWidth, GlobalSettings.screenHeight);
        primaryStage.setTitle("Map");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
