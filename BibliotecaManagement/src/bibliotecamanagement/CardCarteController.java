/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bibliotecamanagement;

import constants.Constants;
import database.database;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.CarteData;
import models.data;

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
    private Spinner<Integer> card_spinner;

    private CarteData carteData;
    private Image image;

    private String IdCarte;

    private SpinnerValueFactory<Integer> spin;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert alert;

    // aici setam datele din carduri
    public void setData(CarteData carteData) {
        this.carteData = carteData;

        IdCarte = carteData.getIdCarte();
        card_numeCarte.setText(carteData.getNumeCarte());
        card_pretCarte.setText(String.valueOf(carteData.getPretCarte()) + " RON");
        String path = "File:" + carteData.getImage();
        image = new Image(path, 210, 90, false, true);
        card_imageView.setImage(image);
        pret = carteData.getPretCarte();

    }
    private int cantitate;

    public void setQuantity() {
        // setam range pt spinner intre 0 si 100
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        // setam proprietatea spinerului din UI
        card_spinner.setValueFactory(spin);
    }
    private double pretTotal;
    private double pret;
     
    public void addBtn() {
        cantitate = card_spinner.getValue();
        Integer stoc = 0;
        String verificaStoc = "SELECT stoc_carte FROM inventar_carti WHERE nume_carte ='"
                + card_numeCarte.getText() + "'";
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(verificaStoc);
            result = prepare.executeQuery();
            if (result.next()) {
                stoc = result.getInt("stoc_carte");
            }
            // verificam daca avem stoc sau cantitate diferita de 0 sau stoc necesar pt cantitatea respectiva
            if (stoc == 0 || cantitate == 0 || stoc - cantitate < 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Constants.ErrorMessages.TitluEroare);
                alert.setHeaderText(null);
                alert.setContentText(Constants.ErrorMessages.CevaNuAMersBine);
                alert.showAndWait();
            }else if(stoc == 0){
                // in caz ca stocul este 0
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Constants.ErrorMessages.TitluEroare);
                alert.setHeaderText(null);
                alert.setContentText(Constants.ErrorMessages.NoStoc);
                alert.showAndWait();
            } 
            else {
                // scadem din stoc cantitatea adaugata pentru a calcula stocul nou
                Integer stocNou = stoc - cantitate;
                // selectam din baza id ul clientului
                String getClient_id = "SELECT id FROM users WHERE username ='"
                        + data.username + "'";
                Integer client_id = 0;
                prepare = connect.prepareStatement(getClient_id);
                result = prepare.executeQuery();
                if (result.next()) {
                    client_id = result.getInt("id");
                }
                // inseram datele in tabelul cu clienti practic asta reprezinta comanda individuala per carte
                String insertData = "INSERT INTO clientii (client_id, nume_carte, cantitate, pret, date, username)"
                        + " VALUES(?, ?, ?, ?, ?, ?)";
                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, String.valueOf(client_id));
                prepare.setString(2, card_numeCarte.getText());
                prepare.setString(3, String.valueOf(cantitate));
                pretTotal = (cantitate * pret);
                prepare.setString(4, String.valueOf(pretTotal));
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(5, String.valueOf(sqlDate));
                prepare.setString(6, data.username);
                prepare.executeUpdate();
                // actualizam stocul de carti 
                String updateStoc = "UPDATE inventar_carti SET stoc_carte = "
                        + String.valueOf(stocNou) + " WHERE id_carte ='"
                        + IdCarte + "'";
                prepare = connect.prepareStatement(updateStoc);
                prepare.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(Constants.ErrorMessages.TitluInformatie);
                alert.setHeaderText(null);
                alert.setContentText(Constants.ErrorMessages.AdaugatCuSucces);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setQuantity();
    }

}
