package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Created by mohammedissa on 11/23/16.
 */
public class MainController implements Initializable {

    @FXML
    CheckBox underLined,bold;
    @FXML
    javafx.scene.control.TextField textField;
    @FXML
    ImageView imageView;
    @FXML
    ComboBox<String> comboBox;
    @FXML
    Button color;
    @FXML
    javafx.scene.canvas.Canvas canvas;
    String[] line;
    Letter currletter;

    int w,h;

    static int r,g,b;



    int x,y;

    Letter last;

    BufferedImage img ;


    boolean rtl,isFirst=true,exist;

    int selectedLanguage;



    List<Language> languages = new ArrayList<>();

    ObservableList<String> items = FXCollections.observableArrayList ();
    ArrayList<Letter> letters = new ArrayList<>();
    private boolean first = true;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        BufferedImage image = null;

        int type = BufferedImage.TYPE_INT_RGB;



        Image  imga = new ImageIcon("src/sample/paper.png").getImage();
        w = (int) (imga.getWidth(null));h= (int) (imga.getHeight(null));

        img = new BufferedImage((int) (imga.getWidth(null)), (int) (imga.getHeight(null)),
                BufferedImage.TYPE_INT_RGB);

        Graphics g = img.createGraphics();
        g.drawImage(imga, 0, 0, null);
        g.dispose();


        // File representing the folder that you select using a FileChooser
        final File dir = new File("fonts/");

        // array of supported extensions (use a List if you prefer)
        final String[] EXTENSIONS = new String[]{
                "fnt" // and other formats you need
        };
        // filter to identify images based on their extensions
        final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

