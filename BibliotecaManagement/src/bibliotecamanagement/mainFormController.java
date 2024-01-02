/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bibliotecamanagement;

import constants.Constants;
import database.database;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
    
     @FXML
    private Button meniu_DeleteBtn;

    @FXML
    private Button meniu_PayBtn;

    @FXML
    private ScrollPane meniu_ScrollPane;

    @FXML
    private TextField meniu_cantitate;

    @FXML
    private TableColumn<?, ?> meniu_col_Pret;

    @FXML
    private TableColumn<?, ?> meniu_col_cantitate;

    @FXML
    private TableColumn<?, ?> meniu_col_numeCarte;

    @FXML
    private AnchorPane meniu_form;

    @FXML
    private GridPane meniu_gridPane;

    @FXML
    private TableView<?> meniu_tableView;

    @FXML
    private Label meniu_total;

    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;
    
    private ObservableList<CarteData> cardListData;

    // practic aici unim toate informatiile pentru a le afisa din tabel
    public ObservableList<CarteData> inventoryDataList() {
        ObservableList<CarteData> cartiDataLista = FXCollections.observableArrayList();
        // selectam tot din tabelul inventar_carti
        String sqlQuery = "SELECT * FROM inventar_carti";
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(sqlQuery);
            result = prepare.executeQuery();

            CarteData carteData;
            //cat timp avem date in rezultat vom crea un nou obiect de tipul carte pe care il adaugam in lista cu toate cartile
            while (result.next()) {
                // mapam coloanele din baza la proprietatile obiectului din constructor
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

    public void inventarAddBtn() {
        // facem o verificare a filedurilor daca sunt goale 
        if (inventar_ID.getText().isEmpty()
                || inventar_Nume.getText().isEmpty()
                || inventar_Pret.getText().isEmpty()
                || inventar_Stoc.getText().isEmpty()
                || inventar_Gen.getSelectionModel().getSelectedItem() == null
                || inventar_Descriere.getText().isEmpty()
                || data.path == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.ErrorMessages.TitluEroare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.BlankFields);
            alert.showAndWait();
        } else {
            // verficam daca exista deja o carte cu aceelasi id
            String verificareId = "SELECT id_carte FROM inventar_carti WHERE id_carte='"
                    + inventar_ID.getText() + "'";
            connect = database.connectDB();
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(verificareId);
                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle(Constants.ErrorMessages.TitluEroare);
                    alert.setHeaderText(null);
                    alert.setContentText(inventar_ID.getText() + "este deja utilizat!");
                    alert.showAndWait();
                } else {
                    // inseram datele din fielduri in tabel
                    String insert = "INSERT INTO inventar_carti "
                            + "(id_carte, nume_carte, descriere_carte, pret_carte, date, gen_carte, image, stoc_carte) "
                            + "VALUES(?,?,?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insert);
                    prepare.setString(1, inventar_ID.getText());
                    prepare.setString(2, inventar_Nume.getText());
                    prepare.setString(3, inventar_Descriere.getText());
                    prepare.setString(4, inventar_Pret.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.setString(6, (String) inventar_Gen.getSelectionModel().getSelectedItem());

                    String imagePath = data.path;
                    imagePath.replace("\\", "\\\\");
                    prepare.setString(7, imagePath);
                    prepare.setString(8, inventar_Stoc.getText());
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(Constants.ErrorMessages.TitluInformatie);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.CarteaAdaugata);
                    alert.showAndWait();
                    // dam reload la informatii
                    InventoryShowData();
                    inventarClearBtn();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // aici ne ocupam de setarea informatiilor din cardurile cu cartii
    public ObservableList<CarteData> meniuGetData(){
        return cardListData;
    }
    
    public void inventarSelectBook() {
        // selectam din tabel respectiva carte si completam informatiile ei in field-uri
        CarteData carte = inventar_tabel.getSelectionModel().getSelectedItem();
        int number = inventar_tabel.getSelectionModel().getSelectedIndex();
        if ((number - 1) < -1) {
            return;
        }
        inventar_ID.setText(carte.getIdCarte());
        inventar_Nume.setText(carte.getNumeCarte());
        inventar_Descriere.setText(carte.getDescriereCarte());
        inventar_Pret.setText(String.valueOf(carte.getPretCarte()));
        inventar_Stoc.setText(String.valueOf(carte.getStocCarte()));
        data.path = carte.getImage();
        String path = "File:" + carte.getImage();
        data.date = String.valueOf(carte.getDate());
        data.id = carte.getId();
        image = new Image(path, 120, 140, false, true);
        inventar_image.setImage(image);
    }

    public void inventarUpdateBtn() {
        // facem aceeasi verificare ca atunci cand adaugam o carte
        if (inventar_ID.getText().isEmpty()
                || inventar_Nume.getText().isEmpty()
                || inventar_Pret.getText().isEmpty()
                || inventar_Stoc.getText().isEmpty()
                || inventar_Gen.getSelectionModel().getSelectedItem() == null
                || inventar_Descriere.getText().isEmpty()
                || data.path == null || data.id == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.ErrorMessages.TitluEroare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.BlankFields);
            alert.showAndWait();
        } else {
            // cream qury de actualizare cu datele din fields
            String path = data.path;
            path = path.replace("\\", "\\\\");
            String updateCarte = "UPDATE inventar_carti SET "
                    + "id_carte ='" + inventar_ID.getText()
                    + "', nume_carte='" + inventar_Nume.getText()
                    + "', descriere_carte='" + inventar_Descriere.getText()
                    + "', pret_carte='" + inventar_Pret.getText()
                    + "', gen_carte='" + (String) inventar_Gen.getSelectionModel().getSelectedItem()
                    + "', stoc_carte='" + inventar_Stoc.getText()
                    + "', image='" + path
                    + "', date='" + data.date
                    + "' WHERE id = " + data.id;
            connect = database.connectDB();
            try {
                // dialog de confirmare
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle(Constants.ErrorMessages.Confirmare);
                alert.setHeaderText(null);
                alert.setContentText(Constants.ErrorMessages.ActualizareCarte);
                Optional<ButtonType> option = alert.showAndWait();
                // daca este confirmat executam query, dam refresh la data si facem clear la fields
                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateCarte);
                    prepare.executeUpdate();
                    InventoryShowData();
                    inventarClearBtn();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(Constants.ErrorMessages.TitluInformatie);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.CarteActualizata);
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle(Constants.ErrorMessages.TitluEroare);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.ActualizareAnulata);
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // stergem cartea din baza de date la ID respectiv
    public void inventarDeleteBtn() {
        //verificarea initiala daca id exista
        if ( data.id == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle(Constants.ErrorMessages.TitluEroare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.BlankFields);
            alert.showAndWait();
        } else {
            //popup de confirmare
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(Constants.ErrorMessages.Confirmare);
            alert.setHeaderText(null);
            alert.setContentText(Constants.ErrorMessages.StergereCarte);
            Optional<ButtonType> option = alert.showAndWait();
            // daca a fost confirmata stergerea
            if (option.get().equals(ButtonType.OK)) {
                String deleteCarte = "DELETE FROM inventar_carti WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteCarte);
                    prepare.executeUpdate();
                    // sa dam refresh la data
                    InventoryShowData();
                    // sa facem clear la fielduri
                    inventarClearBtn();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(Constants.ErrorMessages.TitluInformatie);
                    alert.setHeaderText(null);
                    alert.setContentText(Constants.ErrorMessages.CarteaStearsa);
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // daca nu a fost confirmata stergerea
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(Constants.ErrorMessages.TitluEroare);
                alert.setHeaderText(null);
                alert.setContentText(Constants.ErrorMessages.StergereAnulata);
                alert.showAndWait();
            }

        }
    }

    // facem clear la toate fieldurile
    public void inventarClearBtn() {
        inventar_ID.setText(Constants.Utils.EMPTY_STRING);
        inventar_Nume.setText(Constants.Utils.EMPTY_STRING);
        inventar_Descriere.setText(Constants.Utils.EMPTY_STRING);
        inventar_Pret.setText(Constants.Utils.EMPTY_STRING);
        inventar_Gen.getSelectionModel().clearSelection();
        inventar_Stoc.setText(Constants.Utils.EMPTY_STRING);
        data.path = Constants.Utils.EMPTY_STRING;
        inventar_image.setImage(null);
        // resetam si datele din variabila comuna data
        data.id = 0;
        data.path = Constants.Utils.EMPTY_STRING;

    }

    // butonul de import pt a adauga imaginea cartii
    public void inventarImportBtn() {
        FileChooser openFile = new FileChooser();
        // practic ii setam o regula sa nu poti adauga decat png sau jpg
        openFile.getExtensionFilters().add(new ExtensionFilter("Deschide imaginea", "*png", "*jpg"));
        // deschidem o fereastra de dialog sa alegem respectivul file din pc
        File file = openFile.showOpenDialog(main_form.getScene().getWindow());
        // daca file este ales si nu e nul ii setam pathu absolut
        if (file != null) {
            data.path = file.getAbsolutePath();
            // aici cream o imagine cu ajutorul pathului de aia il convertim la string, ii setam rezolutia dorita, sa nu isi pastreze aspect ratio si sa fie enabled smoothing
            image = new Image(file.toURI().toString(), 120, 140, false, true);
            inventar_image.setImage(image);
        }
    }

    private ObservableList<CarteData> inventoryListData;

    public void InventoryShowData() {
        inventoryListData = inventoryDataList();
        // aici facem legatura dintre coloana din tabel si proprietatea din obiect care ii corespunde
        inventar_tabel_Id.setCellValueFactory(new PropertyValueFactory<>("IdCarte"));
        inventar_tabel_nume.setCellValueFactory(new PropertyValueFactory<>("NumeCarte"));
        inventar_tabel_descriere.setCellValueFactory(new PropertyValueFactory<>("DescriereCarte"));
        inventar_tabel_pret.setCellValueFactory(new PropertyValueFactory<>("PretCarte"));
        inventar_tabel_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        inventar_tabel_gen.setCellValueFactory(new PropertyValueFactory<>("GenCarte"));
        inventar_tabel_stoc.setCellValueFactory(new PropertyValueFactory<>("StocCarte"));
        // aici setam obiectul care va popula cu informatii tabelul
        inventar_tabel.setItems(inventoryListData);

    }

    public void TipCartiInventarInitializare() {
        // initializam lista din drop down pe care o aveam din fisierul de constante
        List<String> tipuriCarti = new ArrayList<>(Arrays.asList(Constants.TipurCarti));

        ObservableList listData = FXCollections.observableArrayList(tipuriCarti);
        // setam in drop down lista cu alegerile
        inventar_Gen.setItems(listData);
    }

    public void signOut() {
        try {
            // pop up de confirmare daca vrem sa ne delogam
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(Constants.ErrorMessages.Confirmare);
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
        // luam username din variabila comuna data
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        // setam valoarea in label
        numeUtilizator.setText(user);
    }

    // Se apeleaza atunci cand se intializeaza componenta
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setam username conectat
        displayUsername();
        // setam variantele in drop down cu tipul cartii
        TipCartiInventarInitializare();
        // luam datele din baza de date
        InventoryShowData();
    }

}
