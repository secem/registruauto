package registruauto.gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.gui.util.AlertsUtil;
import registruauto.gui.util.NotificationType;
import static registruauto.gui.util.NotificationType.ASIGURARE;
import static registruauto.gui.util.NotificationType.DESERVIRE;
import static registruauto.gui.util.NotificationType.TESTARE;
import registruauto.models.Auto;

/**
 * FXML Controller class
 *
 * @author Sergio
 */
public class FXML_Dark_NotificariController implements Initializable {

    @FXML
    private JFXToggleButton toggleDeservireAnuala;
    @FXML
    private JFXDatePicker dateDeservireAnuala;
    @FXML
    private JFXToggleButton toggleAsigurareAuto;
    @FXML
    private JFXDatePicker dateAsigurareAuto;
    @FXML
    private JFXToggleButton toggleTestareAuto;
    @FXML
    private JFXDatePicker dateTestareTehnica;

    private AutoDaoIntf autoDao;

    static FXMLMain3_DarkController main;

    public static void injectFXMLMain3_DarkController(FXMLMain3_DarkController main) {
        FXML_Dark_NotificariController.main = main;
    }

    private static final Logger LOG = Logger.getLogger(AutoDaoImpl.class.getName());
    @FXML
    private Label dateDeservireAnualaText;
    @FXML
    private Label dateAsigurareAutoText;
    @FXML
    private Label dateTestareTehnicaText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoDao = new AutoDaoImpl();
        //get the ID from the selected auto
        Auto a = main.autoSelectat.getValue();
        int autoId = a.getId();
        //initialize the Notifications toggle buttons from DB
        try {
            initializeToggleButtons(autoId);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleToggleDeservireAnuala(ActionEvent event) {
        Auto auto = main.autoSelectat.getValue();
        if (toggleDeservireAnuala.selectedProperty().getValue() == false) {
            //will need to clear the value in the DB as well
            dateDeservireAnuala.setValue(null);
            dateDeservireAnuala.setDisable(true);
            dateDeservireAnualaText.setDisable(true);
        } else {
            dateDeservireAnuala.setDisable(false);
            dateDeservireAnualaText.setDisable(false);
        }
    }

    @FXML
    private void handleToggleAsigurareAuto(ActionEvent event) {
        if (toggleAsigurareAuto.selectedProperty().getValue() == false) {
            dateAsigurareAuto.setValue(null);
            dateAsigurareAuto.setDisable(true);
            dateAsigurareAutoText.setDisable(true);
        } else {
            dateAsigurareAuto.setDisable(false);
            dateAsigurareAutoText.setDisable(false);
        }
    }

    @FXML
    private void handleToggleTestareAuto(ActionEvent event) {
        if (toggleTestareAuto.selectedProperty().getValue() == false) {
            dateTestareTehnica.setValue(null);
            dateTestareTehnica.setDisable(true);
            dateTestareTehnicaText.setDisable(true);
        } else {
            dateTestareTehnica.setDisable(false);
            dateTestareTehnicaText.setDisable(false);
        }
    }

    //de analizat varianta de a modifica toate toggle butonurile aparte
    @FXML
    private void createNotificareDeservire(ActionEvent event) {
        Auto auto = main.autoSelectat.getValue();
        updateNotification(auto, dateDeservireAnuala, DESERVIRE);
    }

    @FXML
    private void createNotificareAsigurare(ActionEvent event) {
        Auto auto = main.autoSelectat.getValue();
        updateNotification(auto, dateAsigurareAuto, ASIGURARE);
    }

    @FXML
    private void createNotificareTestare(ActionEvent event) {
        Auto auto = main.autoSelectat.getValue();
        updateNotification(auto, dateTestareTehnica, TESTARE);
    }

    private void updateNotification(Auto auto, JFXDatePicker notificare, NotificationType notificationType) {
        AlertsUtil alert = new AlertsUtil();

        try {
            if (notificare.getValue() != null) {
                auto.setDeservireAlert(notificare.getValue());
            } else {
                auto.setDeservireAlert(null);
            }

            if (notificare.getValue() != null) {
                auto.setAsigurareAlert(notificare.getValue());
            } else {
                auto.setAsigurareAlert(null);
            }

            if (notificare.getValue() != null) {
                auto.setTestareAlert(notificare.getValue());
            } else {
                auto.setTestareAlert(null);
            }

            autoDao.saveNotification(auto, notificationType);
            alert.showMessage(Alert.AlertType.INFORMATION, "Succes", "Salvare cu succes!", "Notificare salvată cu succes!");

        } catch (SQLException ex) {
            alert.showMessage(Alert.AlertType.ERROR, "Eroare", "Eroare!", "Eroare la setarea notificării: " + ex.getMessage());
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void initializeToggleButtons(int id) throws SQLException {
        LocalDate deservireAlert = autoDao.retrieveNotifications(id).getDeservireAlert();
        LocalDate asigurareAlert = autoDao.retrieveNotifications(id).getAsigurareAlert();
        LocalDate testareAlert = autoDao.retrieveNotifications(id).getTestareAlert();

        if (deservireAlert != null) {
            toggleDeservireAnuala.selectedProperty().setValue(Boolean.TRUE);
            dateDeservireAnuala.setValue(deservireAlert);
        } else {
            toggleDeservireAnuala.selectedProperty().setValue(Boolean.FALSE);
            dateDeservireAnuala.setValue(null);
            dateDeservireAnuala.setDisable(true);
            dateDeservireAnualaText.setDisable(true);
        }

        if (asigurareAlert != null) {
            toggleAsigurareAuto.selectedProperty().setValue(Boolean.TRUE);
            dateAsigurareAuto.setValue(asigurareAlert);
        } else {
            toggleAsigurareAuto.selectedProperty().setValue(Boolean.FALSE);
            dateAsigurareAuto.setValue(null);
            dateAsigurareAuto.setDisable(true);
            dateAsigurareAutoText.setDisable(true);
        }

        if (testareAlert != null) {
            toggleTestareAuto.selectedProperty().setValue(Boolean.TRUE);
            dateTestareTehnica.setValue(testareAlert);
        } else {
            toggleTestareAuto.selectedProperty().setValue(Boolean.FALSE);
            dateTestareTehnica.setValue(null);
            dateTestareTehnica.setDisable(true);
            dateTestareTehnicaText.setDisable(true);
        }
    }

}
