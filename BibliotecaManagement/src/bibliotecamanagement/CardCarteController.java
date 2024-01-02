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
import java.util.ResourceBundle;
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

        card_numeCarte.setText(carteData.getNumeCarte());
        card_pretCarte.setText(String.valueOf(carteData.getPretCarte()) + " RON");
        String path = "File:" + carteData.getImage();
        image = new Image(path, 210, 90, false, true);
        card_imageView.setImage(image);

    }
    private int cantitate;

    public void setQuantity() {
        // setam range pt spinner intre 0 si 100
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        // setam proprietatea spinerului din UI
        card_spinner.setValueFactory(spin);
    }

    public void addBtn() {
        cantitate = card_spinner.getValue();
        Integer stoc = 0;
        String verificaStoc = "SELECT stoc_carte FROM inventar_carti WHERE nume_carte ='"
                + card_numeCarte.getText() + "'";
        connect = database.connectDB();

        try {
            prepare = connect.prepareCall(verificaStoc);
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
            }else {
                // TO DO fa si tu un tabel pt clienti si tot procedeul de cumparare a cartii
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
