package sample;



import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;




public class ColorController  {


    public Label Colorlabel;




    public Slider Rslider = new Slider();
    public Slider Gslider=new Slider();
    public Slider Bslider=new Slider();



    public AnchorPane anchor;


    public Button closeButton;


    public void RsliderF(){//this function to get R color value
        MainController.r = (int) Rslider.getValue();
        setcolorRGB();

    }

    public void GsliderF(){//this function to get G color value
        MainController.g= (int)Gslider.getValue();
        setcolorRGB();
    }

    public void BsliderF(){//this function to get B color value
        MainController.b = (int)Bslider.getValue();
        setcolorRGB();
    }

    void setcolorRGB(){//this function to colorize the label by rgb

        Colorlabel.setStyle("-fx-background-color: rgb("+MainController.r+","+MainController.g+","+MainController.b+");");

    }







    public void close(){

        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }



}


