package registruauto.gui;

import com.jfoenix.controls.JFXButton;
import registruauto.gui.archive.FXMLMain3Controller;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.StringConverter;
import registruauto.RegistruAuto;
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.LucrareDaoIntf;
import registruauto.dao.VizitaDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.dao.impl.LucrareDaoImpl;
import registruauto.dao.impl.VizitaDaoImpl;
import registruauto.gui.util.AlertsUtil;
import registruauto.gui.util.PaneType;
import registruauto.models.Auto;

public class FXMLMain3_DarkController implements Initializable {

    private AutoDaoIntf autoDao;
    private VizitaDaoIntf vizitaDao;
    private LucrareDaoIntf lucrareDao;
    private RegistruAuto registruAuto;

    @FXML
    private RowConstraints gridContent;
    @FXML
    private GridPane mainGrid;
    @FXML
    public JFXComboBox<Auto> autoSelectat;

    private static final Logger LOG = Logger.getLogger(FXMLMain3_DarkController.class.getName());
    @FXML
    private ImageView mainLogo;

    AlertsUtil alert = new AlertsUtil();
    @FXML
    private JFXButton garajMenu;
    @FXML
    private JFXButton notificariMenu;
    @FXML
    private JFXButton reparatiiMenu;
    @FXML
    private JFXButton rapoarteMenu;

    static FXML_Dark_GarajController garaj;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoDao = new AutoDaoImpl();
        vizitaDao = new VizitaDaoImpl();
        lucrareDao = new LucrareDaoImpl();
        showAutoSelectat();
        FXML_Dark_AcasaController.injectFXMLMain3_DarkController(this);
        FXML_Dark_AcasaAutoController.injectFXMLMain3_DarkController(this);
        FXML_Dark_GarajController.injectFXMLMain3_DarkController(this);
        FXML_Dark_ReparatiiController.injectFXMLMain3_DarkController(this);
        FXML_Dark_NotificariController.injectFXMLMain3_DarkController(this);
        FXML_Dark_RapoarteController.injectFXMLMain3_DarkController(this);

        //setam meniurile pe disabled daca nu este selectat un auto
        notificariMenu.setDisable(true);
        reparatiiMenu.setDisable(true);
        rapoarteMenu.setDisable(true);

    }

    public void setRegistruAuto(RegistruAuto registruAuto) {
        this.registruAuto = registruAuto;
    }

    @FXML
    private void handleBtnAcasa(ActionEvent event) throws IOException {

        if (autoSelectat.getValue() == null) {
            registruAuto.showPane(PaneType.ACASA);
        } else {
            registruAuto.showPane(PaneType.ACASA_AUTO);
        }
    }

    @FXML
    private void handleBtnGaraj(ActionEvent event) throws IOException {
        registruAuto.showPane(PaneType.GARAJ);
        //garaj.autoEditat = false; //setam flagul pentru auto editat ca false
    }

    @FXML
    private void handleNotificari(ActionEvent event) throws IOException {
        if (autoSelectat.getValue() != null) {
            registruAuto.showPane(PaneType.NOTIFICARI);
        } else {
            alert.showMessage(Alert.AlertType.INFORMATION, "Eroare", "Atenție!", "Selectați un automobil mai întâi!");
        }

    }

    @FXML
    private void handleReparatii(ActionEvent event) throws IOException {
        if (autoSelectat.getValue() != null) {
            registruAuto.showPane(PaneType.REPARATII);
        } else {
            alert.showMessage(Alert.AlertType.INFORMATION, "Eroare", "Atenție!", "Selectați un automobil mai întâi!");
        }

    }

    @FXML
    private void handleRapoarte(ActionEvent event) throws IOException {
        if (autoSelectat.getValue() != null) {
            registruAuto.showPane(PaneType.RAPOARTE);
        } else {
            alert.showMessage(Alert.AlertType.INFORMATION, "Eroare", "Atenție!", "Selectați un automobil mai întâi!");
        }

    }

    @FXML
    private void handleDespre(ActionEvent event) throws IOException {
        registruAuto.showPane(PaneType.DESPRE);
    }

    private void showLogo() throws IOException {

        Auto a = autoSelectat.getValue();
        String logo = "registruauto/gui/logo/no_logo.png";
        if (a != null) {
            try {
                logo = autoDao.returnLogo(a.getId());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLMain3Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Image image = new Image(logo);
        mainLogo.setImage(image);

    }

    //populate the combobox with values from DB
    public void showAutoSelectat() {
        autoSelectat.setItems(FXCollections.observableArrayList(autoDao.retrieve()));

        autoSelectat.setConverter(new StringConverter<Auto>() {
            @Override
            public String toString(Auto object) {
                if (object == null) {
                    return "Nu aveti automobile salvate...";
                } else {
                    return object.getModel();
                }
            }

            @Override
            public Auto fromString(String string) {
                return autoSelectat.getValue();
            }
        });
    }

    @FXML
    private void handleAutoSelectat(ActionEvent event) throws IOException {
        //go back to home page each time user selects another auto
        if (autoSelectat.getValue() == null) {
            registruAuto.showPane(PaneType.ACASA);
        } else {
            registruAuto.showPane(PaneType.ACASA_AUTO);
            //setam meniurile pe active daca este selectat un auto
            notificariMenu.setDisable(false);
            reparatiiMenu.setDisable(false);
            rapoarteMenu.setDisable(false);
        }
        //show the corresponding logo
        showLogo();
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }

}
