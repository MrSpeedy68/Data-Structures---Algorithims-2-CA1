package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class TricolourController {

    @FXML public ImageView ImageViewTri;
    @FXML public MenuItem OpenFile;
    @FXML public MenuItem Quit;
    @FXML public Slider SaturationSlider;
    @FXML public Slider BrightnessSlider;
    @FXML public Slider HueSlider;
    @FXML public Slider ContrastSlider;
    @FXML public Slider RedSlider;
    @FXML public Button RedBtn;

    public Image OriginalImage = Controller.inputImage; //Map inputted image to OriginalImage
    public PixelReader pixelReader;


    @FXML
    public void initialize() {
        ImageViewTri.setImage(Controller.inputImage); //Initialise the opened image in new tab
        pixelReader = OriginalImage.getPixelReader();
    }

    @FXML
    public void colorAdjust() {
        ColorAdjust colorAdjust = new ColorAdjust();

        BrightnessSlider.valueProperty().addListener((observableValue, number, t1) -> colorAdjust.setBrightness(BrightnessSlider.getValue() / 100));
        SaturationSlider.valueProperty().addListener((observableValue, number, t1) -> colorAdjust.setSaturation(SaturationSlider.getValue() / 100));
        HueSlider.valueProperty().addListener((observableValue, number, t1) -> colorAdjust.setHue(HueSlider.getValue() / 100));
        ContrastSlider.valueProperty().addListener((observableValue, number, t1) -> colorAdjust.setContrast(ContrastSlider.getValue() / 100));

        ImageViewTri.setEffect(colorAdjust);
    }

    @FXML
    public void RGBAdjust() {

    }

    @FXML
    public void ExitProgram(ActionEvent e) {
        Runtime.getRuntime().exit(0);
    }

    @FXML
    public void triColourProcessing(ActionEvent e) {
        WritableImage wImage = new WritableImage(
                (int) OriginalImage.getWidth(),
                (int) OriginalImage.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();

        for(int readY = 0; readY < OriginalImage.getHeight(); readY++) {
            for(int readX = 0; readX < OriginalImage.getWidth(); readX++) {
                Color color = pixelReader.getColor(readX,readY);
                double Red = color.getRed();
                double Green = color.getGreen();
                double Blue = color.getBlue();

                wImage.getPixelWriter().setColor(readX, readY, Color.color(Red, 0, 0));
            }
        }
        ImageViewTri.setImage(wImage);
    }


}
