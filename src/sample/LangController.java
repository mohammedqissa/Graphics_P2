package sample;

/**
 * Created by mohammedissa on 11/23/16.
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class LangController implements Initializable{

    @FXML
    ListView<String> listView = new ListView<String>();

    List<Language> languages = new ArrayList<>();

    ObservableList<String> items = FXCollections.observableArrayList ();



    static Stage stage ;


    static String name;
    static boolean editFont;


    @FXML
    Button closeB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


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
            listView.setItems(items);
            listView.getFocusModel().focus(0);

        }

    }

    @FXML
    public void addLanguage(){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){

            name=result.get();


            try{

                editFont = false;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("Add Font");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException h) {
                h.printStackTrace();
            }





        }

        stage = (Stage) listView.getScene().getWindow();



    }


    @FXML
    public void edit(){

        listView.setOnMouseClicked(e ->{


            if (e.getClickCount() == 2){

                name = listView.getSelectionModel()
                        .getSelectedItem();


                try{

                    editFont = true;
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setTitle("Edit Font");
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (IOException h) {
                    h.printStackTrace();
                }




            }

            stage = (Stage) listView.getScene().getWindow();

        });
    }


    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeB.getScene().getWindow();
        // do what you have to do
        stage.close();
    }


    @FXML
    private void delete(){

       if (listView.getSelectionModel().getSelectedIndex()>=0){

           String name = listView.getSelectionModel().getSelectedItem();

           int i = listView.getSelectionModel().getSelectedIndex();

           items.remove(i);
           listView.setItems(items);

           File file = new File("fonts/"+name+".fnt");
           file.delete();



       }

    }







}
