package registruauto.gui.archive;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.models.Auto;

/**
 *
 * @author Sergio
 */
public class FXMLMain2Controller implements Initializable {

    private static final Logger LOG = Logger.getLogger(FXMLMain2Controller.class.getName());

    AutoDaoIntf autoDao;

    @FXML
    private Label label;
    @FXML
    private MenuItem adaugaAuto;
    @FXML
    private Button genereazaPdf;
    @FXML
    private Button adaugaVizita;
    @FXML
    private MenuItem stergeAuto;
    @FXML
    private Button saveButton;
    @FXML
    private MenuItem butonIesire;
    @FXML
    private Button butonIesireMain;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TitledPane blocVizitaService;
    @FXML
    private ComboBox<Auto> autoSelectat;
    @FXML
    private MenuItem butonDespre;
    @FXML
    private ImageView mainLogo;
    @FXML
    private Button butonAdauga;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @FXML
    private void handleAdaugaAuto(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/registruauto/gui/FXMLAdaugaAuto.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Adaugare Automobil");
            stage.setScene(new Scene(root, 661, 389));
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleStergeAuto(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/registruauto/gui/FXMLStergeAuto.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Stergere Automobil");
            stage.setScene(new Scene(root, 661, 181));
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleButonIesire(ActionEvent event) {
        Stage stage = (Stage) butonIesireMain.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleMenuIesire(ActionEvent event) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleVizitaService(ActionEvent event) {
        blocVizitaService.setExpanded(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoDao = new AutoDaoImpl();
        autoSelectat.setItems(FXCollections.observableArrayList(autoDao.retrieve()));
    }

    @FXML
    private void handleAutoSelectat(ActionEvent event) {
        //to do
    }

    @FXML
    private void handleButonDespre(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Despre");
        alert.setHeaderText("Despre program");
        alert.setContentText("Program creat de Sergiu Cemirtan in cadrul cursului \"Java SE\" la Iucosoft in anul 2020.");
        alert.showAndWait();

    }

    @FXML
    private void handleGenereazaPDF(ActionEvent event) {
    }

    @FXML
    private void handleMainLogo(MouseEvent event) {
    }

    @FXML
    private void handleButonAdauga(ActionEvent event) {
    }

}
