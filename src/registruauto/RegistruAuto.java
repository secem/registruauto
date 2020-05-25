package registruauto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import registruauto.dao.UtilDaoIntf;
import registruauto.dao.impl.UtilDaoImpl;
import registruauto.gui.FXMLMain3_DarkController;
import registruauto.gui.util.PaneType;

public class RegistruAuto extends Application {

    private BorderPane root;
    private static GridPane gridMain;
    private UtilDaoIntf utilDao;

    private static final Logger LOG = Logger.getLogger(RegistruAuto.class.getName());

    @Override
    public void start(Stage stage) throws Exception {
        //versiunea mea veche

//        //incarcam fereastra principala
//        root = FXMLLoader.load(getClass().getResource("/registruauto/gui/FXMLMain3_Dark.fxml"));
//        FXMLLoader loader = new FXMLLoader();
//        Scene scene = new Scene(root);
//        //stage.initStyle(StageStyle.UNDECORATED);
//        stage.setResizable(false);
//        stage.setTitle("Registru de deservire auto");
//        stage.getIcons().add(new Image("/registruauto/util/png/icons8_car_24px.png"));
//        stage.setScene(scene);
//        stage.show();
//
//        //obtinem GridPane care se afla in centrul lui BorderPane
//        gridMain = (GridPane) root.getCenter();
//        showPane(PaneType.ACASA);
        //versiunea dupa discutia cu Domnul Iurie
        //incarcam fereastra principala
        //root = FXMLLoader.load(getClass().getResource("/registruauto/gui/FXMLMain3_Dark.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RegistruAuto.class.getResource("/registruauto/gui/FXMLMain3_Dark.fxml"));
        root = loader.load();

        Scene scene = new Scene(root);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("Registru de deservire auto");
        stage.getIcons().add(new Image("/registruauto/util/png/icons8_car_24px.png"));

        FXMLMain3_DarkController mc = (FXMLMain3_DarkController) loader.getController();
        mc.setRegistruAuto(this);

        //obtinem GridPane care se afla in centrul lui BorderPane
        gridMain = (GridPane) root.getCenter();
        showPane(PaneType.ACASA);

        stage.setScene(scene);

        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    //versiunea veche era static

    public void showPane(PaneType paneType) throws IOException {
        selectPane(paneType);
    }
    //versiunea veche era static

    private void selectPane(PaneType paneType) throws IOException {
        switch (paneType) {
            case ACASA:
                setPane("/registruauto/gui/FXML_Dark_Acasa.fxml");
                break;
            case ACASA_AUTO:
                setPane("/registruauto/gui/FXML_Dark_AcasaAuto.fxml");
                break;
            case GARAJ:
                setPane("/registruauto/gui/FXML_Dark_Garaj.fxml");
                break;
            case NOTIFICARI:
                setPane("/registruauto/gui/FXML_Dark_Notificari.fxml");
                break;
            case REPARATII:
                setPane("/registruauto/gui/FXML_Dark_Reparatii.fxml");
                break;
            case RAPOARTE:
                setPane("/registruauto/gui/FXML_Dark_Rapoarte.fxml");
                break;
            case DESPRE:
                setPane("/registruauto/gui/FXML_Dark_Despre.fxml");
                break;
        }
    }
    //versiunea veche era static

    private void setPane(String viewFXMLName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RegistruAuto.class.getResource(viewFXMLName));
        AnchorPane acasa = loader.load();
        gridMain.add(acasa, 0, 1);
    }

    @Override
    public void stop() {
        utilDao = new UtilDaoImpl();
        //setam ultima data a vizitei pe astazi
        try {
            utilDao.setLastVisitDate();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLMain3_DarkController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
