package Gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoadableView {
    protected FXMLLoader loader = null;
    protected Parent view = null;

    public Parent getView() {
        try {
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FXMLLoader getLoader() {
        return this.loader;
    }
}
