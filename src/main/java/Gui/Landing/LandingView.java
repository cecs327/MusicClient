package Gui.Landing;

import Gui.LoadableView;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class LandingView extends LoadableView {
    public LandingView() {
        loader = new FXMLLoader(getClass().getResource("ui/Landing.fxml"));
    }

    protected void displayError() {
        new Alert(Alert.AlertType.ERROR, "Invalid username/password.", ButtonType.OK)
                .showAndWait();
    }
}
