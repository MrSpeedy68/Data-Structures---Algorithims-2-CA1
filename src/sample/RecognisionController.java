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
        int i =0;
        for (int readY = 0; readY < width; readY++) {
            for (int readX = 0; readX < height-1; readX++) {
                if (TricolourController.BloodCells[readY*width+readX] != 0 && (TricolourController.BloodCells[readY*width+readX+1] != 0)) {
                    Recognision.union(TricolourController.BloodCells,readY*width+readX,readY*width+readX+1);
                }
                if (TricolourController.BloodCells[readY*width+readX] != 0 && TricolourController.BloodCells[width+readX] != 0){
                    Recognision.union(TricolourController.BloodCells,readY*width+readX,width+readX);

                    i++;
                }
            }
        }
        System.out.println("Union Set---------------------------------------------------------------");
        for(int id=0;id<TricolourController.BloodCells.length;id++)
            System.out.println("The root of element "+id+" is "+Recognision.find(TricolourController.BloodCells,id)+" (element value: "+TricolourController.BloodCells[id]+")");
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

/*
    //Recursive version of union find
    public static int findE(int[] a, int id) {
        if(a[id] == id) return id;
        else return a[id]= findE(a,a[id]);
        //else return find(a,a[id]);
    }

    public static int find(int[] a, int id) {
        if (a[id] == -1) return -1;
        while(a[id] != id) {
            a[id] = a[a[id]];
            id=a[id];
        }
        return id;
    }

    //Quick union of disjoint sets containing elements p and q
    public static void union(int[] a, int p, int q) {
        a[find(a,q)]=find(a,p);
    }
*/




}
