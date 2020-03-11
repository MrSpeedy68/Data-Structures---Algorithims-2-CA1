package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.io.IOException;

public class RecognisionController {

    public int[] BloodCell = new int[16384];
    private PixelReader pixelReader;

    @FXML
    Button BloodCellBtn;
    @FXML
    ImageView ImageViewTri;
    @FXML
    MenuItem Quit;
    @FXML
    MenuItem MainWindow;




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
