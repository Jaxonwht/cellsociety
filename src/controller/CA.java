package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import UI.UIManager;

/**
 * @author Julia Saveliff
 */
public class CA extends Application {
    private Stage primaryStage;
    private UIManager myUI;

    /**
     * Initialize and show the stage. Call UIManager to handle all user interface components and updates.
     * @param stage: A JavaFx Stage object.
     */
    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        myUI = new UIManager(primaryStage);
        myUI.create();

        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Start the program.
     */
    public static void main(String[] args) {
        launch(args);
    }

}