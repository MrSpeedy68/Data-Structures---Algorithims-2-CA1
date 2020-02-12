package sample;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class TricolourController {

    @FXML public ImageView ImageViewTri;
    @FXML public MenuItem OpenFile;
    @FXML public MenuItem Quit;
    @FXML public Slider SaturationSlider;
    @FXML public Slider BrightnessSlider;
    @FXML public Slider HueSlider;
    @FXML public Slider ContrastSlider;


    @FXML
    public void initialize() {
        ImageViewTri.setImage(Controller.inputImage); //Initialise the opened image in new tab
    }

    @FXML
    public void colorAdjust() {

    }


}
