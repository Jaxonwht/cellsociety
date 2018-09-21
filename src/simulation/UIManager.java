package simulation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class UIManager {
    // constant screen dimensions
    private final static int SPLASH_SIZE = 300;
    private final static int PANEL_WIDTH = 175;
    private final static int LAYOUT_SPACING = 20;

    // button text
    private final static String LOAD_NEW_TEXT = "Load new simulation";
    private final static String PAUSE_TEXT = "Pause";
    private final static String RESUME_TEXT = "Resume";
    private final static String STEP_TEXT = "Step";
    private final static String CHOOSE_TEXT = "Select XML file";
    private final static String SELECTED_TEXT = "Selected file:";
    private final static String START_TEXT = "Start simulation";

    // animation constants
    private static final int FRAMES_PER_SECOND = 40;
    private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private final static double MIN_RATE = 0.1;
    private final static double MAX_RATE = 3;
    private final static double RATE = 1.5;

    // UI components
    private Stage myStage;
    private Group mySplashRoot;
    private Group mySimRoot;
    private Button splashToSimButton;
    private Button simToSplashButton;
    private Slider mySpeedSlider;

    // file read components
    private File myFile;
    private ReadXML myReader;
    private Text myFileString;

    // animation components
    private Timeline myAnimation;

    // test
    private Text textToTestStep;


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
        myAnimation.play();
    }

    /**
     * Animation for the simulations.
     * TODO: Change states and appearances of cells. Forward the states of the cells to the next generation.
     */
    private void step(double stepTime) {
        // Adjust rate according to user speed input
        myAnimation.setRate(mySpeedSlider.getValue());

        // Move test text across screen
        textToTestStep.setX(textToTestStep.getX()-50*stepTime);
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
                System.out.println("Created reader");
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
        simToSplashButton = new Button(LOAD_NEW_TEXT);

        // user simulation controls
        var pauseButton = new Button(PAUSE_TEXT);
        pauseButton.setOnAction( event -> handlePause() );
        var resumeButton = new Button(RESUME_TEXT);
        resumeButton.setOnAction( event -> handleResume() );
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
        var gridButton = new Button(title);

        textToTestStep = new Text("hi...");
        textToTestStep.setX(300);
        textToTestStep.setY(150);

        // add elements to each region
        userPanel.getChildren().addAll(simToSplashButton, pauseButton, resumeButton, stepButton, speedText, mySpeedSlider);
        gridRegion.getChildren().add(gridButton);
        gridRegion.getChildren().add(textToTestStep);

        // set layout regions
        border.setRight(userPanel);
        border.setLeft(gridRegion);

        // add layout to root
        root.getChildren().add(border);

        return root;

    }

    private void setupSceneTransitions(Stage stage, Group splash, Group simulation) {

        splashToSimButton.setOnAction( event -> { if(myFile !=null) {
                    stage.setScene(simulation.getScene());
                    run();
                }
            }
        );
        simToSplashButton.setOnAction( event -> stage.setScene(splash.getScene()) );

    }

    private void handleReader(ReadXML reader) {

        System.out.println("handleReader called");
        mySimRoot = setupSimulation(myStage, reader.getHeight(), reader.getWidth(), reader.getName());
        setupSceneTransitions(myStage, mySplashRoot, mySimRoot);

    }

    private void handlePause() {
        // pause animation
        myAnimation.pause();
        System.out.println(PAUSE_TEXT);
    }

    private void handleResume() {
        // resume animation
        myAnimation.play();
        System.out.println(RESUME_TEXT);
    }

    private void handleStep() {
        // step through animation
        myAnimation.pause();
        step(SECOND_DELAY);
        System.out.println(STEP_TEXT);
    }

    public File getFile() { return myFile; }

    public ReadXML getReader() { return myReader; }

}
