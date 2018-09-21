package simulation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CA extends Application {
    public static final Paint BACKGROUND = Color.WHITE;
    public static final String TITLE = "Cell Society Simulation";

    private Stage primaryStage;
    private Timeline animation;
    private UIManager myUI;
    private File myFile;
    private ReadXML myReader;

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

        // start simulation
        // myUI.run();

        // read xml file
        File myFile = myUI.getFile();
        myReader = myUI.getReader();

    }

    /**
     * Create
     * @param file: The File Object representing the XML file to be read.
     * @return root: Return a JavaFx Group node called root that will displayed.
     */
    private Group initializeSimulation (File file) {
        Group root = new Group();

        Grid myGrid = new Grid(root, myReader);

        //myGrid.addCellImageViewToRoot();
        //Rule myRule = new Rule(myGrid);
        return root;
    }

    /**
     * Start the program.
     */
    public static void main(String[] args) {
        launch(args);
    }

}