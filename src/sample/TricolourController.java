package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TricolourController {

    @FXML public ImageView ImageViewTri;
    @FXML public MenuItem OpenFile;
    @FXML public MenuItem Quit;
    @FXML public MenuItem MainWindow;
    @FXML public Slider SaturationSlider;
    @FXML public Slider BrightnessSlider;
    @FXML public Slider HueSlider;
    @FXML public Slider ContrastSlider;
    @FXML public Slider RedSlider;
    @FXML public Slider GreenSlider;
    @FXML public Slider BlueSlider;
    @FXML public Button TricolourProcess;
    @FXML public Button DetectCellsBtn;
    //@FXML public Button SaveFile;


    private Image OriginalImage = Controller.inputImage; //Map inputted image to OriginalImage
    private PixelReader pixelReader;

    @FXML
    public void MainWindowScene(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Main.mainWindow.setScene(new Scene(root));
        } catch (IOException el) {
            el.printStackTrace();
        }
    }

    @FXML
    public void DetectionTab(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("RecognisionTab.fxml"));
            Main.mainWindow.setScene(new Scene(root));
        } catch (IOException el) {
            el.printStackTrace();
        }
    }


    @FXML
    public void initialize() {
        if(Controller.processedImg == null) {
            ImageViewTri.setImage(OriginalImage);
        }
        else ImageViewTri.setImage(Controller.processedImg);

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

                int finalReadX = readX;
                int finalReadY = readY;
                RedSlider.valueProperty().addListener(((observableValue, number, t1) -> wImage.getPixelWriter().setColor(finalReadX, finalReadY, Color.color(RedSlider.getValue() / 255, Blue, Green))));
                BlueSlider.valueProperty().addListener(((observableValue, number, t1) -> wImage.getPixelWriter().setColor(finalReadX, finalReadY, Color.color(Red, BlueSlider.getValue() / 255, Green))));
                GreenSlider.valueProperty().addListener(((observableValue, number, t1) -> wImage.getPixelWriter().setColor(finalReadX, finalReadY, Color.color(Red, Blue, GreenSlider.getValue() / 255))));
                //wImage.getPixelWriter().setColor(readX, readY, Color.color(RedSlider.getValue() / 255, BlueSlider.getValue() / 255, GreenSlider.getValue() / 255));
            }
        }
        ImageViewTri.setImage(wImage);
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

                if(Red > 0.560 && Red < 0.85) {
                    wImage.getPixelWriter().setColor(readX, readY, Color.color(1.0, 0, 0));
                }
                else if(Red > 0.27 && Red < 0.5 && Green > 0.04 && Green < 0.22 && Blue > 0.5 && Blue < 0.70 ) {
                    wImage.getPixelWriter().setColor(readX, readY, Color.color(0.27, 0.133, 0.384));
                }
                else wImage.getPixelWriter().setColor(readX,readY, Color.color(1.0,1.0,1.0));
            }
        }
        ImageViewTri.setImage(wImage);
        Controller.processedImg = wImage;
    }
/*
    @FXML
    public static void saveImage(Image image) {
        File outputFile = new File("C:/AdjustedImg.png");
        BufferedImage bImage = SwingFXUtils.fromFXImage();
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void saveImageAsFile(ActionEvent e) {
        saveImage(ImageViewTri.getImage());
    }
*/

}
