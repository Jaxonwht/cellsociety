package simulation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collection;

/**
 * @author Julia Saveliff, Yunhao Qing
 */
public class UIManager {
    // constant screen dimensions
    private final static int SPLASH_SIZE = 300;
    private final static int PANEL_WIDTH = 175;
    private final static int LAYOUT_SPACING = 20;

    // button text
    private final static String LOAD_NEW_TEXT = "Load new simulation";
    private final static String PAUSE_TEXT = "Pause";
    private final static String PLAY_TEXT = "Play";
    private final static String STEP_TEXT = "Step";
    private final static String CHOOSE_TEXT = "Select XML file";
    private final static String SELECTED_TEXT = "Selected file:";
    private final static String START_TEXT = "Start simulation";

    // animation constants
    private static final int FRAMES_PER_SECOND = 3;
    private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private final static double MIN_RATE = 0.1;
    private final static double MAX_RATE = 3;
    private final static double RATE = 1.5;
    private final static int MAX_GENERATION = 500;

    // UI components
    private Stage myStage;
    private Group mySplashRoot;
    private Group mySimRoot;
    private Button splashToSimButton;
    private Button simToSplashButton;
    private Slider mySpeedSlider;
    private Grid myGrid;
    private Rule myRule;
    private int myNumGenerations=1;
    private Text myGenerationsDisplay;

    // file read components
    private File myFile;
    private ReadXML myReader;
    private Text myFileString;

    // animation components
    private Timeline myAnimation;

    public UIManager(Stage stage) {
        myStage = stage;
    }

    /**
     * Method to be called once from main class to initiate UI.
     */
    public void create() {

        mySplashRoot = setupSplash(myStage);
        myStage.setScene(mySplashRoot.getScene());

    }

    /**
     * Begins animation to regularly call step method.
     */
    public void run() {
        myAnimation = new Timeline();
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
    }

    /**
     * Animation for the simulations.
     * TODO: Change states and appearances of cells. Forward the states of the cells to the next generation.
     */
    private void step(double stepTime) {
        // Adjust rate according to user speed input
        myAnimation.setRate(mySpeedSlider.getValue());

        // Update num generations
        myNumGenerations += 1;
        if (myNumGenerations > MAX_GENERATION) {
            myAnimation.stop();
        }
        myGenerationsDisplay.setText("Generations: "+myNumGenerations);

        //update cells
        myRule.determineNextStates();
        myRule.updateGrid();

    }

    private Group setupSplash(Stage stage) {

        var root = new Group();
        var scene = new Scene(root, SPLASH_SIZE, SPLASH_SIZE);

        var layout = new VBox(LAYOUT_SPACING);
        layout.setPrefSize(SPLASH_SIZE,SPLASH_SIZE);
        layout.setAlignment(Pos.CENTER);

        var chooseFileButton = new Button(CHOOSE_TEXT);
        var selectedFile = new Text(SELECTED_TEXT);
        myFileString = new Text();
        splashToSimButton = new Button(START_TEXT);

        layout.getChildren().addAll(chooseFileButton, selectedFile, myFileString, splashToSimButton);
        root.getChildren().add(layout);

        var fileChooser = new FileChooser();
        fileChooser.setTitle(CHOOSE_TEXT);
        chooseFileButton.setOnAction( event -> handleFile(fileChooser.showOpenDialog(stage)) );

        return root;

    }

    private void handleFile(File file) {
        // TODO: Error checking on file type
        if (file != null) {

            myFile = file;
            String[] myFileNameArray = myFile.toString().split("/");
            String myFileName = myFileNameArray[myFileNameArray.length-1];
            myFileString.setText(myFileName);

            try {
                myReader = new ReadXML(myFile);
                handleReader(myReader);
            } catch (ParserConfigurationException PCe) {
                // TODO: handle exception
                System.out.println("ParserConfigurationException: "+PCe.getMessage());
            } catch (IOException IOe) {
                // TODO: handle exception
                System.out.println("IOException: "+IOe.getMessage());
            } catch (SAXException SAXe) {
                // TODO: handle exception
                System.out.println("SAXException: "+SAXe.getMessage());
            }

        } else {
            // TODO: Handle null file
        }
    }


    private Group setupSimulation(Stage stage, int width, int height, String title) {
        // set up layout of scene
        var root = new Group();
        var scene = new Scene(root, width+PANEL_WIDTH, height);
        var border = new BorderPane();

        // user panel
        var userPanel = new VBox(20);
        userPanel.setPrefSize(PANEL_WIDTH,height);
        userPanel.setStyle("-fx-background-color: #336699");
        userPanel.setAlignment(Pos.CENTER);

        // user simulation controls
        myGenerationsDisplay = new Text("Generations: "+myNumGenerations);
        simToSplashButton = new Button(LOAD_NEW_TEXT);
        var playButton = new Button(PLAY_TEXT);
        playButton.setOnAction( event -> handlePlay() );
        var pauseButton = new Button(PAUSE_TEXT);
        pauseButton.setOnAction( event -> handlePause() );
        var stepButton = new Button(STEP_TEXT);
        stepButton.setOnAction( event -> handleStep() );
        var speedText = new Text("Speed");
        mySpeedSlider = new Slider();
        mySpeedSlider.setMin(MIN_RATE);
        mySpeedSlider.setMax(MAX_RATE);
        mySpeedSlider.setValue(RATE);

        // grid region
        var gridRegion = new Pane();
        gridRegion.setPrefSize(width, height);

        myGrid = new Grid(root, myReader);
        myGrid.populateCells();
        var cellsToAdd = myGrid.getAllShape();

        String type = myReader.getName();
        try {
            Class<?> clazz = Class.forName("simulation." + type + "Rule");
            Constructor<?> constructor = clazz.getConstructor(Grid.class);
            Object instance = constructor.newInstance(myGrid);
            myRule = (Rule) instance;
        } catch (Exception e){
            // TODO: catch exception
            System.out.println("Exception caught: "+e.getMessage());
        }

        // add elements to each region
        userPanel.getChildren().addAll(myGenerationsDisplay, simToSplashButton, playButton, pauseButton, stepButton, speedText, mySpeedSlider);
        gridRegion.getChildren().addAll(cellsToAdd);

        // set layout regions
        border.setRight(userPanel);
        border.setLeft(gridRegion);

        // add layout to root
        root.getChildren().add(border);

        return root;

    }

    private void setupSceneTransitions(Stage stage, Group splash, Group simulation) {

        splashToSimButton.setOnAction( event -> {
            if(myFile !=null) {
                stage.setScene(simulation.getScene());
                run();
            }
        });
        simToSplashButton.setOnAction( event -> {
            myAnimation.stop();
            stage.setScene(splash.getScene());
            myNumGenerations = 1;
        } );

    }

    private void handleReader(ReadXML reader) {

        mySimRoot = setupSimulation(myStage, reader.getHeight(), reader.getWidth(), reader.getName());
        setupSceneTransitions(myStage, mySplashRoot, mySimRoot);

    }

    private void handlePause() {
        // pause animation
        myAnimation.pause();
    }

    private void handlePlay() {
        // resume animation
        myAnimation.play();
    }

    private void handleStep() {
        // step through animation
        myAnimation.pause();
        step(SECOND_DELAY);
    }

    public File getFile() { return myFile; }

    public ReadXML getReader() { return myReader; }

}
