package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * RecognitionController is the main class to handle the processed image and aquire the roots of each blood cell and then place rectangles around each blood cell and number them accordingly
 */
public class RecognisionController {

    private PixelReader pixelReader;
    private Image OriginalImage = Controller.inputImage;


    @FXML Button BloodCellBtn;
    @FXML ImageView ImageViewTri;
    @FXML MenuItem Quit;
    @FXML MenuItem MainWindow;
    @FXML TextArea RedCellText;

    int width = (int) Controller.processedImg.getWidth();
    int height = (int) Controller.processedImg.getHeight();

    /**
     * initialize method used to bring in current original image and processed tri colour image
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
     * AnalyiseImg method is used to go through the image and group the red bloodcells together by scanning the image and the first red pixel it hits it sets it as a root and then checks the pixel above it below it and next to it if they are also red blood cells
     * if they are also red it adds them to the union of the root
     * @param e Button BloodCellbtn click
     */
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




                if (TricolourController.WhiteCells[readY * width + readX] != 0 && (TricolourController.WhiteCells[readY * width + readX + 1] != 0)) {
                    Recognision.union(TricolourController.WhiteCells, readY * width + readX, readY * width + readX + 1);
                }
                if (readY < height - 1 && TricolourController.WhiteCells[readY * width + readX] != 0 && TricolourController.WhiteCells[readY * width + readX + width] != 0) {
                    Recognision.union(TricolourController.WhiteCells, readY * width + readX, readY * width + readX + width);
                }

            }
        }

        /*for (int i = 0; i < TricolourController.BloodCells.length; i++) {
            if (i % width == 0) System.out.println(); //New line
            System.out.print(Recognision.find(TricolourController.BloodCells, i) + " "); //Print root value
        }

        for (int i = 0; i < TricolourController.WhiteCells.length; i++) {
            if (i % width == 0) System.out.println(); //New line
            System.out.print(Recognision.find(TricolourController.WhiteCells, i) + " "); //Print root value
        }*/

        displayRectangles();
    }

    Rectangle[] r = new Rectangle[1000]; //hard coded values for the amount of rectangles
    Label[] l = new Label[1000]; //hard coded value for the amount of labels

    /**
     * displayRectangles method is used to place rectangles around the roots of each blood cells and also place a label with a number on it.
     * The method first gathers all the unique roots from the getArrayRoots() method and works by checking the roots and once on a root thats not 0 it places a rectangle of 1 pixel by 1 pixel
     * it then keeps going through the image and starts expanding the placed rectangle around the whole root. Also making sure it only places a rectangle on the Imnageview only where the image is located
     */
    public void displayRectangles() {
        findDuplicates();

            int arr[] = IntStream.of(findDuplicates()).distinct().toArray(); //takes only the unique values from the array
            int rIndex = 0;
            int lIndex = 0;
            int numCells = 0;
            System.out.println(Arrays.toString(arr));

            for(int j=0; j < arr.length - 1; j++) {
                for (int i = 0; i < TricolourController.BloodCells.length; i++) {
                    if (TricolourController.BloodCells[i] == arr[j]) {
                        int x = i % width, y = i / width;
                        if (r[rIndex] == null) {
                            r[rIndex] = new Rectangle(x, y, 1, 1);
                            numCells++;
                        }
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
                    if(r[rIndex].getWidth() * r[rIndex].getHeight() > 2500) {
                        r[rIndex].setStroke(Color.BLUE);
                    }
                    else r[rIndex].setStroke(Color.DARKGREEN);

                    r[rIndex].setTranslateX(ImageViewTri.getLayoutX());
                    r[rIndex].setTranslateY(ImageViewTri.getLayoutY());
                    ((Pane) ImageViewTri.getParent()).getChildren().add(r[rIndex++]);
                    RedCellText.setText(String.valueOf(numCells));
                }
                    l[lIndex] = new Label();
                    l[lIndex].setText(String.valueOf(numCells));
                    l[lIndex].setTextFill(Color.BLACK);
                    l[lIndex].setTranslateX(r[rIndex - 1].getX());
                    l[lIndex].setTranslateY(r[rIndex - 1].getY());

                    ((Pane) ImageViewTri.getParent()).getChildren().add(l[lIndex++]);
            }
        }


//Gets the roots of the array and adds them to a new array if the roots are greater than 100 in size

    /**
     * This method creates an array of the size of the image and creates a new array arr[] of all the roots that are greater than 100 in size
     * @return array arr[]
     */
        public int[] getArrayRoots() {
        int arr[] = new int[width*height];
        int temp =0;
        for(int i = 0; i< TricolourController.BloodCells.length; i++) {
            if(TricolourController.BloodCells[i] > 1000) {
                arr[temp] = TricolourController.BloodCells[i];
                temp++;
            }
        }
        return arr;
        }

        public int[] findDuplicates() {
            int arr[] = new int[width*height];
            int currentNum;
            int count = 0;
            int temp = 0;
            for(int i = 0; i < TricolourController.BloodCells.length; i++) {
                currentNum = TricolourController.BloodCells[i];
                for(int j = 0; j < TricolourController.BloodCells.length && count <= 101; j++) {
                    if(currentNum == TricolourController.BloodCells[j]) {
                        count++;
                    }
                    if(count > 100) {
                        arr[temp] = currentNum;
                        temp++;
                    }
                    //go through teh array grab a number go through the whole array if it oocurs more than 100 times add it to the array
                }
            }
            return arr;
        }



    //Exit Program Method

    /**
     * exit program method
     * @param e MenuItem Quit click
     */
    @FXML
    public void ExitProgram(ActionEvent e) {
        Runtime.getRuntime().exit(0);
    }

    /**
     * MainWindowScene is used to bring the user back to the Mainwindow scene
     * @param e MenuItem MainWindow
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

}
