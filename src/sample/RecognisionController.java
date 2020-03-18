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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.io.IOException;

public class RecognisionController {

    private PixelReader pixelReader;
    private Image OriginalImage = Controller.inputImage;


    @FXML Button BloodCellBtn;
    @FXML ImageView ImageViewTri;
    @FXML MenuItem Quit;
    @FXML MenuItem MainWindow;

    int width = (int) Controller.processedImg.getWidth();
    int height = (int) Controller.processedImg.getHeight();


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

        for (int readY = 0; readY < height; readY++) {
            for (int readX = 0; readX < width - 1; readX++) {
                if (TricolourController.BloodCells[readY * width + readX] != 0 && (TricolourController.BloodCells[readY * width + readX + 1] != 0)) {
                    Recognision.union(TricolourController.BloodCells, readY * width + readX, readY * width + readX + 1);
                }
                if (readY < height - 1 && TricolourController.BloodCells[readY * width + readX] != 0 && TricolourController.BloodCells[readY * width + readX + width] != 0) {
                    Recognision.union(TricolourController.BloodCells, readY * width + readX, readY * width+readX+width);
                }
            }
        }

        for (int i = 0; i < TricolourController.BloodCells.length; i++) {
            if (i % width == 0) System.out.println(); //New line
            System.out.print(Recognision.find(TricolourController.BloodCells, i) + " "); //Print root value
        }

        int id = 1543;
        Rectangle r = null;

        for (int i = 0; i < TricolourController.BloodCells.length; i++) {
            if (TricolourController.BloodCells[i] != 0 && Recognision.find(TricolourController.BloodCells, i) == 1543) {
                {
                    int x = i % width, y = i / width;
                    if (r == null) r = new Rectangle(x, y, 1, 1);
                    else {
                        if (x > r.getX() + r.getWidth()) r.setWidth(x - r.getX());
                        if (x < r.getX()) {
                            r.setWidth(r.getX() + r.getWidth() - x);
                            r.setX(x);
                        }
                        if (y > r.getY() + r.getHeight()) r.setHeight(y - r.getY());
                    }
                }
            }
        }
        if (r != null) {
            r.setFill(Color.TRANSPARENT);
            r.setStroke(Color.BLUE);
            r.setTranslateX(ImageViewTri.getLayoutX());
            r.setTranslateY(ImageViewTri.getLayoutY());
            ((Pane) ImageViewTri.getParent()).getChildren().add(r);
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
