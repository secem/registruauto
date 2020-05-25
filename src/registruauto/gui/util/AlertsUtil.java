package registruauto.gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;

public class AlertsUtil {
    
    public void showMessage(Alert.AlertType aT, String title, String headerText, String contentText) {

        Alert alert = new Alert(aT);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        //am definit un DialogPane pentru a putea aplica stiluri pentru Alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.showAndWait();

    }
}
