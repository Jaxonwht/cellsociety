package UI;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ComboBox;
import simulation.Grid;
import simulation.ReadXML;
import rule.Rule;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Julia Saveliff, Haotian Wang
 *
 * Handles and controls all user interface components. All UI functionality is handled
 * by calling the method public void create().
 */
public class UIManager {
    // constant screen dimensions
    private final static int SPLASH_SIZE = 300;
    private final static int PANEL_WIDTH = 175;
    private final static int CHART_HEIGHT = 200;
    private final static int LAYOUT_SPACING = 20;
    private final static int USER_PANEL_ITEM_SPACING = 20;

    // button text
    private ResourceBundle myTextResources;
    private final static String DEFAULT_TEXT_RESOURCE_FILE = "UI/UI_text";

    // graphic components
    private ResourceBundle myGraphicResources;
    private final static String DEFAULT_GRAPHIC_RESOURCE_FILE = "UI/UI_graphic";

    // animation constants
    private static final int FRAMES_PER_SECOND = 3;
    private static final double MILLISECOND_DELAY = 1000.0 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private final static double MIN_RATE = 0.1;
    private final static double MAX_RATE = 3;
    private final static double RATE = 1.5;
    private final static int MAX_GENERATION = 500;

    // UI components
    private Stage myStage;
    private Group mySplashRoot;
    private Button splashToSimButton;
    private Button simToSplashButton;
    private ComboBox<String> gridTypeButton;
    private ComboBox<String> cellShapeButton;
    private Slider mySpeedSlider;
    private Rule myRule;
    private int myGenerationCount=0;
    private Text myGenerationsDisplay;
    private Text myErrorDisplay;
    private GridUI myGridUI;
    private Grid myGrid;
    private LineChart myLineChart;
    private XYChart.Series[] mySeriesArray;

    // file read components
    private File myFile;
    private Text myFileText;
    private String myGridType;
    private String myCellShape;

    // animation components
    private Timeline myAnimation;

    /**
     * Class constructor
     * @param stage: A JavaFx Stage object.
     */
    public UIManager(Stage stage) {
        myTextResources = ResourceBundle.getBundle(DEFAULT_TEXT_RESOURCE_FILE);
        myGraphicResources = ResourceBundle.getBundle(DEFAULT_GRAPHIC_RESOURCE_FILE);
        myStage = stage;
        myStage.setTitle(myTextResources.getString("Title"));
    }

    /**
     * Method to be called once from main class to initiate UI.
     */
    public void create() {
        mySplashRoot = setupSplash();


        myStage.setScene(mySplashRoot.getScene());
    }

    /**
     * Begins animation to regularly call step method.
     */
    private void run() {
        myAnimation = new Timeline();
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
    }

    /**
     * Animation for the simulations to run every moment in time. Update state and appearances of cells and forward
     * states of cells to next generation.
     */
    private void step(double stepTime) {
        // adjust animation rate according to user input
        myAnimation.setRate(mySpeedSlider.getValue());

        // update generation count
        myGenerationCount += 1;
        if (myGenerationCount > MAX_GENERATION) {
            myAnimation.stop();
        }
        myGenerationsDisplay.setText(myTextResources.getString("GenerationText")+myGenerationCount);

        // update cells
        myRule.determineNextStates();
        myRule.updateGrid();
        myGridUI.updateAppearance();

        var stateMap = myRule.getStateMap();
        for (Integer key : stateMap.keySet()) {
            int currPop = myGrid.getStateCount(key);
            mySeriesArray[key].getData().add(new XYChart.Data(myGenerationCount, currPop));
        }

    }


