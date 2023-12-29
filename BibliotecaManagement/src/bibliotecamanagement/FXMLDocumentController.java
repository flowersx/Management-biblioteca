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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javax.naming.spi.DirStateFactory.Result;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button back_button_fp;

    @FXML
    private PasswordField confirmare_parola_fp;

    @FXML
    private AnchorPane forgot_password;

    @FXML
    private Button inregistare_buton;

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

    @FXML
    void butonInregistrare(ActionEvent event) {

    }

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

            forgot_password.setVisible(false);
            login_form.setVisible(true);
            reset_password.setVisible(false);
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

            forgot_password.setVisible(false);
            login_form.setVisible(true);
            reset_password.setVisible(false);

        });

        slider.play();
    }

    public void switchForgotPassword() {
        forgot_password.setVisible(true);
        login_form.setVisible(false);

        forgotPasswordQuestionList();
    }

    public void proceedButton() {
        if (raspuns_fp_form.getText().isEmpty()
                || utilizator_fp.getText().isEmpty()
                || intrebari_fp_form.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.ErrorMessages.TitluEroare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.BlankFields);
            alert.showAndWait();
        } else {
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
                    reset_password.setVisible(true);
                    forgot_password.setVisible(false);
                } else {
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
        if (login_utilizator.getText().isEmpty() || login_parola.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.ErrorMessages.TitluEroare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.BlankFields);
            alert.showAndWait();
        } else {
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
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(Constants.ErrorMessages.TitluInformatie);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.LoginCont);
                    alert.showAndWait();
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
                if (checkUsername(inregistare_utilizator.getText())) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle(Constants.ErrorMessages.TitluEroare);
                    alert.setHeaderText(null);
                    alert.setContentText(inregistare_utilizator.getText() + "is already taken!");
                    alert.showAndWait();
                } else if (inregistare_parola.getText().length() < 8) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle(Constants.ErrorMessages.TitluEroare);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.PasswordTooShort);
                    alert.showAndWait();
                } else {
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
        String checkUsername = "Select username FROM users WHERE username = '"
                + username + "'";
        try {
            prepare = connect.prepareStatement(checkUsername);
            result = prepare.executeQuery();
            return result.next();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private String[] questionList = {Constants.Questions.Question_1,
        Constants.Questions.Question_2,
        Constants.Questions.Question_3,
        Constants.Questions.Question_4,
        Constants.Questions.Question_5
    };

    public void forgotPasswordQuestionList() {
        List<String> listQuestions = new ArrayList<>(Arrays.asList(questionList));

        ObservableList listData = FXCollections.observableArrayList(listQuestions);
        intrebari_fp_form.setItems(listData);
    }

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
