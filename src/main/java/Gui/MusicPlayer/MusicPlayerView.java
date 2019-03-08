package Gui.MusicPlayer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MusicPlayerView {
    private Parent view;
    private FXMLLoader loader;

    public MusicPlayerView() {
        loader = new FXMLLoader(getClass().getResource("../../../res/ui/MusicPlayer.fxml"));
    }

    public Parent loadView() {
        try {
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
