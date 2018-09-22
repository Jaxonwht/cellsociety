package simulation;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Julia Saveliff
 */
public class CA extends Application {
    public static final String TITLE = "Cell Society Simulation";
    private Stage primaryStage;
    private UIManager myUI;

    /**
     * Initialize the stage and a scene. Define how the scene will be updated.
     * @param stage: A JavaFx Stage object.
     */
    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        // create UI Manager object
        myUI = new UIManager(primaryStage);

        // set up scene
        myUI.create();
        primaryStage.setTitle(TITLE);
        primaryStage.show();

    }

    /**
     * Start the program.
     */
    public static void main(String[] args) {
        launch(args);
    }

}