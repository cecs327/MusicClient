package Gui.MusicPlayer;

import Gui.Homepage.HomepagePresenter;
import Gui.MainDisplay.MainDisplayPresenter;
import app.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import model.CollectionLightWeight;
import model.Playlist;
import rpc.CECS327InputStream;
import rpc.ProxyInterface;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class MusicPlayerPresenter {
    ArrayList<CollectionLightWeight> playlist;
    CollectionLightWeight currentSong;

    private MainDisplayPresenter mainDisplayPresenter;
    private Node view;
    private boolean isPlaying;
    private BasicPlayer myPlayer = new BasicPlayer();
    private String songFile;

    @FXML
    private Slider slider;
    @FXML
    private Group playButton;
    @FXML
    private Group previousButton;
    @FXML
    private Group nextButton;

    @FXML
    private Label songLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label albumLabel;

    public MusicPlayerPresenter() {
        this.playlist = new ArrayList<>();
    }

    private ProxyInterface clientProxy;

    @FXML
    public void initialize() {
        try {
            isPlaying = false;

            playButton.setOnMouseEntered(e -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
            playButton.setOnMouseExited(e -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));
            playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    togglePlay();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            nextButton.setOnMouseEntered(e -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
            nextButton.setOnMouseExited(e -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));
            nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                try {
                    if (isPlaying) togglePlay();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if (playlist != null && playlist.size() > 0 && currentSong != null) {
                    int songNum = findIndex(currentSong);
                    int size = playlist.size();
                    CollectionLightWeight nextSong = playlist.get((songNum + 1) % size);
                    currentSong = nextSong;
                    int nextID = (int) nextSong.getId();
                    setSongFile(Integer.toString(nextID), clientProxy);

                    songLabel.setText(nextSong.getSongTitle());
                    artistLabel.setText(nextSong.getArtistName());
                    albumLabel.setText(nextSong.getReleaseName());
                }

            });

            previousButton.setOnMouseEntered(e -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
            previousButton.setOnMouseExited(e -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));
            previousButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                try {
                    if (isPlaying) togglePlay();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if (playlist != null && playlist.size() > 0 && currentSong != null) {
                    int songNum = findIndex(currentSong);
                    int size = playlist.size();
                    CollectionLightWeight prevSong = playlist.get(((songNum - 1) + size) % size);
                    int prevID = (int) prevSong.getId();
                    currentSong = prevSong;

                    setSongFile(Integer.toString(prevID), clientProxy);

                    songLabel.setText(prevSong.getSongTitle());
                    artistLabel.setText(prevSong.getArtistName());
                    albumLabel.setText(prevSong.getReleaseName());
                }

            });

            slider.setValue(50);
            slider.setOnMouseEntered(e -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
            slider.setOnMouseExited(e -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    myPlayer.setGain((Double) newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MusicPlayerPresenter(MainDisplayPresenter mainDisplayPresenter) {
        try {
            this.mainDisplayPresenter = mainDisplayPresenter;
            clientProxy = mainDisplayPresenter.getProxy();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/MusicPlayer.fxml"));
            loader.setController(this);
            view = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void togglePlay() throws Exception {
        if (!isPlaying) {
            if (!songFile.isEmpty() && myPlayer.getStatus() == BasicPlayer.PAUSED) {
                myPlayer.resume();
            } else {
                try {
                    myPlayer.play();
                } catch (BasicPlayerException e) {
                    e.printStackTrace();
                }
            }
            isPlaying = true;
        } else {
            try {
                myPlayer.pause();
                isPlaying = false;
                return;
            } catch (BasicPlayerException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSongFile(String songFile, ProxyInterface proxy) {
        try {
            this.songFile = songFile;
            myPlayer.open(new BufferedInputStream(new CECS327InputStream(Long.valueOf(songFile), proxy)));
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public Node getView() {
        return view;
    }

    // play song
    public void receivePlaylistItemPlayRequest(HomepagePresenter sender, CollectionLightWeight song, Playlist playlist) {
        this.playlist = playlist.getSongList();
        this.currentSong = song;

        songLabel.setText(song.getSongTitle());
        artistLabel.setText(song.getArtistName());
        albumLabel.setText(song.getReleaseName());

        try {
            int songId = (int) song.getId();
            String filename = Integer.toString(songId);
            setSongFile(Integer.toString(songId), clientProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayNotPlayableError() {
        new Alert(Alert.AlertType.ERROR, "This song isn't in the playable library.", ButtonType.OK)
                .showAndWait();
    }

    private int findIndex(CollectionLightWeight song) {
        if (playlist == null || playlist.size() < 1) {
            return -1;
        }

        int index = 0;
        for (CollectionLightWeight c : playlist) {
            if (c.getId() == song.getId())
                return index;
            index++;
        }
        return index;
    }
}