package registruauto.gui.archive;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.models.Auto;

/**
 *
 * @author Sergio
 */
public class FXMLAdaugaAutoController implements Initializable {

    private static final Logger LOG = Logger.getLogger(FXMLAdaugaAutoController.class.getName());

    AutoDaoIntf autoDao;
    String imageUrl;
    
    @FXML
    private Label label;
    @FXML
    private Button saveButton;
    @FXML
    private TextField textModelAuto;
    @FXML
    private TextField anulProd;
    @FXML
    private TextField parcurs;
    @FXML
    private TextField culoarea;
    @FXML
    private TextField nrInmatr;
    @FXML
    private TextField codVIN;
    @FXML
    private Button browseLogo;
    @FXML
    private Button butonIesire;
    @FXML
    private ImageView logo;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @FXML
    private void handleButonIesire(ActionEvent event) {
        Stage stage = (Stage) butonIesire.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleSaveButton(ActionEvent event) {
        try {
            Auto a = readForm();
            
            a.setLogo(imageUrl);
            autoDao.save(a);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText("Salvare cu succes!");
            alert.setContentText("Automobilul " + a.getModel() + " a fost salvat cu succes in baza de date!");
            alert.showAndWait();
            
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare");
            alert.setHeaderText("Eroare");
            alert.setContentText("Eroare la salvarea automobilului in baza de date: \n"+ex.getMessage());
            alert.showAndWait();
            
            LOG.log(Level.SEVERE, null, ex);
        }
        clearForm();
        
        
        
    }
    
    @FXML
    private void handleBrowseLogo(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecteaza un logo");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        
        imageUrl = selectedFile.toURI().toString();
        
        Image image = new Image(imageUrl);
        logo.setImage(image);
                
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       autoDao = new AutoDaoImpl();
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
    }

    @FXML
    private void setLogo(MouseEvent event) {
                          
        
    }

}
