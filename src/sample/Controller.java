package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.scene.image.*;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Controller {

    @FXML public ImageView ImageViewMain;
    @FXML public MenuItem OpenFile;
    @FXML public MenuItem Quit;
    @FXML public Button Btn;
    @FXML public Button btn2;


    public String filepath = "";
    public static Image inputImage;
    public static Image processedImg = null;
    public PixelReader pixelReader;

    //Open Image Method
    @FXML
    public void OpenPicture(ActionEvent e) {
        FileChooser fileChooser = new FileChooser(); //Open image method
        fileChooser.setTitle("Open Image File");
        filepath = fileChooser.showOpenDialog(Main.mainWindow).getAbsolutePath();

        inputImage = new Image("file:///" + filepath, 512,512,false,false); //file:/// is needed to pass the bug where absolute path will not work.
        ImageViewMain.setImage(inputImage);

        pixelReader = inputImage.getPixelReader();
    }

    //Change Scene to Tricolour
    @FXML
    public void triColour(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("TricolourTab.fxml"));
            Main.mainWindow.setScene(new Scene(root));
        } catch (IOException el) {
            el.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        ImageViewMain.setImage(inputImage);
    }

    //Exit Program Method
    @FXML
    public void ExitProgram(ActionEvent e) {
        Runtime.getRuntime().exit(0);
    }



}