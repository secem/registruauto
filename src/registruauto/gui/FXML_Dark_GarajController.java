package registruauto.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTogglePane;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.gui.archive.FXMLAdaugaAutoController;
import registruauto.gui.archive.FXMLStergeAutoController;
import registruauto.gui.util.AlertsUtil;
import registruauto.models.Auto;

/**
 * FXML Controller class
 *
 * @author Sergio
 */
public class FXML_Dark_GarajController implements Initializable {

    private static final Logger LOG = Logger.getLogger(FXMLAdaugaAutoController.class.getName());

    AutoDaoIntf autoDao;
    String imageUrl;
    Boolean autoEditat = false;
    Boolean logoPersonal = false;

    @FXML
    private JFXTextField textModelAuto;
    @FXML
    private JFXTextField anulProd;
    @FXML
    private JFXTextField parcurs;
    @FXML
    private JFXTextField culoarea;
    @FXML
    private JFXTextField nrInmatr;
    @FXML
    private JFXTextField codVIN;
    @FXML
    private ImageView logo;
    @FXML
    private JFXTogglePane togglePane;
    @FXML
    private JFXButton browseLogo;

    static FXMLMain3_DarkController main;

    //Injection methods in order to access the methods and variables from main controller(source: https://coderanch.com/t/710811/java/access-elements-uploaded-FXML-file)
    public static void injectFXMLMain3_DarkController(FXMLMain3_DarkController main) {
        FXML_Dark_GarajController.main = main;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoDao = new AutoDaoImpl();
    }

