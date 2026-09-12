package org.server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.server.Models.Shape;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Logic logic = new Logic(Shape.HYPERBOLA);

        logic.loadFile("hyperbola2");
        logic.runPerceptron();
        logic.drawNodes();

        Scene scene = new Scene(logic.getRoot(), GlobalSettings.screenWidth, GlobalSettings.screenHeight);
        primaryStage.setTitle("Perceptron - " + logic.getShape().name().toLowerCase());
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(_ -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