    /**
     * Create all components for splash screen.
     * @return root node of splash scene
     */
    private Group setupSplash() {

        var root = new Group();
        var scene = new Scene(root, SPLASH_SIZE, SPLASH_SIZE);

        var layout = new VBox(LAYOUT_SPACING);
        layout.setPrefSize(SPLASH_SIZE,SPLASH_SIZE);
        layout.setAlignment(Pos.CENTER);

        var chooseFileButton = makeButton("ChooseFileText");
        var selectedFile = new Text(myTextResources.getString("SelectedText"));
        myFileText = new Text();
        splashToSimButton = makeButton("StartText");

        var selectGridType = new Text(myTextResources.getString("SelectGridType"));
        gridTypeButton = new ComboBox<>();
        gridTypeButton.getItems().addAll("Finite","Toroidal");
        gridTypeButton.setEditable(true);


        gridTypeButton.setOnAction(event -> {
            myGridType =  gridTypeButton.getSelectionModel().getSelectedItem();
        });


        var selectCellShape = new Text(myTextResources.getString("SelectCellShape"));
        cellShapeButton = new ComboBox<>();
        cellShapeButton.getItems().addAll("Square","Hexagon","Triangle");
        cellShapeButton.setEditable(true);

        cellShapeButton.setOnAction(event -> {
            myCellShape =  cellShapeButton.getSelectionModel().getSelectedItem();
        });



        layout.getChildren().addAll(chooseFileButton, selectedFile, myFileText, splashToSimButton, selectGridType,
                gridTypeButton,selectCellShape, cellShapeButton);
        root.getChildren().add(layout);

        var fileChooser = new FileChooser();
        chooseFileButton.setOnAction( event -> handleFile(fileChooser.showOpenDialog(myStage)) );



        return root;

    }

    /**
     * Initialize new button using resource bundle property files.
     * @param text: key of text to use in property file
     * @return Button displaying desired text value
     */
    private Button makeButton(String text) {
        return new Button(myTextResources.getString(text));
    }

    /**
     * To check file chosen from file chooser feature and update UI.
     * @param file chosen by user
     */
    private void handleFile(File file) {
        if (file != null) {
            myFile = file;
            String[] myFileNameArray = myFile.toString().split("/");
            String myFileName = myFileNameArray[myFileNameArray.length-1];
            myFileText.setText(myFileName);
            try {
                handleReader(new ReadXML(myFile));
            } catch (Exception e) {
                e.printStackTrace();
                myFileText.setText(myTextResources.getString("FileErrorText"));
            }
        } else {
            myFileText.setText(myTextResources.getString("FileErrorText"));
        }
    }