            @Override
            public boolean accept(final File dir, final String name) {
                for (final String ext : EXTENSIONS) {
                    if (name.endsWith("." + ext)) {
                        return (true);
                    }
                }
                return (false);
            }
        };




        if (dir.isDirectory()) { // make sure it's a directory
        boolean first =true;

            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                Scanner scanner;

                try {
                    scanner = new Scanner(f);

                    boolean rtl = scanner.nextLine().equals("rtl");

                    String name = f.getName();
                    int pos = name.lastIndexOf(".");
                    if (pos > 0) {
                        name = name.substring(0, pos);
                    }

                    Language language = new Language(name,rtl);

                    if (first){
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
                                    letter.cases[i][j/16][j%16] = arrayList.get(i).charAt(j) == '1' ? (byte) 1 : (byte) 0;

                                }
                            }

//                        letter.cases=cases;
                            language.letters.add(letter);

                            first=false;
                        }
                    }

                    languages.add(language);

                    items.add(name);

                } catch (final IOException e) {
                    // handle errors here
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Something is wrong");
                    alert.setContentText("we are trying to fix this!");

                    alert.showAndWait();
                }
            }

        }

        comboBox.setItems(items);
        comboBox.getSelectionModel().selectFirst();
        selectedLanguage = comboBox.getSelectionModel().getSelectedIndex();

        x=48;
        if (languages.size()>0){
            if (languages.get(selectedLanguage).rightToLeft){
                x=w-48;
                rtl=true;
            }
        }

        y=10;

        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());

    }


    @FXML
    private void languages(){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("languages.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Exist Fonts");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void comboChange(){

        last=null;
        if (selectedLanguage!=comboBox.getSelectionModel().getSelectedIndex()){
            String name = "fonts/";

            name += comboBox.getSelectionModel().getSelectedItem()+".fnt";
            final File f = new File(name);
            if (f.exists()) { // make sure it's a directory


                Scanner scanner;

                try {
                    scanner = new Scanner(f);

                    rtl = scanner.nextLine().equals("rtl");

                    String[] line;

                    while (scanner.hasNext()) {


                        line = scanner.nextLine().trim().split(":");



                        char ch = line[0].charAt(0);

                        Letter letter = new Letter(ch);
                        letter.case1=true;letter.case2=true;letter.case3=true;

                        String s1 = line[1].substring(0,256);
                        String s2 = line[1].substring(257,513);
                        String s3 = line[1].substring(514,770);



                        ArrayList<String> arrayList = new ArrayList<>();
                        arrayList.add(s1);arrayList.add(s2);arrayList.add(s3);

                        for (int i = 0; i < 3; i++) {

                            for (int j = 0; j < arrayList.get(i).length() ; j++) {
                                letter.cases[i][j/16][j%16] = arrayList.get(i).charAt(j) == '1' ? (byte) 1 : (byte) 0;

                            }
                        }

                        languages.get(selectedLanguage).letters.add(letter);
                        languages.get(selectedLanguage).rightToLeft=rtl;



                    }




                } catch (final IOException e) {
                    // handle errors here
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Something is wrong");
                    alert.setContentText("we are trying to fix this!");

                    alert.showAndWait();
                }







            }
        }

        if (languages.get(selectedLanguage).rightToLeft){
            rtl=true;
             x = w-48;

        }
        else {
            rtl = false;
            x = 48;
        }

    }


    @FXML
    private void type(){

        int[] color= new int[]{r,g,b};

        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());

        canvas.setOnKeyPressed(e -> {


            if (e.getCode().equals(KeyCode.ENTER)){
//
//                img.getGraphics().setColor(Color.white);
//                img.getGraphics().fillRect(x,y-9 ,32,42);
//
//
//
//                for (int i = 0; i < last.cases[1].length; i++) {
//                    for (int j = 0; j < last.cases[1][i].length; j++) {
//                        if(last.cases[1][i][j]==1){
//                            img.getRaster().setPixel(x+j*2,y+i*2,color);
//                            img.getRaster().setPixel(x+(j*2)+1,y+(i*2)+1,color);
//                            img.getRaster().setPixel(x+(j*2)-1,y+(i*2)-1,color);
//
//
//                            if (bold.isSelected()){
//                                img.getRaster().setPixel(x+(j*2)+1,y+(i*2)+1,color);
//                                img.getRaster().setPixel(x+(j*2)+2,y+(i*2)+1,color);
//                                img.getRaster().setPixel(x+(j*2)-2,y+(i*2)-1,color);
//
//                            }
//
//                        }
//                    }
//                }

                isFirst=true;

                if (rtl){
                    x=w-32;
                }
                else x=32;
                last=null;


                y+=23;
                return;
            }

            if (e.getCode().equals(KeyCode.SPACE)){

//                img.getGraphics().setColor(Color.white);
//                img.getGraphics().fillRect(x,y-9 ,32,42);
//
//
//
//                for (int i = 0; i < last.cases[1].length; i++) {
//                    for (int j = 0; j < last.cases[1][i].length; j++) {
//                        if(last.cases[1][i][j]==1){
//                            img.getRaster().setPixel(x+j*2,y+i*2,color);
//                            img.getRaster().setPixel(x+(j*2)+1,y+(i*2)+1,color);
//                            img.getRaster().setPixel(x+(j*2)-1,y+(i*2)-1,color);
//
//
//                            if (bold.isSelected()){
//                                img.getRaster().setPixel(x+(j*2)+1,y+(i*2)+1,color);
//                                img.getRaster().setPixel(x+(j*2)+2,y+(i*2)+1,color);
//                                img.getRaster().setPixel(x+(j*2)-2,y+(i*2)-1,color);
//
//                            }
//
//                        }
//                    }
//                }
                isFirst=true;


                if (rtl){
                    x-=16;
                    if (x<=32){
                        x=w-32;
                        y+=23;
                    }
                }
                else {
                    x+=16;
                    if (x>=w-32){
                        x=32;
                        y+=23;
                    }
                }


                img.getGraphics().setColor(Color.white);
                img.getGraphics().fillRect(x,y ,16,16);

                last=null;

                javafx.scene.image.Image image2 = SwingFXUtils.toFXImage(img, null);
                imageView.setImage(image2);
                return;
            }

            if (e.getCode().equals(KeyCode.BACK_SPACE)){
                img.getGraphics().setColor(Color.white);
                img.getGraphics().fillRect(x,y ,16,16);

                if (rtl){
                    x+=16;
                    if (x>=w-16){
                        x=32;
                        y-=23;
                    }
                }
                else {
                    x-=16;
                    if (x<=16){
                        x=w-32;
                        y-=23;
                    }
                }
                last=null;
                javafx.scene.image.Image image2 = SwingFXUtils.toFXImage(img, null);
                imageView.setImage(image2);
                return;
            }


            char ch = e.getText().charAt(0);



            for (int i = 0; i < languages.get(selectedLanguage).letters.size(); i++) {
                if (languages.get(selectedLanguage).letters.get(i).aChar == ch){
                    currletter = languages.get(selectedLanguage).letters.get(i);
                    exist=true;
                    break;
                }
            }

            if (exist){



                if (!isFirst){
                if (last!=null){
                    img.getGraphics().setColor(Color.white);
                    img.getGraphics().fillRect(x,y ,17,16);
                    System.out.println("asdkjhgkjhjkhashdjkhasjdh");

                    for (int i = 0; i < last.cases[1].length; i++) {
                        for (int j = 0; j < last.cases[1][i].length; j++) {
                            if(last.cases[1][i][j]==1){
                                img.getRaster().setPixel(x+j,y+i,color);
//                                img.getRaster().setPixel(x+(j)+1,y+(i),color);
//                                img.getRaster().setPixel(x+(j)-1,y+(i),color);

//
                                if (bold.isSelected()){
                                    img.getRaster().setPixel(x+(j)+1,y+(i),color);
//                                    img.getRaster().setPixel(x+(j)-1,y+(i),color);

                                }

                            }
                        }
                    }
                }}




                if (rtl){
                x-=16;
                if (x<=32){
                    x=w-32;
                    y+=23;
                }
            }
            else {
                x+=16;
                if (x>=w-32){
                    x=32;
                    y+=23;
                }
            }




                img.getGraphics().setColor(Color.white);
                img.getGraphics().fillRect(x,y ,17,16);

                int cas;
                if (isFirst)
                    cas=0;
                else
                    cas=2;

                last = currletter;

                for (int i = 0; i < currletter.cases[cas].length; i++) {
                    for (int j = 0; j < currletter.cases[cas][i].length; j++) {
                        if(currletter.cases[cas][i][j]==1){
                            img.getRaster().setPixel(x+j,y+i,color);
//                            img.getRaster().setPixel(x+(j)+1,y+(i),color);
//                            img.getRaster().setPixel(x+(j)-1,y+(i),color);

//
                            if (bold.isSelected()){
                            img.getRaster().setPixel(x+(j)+1,y+(i),color);
//                            img.getRaster().setPixel(x+(j)-1,y+(i),color);
                            }

                        }
                    }
                }

                isFirst=false;
                exist=false;
                javafx.scene.image.Image image2 = SwingFXUtils.toFXImage(img, null);
                imageView.setImage(image2);
            }




        });


    }


