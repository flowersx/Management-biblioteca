/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package bibliotecamanagement;

import constants.Constants;
import static constants.Constants.Utils.EMPTY_STRING;
import database.database;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    private Alert alert;

    public void ReseteazaInregistrarea() {
        inregistare_utilizator.setText(EMPTY_STRING);
        inregistare_parola.setText(EMPTY_STRING);
        inregistare_intrebare.getSelectionModel().clearSelection();
        inregistare_raspuns.setText(EMPTY_STRING);
    }

    public void SlideRight(TranslateTransition slider) {
        slider.setNode(login_sideForm);
        slider.setToX(300);
        slider.setDuration(Duration.seconds(.5));

        slider.setOnFinished((ActionEvent e) -> {
            login_dejaAvetiContButon.setVisible(true);

            login_inregistrareButon.setVisible(false);
        });
        requiredQuestionList();
        slider.play();
    }

    public void SliderLeft(TranslateTransition slider) {
        slider.setNode(login_sideForm);
        slider.setToX(0);
        slider.setDuration(Duration.seconds(.5));

        slider.setOnFinished((ActionEvent e) -> {
            login_dejaAvetiContButon.setVisible(false);
            login_inregistrareButon.setVisible(true);
        });

        slider.play();
    }

    public void butonInregistrare() {
        if (inregistare_raspuns.getText().isEmpty()
                || inregistare_parola.getText().isEmpty()
                || inregistare_intrebare.getSelectionModel().getSelectedItem() == null
                || inregistare_utilizator.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.ErrorMessages.TitluEroare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.BlankFields);
            alert.showAndWait();
        } else {
            String requiredData = "INSERT INTO users (username, password, question, answer, date) "
                    + "VALUES (?,?,?,?,?)";
            connect = database.connectDB();

            try {
                prepare = connect.prepareStatement(requiredData);
                prepare.setString(1, inregistare_utilizator.getText());
                prepare.setString(2, inregistare_parola.getText());
                prepare.setString(3, (String) inregistare_intrebare.getSelectionModel().getSelectedItem());
                prepare.setString(4, inregistare_raspuns.getText());
                
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(5, String.valueOf(sqlDate));

                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(Constants.ErrorMessages.TitluInformatie);
                alert.setHeaderText(null);
                alert.setContentText(Constants.ErrorMessages.InregistrareCont);
                alert.showAndWait();

                ReseteazaInregistrarea();
                
                TranslateTransition slider = new TranslateTransition();
                SliderLeft(slider);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] questionList = {Constants.Questions.Question_1,
        Constants.Questions.Question_2,
        Constants.Questions.Question_3,
        Constants.Questions.Question_4,
        Constants.Questions.Question_5
    };

    public void requiredQuestionList() {
        List<String> listQuestions = new ArrayList<>(Arrays.asList(questionList));

        ObservableList listData = FXCollections.observableArrayList(listQuestions);
        inregistare_intrebare.setItems(listData);
    }

    public void swithcForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == login_inregistrareButon) {
            SlideRight(slider);
        } else if (event.getSource() == login_dejaAvetiContButon) {
            SliderLeft(slider);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
