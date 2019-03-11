package Gui.Homepage;

import Gui.MainDisplay.MainDisplayPresenter;
import Gui.MusicPlayer.MusicPlayerPresenter;
import Gui.PlaylistList.PlaylistListPresenter;
import Gui.SearchBar.SearchBarPresenter;
import app.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import model.CollectionLightWeight;
import model.Playlist;
import rpc.ProxyInterface;

import java.io.IOException;

public class HomepagePresenter {

    private Parent view;
    private ProxyInterface clientProxy;

    // Presenters
    private MainDisplayPresenter mainDisplayPresenter;
    private PlaylistListPresenter playlistListPresenter;
    private SearchBarPresenter searchBarPresenter;
    private MusicPlayerPresenter musicPlayerPresenter;

    @FXML
    private GridPane gridPane;

    public HomepagePresenter(ProxyInterface proxy) {
        clientProxy = proxy;

        try {
            mainDisplayPresenter = new MainDisplayPresenter(this);
            playlistListPresenter = new PlaylistListPresenter(this.mainDisplayPresenter, this);
            searchBarPresenter = new SearchBarPresenter(mainDisplayPresenter);
            musicPlayerPresenter = new MusicPlayerPresenter(this.mainDisplayPresenter);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/HomepageAlt.fxml"));
            loader.setController(this);
            view = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Note: you can only modify javafx objects loaded from FXML (i.e. adding elements)
     * after calling FXMLLoader.load(). This is invoked when calling any of the xView constructors.
     */
    public void initialize() {
        // TODO: Fix this with login dispatcher
        //Text profileText = new Text(UserSession.getCurrentSession().getUsername());
        //profileText.requestFocus(); // doesn't work
        //gridPane.add(profileText, 0, 0);
        gridPane.add(searchBarPresenter.getView(), 3, 0, 3, 1);
        gridPane.add(playlistListPresenter.getView(), 0, 2, 2, 4);
        gridPane.add(mainDisplayPresenter.getView(), 3, 2, 3, 3);
        gridPane.add(musicPlayerPresenter.getView(), 2, 6);
    }

    public void showDefaultPage() {
        Scene scene = new Scene(view);
        App.getPrimaryStage().setScene(scene);
    }

    public void receivePlaylistItemClick(MainDisplayPresenter sender, CollectionLightWeight song, Playlist playlist) {
        musicPlayerPresenter.receivePlaylistItemPlayRequest(this, song, playlist);
    }

    public void receivePlaylistItemClick(PlaylistListPresenter sender, Playlist obj) {
        mainDisplayPresenter.receivePlaylistItemClick(this, obj);
    }

    public ProxyInterface getProxy() {
        return clientProxy;
    }
}
