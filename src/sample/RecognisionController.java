package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import javax.swing.*;

public class RecognisionController {

    public int[] BloodCell = new int[16384];
    private PixelReader pixelReader;

    @FXML
    public void AnalyiseImg(ActionEvent e) {
        for (int readY = 0; readY < Controller.processedImg.getHeight(); readY++) {
            for (int readX = 0; readX < Controller.processedImg.getWidth(); readX++) {
                Color color = pixelReader.getColor(readX,readY);

                double Red = color.getRed();
                pixelReader.getPixelFormat();

                if(Red == 1.0) {
                    //BloodCell[1] = Red;
                }

            }
        }
    }
}
