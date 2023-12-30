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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import models.CarteData;
import models.data;

/**
 *
 * @author Florin
 */
public class mainFormController implements Initializable {

    @FXML
    private Button bord_btn;

    @FXML
    private Button clienti_btn;

    @FXML
    private TextArea inventar_Descriere;

    @FXML
    private ComboBox<?> inventar_Gen;

    @FXML
    private TextField inventar_ID;

    @FXML
    private TextField inventar_Nume;

    @FXML
    private TextField inventar_Pret;

    @FXML
    private TextField inventar_Stoc;

    @FXML
    private Button inventar_actualizeazaBtn;

    @FXML
    private Button inventar_addBtn;

    @FXML
    private Button inventar_btn;

    @FXML
    private Button inventar_curataBtn;

    @FXML
    private AnchorPane inventar_form;

    @FXML
    private ImageView inventar_image;

    @FXML
    private Button inventar_import_btn;

    @FXML
    private Button inventar_stergeBtn;

    @FXML
    private TableView<CarteData> inventar_tabel;

    @FXML
    private TableColumn<CarteData, String> inventar_tabel_Id;

    @FXML
    private TableColumn<CarteData, String> inventar_tabel_date;

    @FXML
    private TableColumn<CarteData, String> inventar_tabel_descriere;

    @FXML
    private TableColumn<CarteData, String> inventar_tabel_gen;

    @FXML
    private TableColumn<CarteData, String> inventar_tabel_nume;

    @FXML
    private TableColumn<CarteData, String> inventar_tabel_pret;

    @FXML
    private TableColumn<CarteData, String> inventar_tabel_stoc;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button meniu_btn;

    @FXML
    private Label numeUtilizator;

    @FXML
    private Button signOut_btn;

    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public ObservableList<CarteData> inventoryDataList() {
        ObservableList<CarteData> cartiDataLista = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM inventar_carti";

        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(sqlQuery);
            result = prepare.executeQuery();

            CarteData carteData;

            while (result.next()) {
                carteData = new CarteData(result.getInt("id"),
                        result.getString("nume_carte"),
                        result.getString("id_carte"),
                        result.getString("descriere_carte"),
                        result.getDouble("pret_carte"),
                        result.getDate("date"),
                        result.getString("gen_carte"),
                        result.getInt("stoc_carte"),
                        result.getString("image"));
                cartiDataLista.add(carteData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartiDataLista;
    }
    
    private ObservableList<CarteData> inventoryListData;
    public void InventoryShowData(){
        inventoryListData = inventoryDataList();
        
        inventar_tabel_Id.setCellValueFactory(new PropertyValueFactory<>("IdCarte"));
        inventar_tabel_nume.setCellValueFactory(new PropertyValueFactory<>("NumeCarte"));
        inventar_tabel_descriere.setCellValueFactory(new PropertyValueFactory<>("DescriereCarte"));
        inventar_tabel_pret.setCellValueFactory(new PropertyValueFactory<>("PretCarte"));
        inventar_tabel_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        inventar_tabel_gen.setCellValueFactory(new PropertyValueFactory<>("GenCarte"));
        inventar_tabel_stoc.setCellValueFactory(new PropertyValueFactory<>("StocCarte"));
        
        inventar_tabel.setItems(inventoryListData);
        
    }
    
    public void TipCartiInventarInitializare() {
        List<String> tipuriCarti = new ArrayList<>(Arrays.asList(Constants.TipurCarti));

        ObservableList listData = FXCollections.observableArrayList(tipuriCarti);

        inventar_Gen.setItems(listData);
    }

    public void signOut() {
        try {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(Constants.ErrorMessages.TitluEroare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.SignOut);
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // Ca sa ascundem main formu
                signOut_btn.getScene().getWindow().hide();

                // Ca sa linkuim pagina de login si sa o afisam
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle(Constants.Utils.APP_TITLE);

                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsername() {
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        numeUtilizator.setText(user);
    }

    // Se apeleaza atunci cand se intializeaza componenta
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        TipCartiInventarInitializare();
        InventoryShowData();
    }

}
