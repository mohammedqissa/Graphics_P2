package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    ObservableList<Character> items = FXCollections.observableArrayList ();

    @FXML
    ListView<Character> listView = new ListView<>();

    ArrayList<Letter> letters = new ArrayList<>();

    Letter currletter = null;


    @FXML
    Label label;

    boolean acharChanged =false,isExist;

    byte[][][] cases ;

    char aChar ;

    Stack<double[]> stack = new Stack<>();

    int selectedItem=0;


    @FXML
    Button closeB,save;

    @FXML
    ImageView thumpImage;

    @FXML
    TextField keyT;
    @FXML
    Label fontLabelname;

    ToggleGroup group = new ToggleGroup();
    @FXML
    RadioButton rb1, rb2, rb3;
    @FXML
    CheckBox rtl,erase;

    boolean saved;
    String[] line;

    @FXML
    private Canvas drawArea;
    private GraphicsContext gc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gc = drawArea.getGraphicsContext2D();

        if (LangController.editFont) {

            String name = "fonts/";

            saved=true;
            name += LangController.name+".fnt";
            final File f = new File(name);
            if (f.exists()) { // make sure it's a directory


                Scanner scanner;

                try {
                    scanner = new Scanner(f);

                    boolean rtl = scanner.nextLine().equals("rtl");
                    this.rtl.setSelected(!rtl);


                    while (scanner.hasNext()) {


                        line = scanner.nextLine().trim().split(":");



                        char ch = line[0].charAt(0);

                        Letter letter = new Letter(ch);
                        letter.case1=true;letter.case2=true;letter.case3=true;

                        String s1 = line[1].substring(0,256);
                        String s2 = line[1].substring(257,513);
                        String s3 = line[1].substring(514,770);



                        ArrayList<String> arrayList = new ArrayList();
                        arrayList.add(s1);arrayList.add(s2);arrayList.add(s3);
//                        cases = new byte[3][16][16];

                        for (int i = 0; i < 3; i++) {

                            for (int j = 0; j < arrayList.get(i).length() ; j++) {
                                letter.cases[i][j%16][j/16] = arrayList.get(i).charAt(j) == '1' ? (byte) 1 : (byte) 0;

                            }
                        }

//                        letter.cases=cases;
                        letters.add(letter);
                        items.add(letter.aChar);



                    }

                    acharChanged=true;
                    this.aChar = letters.get(0).aChar;
                    isExist =true;
//                    System.out.println(items.get(0));
                    listView.setItems(items);
                    listView.getFocusModel().focus(0);

                    final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-up.png"));
                    thumpImage.setImage(image2);

                    drow(letters.get(0), 0);
//                    addToList();
//                    System.out.println(letters.size());
                    label.setText("Key : "+aChar);
                    label.setVisible(true);
                    keyT.setVisible(false);
                    label.requestFocus();
                    selectedItem=0;


                } catch (final IOException e) {
                    // handle errors here
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Something is wrong");
                    alert.setContentText("we are trying to fix this!");

                    alert.showAndWait();
                }







            }
            LangController.editFont = false;

        } else {

//            newLetter();
            listView.getItems().clear();

        }
        fontLabelname.setText(LangController.name + " Font");

        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        listView.requestFocus();
        rb1.setSelected(true);

    }

    @FXML
    private void print() {



        drawArea.setOnMouseClicked(e -> {

            int x = (int) e.getX(), y = (int) e.getY();



            if (!erase.isSelected()){
                if ((x < 264 && x >= 0) && (y < 264 && y >= 0)) {
                    if ((letters.get(selectedItem).cases[getSelectedRadio()][(int) (x / 16.5)][(int) (y / 16.5)] == 0)) {
                        stack.push(new double[]{x, y,1});

                        gc.fillRect(16.5 * ((int) (x / 16.5)), 16.5 * ((int) (y / 16.5)), 15.5, 15.5);
                        letters.get(selectedItem).cases[getSelectedRadio()][(int) (x / 16.5)][(int) (y / 16.5)] = 1;
                    }
                }
            }
            else {
                if ((x < 264 && x >= 0) && (y < 264 && y >= 0)) {
                    if ((letters.get(selectedItem).cases[getSelectedRadio()][(int) (x / 16.5)][(int) (y / 16.5)] == 1)) {
                        stack.push(new double[]{x, y,0});

                        gc.clearRect(16.5 * ((int) (x / 16.5)), 16.5 * ((int) (y / 16.5)), 15.5, 15.5);
                        letters.get(selectedItem).cases[getSelectedRadio()][(int) (x / 16.5)][(int) (y / 16.5)] = 0;
                    }
                }
            }


        });

        drawArea.setOnMouseDragged(e -> {


            int x = (int) e.getX(), y = (int) e.getY();


            if (!erase.isSelected()){
                if ((x < 264 && x >= 0) && (y < 264 && y >= 0)) {
                    if ((letters.get(selectedItem).cases[getSelectedRadio()][(int) (x / 16.5)][(int) (y / 16.5)] == 0)) {
                        stack.push(new double[]{x, y,1});

                        gc.fillRect(16.5 * ((int) (x / 16.5)), 16.5 * ((int) (y / 16.5)), 15.5, 15.5);
                        letters.get(selectedItem).cases[getSelectedRadio()][(int) (x / 16.5)][(int) (y / 16.5)] = 1;
                    }
                }
            }
            else {
                if ((x < 264 && x >= 0) && (y < 264 && y >= 0)) {
                    if ((letters.get(selectedItem).cases[getSelectedRadio()][(int) (x / 16.5)][(int) (y / 16.5)] == 1)) {
                        stack.push(new double[]{x, y,0});

                        gc.clearRect(16.5 * ((int) (x / 16.5)), 16.5 * ((int) (y / 16.5)), 15.5, 15.5);
                        letters.get(selectedItem).cases[getSelectedRadio()][(int) (x / 16.5)][(int) (y / 16.5)] = 0;
                    }
                }
            }


        });




    }

    @FXML
    private void save() {

        File file = new File("fonts/"+LangController.name+".fnt");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    PrintWriter writer = new PrintWriter(file,"UTF-8");
                    writer.println(!this.rtl.isSelected()?"ltr":"rtl");
                    System.out.println(!this.rtl.isSelected()?"ltr":"rtl");
                    for (int i = 0; i < letters.size(); i++) {
                        writer.println(letters.get(i).toString());
                        System.out.println(i);
                    }
                    writer.close();
                    saved = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = (Stage) save.getScene().getWindow();
            stage.close();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("File is exist!");
            alert.setContentText("replace file?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                file.delete();
                try {
                    if (file.createNewFile()) {
                        PrintWriter writer = new PrintWriter(file,"UTF-8");
                        writer.println(rtl.isSelected()?"ltr":"rtl");
                        System.out.println(rtl.isSelected()?"ltr":"rtl");
                        for (int i = 0; i < letters.size(); i++) {
                            writer.println(letters.get(i).toString());
                            System.out.println(i);
                        }
                        writer.close();
                        saved = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                alert.close();
                // ... user chose CANCEL or closed the dialog
            }

            Stage stage = (Stage) save.getScene().getWindow();
            stage.close();
//            LangController.stage.close();
        }
        LangController.stage.close();






    }

    @FXML
    private void clear() {

        gc.clearRect(0, 0, 264, 264);
        for (int i = 0; i < letters.get(selectedItem).cases[getSelectedRadio()].length; i++) {
            for (int j = 0; j < letters.get(selectedItem).cases[getSelectedRadio()][i].length; j++) {
                letters.get(selectedItem).cases[getSelectedRadio()][j][i]=0;
            }
        }
        stack.clear();

    }

    private void drow(Letter letter, int cas) {

        byte[][] bytes = letter.cases[cas];

        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < bytes[i].length; j++) {
                if(bytes[i][j]==1){

                    gc.fillRect(i*16.5,j*16.5,15.5,15.5);
                    stack.push(new double[]{i*16.5,j*15.5,1});
                }
            }
        }


    }


    private int getSelectedRadio() {
        if (rb1.isSelected())
            return 0;
        else if (rb2.isSelected())
            return 1;
        else
            return 2;
    }

    @FXML
    private void retur() {

        if (!stack.isEmpty()) {
            double[] s = stack.pop();
            if (s[2]==1){
                gc.clearRect(16.5 * ((int) (s[0] / 16.5)), 16.5 * ((int) (s[1] / 16.5)), 15.5, 15.5);
                letters.get(selectedItem).cases[getSelectedRadio()][(int) (s[0] / 16.5)][(int) (s[1] / 16.5)] = 0;
            }
            else {
                gc.fillRect(16.5 * ((int) (s[0] / 16.5)), 16.5 * ((int) (s[1] / 16.5)), 15.5, 15.5);
                letters.get(selectedItem).cases[getSelectedRadio()][(int) (s[0] / 16.5)][(int) (s[1] / 16.5)] = 1;
            }
        }


    }

    @FXML
    private void thump(){



        switch (getSelectedRadio()){
            case 0:{

                if (!letters.get(selectedItem).case1){
                    letters.get(selectedItem).case1=true;
//                    letters.get(selectedItem).cases[0] = cases[0].clone();
                    final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-up.png"));
                    thumpImage.setImage(image2);
                }
                break;
            }
            case 1:{
                if (!letters.get(selectedItem).case2){
                    letters.get(selectedItem).case2=true;
//                    letters.get(selectedItem).cases[1] = cases[1].clone();
                    final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-up.png"));
                    thumpImage.setImage(image2);
                }
                break;

            }
            case 2:{
                if (!letters.get(selectedItem).case3){
                    letters.get(selectedItem).case3=true;
//                    letters.get(selectedItem).cases[1] = cases[1].clone();
                    final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-up.png"));
                    thumpImage.setImage(image2);
                }
                break;
            }
        }


//        if (isready()){
//            if (acharChanged){
////                addToList();
//                items.add(aChar);
//                listView.setItems(items);
//
//                System.out.println("flag1");
//
//            }
//            else {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Character Key");
//                alert.setHeaderText("Character Key!");
//                alert.setContentText("it seems you have drawed all character cases\nplease type character key below the canvas");
//
//                alert.showAndWait();
//
//                System.out.println("flag2");
//
//            }
//        }


    }

    @FXML
    private void radioChange(){
        gc.clearRect(0, 0, 264, 264);
        stack.clear();
//        drow(letters.get(selectedItem),getSelectedRadio());

        switch (getSelectedRadio()){
            case 0:{

                if (!letters.get(selectedItem).case1){
                    drow(letters.get(selectedItem),0);

                    final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-down-hand.png"));
                    thumpImage.setImage(image2);
                }
                else {
                    drow(letters.get(selectedItem),0);
                    final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-up.png"));
                    thumpImage.setImage(image2);
                }
                break;
            }
            case 1:{
                if (!letters.get(selectedItem).case2){
                    drow(letters.get(selectedItem),1);

                    final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-down-hand.png"));
                    thumpImage.setImage(image2);
                }
                else {
                    drow(letters.get(selectedItem),1);

                    final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-up.png"));
                    thumpImage.setImage(image2);
                }
                break;

            }
            case 2:{
                if (!letters.get(selectedItem).case3){
                    drow(letters.get(selectedItem),2);

                    final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-down-hand.png"));
                    thumpImage.setImage(image2);
                }
                else {
                    drow(letters.get(selectedItem),2);
                    final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-up.png"));
                    thumpImage.setImage(image2);
                }
                break;
            }
        }


    }

    @FXML
    private void listChange(){


        if (!isready()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("some cases are not Thumbed up!");
            alert.setContentText("Please thumb up all cases");

            alert.showAndWait();


        }


        selectedItem = listView.getSelectionModel().getSelectedIndex();
        gc.clearRect(0, 0, 264, 264);
        stack.clear();
//            clear();
//            aChar = listView.getSelectionModel().getSelectedItem();
//            addToList();


//            System.out.println("seleced "+selectedItem);
//
//            System.out.println(letters.get(selectedItem));

//            letter = letters.get(listView.getSelectionModel().getSelectedIndex());

//            letter.case1=false;letter.case2=false;letter.case3=false;

//            cases = letters.get(selectedItem).cases;
        aChar =  letters.get(selectedItem).aChar;
        acharChanged=true;
        label.setText("Key : "+letters.get(selectedItem).aChar);

        drow(letters.get(selectedItem),0);

//            selectedItem = listView.getSelectionModel().getSelectedIndex();
        rb1.setSelected(true);

        final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-up.png"));
        thumpImage.setImage(image2);

        isExist=true;

    }

    private boolean isready(){
        return letters.get(selectedItem).case1&&letters.get(selectedItem).case2&&letters.get(selectedItem).case3;
    }

    @FXML
    private boolean key(){

        keyT.setText("");
        keyT.setOnKeyTyped(e -> {

            this.aChar= e.getCharacter().charAt(0);
            acharChanged=true;

            keyT.setVisible(false);
            label.setText("Key : "+aChar);
            label.setVisible(true);
            currletter.aChar=aChar;
            items.add(currletter.aChar);
            listView.setItems(items);
            listView.requestFocus();
            listView.getSelectionModel().select(selectedItem);

        });

        return acharChanged;


    }

    @FXML
    private void newLetter(){

        keyT.setDisable(false);
        keyT.requestFocus();
        key();

        rb1.setSelected(true);
        isExist = false;
        saved=false;
        acharChanged=false;

        gc.clearRect(0, 0, 264, 264);

//        cases = new byte[3][16][16];

        keyT.setText("");
        keyT.setVisible(true);
        label.setVisible(false);

        stack.clear();


            currletter = new Letter(this.aChar);

            letters.add(currletter);


            isExist=true;
            selectedItem=letters.size()-1;


            final Image image2 = new Image(Main.class.getResourceAsStream("thumbs-down-hand.png"));
            thumpImage.setImage(image2);



    }


    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeB.getScene().getWindow();

        if (saved){
            // get a handle to the stage

            // do what you have to do
            stage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Changes didn't saved!");
            alert.setContentText("Exit without saving?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                stage.close();

            } else {
                alert.close();
                // ... user chose CANCEL or closed the dialog
            }
        }

    }

    @FXML
    private void delete(){

        letters.remove(selectedItem);
        items.remove(selectedItem);
        selectedItem-=1;
        listView.setItems(items);
        if (selectedItem>=0){
            listView.getSelectionModel().select(selectedItem);
            listView.requestFocus();
        }
        else {
            selectedItem=0;
            listView.getSelectionModel().select(selectedItem);
            listView.requestFocus();
        }
    }

    @FXML
    private void cursor(){
        if (!erase.isSelected()){
            drawArea.setCursor(Cursor.CROSSHAIR);
        }else drawArea.setCursor(Cursor.DISAPPEAR);
    }

//    void addToList(){

//        label.setText("Key : "+aChar);
//        label.setVisible(true);
//        keyT.setVisible(false);
//        label.requestFocus();

//        if (!isExist){
//            letter.aChar=aChar;
//            letter.cases=cases;
//            letters.add(letter);
//            items.add(aChar);
//            listView.setItems(items);

//        }




//        acharChanged=false;


//    }

}

