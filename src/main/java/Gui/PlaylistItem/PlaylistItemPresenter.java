package Gui.PlaylistItem;

import Gui.PlaylistList.PlaylistListPresenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Playlist;

import java.io.IOException;


public class PlaylistItemPresenter {

    private PlaylistItemModel playlistItemModel;
    private Parent view;

    @FXML
    Pane playlistItemPane;
    @FXML
    Text playlistNameText;

    public PlaylistItemPresenter() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);

            // Load the playlist
            loader.setLocation(getClass().getResource("/ui/PlaylistItem.fxml"));
            view = loader.load();

            playlistItemModel = new PlaylistItemModel();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        playlistItemPane.setOnMouseEntered(e -> {
            playlistItemPane.setStyle("-fx-background-color: #464646");
        });
        playlistItemPane.setOnMouseExited(e -> {
            playlistItemPane.setStyle("-fx-background-color: #333333");
        });
    }

    public void receivePlaylist(PlaylistListPresenter sender, Playlist playlist) {
        playlistItemModel.setPlaylist(playlist);
    }

    public Parent getView() {
        return view;
    }
}
