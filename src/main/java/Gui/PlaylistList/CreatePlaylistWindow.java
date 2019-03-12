package Gui.PlaylistList;

import data.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreatePlaylistWindow {

    private Stage stage;
    private Scene scene;
    private Parent view;
    private PlaylistListPresenter parent;

    @FXML
    private AnchorPane createPlaylistBox;
    @FXML
    private TextField textField;
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;

    public CreatePlaylistWindow(PlaylistListPresenter parent) {
        try {
            this.parent = parent;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/CreatePlaylistWindow.fxml"));
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
        createPlaylistBox.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });

        textField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                sendText();
                stage.close();
            }
        });
        createButton.setOnMouseEntered(e -> stage.getScene().setCursor(Cursor.HAND));
        createButton.setOnMouseExited(e -> stage.getScene().setCursor(Cursor.DEFAULT));
        createButton.setOnMouseClicked(e -> {
            if (!textField.getText().isEmpty()) {
                if (UserSession.getCurrentSession()
                        .getUserProfile()
                        .getPlaylist(textField.getText()) != null) {
                    new Alert(Alert.AlertType.ERROR, "Playlist name already exists.", ButtonType.OK)
                            .showAndWait();
                } else {
                    sendText();
                    stage.close();
                }
            }
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

    private void sendText() {
        parent.receivePlaylistCreateClick(this, textField.getText());
    }
}