//    public void keyTyped(KeyEvent e) {
////        if (languages.get(selectedLanguage).rightToLeft) {
////            if (x < 50) {
////                for (int i = 0; i < lastword.size(); i++) {
////                    resetRectangle(img.getRaster());
////                }
////                newLine();
////                for (int i = 0; i < lastword.size(); i++) {
////                    int c = (int) lastword.get(i).charAt(0);
////                    int pos = 0;
////                    for (int j = 0; j < map.length; j++) {
////                        if (c == map[j]) {
////                            pos = j;
////                            break;
////                        }
////                    }
////                    if (i == 0) {
////                        drawLetter(img.getRaster(), this.binaries1.elementAt(pos),
////                                new int[] { rr.getValue(), gg.getValue(), bb.getValue() });
////                    } else if (i == lastword.size() - 1) {
////                        drawLetter(img.getRaster(), this.binaries3.elementAt(pos),
////                                new int[] { rr.getValue(), gg.getValue(), bb.getValue() });
////                    } else {
////                        drawLetter(img.getRaster(), this.binaries2.elementAt(pos),
////                                new int[] { rr.getValue(), gg.getValue(), bb.getValue() });
////                    }
////                }
////            }
////        } else {
////            if (Xpos > img.getWidth() - 50) {
////                for (int i = 0; i < lastword.size() - 1; i++) {
////                    resetRectangle(img.getRaster());
////                }
////                newLine();
////                for (int i = 0; i < lastword.size(); i++) {
////                    int c = (int) lastword.get(i).charAt(0);
////                    int pos = 0;
////                    for (int j = 0; j < map.length; j++) {
////                        if (c == map[j]) {
////                            pos = j;
////                            break;
////                        }
////                    }
////                    drawLetter(img.getRaster(), this.selected.elementAt(pos),
////                            new int[] { rr.getValue(), gg.getValue(), bb.getValue() });
////                }
////            }
////        }
//
////        this.selected = binaries1;
//        char ch =  e.getCharacter().charAt(0);
//        int position = -1;
//
//        for (int i = 0; i < languages.get(selectedLanguage).letters.size(); i++) {
////                System.out.println(languages.get(selectedLanguage).letters);
//            if (languages.get(selectedLanguage).letters.get(i).aChar == ch){
//                position=i;
////                System.out.println(currletter);
//                break;
//            }
//        }
//
//        if (languages.get(selectedLanguage).rightToLeft) {
//            if (lastLetterPosition == 0 || lastLetterPosition == 7 || lastLetterPosition == 8 || lastLetterPosition == 9
//                    || lastLetterPosition == 10 || lastLetterPosition == 26) {
//                this.selected = binaries1;
//            } else {
//                if (first == false) {
//                    this.selected = binaries2;
//                }
//            }
//            if (space == true) {
//                this.selected = binaries1;
//            }
//            space = false;
//            first = false;
//        }
//
//        if (position != -1) {
//            viewLetter(this.selected, position);
//
//            if (languages.get(selectedLanguage).rightToLeft) {
//                int which = -1;
//                if (this.selected == binaries1) {
//                    which = 1;
//                } else if (this.selected == binaries2) {
//                    which = 2;
//                } else {
//                    which = 3;
//                }
//                letters.add((char) selected + "," + which);
//                lastword.add(e.getKeyChar() + "," + which);
//                lastLetter = binaries3.elementAt(position);
//                lastLetterPosition = position;
//            } else {
//                letters.add((char) selected + "");
//                lastword.add(e.getKeyChar() + "");
//            }
//
//
//            drawLetter(img.getRaster(), this.selected.elementAt(position),
//                    new int[] { rr.getValue(), gg.getValue(), bb.getValue() });
//
//            this.selected = binaries1;
//        }
//
//    }

    @FXML
    private void color(){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("color.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Color");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
