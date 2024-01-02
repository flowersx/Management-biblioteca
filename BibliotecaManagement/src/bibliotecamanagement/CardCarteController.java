/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bibliotecamanagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.CarteData;

/**
 *
 * @author Florin
 */
public class CardCarteController implements Initializable {

    @FXML
    private Button card_adaugaBtn;

    @FXML
    private AnchorPane card_form;

    @FXML
    private ImageView card_imageView;

    @FXML
    private Label card_numeCarte;

    @FXML
    private Label card_pretCarte;

    @FXML
    private Spinner<?> card_spinner;
    
    private CarteData carteData;
    private Image image;
    
    // aici setam datele din carduri
    public void setData(CarteData carteData){
        this.carteData = carteData;
        
        card_numeCarte.setText(carteData.getNumeCarte());
        card_pretCarte.setText(String.valueOf(carteData.getClass())+ " RON");
        String path = "File:" + carteData.getImage();
        image = new Image(path, 190, 94, false, true);
        card_imageView.setImage(image);
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
