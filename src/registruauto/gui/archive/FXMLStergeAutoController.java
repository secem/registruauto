package registruauto.gui.archive;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.models.Auto;

/**
 *
 * @author Sergio
 */
public class FXMLStergeAutoController implements Initializable {

    private static final Logger LOG = Logger.getLogger(FXMLStergeAutoController.class.getName());

    AutoDaoIntf autoDao;

    @FXML
    private Label label;
    @FXML
    private ComboBox<Auto> autoDropDown;
    @FXML
    private Button stergeAuto;
    @FXML
    private Button butonIesire;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @FXML
    private void handleButonIesire(ActionEvent event) {
        Stage stage = (Stage) butonIesire.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoDao = new AutoDaoImpl();
        autoDropDown.setItems(FXCollections.observableArrayList(autoDao.retrieve()));

        //autoDropDown.setItems((autoDao.retrieve()));
        autoDropDown.setConverter(new StringConverter<Auto>() {
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
                //return autoDropDown.getItems().stream().filter(ap
                //        -> ap.getModel().equals(string)).findFirst().orElse(null);
                return autoDropDown.getValue();
            }
        });

    }

    @FXML
    private void handleStergeAuto(ActionEvent event) {
        Auto a = autoDropDown.getValue();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmare");
        alert.setHeaderText("Confirmare Stergere");
        alert.setContentText("Sunteti sigur ca vreti sa stergeti automobilul? \n(Automobilul va fi dezactivat dar disponibil in baza de date)");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {

            try {
                autoDao.remove(a);
                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                alertInfo.setTitle("Succes");
                alertInfo.setHeaderText("Stergere reusita!");
                alertInfo.setContentText("Automobilul " + a.getModel() + " a fost sters cu succes!");
                //alertInfo.getButtonTypes().removeAll(ButtonType.CANCEL);
                alertInfo.showAndWait();
            } catch (SQLException ex) {
                Alert alertInfo = new Alert(Alert.AlertType.ERROR);
                alertInfo.setTitle("Eroare");
                alertInfo.setHeaderText("Eroare");
                alertInfo.setContentText("Eroare la stergerea automobilului in baza de date: \n" + ex.getMessage());
                alertInfo.getButtonTypes().removeAll(ButtonType.CANCEL);
                alertInfo.showAndWait();
                Logger.getLogger(FXMLStergeAutoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            alert.close();
        }

        initialize(null, null);
    }

}