    /**
     * Create all components for simulation screen.
     * @param reader ReadXML object created with user selected file
     * @return root node of simulation screen
     */
    private Group setupSimulation(ReadXML reader) {
        // set up layout of scene
        var width = Double.parseDouble(myGraphicResources.getString("WidthOfSimulation"));
        var height = Double.parseDouble(myGraphicResources.getString("HeightOfSimulation"));

        var root = new Group();
        var scene = new Scene(root, width+PANEL_WIDTH, height+CHART_HEIGHT);
        var border = new BorderPane();

        // user panel
        var userPanel = new VBox(USER_PANEL_ITEM_SPACING);
        userPanel.setPrefSize(PANEL_WIDTH,height);
        userPanel.setAlignment(Pos.CENTER);

        // user controls
        myErrorDisplay = new Text();
        myGenerationsDisplay = new Text(myTextResources.getString("GenerationText")+myGenerationCount);
        simToSplashButton = makeButton("LoadText");
        var playButton = makeButton("PlayText");
        playButton.setOnAction( event -> handlePlay() );
        var pauseButton = makeButton("PauseText");
        pauseButton.setOnAction( event -> handlePause() );
        var stepButton = makeButton("StepText");
        stepButton.setOnAction( event -> handleStep() );
        var speedText = new Text(myTextResources.getString("SpeedText"));
        mySpeedSlider = new Slider();
        mySpeedSlider.setMin(MIN_RATE);
        mySpeedSlider.setMax(MAX_RATE);
        mySpeedSlider.setValue(RATE);

        // grid region
        var gridRegion = new Pane();
        gridRegion.setPrefSize(width, height);
        myGrid = new Grid(reader, myGridType, myCellShape);
        Class<?> clazz = null;
        Constructor<?> constructor = null;
        Object instance = null;
        try {
            clazz = Class.forName("UI.GridUI" + myCellShape);
            constructor = clazz.getConstructor(Grid.class, ResourceBundle.class);
            instance = constructor.newInstance(myGrid, myGraphicResources);
        } catch (ClassNotFoundException e) {
            // TODO: error handling
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        myGridUI = (GridUI) instance;
        var gridNodes = myGridUI.getMyNodes();
        myRule = makeRuleByReflection(myGrid, reader.getName(), reader.getExtraParameters());

        // chart region
        var chartRegion = new HBox();
        chartRegion.setPrefSize(width, CHART_HEIGHT);
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Generation");
        yAxis.setLabel("Population");
        myLineChart = new LineChart<>(xAxis,yAxis);
        myLineChart.setTitle("Cell Population by State Over Time");
        myLineChart.setCreateSymbols(false);
        myLineChart.setLegendVisible(true);
        var stateMap = myRule.getStateMap();
        mySeriesArray = new XYChart.Series[stateMap.size()];
        for (int ind = 0; ind<stateMap.size(); ind++) {
            mySeriesArray[ind] = new XYChart.Series<>();
        }
        for (Integer key : stateMap.keySet()) {
            int currPop = myGrid.getStateCount(key);
            mySeriesArray[key].getData().add(new XYChart.Data(myGenerationCount, currPop));
            mySeriesArray[key].setName(stateMap.get(key));
        }
        myLineChart.getData().addAll(mySeriesArray);

        // add elements to each region
        userPanel.getChildren().addAll(myErrorDisplay, myGenerationsDisplay, simToSplashButton, playButton,
                pauseButton, stepButton, speedText, mySpeedSlider);
        gridRegion.getChildren().addAll(gridNodes);
        chartRegion.getChildren().add(myLineChart);

        // set layout regions
        border.setRight(userPanel);
        border.setBottom(chartRegion);
        border.setLeft(gridRegion);

        // add layout to root
        root.getChildren().add(border);

        return root;

    }

    /**
     * Instantiates a simulation Rule subclass according to simulation type read in from XML file.
     * For example, if simulationType is "GameOfLife", this method will return an instance of the GameOfLifeRule class.
     * @param grid simulation grid to pass to Rule constructor
     * @param simulationType type read in from XML file
     * @param simulationParameters extra parameters read in from XML file
     * @return instantiated Rule instance of desired subclass
     */
    private Rule makeRuleByReflection(Grid grid, String simulationType, List<Double> simulationParameters) {
        try {
            var packageName = "rule.";
            var className = "Rule";
            Class<?> clazz = Class.forName(packageName + simulationType + className);
            Constructor<?> constructor = clazz.getConstructor(Grid.class, List.class);
            Object instance = constructor.newInstance(grid, simulationParameters);
            return (Rule) instance;
        } catch (Exception e) {
            myErrorDisplay.setText(myTextResources.getString("InterpretErrorText"));
            return null;
        }
    }

    /**
     * Defines transitions between animation nodes after both have been created.
     * @param stage animation stage
     * @param splash splash root node
     * @param simulation simulation root node
     */
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
            myGenerationCount = 0;
        } );

    }

    /**
     * Creates simulation root node using ReadXML created by user's input.
     * @param reader ReadXML created with user's choice of XML file
     */
    private void handleReader(ReadXML reader) {
        var simulationRoot = setupSimulation(reader);
        setupSceneTransitions(myStage, mySplashRoot, simulationRoot);
    }

    /**
     * To be called when user pauses simulation.
     */
    private void handlePause() {
        // pause animation
        myAnimation.pause();
    }

    /**
     * To be called when user starts simulation.
     */
    private void handlePlay() {
        // resume animation
        myAnimation.play();
    }

    /**
     * To be called when user steps through simulation.
     */
    private void handleStep() {
        // step through animation
        myAnimation.pause();
        step(SECOND_DELAY);
    }

}
