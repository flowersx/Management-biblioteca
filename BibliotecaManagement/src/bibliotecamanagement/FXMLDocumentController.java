/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package bibliotecamanagement;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javax.naming.spi.DirStateFactory.Result;

public class FXMLDocumentController implements Initializable {
    
   @FXML
    private Button inregistare_buton;

    @FXML
    private AnchorPane inregistare_form;

    @FXML
    private ComboBox<?> inregistare_intrebare;

    @FXML
    private TextField inregistare_parola;

    @FXML
    private TextField inregistare_raspuns;

    @FXML
    private TextField inregistare_utilizator;

    @FXML
    private Button login_btnLogin;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button login_inregistrareButon;

    @FXML
    private TextField login_parola;

    @FXML
    private Hyperlink login_parolaUitata;

    @FXML
    private AnchorPane login_sideForm;

    @FXML
    private TextField login_utilizator;
    
    @FXML
    private Button login_dejaAvetiContButon;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Result result;
    
    public void swithcForm(ActionEvent event){
        TranslateTransition slider = new TranslateTransition();
        if(event.getSource() == login_inregistrareButon){
            slider.setNode(login_sideForm);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            
            slider.setOnFinished((ActionEvent e)->{
                login_dejaAvetiContButon.setVisible(true);
                
                
                login_inregistrareButon.setVisible(false);
            });
            
            slider.play();
        }else if(event.getSource() == login_dejaAvetiContButon){
            slider.setNode(login_sideForm);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            
            slider.setOnFinished((ActionEvent e)->{
                login_dejaAvetiContButon.setVisible(false);
                login_inregistrareButon.setVisible(true);
            });
            
            slider.play();
        }
    }   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
