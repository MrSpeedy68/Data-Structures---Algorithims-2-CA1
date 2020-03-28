package sample;

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
import java.io.IOException;

/**
 * TricolourController is the main controller used for the FXML scene for the conversion of the image to a Red White and Purple image
 * detecting the blood cells colour value and setting it full red and detecting the white blood cells and setting them to a purple colour
 * and setting the background to white while at the same time adding to the BloodCells array the values of the Red Blood cells.
 * There is also custom hue, brightness, saturation, contrast, red, blue and green sliders for fine tuning the image.
 */
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

    /**
     * Image of the original inputted image from the main Controller class
     */
    private Image OriginalImage = Controller.inputImage; //Map inputted image to OriginalImage
    /**
     * Pixel Reader initialisation
     */
    private PixelReader pixelReader;
    /**
     * ConvertedImage will be set as the image that gets converted to the Red Purple and White for the recognition of blood cells
     */
    public static Image ConvertedImage = null; //Image to be used in recognision
    /**
     * int array of the blood cells found in the image
     */
    public static int[] BloodCells;

    /**
     * MainWindowScene is used to open the main window of the program where you load the image.
     * @param e MenuItem MainWindow click
     */
    @FXML
    public void MainWindowScene(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Main.mainWindow.setScene(new Scene(root));
        } catch (IOException el) {
            el.printStackTrace();
        }
    }

    /**
     * DetectionTab is used to open the Recognition tab FXML file
     * @param e DetectCellsBtn click
     */
    @FXML
    public void DetectionTab(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("RecognisionTab.fxml"));
            Main.mainWindow.setScene(new Scene(root));
        } catch (IOException el) {
            el.printStackTrace();
        }
    }

    /**
     * initialize method is used to set the Imageview to the appropriate image if the image hasn't been processed yet then set it to the original
     * image if it has been processed then leave it as the tri colour image
     */
    @FXML
    public void initialize() {
        if(Controller.processedImg == null) {
            ImageViewTri.setImage(OriginalImage);
        }
        else ImageViewTri.setImage(Controller.processedImg);

        pixelReader = OriginalImage.getPixelReader();
    }

    /**
     * colorAdjust method uses sliders and their values to change the Brightness, Saturation, Hue and contrast of the image
     */
    @FXML
    public void colorAdjust() {
        ColorAdjust colorAdjust = new ColorAdjust();

        BrightnessSlider.valueProperty().addListener((observableValue, number, t1) -> colorAdjust.setBrightness(BrightnessSlider.getValue() / 100));
        SaturationSlider.valueProperty().addListener((observableValue, number, t1) -> colorAdjust.setSaturation(SaturationSlider.getValue() / 100));
        HueSlider.valueProperty().addListener((observableValue, number, t1) -> colorAdjust.setHue(HueSlider.getValue() / 100));
        ContrastSlider.valueProperty().addListener((observableValue, number, t1) -> colorAdjust.setContrast(ContrastSlider.getValue() / 100));

        ImageViewTri.setEffect(colorAdjust);
    }

    /**
     * Just like colorAdjust RGBAdjust changes the RGB values of the image by using pixel reader and and getting the current rgb values and
     * changing them all with the slider.
     */
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

    /**
     * ExitProgram is to close the program
     * @param e Quit MenuItem click
     */
    @FXML
    public void ExitProgram(ActionEvent e) {
        Runtime.getRuntime().exit(0);
    }

    /**
     * triColourProcessing method handles the detection of red and white blood cells by reading through every pixel and comparing its RGB
     * values. Through experimentation I found these values of red and purple work best for detecting each blood cell type. once a blood
     * cell has a red value in the given ranges its value is turned to full red and 0 blue and green while on the same time being added
     * as a value to the BloodCells array.
     * At the end the image is saved and set as the current Image view.
     * @param e Button TricolourProcess click
     */
    @FXML
    public void triColourProcessing(ActionEvent e) {
        WritableImage wImage = new WritableImage(
                (int) OriginalImage.getWidth(),
                (int) OriginalImage.getHeight());
        int width = (int) OriginalImage.getWidth();
        int height = (int) OriginalImage.getHeight();
        PixelWriter pixelWriter = wImage.getPixelWriter();
        BloodCells = new int[width * height];
        int i =0;

        for(int readY = 0; readY < OriginalImage.getHeight(); readY++) {
            for(int readX = 0; readX < OriginalImage.getWidth(); readX++) {
                Color color = pixelReader.getColor(readX,readY);
                double Red = color.getRed();
                double Green = color.getGreen();
                double Blue = color.getBlue();

                if(Red > 0.560 && Red < 0.85) { //Hard coded RGB values from experimenting
                    wImage.getPixelWriter().setColor(readX, readY, Color.color(1, 0, 0));
                    BloodCells[i] = i;
                }
                else if(Red > 0.27 && Red < 0.5 && Green > 0.04 && Green < 0.22 && Blue > 0.5 && Blue < 0.70 ) {
                    wImage.getPixelWriter().setColor(readX, readY, Color.color(0.27, 0.133, 0.384));
                    BloodCells[i] =i;                }
                else {
                    wImage.getPixelWriter().setColor(readX,readY, Color.color(1,1,1));
                    BloodCells[i] = 0;
                }
                //System.out.print(BloodCells[i]);
                i++;
            }
            //System.out.println();
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
