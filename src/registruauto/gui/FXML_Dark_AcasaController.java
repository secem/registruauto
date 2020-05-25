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
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.UtilDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.dao.impl.UtilDaoImpl;
import static registruauto.gui.FXML_Dark_AcasaAutoController.main;
import registruauto.models.Auto;

public class FXML_Dark_AcasaController implements Initializable {

    @FXML
    private Label nrMasini;
    @FXML
    private Label dataUltimiiVizite;
    @FXML
    private Label nrNotificari;

    private AutoDaoIntf autoDao;
    private UtilDaoIntf utilDao;

    public static void injectFXMLMain3_DarkController(FXMLMain3_DarkController main) {
        FXML_Dark_AcasaAutoController.main = main;
    }

    private static final Logger LOG = Logger.getLogger(AutoDaoImpl.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoDao = new AutoDaoImpl();
        utilDao = new UtilDaoImpl();
        getNrMasini();
        getNrNotificari();
        getUltimaVizita();
    }

    private void getNrMasini() {
        String nr = null;
        try {
            nr = String.valueOf(autoDao.countAuto());
        } catch (SQLException ex) {
            Logger.getLogger(FXML_Dark_AcasaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nrMasini.setText(nr);
    }

    private void getNrNotificari() {
        int notificariCount = 0;

        //parcurgem toate auto din dropdown si numaram numaram notificarile active dupa data
        for (Auto a : main.autoSelectat.getItems()) {
            int autoID = a.getId();
            try {
                LocalDate deservireAlert = autoDao.retrieveNotifications(autoID).getDeservireAlert();
                LocalDate asigurareAlert = autoDao.retrieveNotifications(autoID).getAsigurareAlert();
                LocalDate testareAlert = autoDao.retrieveNotifications(autoID).getTestareAlert();

                if (deservireAlert != null) {
                    deservireAlert = deservireAlert.plusYears(1);
                    Period period = Period.between(deservireAlert, LocalDate.now());
                    if (period.getYears() == 0 & period.getMonths() == 0 & deservireAlert.compareTo(LocalDate.now()) <= 0) {
                        notificariCount++;
                    } else if (period.getYears() == 0 & period.getMonths() == 0 & deservireAlert.compareTo(LocalDate.now()) > 0) {
                        notificariCount++;
                    }
                }

                if (asigurareAlert != null) {
                    asigurareAlert = asigurareAlert.plusYears(1);
                    Period period = Period.between(asigurareAlert, LocalDate.now());
                    if (period.getYears() == 0 & period.getMonths() == 0 & asigurareAlert.compareTo(LocalDate.now()) <= 0) {
                        notificariCount++;
                    } else if (period.getYears() == 0 & period.getMonths() == 0 & asigurareAlert.compareTo(LocalDate.now()) > 0) {
                        notificariCount++;
                    }
                }

                if (testareAlert != null) {
                    testareAlert = testareAlert.plusYears(1);
                    Period period = Period.between(testareAlert, LocalDate.now());
                    if (period.getYears() == 0 & period.getMonths() == 0 & testareAlert.compareTo(LocalDate.now()) <= 0) {
                        notificariCount++;
                    } else if (period.getYears() == 0 & period.getMonths() == 0 & testareAlert.compareTo(LocalDate.now()) > 0) {
                        notificariCount++;
                    }
                }

            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }

        }
        nrNotificari.setText(String.valueOf(notificariCount));
    }

    private void getUltimaVizita() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //formatam data
        String date = null;
        LocalDate localDate = null;
        try {
            localDate = utilDao.getLastVisitDate();
            Date dateDB = java.sql.Date.valueOf(localDate);
            date = formatter.format(dateDB);  //aplicam formatarea datei
        } catch (SQLException ex) {
            Logger.getLogger(FXML_Dark_AcasaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //data ultima data a vizitei este azi, atunci setam data pe cuvantul "astazi"
        if (localDate != null && localDate.compareTo(LocalDate.now()) == 0) {
            dataUltimiiVizite.setText("astăzi");
        } else {
            dataUltimiiVizite.setText(date);
        }

    }

}
