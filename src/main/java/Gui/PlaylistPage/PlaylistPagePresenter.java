package Gui.PlaylistPage;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import model.Playlist;

public class PlaylistPagePresenter {
    private PlaylistPageModel playlistPageModel;

    @FXML
    public void initialize() {
        playlistPageModel.setPlaylist(null);
    }

    public void receivePlaylists(Playlist playlist) {
        playlistPageModel.setPlaylist(playlist);
    }

    public Parent getView() {
        return null;
    }
}
