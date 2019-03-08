package Gui.PlaylistList;

import Gui.PlaylistItem.PlaylistItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DeletePlaylistWindow {

    private Parent view;
    private PlaylistItem parent;
    private Scene scene;
    private Stage stage;

    @FXML
    private AnchorPane deletePlaylistBox;
    @FXML
    private TextField textField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;

    public DeletePlaylistWindow(PlaylistItem parent) {
        try {
            this.parent = parent;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/DeletePlaylistWindow.fxml"));
            loader.setController(this);
            view = loader.load();
            stage = new Stage();

            // Prevent user from interacting with anything other than current window.
            stage.initModality(Modality.APPLICATION_MODAL);
            scene = new Scene(view);
            stage.setScene(scene);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        deletePlaylistBox.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });

        deleteButton.setOnMouseEntered(e -> stage.getScene().setCursor(Cursor.HAND));
        deleteButton.setOnMouseExited(e -> stage.getScene().setCursor(Cursor.DEFAULT));
        deleteButton.setOnMouseClicked(e -> {
            sendClick();
            stage.close();
        });

        cancelButton.setOnMouseEntered(e -> stage.getScene().setCursor(Cursor.HAND));
        cancelButton.setOnMouseExited(e -> stage.getScene().setCursor(Cursor.DEFAULT));
        cancelButton.setOnMouseClicked(e -> stage.close());
    }

    public Parent getView() {
        return view;
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    /**
     * Informs the parent, a PlaylistItem object, to delete the playlist
     */
    private void sendClick() {
        parent.receivePlaylistDeleteClick(this);
    }
}
