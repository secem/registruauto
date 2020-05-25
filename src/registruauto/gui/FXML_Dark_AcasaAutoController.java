package registruauto.gui;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.UtilDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.models.Auto;

public class FXML_Dark_AcasaAutoController implements Initializable {

    @FXML
    private Label model;
    @FXML
    private Label anProducerii;
    @FXML
    private Label parcurs;
    @FXML
    private Label culoare;
    @FXML
    private Label nrInmatriculare;
    @FXML
    private Label codVin;
    @FXML
    private Label dataUltimeiViziteLaService;
    @FXML
    private Label nrNotificari;
    @FXML
    private Label nrTotalVizite;
    @FXML
    private VBox notificariSection;

    private AutoDaoIntf autoDao;
    private UtilDaoIntf utilDao;

    static FXMLMain3_DarkController main;
    Auto a = main.autoSelectat.getValue();
    int autoID = a.getId();
    int notificariCount;

    public static void injectFXMLMain3_DarkController(FXMLMain3_DarkController main) {
        FXML_Dark_AcasaAutoController.main = main;
    }

    private static final Logger LOG = Logger.getLogger(AutoDaoImpl.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoDao = new AutoDaoImpl();
        populateAuto();  //populate auto details
        getNrNotificari();
        getUltimaVizitaService();
        getNrVizite();
        showDeservireAlert();
        showAsigurareAlert();
        showTestareAlert();

    }

    private void populateAuto() {
        try {
            Auto auto = autoDao.findById(autoID);
            model.setText(auto.getModel());
            String anProd = Integer.toString(auto.getAnulProd());
            anProducerii.setText(anProd);
            parcurs.setText(auto.getParcurs());
            culoare.setText(auto.getCuloare());
            nrInmatriculare.setText(auto.getNrInmatr());
            codVin.setText(auto.getVin());
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void getNrNotificari() {
        nrNotificari.setText(String.valueOf(notificariCount));
    }

    private void getUltimaVizitaService() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //formatam data
        String date = null;
        LocalDate localDate = null;
        try {
            localDate = autoDao.getLastVisitServiceDate(autoID);

            if (localDate != null) {
                Date dateDB = java.sql.Date.valueOf(localDate);
                date = formatter.format(dateDB);  //aplicam formatarea datei
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXML_Dark_AcasaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //data ultima data a vizitei este azi, atunci setam data pe cuvantul "astazi"
        if (localDate != null && localDate.compareTo(LocalDate.now()) == 0) {
            dataUltimeiViziteLaService.setText("astăzi");
        } else if (localDate != null) {
            dataUltimeiViziteLaService.setText(date);
        } else {
            dataUltimeiViziteLaService.setText("Nu sunt înregistrări");
        }

    }

    private void getNrVizite() {
        String nr = null;
        try {
            nr = String.valueOf(autoDao.getNrViziteService(autoID));

            if (nr == null) {
                nr = "0";
            }

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        nrTotalVizite.setText(nr);
    }

    private void showDeservireAlert() {
        try {
            LocalDate deservireAlert = autoDao.retrieveNotifications(autoID).getDeservireAlert();
            if (deservireAlert != null) {
                deservireAlert = deservireAlert.plusYears(1);
                Period period = Period.between(deservireAlert, LocalDate.now());
                int diff = period.getDays();
                if (period.getYears() == 0 & period.getMonths() == 0 & deservireAlert.compareTo(LocalDate.now()) <= 0) {
                    notificariCount++;
                    showAlert("Data deservirii tehnice a expirat " + diff + " zile in urmă! Dacă ați efectuat-o deja, \nsetați o notificare nouă!");
                } else if (period.getYears() == 0 & period.getMonths() == 0 & deservireAlert.compareTo(LocalDate.now()) > 0) {
                    notificariCount++;
                    showAlert("Deservirea tehnică trebuie efectuată în " + (diff *= -1) + " zile!");
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        getNrNotificari();
    }

    private void showAsigurareAlert() {
        try {
            LocalDate asigurareAlert = autoDao.retrieveNotifications(autoID).getAsigurareAlert();
            if (asigurareAlert != null) {
                asigurareAlert = asigurareAlert.plusYears(1);
                Period period = Period.between(asigurareAlert, LocalDate.now());
                int diff = period.getDays();
                if (period.getYears() == 0 & period.getMonths() == 0 & asigurareAlert.compareTo(LocalDate.now()) <= 0) {
                    notificariCount++;
                    showAlert("Asigurarea auto a expirat " + diff + " zile in urmă! Dacă ați reîntocmit-o deja, \nsetați o notificare nouă!");
                } else if (period.getYears() == 0 & period.getMonths() == 0 & asigurareAlert.compareTo(LocalDate.now()) > 0) {
                    notificariCount++;
                    showAlert("Asigurarea auto expiră în " + (diff *= -1) + " zile!");
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        getNrNotificari();
    }

    private void showTestareAlert() {
        try {
            LocalDate testareAlert = autoDao.retrieveNotifications(autoID).getTestareAlert();
            if (testareAlert != null) {
                testareAlert = testareAlert.plusYears(1);
                Period period = Period.between(testareAlert, LocalDate.now());
                int diff = period.getDays();
                if (period.getYears() == 0 & period.getMonths() == 0 & testareAlert.compareTo(LocalDate.now()) <= 0) {
                    notificariCount++;
                    showAlert("Testarea tehnică a expirat " + diff + " zile in urmă! Dacă ați efectuat-o deja, \nsetați o notificare nouă!");
                } else if (period.getYears() == 0 & period.getMonths() == 0 & testareAlert.compareTo(LocalDate.now()) > 0) {
                    notificariCount++;
                    showAlert("Testarea tehnică expiră în " + (diff *= -1) + " zile!");
                }
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        getNrNotificari();
    }
    
    //show the Alert labels
    private void showAlert(String alertText) {
        Image image = new Image(getClass().getResourceAsStream("/registruauto/util/png/icons8_box_important_30px.png"));
        Label label = new Label(alertText);
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #B51E23;");
        label.setGraphic(new ImageView(image));
        notificariSection.getChildren().add(label);
    }

}
