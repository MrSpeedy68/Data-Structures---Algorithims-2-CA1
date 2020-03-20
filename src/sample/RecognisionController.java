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
import java.util.Arrays;
import java.util.stream.IntStream;

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
                    Recognision.union(TricolourController.BloodCells, readY * width + readX, readY * width + readX + width);
                }
            }
        }

        for (int i = 0; i < TricolourController.BloodCells.length; i++) {
            if (i % width == 0) System.out.println(); //New line
            System.out.print(Recognision.find(TricolourController.BloodCells, i) + " "); //Print root value
        }

        displayRectangles();
    }


    Rectangle[] r = new Rectangle[1000];

    public void displayRectangles() {
            int id = 24604;
            int id2 =77872;

            int arr[] = IntStream.of(getArrayRoots()).distinct().toArray(); //takes only the unique values from the array
            int rIndex = 0;


            for(int j=0; j < arr.length - 1; j++) {
                for (int i = 0; i < TricolourController.BloodCells.length; i++) {
                    if (TricolourController.BloodCells[i] == arr[j]) {
                        //if (TricolourController.BloodCells[i] != 0 && Recognision.find(TricolourController.BloodCells, i) == id2 ) {
                        int x = i % width, y = i / width;
                        if (r[rIndex] == null) r[rIndex] = new Rectangle(x, y, 1, 1);
                        else {
                            if (x > r[rIndex].getX() + r[rIndex].getWidth()) r[rIndex].setWidth(x - r[rIndex].getX());
                            if (x < r[rIndex].getX()) {
                                r[rIndex].setWidth(r[rIndex].getX() + r[rIndex].getWidth() - x);
                                r[rIndex].setX(x);
                            }
                            if (y > r[rIndex].getY() + r[rIndex].getHeight()) r[rIndex].setHeight(y - r[rIndex].getY());
                        }
                    }
                }
                if (r[rIndex] != null) {
                    r[rIndex].setFill(Color.TRANSPARENT);
                    r[rIndex].setStroke(Color.BLUE);
                    r[rIndex].setTranslateX(ImageViewTri.getLayoutX());
                    r[rIndex].setTranslateY(ImageViewTri.getLayoutY());
                    ((Pane) ImageViewTri.getParent()).getChildren().add(r[rIndex++]);
                }
            }
        }

//Gets the roots of the array and adds them to a new array if the roots are greater than 50 in size
        public int[] getArrayRoots() {
        int arr[] = new int[width*height];
        int temp =0;
        for(int i = 0; i< TricolourController.BloodCells.length; i++) {
            if(TricolourController.BloodCells[i] > 50) {
                arr[temp] = TricolourController.BloodCells[i];
                temp++;
            }
        }
        return arr;
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
