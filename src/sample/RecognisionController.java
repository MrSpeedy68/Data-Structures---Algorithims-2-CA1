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
        Image img = ImageViewTri.getImage();

        int width = (int) img.getWidth();
        int height = (int) img.getHeight();

        BloodCells = new int[width * height];

        for (int readX = 0; readX < width; readX++) {
            for (int readY = 0; readY < height; readY++) {
                int currentPixel = (int) ((readY * width) + readX);
                Color color = pixelReader.getColor(readY,readX);
                double Red = color.getRed();
                int i = 0;
                //if (color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1) {
                if(Red > 0.560 && Red < 0.85) {
                        BloodCells[i] = 1;
                    }
                     else  {
                        BloodCells[i] = -1;
                    }
                    System.out.print(BloodCells[i]);

                if(currentPixel < (height * width - 1) && BloodCells[i] != -1 && BloodCells[i + 1] != -1) {
                    union(BloodCells,currentPixel,currentPixel + 1); //change one to root
                }
                if(BloodCells[i] != -1 && (currentPixel+width) < (height * width-1) && BloodCells[currentPixel + width] != -1) {
                    union(BloodCells, currentPixel, currentPixel + width); //check above for root
                }
                i++;

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

    //Recursive version of union find
    public static int find(int[] a, int id) {
        if(a[id] == id) return id;
        else return a[id]= find(a,a[id]);
        //else return find(a,a[id]);
    }

    //Quick union of disjoint sets containing elements p and q
    public static void union(int[] a, int p, int q) {
        a[find(a,q)]=find(a,p);
    }


}
