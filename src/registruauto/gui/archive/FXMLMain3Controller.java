package registruauto.gui.archive;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import registruauto.dao.AutoDaoIntf;
import registruauto.dao.LucrareDaoIntf;
import registruauto.dao.VizitaDaoIntf;
import registruauto.dao.impl.AutoDaoImpl;
import registruauto.dao.impl.LucrareDaoImpl;
import registruauto.dao.impl.VizitaDaoImpl;
import registruauto.gui.FXMLMain3_DarkController;
import registruauto.gui.FXML_Dark_GarajController;
import registruauto.models.Auto;
import registruauto.models.Lucrare;
import registruauto.models.Vizita;
import registruauto.servicii.CreazaJasperRaport;

/**
 *
 * @author Sergio
 */
public class FXMLMain3Controller implements Initializable {

    private static final Logger LOG = Logger.getLogger(FXMLMain3Controller.class.getName());

    private AutoDaoIntf autoDao;
    private VizitaDaoIntf vizitaDao;
    private LucrareDaoIntf lucrareDao;

    private Label label;
    private Button butonIesireMain;
    private MenuBar menuBar;
    private TitledPane blocVizitaService;
    private ComboBox<Auto> autoSelectat;
    private ImageView mainLogo;
    private TextField lucrareDeAdaugat;
    private TextField costDeAdaugat;
    private TableView<Lucrare> tabelLucrari;
    private TableColumn<Lucrare, String> colLucrare;
    private TableColumn<Lucrare, String> colCost;

    private ObservableList<Lucrare> lucrari;
    List<Lucrare> lista = new ArrayList<>();

    private DatePicker dataInreg;
    private TextField parcurs;
    private TextField numeService;
    private Text costTotal;
    


    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

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

    private void handleButonIesire(ActionEvent event) {
        Stage stage = (Stage) butonIesireMain.getScene().getWindow();
        stage.close();
    }

    private void handleMenuIesire(ActionEvent event) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    private void handleVizitaService(ActionEvent event) {
        blocVizitaService.setExpanded(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoDao = new AutoDaoImpl();
        vizitaDao = new VizitaDaoImpl();
        lucrareDao = new LucrareDaoImpl();
        costTotal.setText("0");
        showAutoSelectat();

    }

    private void showAutoSelectat() {
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
                //return autoSelectat.getItems().stream().filter(ap
                //        -> ap.getModel().equals(string)).findFirst().orElse(null);
                return autoSelectat.getValue();
            }
        });
    }

    private void handleButonDespre(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Despre");
        alert.setHeaderText("Despre program");
        alert.setContentText("Program creat de Sergiu Cemirtan in cadrul cursului \"Java SE\" la Iucosoft in anul 2020.");
        alert.showAndWait();

    }

    private void handleGenereazaPDF(ActionEvent event) {
        try {
            Auto a = autoSelectat.getValue();
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

    private void showLogo(ActionEvent event) {
        Auto a = autoSelectat.getValue();
        System.out.println(a);
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

        //de facut un switch case pentru afiasarea logoului in functie de:
        //1. de comparat primul cuvint din modelul auto cu denumirea fisierului din mapa cu logouri existente. Regex example: /^(ford)/i
        //2. de avut posibilitatea de incarcat si afisat logoul propriu
        //3. altfel de afisat logoul default no_logo.png
    }

    private void handleButonAdauga() {
        //to make sure that we handle an eventual numaber with decimals we convert the string to decimal and then to integer since we don't really need the decimals to show
        int cost = (int) (Math.round(Double.parseDouble(costDeAdaugat.getText())));
        Lucrare lucrare = new Lucrare(lucrareDeAdaugat.getText(), cost);
        lista.add(lucrare);
        lucrari = FXCollections.observableArrayList(lista);
        //lucrari.addAll(lista);

        colLucrare.setCellValueFactory(new PropertyValueFactory<>("lucrare"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("costLucrare"));
        tabelLucrari.setItems(lucrari);
        //tabelLucrari.getItems().setAll(lista);
        clearLucrareForm();
        handleCostTotal();

    }

    private void handleSalveaza(ActionEvent event) {
        try {
            //Auto a = readForm();
            Vizita v = readForm();
            vizitaDao.save(v);
            //Lucrare luc = readLucrare();
            //System.out.println(luc);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText("Salvare cu succes!");
            alert.setContentText("Vizita din data de " + v.getDataVizita() + " a fost salvata cu succes in baza de date!");
            alert.showAndWait();

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare");
            alert.setHeaderText("Eroare");
            alert.setContentText("Eroare la salvarea vizitei in baza de date: \n" + ex.getMessage());
            alert.showAndWait();

            LOG.log(Level.SEVERE, null, ex);
        }
        clearVizitaForm();

        try {
            saveLucrare();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        clearLucrariTable();

    }

    private Vizita readForm() {
        LocalDate data = dataInreg.getValue();
        String parc = parcurs.getText();
        String numeServ = numeService.getText();
        int costT = Integer.parseInt(costTotal.getText().trim());

        Auto a = autoSelectat.getValue();
        int autoId = a.getId();

        Vizita v = new Vizita(data, parc, numeServ, costT, autoId);
        return v;
    }

    private void saveLucrare() throws SQLException {

//        Vizita v = vizitaDao.retrieve();
//        int idVizita = v.getId();
//
//        for (Lucrare lucr : tabelLucrari.getItems()) {
//            lucr = new Lucrare(lucr.getLucrare(), lucr.getCostLucrare(), idVizita);
//            lucrareDao.adauga(lucr);
//        }

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

}
