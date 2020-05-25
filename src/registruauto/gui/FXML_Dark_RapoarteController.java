package registruauto.gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.models.Auto;
import registruauto.servicii.CreazaJasperRaport;

public class FXML_Dark_RapoarteController implements Initializable {
    
    private AutoDaoIntf autoDao;
    static FXMLMain3_DarkController main;
    private static final Logger LOG = Logger.getLogger(AutoDaoImpl.class.getName());

    public static void injectFXMLMain3_DarkController(FXMLMain3_DarkController main) {
        FXML_Dark_RapoarteController.main = main;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoDao = new AutoDaoImpl();
    }    
    
    @FXML
    private void handleGenereazaPDF(ActionEvent event) {
        try {
            Auto a = main.autoSelectat.getValue();
            CreazaJasperRaport.create(a.getId());
            CreazaJasperRaport.openReport();
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
}
