package Gui.MainDisplay;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.CollectionLightWeight;

import java.io.IOException;

public class MainDisplayItem {
    protected MainDisplayPresenter mainDisplayPresenter;
    protected Parent view;
    protected CollectionLightWeight song;

    protected @FXML
    AnchorPane songPane;
    protected @FXML
    Label songTitleLabel;
    protected @FXML
    Label artistNameLabel;
    protected @FXML
    Label albumNameLabel;


    public MainDisplayItem(MainDisplayPresenter mainDisplayPresenter, CollectionLightWeight song) {
        try {
            this.mainDisplayPresenter = mainDisplayPresenter;
            this.song = song;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/MainDisplayItemAlt.fxml"));
            loader.setController(this);
            view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        songTitleLabel.setText(song.getSongTitle());
        artistNameLabel.setText(song.getArtistName());
        albumNameLabel.setText(song.getReleaseName());

        songPane.setOnMouseEntered(e -> {
            Main.getPrimaryStage()
                    .getScene()
                    .setCursor(Cursor.HAND);
            songPane.setStyle("-fx-background-color: #464646");
        });

        songPane.setOnMouseExited(e -> {
            Main.getPrimaryStage()
                    .getScene()
                    .setCursor(Cursor.DEFAULT);
            songPane.setStyle("-fx-background-color: #333333");
        });

        songPane.setOnMouseClicked(e -> sendPlayRequest());
    }

    public Parent getView() {
        return view;
    }

    private void sendPlayRequest() {
        mainDisplayPresenter.receivePlayRequest(this, song);
    }

}
