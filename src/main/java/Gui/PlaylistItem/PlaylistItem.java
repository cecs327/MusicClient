package Gui.PlaylistItem;

import Gui.PlaylistList.DeletePlaylistWindow;
import Gui.PlaylistList.PlaylistListPresenter;
import app.App;
import data.CollectionFormat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.CollectionLightWeight;
import model.Playlist;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlaylistItem {

    private Parent view;
    private Playlist playlist;
    private PlaylistListPresenter parent;

    @FXML
    Pane playlistItemPane;

    @FXML
    Text playlistNameText;

    @FXML
    StackPane deletePane;

    public PlaylistItem(PlaylistListPresenter parent, Playlist playlist) {
        try {
            this.parent = parent;
            this.playlist = playlist;

            // Load the delete button
            deletePane = new FXMLLoader(getClass().getResource("/ui/DeleteButton.fxml")).load();

            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);

            // Load the playlist item pane
            loader.setLocation(getClass().getResource("/ui/PlaylistItem.fxml"));
            view = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        playlistNameText.setText(playlist.getName());

        deletePane.setVisible(false);
        deletePane.setPickOnBounds(true);
        deletePane.relocate(140, 30);
        deletePane.setOnMouseEntered(f -> {
            App.getPrimaryStage()
                    .getScene()
                    .setCursor(Cursor.HAND);
        });
        deletePane.setOnMouseExited(f -> {
            App.getPrimaryStage()
                    .getScene()
                    .setCursor(Cursor.DEFAULT);
        });
        deletePane.setOnMouseClicked(f -> showDeletePlaylistWindow());

        playlistItemPane.getChildren().add(deletePane);
        playlistItemPane.setOnDragOver(e -> {
            Dragboard dragboard = e.getDragboard();
            if (e.getGestureSource() instanceof AnchorPane &&
                    dragboard.hasContent(CollectionFormat.FORMAT)) {
                e.acceptTransferModes(TransferMode.COPY);
            }
            e.consume();
        });
        playlistItemPane.setOnDragDropped(e -> {
            boolean dragCompleted = false;
            Dragboard dragboard = e.getDragboard();

            if (dragboard.hasContent(CollectionFormat.FORMAT)) {
                CollectionLightWeight song = (CollectionLightWeight) dragboard.getContent(CollectionFormat.FORMAT);
                playlist.addToPlaylist(song);

                Map<String, String> params = new HashMap<>();
                params.put("userToken", Integer.toString(App.userToken));
                params.put("playlistName", playlist.getName());
                params.put("song", Long.toString(song.getId()));
                parent.getProxy().asyncExecution("addSongToPlaylist", params);

                dragCompleted = true;
                sendDragComplete();
            }
            App.setCursorStyle(Cursor.DEFAULT);
            e.setDropCompleted(dragCompleted);
            e.consume();
        });

        playlistItemPane.setOnMouseEntered(e -> {
            playlistItemPane.setStyle("-fx-background-color: #464646");
            deletePane.setVisible(true);
        });
        playlistItemPane.setOnMouseExited(e -> {
            playlistItemPane.setStyle("-fx-background-color: #333333");
            deletePane.setVisible(false);
        });
        playlistItemPane.setOnMouseClicked(e -> sendClick());


    }

    private void sendClick() {
        parent.receivePlaylistItemClick(this, playlist);
    }

    /**
     * Informs the parent, a PlaylistListPresenter object, to delete the playlist associated with this PlaylistItem
     */
    private void sendDeleteClick() {
        parent.receivePlaylistItemDeleteClick(this, playlist);
    }

    /**
     * @param sender - Object sending the message to delete the playlist
     */
    public void receivePlaylistDeleteClick(DeletePlaylistWindow sender) {
        sendDeleteClick();
    }

    public Parent getView() {
        return view;
    }

    private void showDeletePlaylistWindow() {
        DeletePlaylistWindow dpw = new DeletePlaylistWindow(this);
        dpw.show();
    }

    private void sendDragComplete() {
        parent.receiveSongAdd(this);
    }
}
