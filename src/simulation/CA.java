package simulation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.css.Rule;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CA extends Application {
    public static final Paint BACKGROUND = Color.WHITE;
    public static final String TITLE = "Cell Society Simulation";
    public static final double windowWidth = 900;
    public static final double windowHeight = 900;
    public static final int FRAME_PER_SECOND = 60;
    public static final double MILLISECOND_DELAY = 1000 / FRAME_PER_SECOND;

    private Stage primaryStage;
    private Scene mySplashScene;
    private Scene mySimulationScene;
    private Rule myRule;
    private Timeline animation;
    private UIManager myUI;

    /**
     * Initialize the stage and a scene. Define how the scene will be updated.
     * @param stage: A JavaFx Stage object.
     */
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        // create UI Manager object
        myUI = new UIManager();

        // set up scene
        Group splashRoot = myUI.setUpSplash(primaryStage);
        mySplashScene = splashRoot.getScene();
        primaryStage.setScene(mySplashScene);
        primaryStage.setTitle(TITLE);
        primaryStage.show();

        var startButton = new Button("ENTER GAME");
        startButton.setOnAction(e -> primaryStage.setScene(mySimulationScene));

        // set up animation
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }



    /**
     * Create
     * @param file: The File Object representing the XML file to be read.
     * @return root: Return a JavaFx Group node called root that will displayed.
     */
    private Group initializeSimulation (File file) {
        Group root = new Group();
        Grid myGrid = new Grid(root, new XMLReader(file));
        myGrid.addCellImageViewToRoot();
        Rule myRule = new Rule(myGrid);
        return root;
    }

    /**
     * Animation for the simulations. Change states and appearances of cells. Forward the states of the cells to the next generation.
     */
    private void step() {

    }

    /**
     * Start the program.
     */
    public static void main(String[] args) {
        launch(args);
    }

}