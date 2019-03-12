//package Gui.MainDisplayItem;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.control.Hyperlink;
//import model.Collection;
//import Gui.MainDisplay.MainDisplayPresenter;
//
//import java.io.IOException;
//
//public class MainDisplayItemPresenter {
//    private Parent view;
//
////    private MusicPlayerPresenter musicPlayerPresenter;
//    private MainDisplayPresenter mainDisplayPresenter;
//
//    private Collection collection;
//
//    @FXML private Hyperlink song;
//    @FXML private Hyperlink artist;
//    @FXML private Hyperlink album;
//
//    String songStr,
//            artistStr,
//            albumStr;
//
//
//    public MainDisplayItemPresenter(MainDisplayPresenter mainDisplayPresenter, Collection song) {
//        try {
//            this.mainDisplayPresenter = mainDisplayPresenter;
//            this.collection = song;
//            songStr = song.getSongTitle();
//            artistStr = song.getArtistName();
//            albumStr = song.getRelease().getName();
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/MainDisplayItem.fxml"));
//            loader.setController(this);
//            view = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void initialize() {
//        song.setText(songStr);
//        artist.setText(artistStr);
//        album.setText(albumStr);
//
//        song.setOnMouseClicked(e -> sendPlayRequest());
//    }
//
//    public Parent getView() {
//        return view;
//    }
//
//    private void sendPlayRequest() {
////        mainDisplayPresenter.receivePlayRequest(this, collection);
//    }
//}
