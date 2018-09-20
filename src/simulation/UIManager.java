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
    private final static int PANEL_WIDTH = 125;
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
                        File file = myFileChooser.showOpenDialog(stage);
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
                });


        return root;
    }

    private Group setupSimulation(Stage stage) {
        // should get these values from xml file
        int width = 600;
        int height = 400;

        // set up layout of scene
        Group root = new Group();
        Scene scene = new Scene(root, width, height);
        BorderPane border = new BorderPane();

        // user panel
        VBox userPanel = new VBox();
        userPanel.setPrefSize(PANEL_WIDTH,height);
        userPanel.setStyle("-fx-background-color: #336699");
        userPanel.setAlignment(Pos.CENTER);
        simToSplashButton = new Button("Load new simulation");

        // grid region
        GridPane gridRegion = new GridPane();
        gridRegion.setPrefSize(width-PANEL_WIDTH, height);
        Button gridButton = new Button("I'm the grid");

        // add elements to each region
        userPanel.getChildren().add(simToSplashButton);
        gridRegion.getChildren().add(gridButton);

        // set layout regions
        border.setRight(userPanel);
        border.setLeft(gridRegion);

        // add layout to root
        root.getChildren().add(border);

        return root;

    }

    private void setupSceneTransitions(Stage stage, Group splash, Group simulation) {

        splashToSimButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        if (myFile != null) {
                            stage.setScene(simulation.getScene());
                        }
                    }
                });

        simToSplashButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        stage.setScene(splash.getScene());
                    }
                }
        );



    }

    private void handleReader(ReadXML reader) {

        System.out.println("handleReader called");
        mySimRoot = setupSimulation(myStage);
        setupSceneTransitions(myStage, mySplashRoot, mySimRoot);

    }

    public File getFile() { return myFile; }

    public ReadXML getReader() { return myReader; }

}
