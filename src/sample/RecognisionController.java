package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.io.IOException;

public class RecognisionController {

    public int[] BloodCells;
    private PixelReader pixelReader;
    private Image OriginalImage = Controller.inputImage;

    @FXML Button BloodCellBtn;
    @FXML ImageView ImageViewTri;
    @FXML MenuItem Quit;
    @FXML MenuItem MainWindow;


    @FXML
    public void initialize() {
        if(Controller.processedImg == null) {
            ImageViewTri.setImage(OriginalImage);
        }
        else ImageViewTri.setImage(Controller.processedImg);

        pixelReader = OriginalImage.getPixelReader();
    }



    @FXML
    public void AnalyiseImg(ActionEvent e) {
        int width = (int) Controller.processedImg.getWidth();
        int height = (int) Controller.processedImg.getHeight();

        BloodCells = new int[width * height];

        for (int readX = 0; readX < Controller.processedImg.getWidth(); readX++) {
            for (int readY = 0; readY < Controller.processedImg.getHeight(); readY++) {
                Color color = pixelReader.getColor(readY,readX);
                int i = 0;
                i++;
                    if(color.getRed() == 1.0) {
                        BloodCells[i] = 1;
                    }
                    else if(color.getRed() < 1.0){
                        BloodCells[i] = 0;
                    }
                    System.out.print(BloodCells[i]);
            }
            System.out.println();
        }
    }

    //Exit Program Method
    @FXML
    public void ExitProgram(ActionEvent e) {
        Runtime.getRuntime().exit(0);
    }

    @FXML
    public void MainWindowScene(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Main.mainWindow.setScene(new Scene(root));
        } catch (IOException el) {
            el.printStackTrace();
        }
    }


}
