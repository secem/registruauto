package registruauto.gui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.LucrareDaoIntf;
import registruauto.dao.VizitaDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.dao.impl.LucrareDaoImpl;
import registruauto.dao.impl.VizitaDaoImpl;
import registruauto.gui.util.AlertsUtil;
import registruauto.models.Auto;
import registruauto.models.Lucrare;
import registruauto.models.Vizita;

/**
 * FXML Controller class
 *
 * @author Sergio
 */
public class FXML_Dark_ReparatiiController implements Initializable {

    private static final Logger LOG = Logger.getLogger(FXML_Dark_ReparatiiController.class.getName());

    private AutoDaoIntf autoDao;
    private VizitaDaoIntf vizitaDao;
    private LucrareDaoIntf lucrareDao;

    @FXML
    private JFXDatePicker dataInreg;
    @FXML
    private JFXTextField parcurs;
    @FXML
    private JFXTextField numeService;
    @FXML
    private JFXTextField lucrareDeAdaugat;
    @FXML
    private JFXTextField costDeAdaugat;
    @FXML
    private Text costTotal;
    @FXML
    private TableView<Lucrare> tabelLucrari;
    @FXML
    private TableColumn<Lucrare, String> colLucrare;
    @FXML
    private TableColumn<Lucrare, String> colCost;

    ObservableList<Lucrare> lucrari;
    List<Lucrare> lista = new ArrayList<>();

    AlertsUtil alert = new AlertsUtil();

    static FXMLMain3_DarkController main;

    public static void injectFXMLMain3_DarkController(FXMLMain3_DarkController main) {
        FXML_Dark_ReparatiiController.main = main;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoDao = new AutoDaoImpl();
        vizitaDao = new VizitaDaoImpl();
        lucrareDao = new LucrareDaoImpl();
        costTotal.setText("0");
    }

    @FXML
    private void handleButonAdauga(ActionEvent event) {

        //fields validation
        if (lucrareFieldsValidation()
                == false || numberFieldValidation(costDeAdaugat) == false) {
            return;
        }

        //to make sure that we handle an eventual number with decimals we convert the string to decimal and then to integer since we don't really need the decimals to show
        int cost = (int) (Math.round(Double.parseDouble(costDeAdaugat.getText())));

        Lucrare lucrare = new Lucrare(lucrareDeAdaugat.getText(), cost);

        lista.add(lucrare);
        lucrari = FXCollections.observableArrayList(lista);
        //lucrari.addAll(lista);

        colLucrare.setCellValueFactory(
                new PropertyValueFactory<>("lucrare"));
        colCost.setCellValueFactory(
                new PropertyValueFactory<>("costLucrare"));
        tabelLucrari.setItems(lucrari);

        clearLucrareForm();
        handleCostTotal();
    }

    private void saveLucrare() throws SQLException {
        int autoID = main.autoSelectat.getValue().getId(); //aflam id-ul automobilului
        Vizita v = vizitaDao.retrieveLastVisitId(autoID);  //aflam id-ul vizitei
        int idVizita = v.getId(); //aflam id-ul vizitei
        System.out.println(v);
        System.out.println(idVizita);

        for (Lucrare lucr : tabelLucrari.getItems()) {
            lucr = new Lucrare(lucr.getLucrare(), lucr.getCostLucrare(), idVizita);
            lucrareDao.adauga(lucr);
        }

    }

    @FXML
    private void handleSalveaza(ActionEvent event) {
        //ne asiguram ca campurile au fost completate corespuzator
        if (mandatoryFieldsValidation() == false || numberFieldValidation(parcurs) == false) {
            return;
        }

        try {
            Vizita v = readForm();
            vizitaDao.save(v);
            alert.showMessage(Alert.AlertType.INFORMATION, "Succes", "Salvare cu succes!", "Vizita din data de " + v.getDataVizita() + " a fost salvată cu succes în baza de date!");
        } catch (SQLException ex) {
            alert.showMessage(Alert.AlertType.ERROR, "Eroare", "Eroare!", "Eroare la salvarea vizitei în baza de date: \n" + ex.getMessage());
            LOG.log(Level.SEVERE, null, ex);
        }
        clearVizitaForm();

        try {
            saveLucrare();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        clearLucrariTable();
        lista.clear();

    }

    private Vizita readForm() {
        LocalDate data = dataInreg.getValue();
        String parc = parcurs.getText();
        String numeServ = numeService.getText();
        int costT = Integer.parseInt(costTotal.getText().trim());

        Auto a = main.autoSelectat.getValue();
        int autoId = a.getId();

        Vizita v = new Vizita(data, parc, numeServ, costT, autoId);
        return v;
    }

    private void clearVizitaForm() {
        dataInreg.setValue(null);
        parcurs.clear();
        numeService.clear();
        tabelLucrari.refresh();
    }

    private void clearLucrareForm() {
        lucrareDeAdaugat.clear();
        costDeAdaugat.clear();
    }

    private void clearLucrariTable() {
        tabelLucrari.setItems(null);
        costTotal.setText("0");
    }

    private void handleCostTotal() {
        int cTotal = 0;
        for (Lucrare lucr : tabelLucrari.getItems()) {
            cTotal = cTotal + (int) lucr.getCostLucrare();
        }
        costTotal.setText(cTotal + "");
    }

    @FXML
    private void handleRenunta(ActionEvent event) {
        clearForm();
    }

    private void clearForm() {
        dataInreg.setValue(null);
        parcurs.clear();
        numeService.clear();
        lucrareDeAdaugat.clear();
        costDeAdaugat.clear();
        tabelLucrari.setItems(null);
        costTotal.setText("0");
    }

    private boolean numberFieldValidation(JFXTextField field) {
        boolean flag;
        //validam campurile sa contina doar cifre
        String number = field.getText();
        if ((field.getText().isEmpty() == false) & (Pattern.matches("[0-9]+", number) == false)) {
            alert.showMessage(Alert.AlertType.INFORMATION, "Eroare", "Atenție!", field.getPromptText() + " trebuie să conțină doar cifre!!");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    private boolean mandatoryFieldsValidation() {
        boolean flag;

        //validam ca toate campurile sa fie completate
        if (dataInreg.getValue() == null || parcurs.getText().isEmpty() || numeService.getText().isEmpty() || tabelLucrari.getItems().isEmpty()) {
            alert.showMessage(Alert.AlertType.INFORMATION, "Eroare", "Atenție!", "Completați toate câmpurile înainte să salvați!");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    private boolean lucrareFieldsValidation() {
        boolean flag;

        //validam ca toate campurile din lucrare sa fie completate
        if (lucrareDeAdaugat.getText().isEmpty() || costDeAdaugat.getText().isEmpty()) {
            alert.showMessage(Alert.AlertType.INFORMATION, "Eroare", "Atenție!", "Completați toate câmpurile înainte să adăugați!");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }
}
