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

    public UIManager() { }

    public void initialize(Stage stage) {

        myStage = stage;

        mySplashRoot = setupSplash(stage);

        stage.setScene(mySplashRoot.getScene());

    }

    public Group setupSplash(Stage stage) {
        int width = 300;
        int height = 300;
        Group root = new Group();
        Scene scene = new Scene(root, width, height);
        VBox layout = new VBox(20);
        layout.setPrefSize(width,height);

        myFileChooser = new FileChooser();
        myFileChooser.setTitle("Choose XML file");

        Button chooseButton = new Button("Choose XML file");
        Text selectedFile = new Text("Selected file:");
        myFileString = new Text();
        splashToSimButton = new Button("Start simulation");

        layout.getChildren().add(chooseButton);
        layout.getChildren().add(selectedFile);
        layout.getChildren().add(myFileString);
        layout.getChildren().add(splashToSimButton);
        layout.setAlignment(Pos.CENTER);
        root.getChildren().add(layout);

        chooseButton.setOnAction(
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

    public Group setupSimulation(Stage stage) {
        int width = 600;
        int height = 400;

        int panelWidth = 175;

        Group root = new Group();
        Scene scene = new Scene(root, width, height);
        BorderPane border = new BorderPane();

        // user panel
        VBox userPanel = new VBox();
        userPanel.setPrefSize(panelWidth,height);
        userPanel.setStyle("-fx-background-color: #336699");
        userPanel.setAlignment(Pos.CENTER);
        simToSplashButton = new Button("Load new simulation");

        // grid region
        GridPane gridRegion = new GridPane();
        gridRegion.setPrefSize(width-panelWidth, height);
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

    public void setupSceneTransitions(Stage stage, Group splash, Group simulation) {

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

    public void handleReader(ReadXML reader) {

        System.out.println("handleReader called");
        mySimRoot = setupSimulation(myStage);

        setupSceneTransitions(myStage, mySplashRoot, mySimRoot);

    }

    public File getFile() { return myFile; }

    public ReadXML getReader() { return myReader; }

}
