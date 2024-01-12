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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.naming.spi.DirStateFactory.Result;
import models.data;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button back_button_fp;

    @FXML
    private Button buton_inregistrare;

    @FXML
    private PasswordField confirmare_parola_fp;

    @FXML
    private AnchorPane forgot_password;

    @FXML
    private AnchorPane inregistare_form;

    @FXML
    private ComboBox<?> inregistare_intrebare;

    @FXML
    private PasswordField inregistare_parola;

    @FXML
    private TextField inregistare_raspuns;

    @FXML
    private TextField inregistare_utilizator;

    @FXML
    private ComboBox<?> intrebari_fp_form;

    @FXML
    private Button login_btnLogin;

    @FXML
    private Button login_dejaAvetiContButon;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button login_inregistrareButon;

    @FXML
    private PasswordField login_parola;

    @FXML
    private Hyperlink login_parolaUitata;

    @FXML
    private AnchorPane login_sideForm;

    @FXML
    private TextField login_utilizator;

    @FXML
    private PasswordField parola_noua_fp;

    @FXML
    private Button proceed_button;

    @FXML
    private TextField raspuns_fp_form;

    @FXML
    private AnchorPane reset_password;

    @FXML
    private Button reset_passwordButton;

    @FXML
    private TextField utilizator_fp;

    private Connection connect;
    private PreparedStatement prepare;
    private Result result;

    private Alert alert;

    // setam fieldurile de inregistrare sa fie goale
    public void ReseteazaInregistrarea() {
        inregistare_utilizator.setText(EMPTY_STRING);
        inregistare_parola.setText(EMPTY_STRING);
        inregistare_intrebare.getSelectionModel().clearSelection();
        inregistare_raspuns.setText(EMPTY_STRING);
    }
    // facem side menu sa isi dea slide cu o animatie spre dreapta
    public void SlideRight(TranslateTransition slider) {
        // setam nodu cu animatia respectiva
        slider.setNode(login_sideForm);
        // se va duce 300 de pixeli spre dreapta
        slider.setToX(300);
        // durata animatiei
        slider.setDuration(Duration.seconds(.5));
        
        // atunci cand e gata setam vizibila inregistrarea si invizibila logareea
        slider.setOnFinished((ActionEvent e) -> {
            login_dejaAvetiContButon.setVisible(true);
            login_inregistrareButon.setVisible(false);

            forgot_password.setVisible(false);
            login_form.setVisible(true);
            reset_password.setVisible(false);
        });
        requiredQuestionList();
        slider.play();
    }
    // la fel ca cel spre dreapta dar spre stanga
    public void SliderLeft(TranslateTransition slider) {
        slider.setNode(login_sideForm);
        slider.setToX(0);
        slider.setDuration(Duration.seconds(.5));

        slider.setOnFinished((ActionEvent e) -> {
            login_dejaAvetiContButon.setVisible(false);
            login_inregistrareButon.setVisible(true);

            forgot_password.setVisible(false);
            login_form.setVisible(true);
            reset_password.setVisible(false);

        });

        slider.play();
    }

    // seteaza login formu sa nu mai fie vizibil si apare pagina sa confirmi identitatea pentru resetarea parolei
    public void switchForgotPassword() {
        forgot_password.setVisible(true);
        login_form.setVisible(false);

        forgotPasswordQuestionList();
    }

    public void changePassword() {
        if (confirmare_parola_fp.getText().isEmpty() || parola_noua_fp.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.ErrorMessages.TitluEroare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.BlankFields);
            alert.showAndWait();
        } else {
            if (confirmare_parola_fp.getText().equals(parola_noua_fp.getText())) {
                connect = database.connectDB();
                try {
                    // luam date-ul pt actualizare
                    String getDate = "SELECT date FROM users WHERE username ='"
                            + utilizator_fp.getText() + "'";
                    ResultSet result;
                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();
                    String date = "";
                    if (result.next()) {
                        date = result.getString("date");
                    }
                    // actualizam parola la respectivul username
                    String updatePassword = "UPDATE users SET password='"
                            + parola_noua_fp.getText() + "', date='"
                            + date + "' WHERE username = '"
                            + utilizator_fp.getText() + "'";
                    prepare = connect.prepareStatement(updatePassword);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(Constants.ErrorMessages.TitluInformatie);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.ParolaActualizata);
                    alert.showAndWait();
                    // facem din nou vizibila pagina de login
                    login_form.setVisible(true);
                    reset_password.setVisible(false);

                    clearResetPassword();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(Constants.ErrorMessages.TitluEroare);
                alert.setHeaderText(null);
                alert.setContentText(Constants.ErrorMessages.PasswordDontMatch);
                alert.showAndWait();
            }
        }
    }

    // inapoi la pagina de logare
    public void BackToLoginForm() {
        forgot_password.setVisible(false);
        login_form.setVisible(true);
        clearResetPassword();
    }
    // inapoi la pagina de verificare a indentitatii
    public void BackToQuestionForm() {
        forgot_password.setVisible(true);
        reset_password.setVisible(false);
    }
    
    //resetam fieldurile de resetare a parolei
    public void clearResetPassword() {
        parola_noua_fp.setText(Constants.Utils.EMPTY_STRING);
        confirmare_parola_fp.setText(Constants.Utils.EMPTY_STRING);
        intrebari_fp_form.getSelectionModel().clearSelection();
        utilizator_fp.setText(Constants.Utils.EMPTY_STRING);
        raspuns_fp_form.setText(Constants.Utils.EMPTY_STRING);
    }

    
    public void proceedButton() {
        // verificam daca fieldurile sunt goale
        if (raspuns_fp_form.getText().isEmpty()
                || utilizator_fp.getText().isEmpty()
                || intrebari_fp_form.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.ErrorMessages.TitluEroare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.BlankFields);
            alert.showAndWait();
        } else {
            // verificam identitate
            String selectData = "SELECT username, question, answer FROM users WHERE username = ? "
                    + "AND answer = ?"
                    + "AND question = ?";
            connect = database.connectDB();
            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, utilizator_fp.getText());
                prepare.setString(2, raspuns_fp_form.getText());
                prepare.setString(3, (String) intrebari_fp_form.getSelectionModel().getSelectedItem());

                ResultSet result;
                result = prepare.executeQuery();
                if (result.next()) {
                    // facem vizibila partea de resetare deoarece identitatea a fost confirmata
                    reset_password.setVisible(true);
                    forgot_password.setVisible(false);
                } else {
                    // in caz contrar spunem ca informatiile oferite sunt gresite
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle(Constants.ErrorMessages.TitluEroare);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.WrongInformations);
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void butonLogin() {
        // verificam sa nu fie fieldurile goale
        if (login_utilizator.getText().isEmpty() || login_parola.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.ErrorMessages.TitluEroare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.BlankFields);
            alert.showAndWait();
        } else {
            // verificam username si parola din baza de date sa vedem daca exista un user cu aceasta parola
            String selectData = "Select username, password FROM users WHERE username = ? AND password = ?";
            connect = database.connectDB();
            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, login_utilizator.getText());
                prepare.setString(2, login_parola.getText());

                ResultSet result;
                result = prepare.executeQuery();
                // Daca login e cu succes mergem mai departe
                if (result.next()) {
                    
                    // Ca sa putem luea numele utilizatorului logat
                    data.username = login_utilizator.getText();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(Constants.ErrorMessages.TitluInformatie);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.LoginCont);
                    alert.showAndWait();
                    
                    //practic linkuim main form 
                    Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.setTitle(Constants.Utils.APP_TITLE);
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);
                    
                    stage.setScene(scene);
                    stage.show();
                    
                    login_btnLogin.getScene().getWindow().hide();
                    
                } else { // Daca nu e cu succes facem display la un mesaj de eroare
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle(Constants.ErrorMessages.TitluEroare);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.WrongUsernameOrPassword);
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void butonInregistrare() {
        //verificam fieldurile sa nu fie goale
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
            // inseram datele noului utilizator in baza
            String requiredData = "INSERT INTO users (username, password, question, answer, date) "
                    + "VALUES (?,?,?,?,?)";
            connect = database.connectDB();

            try {
                // verificam sa nu exista un username cu aceelasi nume
                if (checkUsername(inregistare_utilizator.getText())) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle(Constants.ErrorMessages.TitluEroare);
                    alert.setHeaderText(null);
                    alert.setContentText("Numele de utilizator " + inregistare_utilizator.getText() + " este deja folosit!");
                    alert.showAndWait();
                    // verificam sa nu fie parola prea scurta
                } else if (inregistare_parola.getText().length() < 8) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle(Constants.ErrorMessages.TitluEroare);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.PasswordTooShort);
                    alert.showAndWait();
                } else {
                    // in caz ca totul este o executam query
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
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean checkUsername(String username) {
        connect = database.connectDB();
        ResultSet result;
        // selectam din baza unsername acolo unde este egal cu cel trimis ca parametru
        String checkUsername = "Select username FROM users WHERE username = '"
                + username + "'";
        try {
            prepare = connect.prepareStatement(checkUsername);
            result = prepare.executeQuery();
            return result.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String[] questionList = {Constants.Questions.Question_1,
        Constants.Questions.Question_2,
        Constants.Questions.Question_3,
        Constants.Questions.Question_4,
        Constants.Questions.Question_5
    };
    // initailizam din baza intrebarile legate de securitate
    public void forgotPasswordQuestionList() {
        List<String> listQuestions = new ArrayList<>(Arrays.asList(questionList));

        ObservableList listData = FXCollections.observableArrayList(listQuestions);
        intrebari_fp_form.setItems(listData);
    }
    // am realizat dupa ca am cod duplicat
    public void requiredQuestionList() {
        List<String> listQuestions = new ArrayList<>(Arrays.asList(questionList));

        ObservableList listData = FXCollections.observableArrayList(listQuestions);
        inregistare_intrebare.setItems(listData);
    }
    // aici verificam pe care parte se afla side form ca sa stim daca dam swithc pe inregistrare sau logare
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
