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

/**
 * Controller method used for main FXML window to load image file into the image view
 */
public class Controller {

    /**
     * ImageViewmain is the main ImageView that the file will be loaded into
     */
    @FXML public ImageView ImageViewMain;
    /**
     * Menu Item used to run the OpenPicture method
     */
    @FXML public MenuItem OpenFile;
    /**
     * Menu Item used to Exit the program
     */
    @FXML public MenuItem Quit;
    /**
     * Button used to change scene to the tri-colour scene
     */
    @FXML public Button Btn;

    /**
     * Initialisation for filepath name
     */
    public String filepath = "";
    /**
     * Static Image for inputted file of the image
     */
    public static Image inputImage;
    /**
     * Static Image of the processed image from the tricolour method which will then replace the null value
     */
    public static Image processedImg = null;
    /**
     * PixelReader initialisation
     */
    public PixelReader pixelReader;

    /**
     *  OpenPicture method is used to open the file chooser so the user can select an image to be used for processing
     *  The program will then take the file path and set the String and place the selected file into the image View of
     *  the ImageViewMain and set its resolution to 512 x 512 pixels.
     * @param e Button click MenuItem OpenFile
     */
    @FXML
    public void OpenPicture(ActionEvent e) {
        FileChooser fileChooser = new FileChooser(); //Open image method
        fileChooser.setTitle("Open Image File");
        filepath = fileChooser.showOpenDialog(Main.mainWindow).getAbsolutePath();

        inputImage = new Image("file:///" + filepath,512,512,false,false); //file:/// is needed to pass the bug where absolute path will not work.
        ImageViewMain.setImage(inputImage);

        pixelReader = inputImage.getPixelReader();
    }

    /**
     * triColour method is used to change FXML scenes to the Tricolour method
     * @param e Button click of Btn
     */
    @FXML
    public void triColour(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("TricolourTab.fxml"));
            Main.mainWindow.setScene(new Scene(root));
        } catch (IOException el) {
            el.printStackTrace();
        }
    }

    /**
     * initialize method used to set the inputted image if you decide to go back to the main tab
     */
    @FXML
    public void initialize() {
        ImageViewMain.setImage(inputImage);
    }

    /**
     * ExitProgram method used to stop the program and exit.
     * @param e MenuItem press Quit
     */
    @FXML
    public void ExitProgram(ActionEvent e) {
        Runtime.getRuntime().exit(0);
    }



}