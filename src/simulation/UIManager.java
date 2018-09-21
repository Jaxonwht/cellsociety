package simulation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class UIManager {
    private final static int SPLASH_SIZE = 300;
    private final static int PANEL_WIDTH = 175;
    private final static String LOAD_NEW_TEXT = "Load new simulation";
    private final static String PAUSE_TEXT = "Pause";
    private final static String RESUME_TEXT = "Resume";
    private final static String STEP_TEXT = "Step";
    private Stage myStage;
    private File myFile;
    private ReadXML myReader;
    private Text myFileString;
    private FileChooser myFileChooser;
    private Group mySplashRoot;
    private Group mySimRoot;
    private Button splashToSimButton;
    private Button simToSplashButton;
    private Boolean fileRead = false;


    public UIManager(Stage stage) {
        myStage = stage;
    }

    public void initialize() {

        mySplashRoot = setupSplash(myStage);
        myStage.setScene(mySplashRoot.getScene());

    }

    private Group setupSplash(Stage stage) {

        Group root = new Group();
        Scene scene = new Scene(root, SPLASH_SIZE, SPLASH_SIZE);
        VBox layout = new VBox();
        layout.setPrefSize(SPLASH_SIZE,SPLASH_SIZE);

        myFileChooser = new FileChooser();
        myFileChooser.setTitle("Choose XML file");

        Button chooseFileButton = new Button("Choose XML file");
        Text selectedFile = new Text("Selected file:");
        myFileString = new Text();
        splashToSimButton = new Button("Start simulation");

        // add elements to layout node
        layout.getChildren().addAll(chooseFileButton, selectedFile, myFileString, splashToSimButton);
        layout.setAlignment(Pos.CENTER);

        // add layout node to root node
        root.getChildren().add(layout);

        // activate choose file button
        chooseFileButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        handleFile(myFileChooser.showOpenDialog(stage));
                    }
                });


        return root;
    }

    private Group setupSimulation(Stage stage, int width, int height, String title) {
        // set up layout of scene
        Group root = new Group();
        Scene scene = new Scene(root, width+PANEL_WIDTH, height);
        BorderPane border = new BorderPane();

        // user panel
        VBox userPanel = new VBox(20);
        userPanel.setPrefSize(PANEL_WIDTH,height);
        userPanel.setStyle("-fx-background-color: #336699");
        userPanel.setAlignment(Pos.CENTER);
        simToSplashButton = new Button(LOAD_NEW_TEXT);
        Button pauseButton = new Button(PAUSE_TEXT);
        Button resumeButton = new Button(RESUME_TEXT);
        Button stepButton = new Button(STEP_TEXT);


        // grid region
        GridPane gridRegion = new GridPane();
        gridRegion.setPrefSize(width, height);
        Button gridButton = new Button(title);

        // add elements to each region
        userPanel.getChildren().addAll(simToSplashButton, pauseButton, resumeButton, stepButton);
        gridRegion.getChildren().add(gridButton);

        // set layout regions
        border.setRight(userPanel);
        border.setLeft(gridRegion);

        // add layout to root
        root.getChildren().add(border);

        return root;

    }

    private void handleFile(File file) {
        if (file != null) {

            System.out.println("Got file and it's not null");

            myFile = file;
            String[] myFileNameArray = myFile.toString().split("/");
            String myFileName = myFileNameArray[myFileNameArray.length-1];
            myFileString.setText(myFileName);

            try {
                myReader = new ReadXML(myFile);
                System.out.println("Created reader");
                fileRead = true;
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

        }
    }

    private void setupSceneTransitions(Stage stage, Group splash, Group simulation) {

        splashToSimButton.setOnAction( event -> { if(myFile !=null) { stage.setScene(simulation.getScene()); }});
        simToSplashButton.setOnAction( event -> stage.setScene(splash.getScene()) );

    }

    private void handleReader(ReadXML reader) {

        System.out.println("handleReader called");
        mySimRoot = setupSimulation(myStage, reader.getHeight(), reader.getWidth(), reader.getName());
        setupSceneTransitions(myStage, mySplashRoot, mySimRoot);

    }

    public File getFile() { return myFile; }

    public ReadXML getReader() { return myReader; }

}
