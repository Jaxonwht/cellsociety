package simulation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class UIManager {
    private File myFile;

    public UIManager() { }

    public Group setUpSplash(Stage stage) {
        Group root = new Group();
        VBox layout = new VBox(20);
        Scene scene = new Scene(root, 300, 300);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose XML file");

        Button chooseButton = new Button("Choose XML file");
        Text selectedFile = new Text("Selected file:");
        Text fileNameText = new Text();
        Button startButton = new Button("Start simulation");

        layout.getChildren().add(chooseButton);
        layout.getChildren().add(selectedFile);
        layout.getChildren().add(fileNameText);
        layout.getChildren().add(startButton);
        layout.setAlignment(Pos.CENTER);
        root.getChildren().add(layout);

        chooseButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            myFile = file;
                            String[] myFileNameArray = myFile.toString().split("/");
                            String myFileName = myFileNameArray[myFileNameArray.length-1];
                            fileNameText.setText(myFileName);
                        }
                    }
                });

        startButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        // stuff
                    }
                });


        return root;
    }

    public File getFile() { return myFile; }

}