    @FXML
    private void handleBrowseLogo(ActionEvent event) {
        logoPersonal = true;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecteaza un logo");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imageUrl = selectedFile.toURI().toString();
            Image image = new Image(imageUrl);
            logo.setImage(image);
        }
    }

    @FXML
    private void handleSaveButton(ActionEvent event) throws SQLException {
        //verificam daca toate campurile sunt completate corespunzator
        if (numberFieldValidation() == false || mandatoryFieldsValidation() == false) {
            return;
        }

        AlertsUtil alert = new AlertsUtil();
        Auto auto = readForm();
        Auto autoExistent = null;

        //check whether the auto exists in the DB or not
        if (autoEditat = true) {
            autoExistent = main.autoSelectat.getValue();
        } else {
            LOG.log(Level.INFO, "Automobilul nu exista un baza de date. Cream automobil nou.");
        }

        //if this is a new auto then do Save, if it already exists in the DB then Update it
        if (autoExistent == null) {
            try {
                auto.setLogo(imageUrl);
                autoDao.save(auto);
                alert.showMessage(Alert.AlertType.INFORMATION, "Succes", "Salvare cu succes!", "Automobilul " + auto.getModel() + " a fost salvat cu succes în baza de date!");
                clearForm();
            } catch (SQLException ex) {
                alert.showMessage(Alert.AlertType.ERROR, "Eroare", "Eroare!", "Eroare la salvarea automobilului în baza de date: \n" + ex.getMessage());
                LOG.log(Level.SEVERE, null, ex);
            }
        } else {
            handleUpdateAction(autoExistent);
            //auto = autoExistent;
        }
        togglePane.visibleProperty().set(false);
        //refresh the combobox
        main.showAutoSelectat();

    }

    private void handleUpdateAction(Auto existingAuto) {
        AlertsUtil alert = new AlertsUtil();

        try {
            //existingAuto = autoDao.findByVin(codVIN.getText());
            existingAuto = main.autoSelectat.getValue();
            int id = existingAuto.getId();
            Auto updatedAuto = readForm();

            existingAuto.setModel(updatedAuto.getModel());
            existingAuto.setAnulProd(updatedAuto.getAnulProd());
            existingAuto.setParcurs(updatedAuto.getParcurs());
            existingAuto.setCuloare(updatedAuto.getCuloare());
            existingAuto.setNrInmatr(updatedAuto.getNrInmatr());
            existingAuto.setVin(updatedAuto.getVin());

            //Setam logo. Daca logoul este modificat atunci il folosim pe el. Altfel il folosim pe cel din DB
            if (imageUrl != null) {
                existingAuto.setLogo(imageUrl);
            } else {
                existingAuto.setLogo(autoDao.returnLogo(id));
            }

            autoDao.update(existingAuto);
            alert.showMessage(Alert.AlertType.INFORMATION, "Succes", "Actualizare cu succes!", "Automobilul " + updatedAuto.getModel() + " a fost actualizat cu succes în baza de date!");

        } catch (SQLException ex) {
            alert.showMessage(Alert.AlertType.ERROR, "Eroare", "Eroare!", "Eroare la actualizarea automobilului în baza de date: \n" + ex.getMessage());
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButonRenunta(ActionEvent event) {
        clearForm();
        togglePane.visibleProperty().set(false);
    }

    private Auto readForm() {
        String model = textModelAuto.getText();
        int an = Integer.parseInt(anulProd.getText());
        String parc = parcurs.getText();
        String culoare = culoarea.getText();
        String numar = nrInmatr.getText();
        String vin = codVIN.getText();

        Auto a = new Auto(model, an, parc, culoare, numar, vin, true);
        return a;
    }

    private void clearForm() {
        textModelAuto.clear();
        anulProd.clear();
        parcurs.clear();
        culoarea.clear();
        nrInmatr.clear();
        codVIN.clear();
        logo.setImage(null);
    }

    @FXML
    private void handleStergeAuto(ActionEvent event) {
        AlertsUtil alert = new AlertsUtil();

        if (main.autoSelectat.getValue() != null) {
            stergeAuto();
        } else {
            alert.showMessage(Alert.AlertType.ERROR, "Eroare", "Eroare!", "Selectați un automobil mai întâi!");
        }

    }

    private void stergeAuto() {
        Auto a = main.autoSelectat.getValue();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmare");
        confirmationAlert.setHeaderText("Confirmare Stergere");
        confirmationAlert.setContentText("Sunteti sigur ca vreti sa stergeti automobilul? \n(Automobilul va fi dezactivat dar disponibil in baza de date)");
        DialogPane dialogPane = confirmationAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("util/myDialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.get() == ButtonType.OK) {

            try {
                autoDao.remove(a);
                AlertsUtil alert = new AlertsUtil();
                alert.showMessage(Alert.AlertType.INFORMATION, "Succes", "Ștergere reusită!", "Automobilul " + a.getModel() + " a fost șters cu succes!");
            } catch (SQLException ex) {
                AlertsUtil alert = new AlertsUtil();
                alert.showMessage(Alert.AlertType.ERROR, "Eroare", "Eroare!", "Eroare la ștergerea automobilului din baza de date: \n" + ex.getMessage());
                Logger.getLogger(FXMLStergeAutoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            main.showAutoSelectat();
        } else {
            confirmationAlert.close();
        }

    }

    @FXML
    private void handleAdaugaAuto(ActionEvent event) {
        autoEditat = false;
        clearForm();
        togglePane.visibleProperty().set(true);
    }

    @FXML
    private void handleEditAuto(ActionEvent event) throws SQLException {
        AlertsUtil alert = new AlertsUtil();
        clearForm();
        if (main.autoSelectat.getValue() != null) {
            togglePane.visibleProperty().set(true);
            autoEditat = true;
            populateAuto();
        } else {
            alert.showMessage(Alert.AlertType.ERROR, "Eroare", "Eroare!", "Selectați un automobil mai întâi!");
        }

    }

    private void populateAuto() throws SQLException {
        int autoId = main.autoSelectat.getValue().getId();
        Auto auto = autoDao.findById(autoId);

        textModelAuto.setText(auto.getModel());
        String anProd = Integer.toString(auto.getAnulProd());
        anulProd.setText(anProd);
        parcurs.replaceSelection(auto.getParcurs());
        culoarea.setText(auto.getCuloare());
        nrInmatr.setText(auto.getNrInmatr());
        codVIN.setText(auto.getVin());
        //show logo
        Image image = new Image(autoDao.returnLogo(autoId));
        logo.setImage(image);
    }

    private boolean numberFieldValidation() {
        AlertsUtil alert = new AlertsUtil();
        boolean flag;

        //validam ca Anul producerii sa contina doar cifre
        String number = anulProd.getText();
        if ((anulProd.getText().isEmpty() == false) & (Pattern.matches("[0-9]+", number) == false)) {
            alert.showMessage(Alert.AlertType.INFORMATION, "Eroare", "Eroare!", "Anul producerii trebuie să conțină doar cifre!!");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    private boolean mandatoryFieldsValidation() {
        AlertsUtil alert = new AlertsUtil();
        boolean flag;

        //validam ca toate campurile sa fie completate
        if (textModelAuto.getText().isEmpty() || anulProd.getText().isEmpty() || parcurs.getText().isEmpty() || culoarea.getText().isEmpty() || nrInmatr.getText().isEmpty() || codVIN.getText().isEmpty() || logo.getImage() == null) {
            alert.showMessage(Alert.AlertType.INFORMATION, "Eroare", "Eroare!", "Completați toate câmpurile înainte să salvați!");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    @FXML
    private void handleAutomaticLogo() {

        String marca = textModelAuto.getText().toLowerCase();
        if (logoPersonal == false & marca.length() > 0 & marca.contains(" ")) { //make sure: no logo is already set, the field contains text and it has a space
            String marcaFirstWord = marca.substring(0, marca.indexOf(' '));

            File dir = new File("src/registruauto/gui/logo/logos");
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    String fileName = child.getName();
                    String fileNameFirstWord = fileName.substring(0, fileName.indexOf('-'));

                    if (fileNameFirstWord.contains(marcaFirstWord)) {
                        imageUrl = child.toURI().toString();
                        Image image = new Image(imageUrl);
                        logo.setImage(image);
                    }
                }
            }
        }

    }

}
