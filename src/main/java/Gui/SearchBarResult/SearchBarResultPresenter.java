//package main.Gui.SearchBarResult;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.text.Text;
//import model.Collection;
//
//import java.io.IOException;
//
//public class SearchBarResultPresenter {
//    private SearchBarResultModel searchBarResultModel;
////    private SearchBarResultView searchBarResultView;
//
//    @FXML
//    private Text song;
//
//    @FXML
//    private Text artist;
//
//    @FXML
//    private Text duration;
//
//    private Parent view;
//
//    public SearchBarResultPresenter(Collection songInfo) {
//        try {
//            searchBarResultModel = new SearchBarResultModel();
//            searchBarResultModel.setSongResult(songInfo);
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../res/ui/SearchBarResult.fxml"));
//            loader.setController(this);
//            view = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    public void initialize() {
//        Collection c = searchBarResultModel.getSongResult();
//        song.setText(c.getSongTitle());
//        artist.setText(c.getArtistName());
//        String d = Float.toString(c.getSongTitle().getDuration());
//        duration.setText(d);
//
//    }
//
//    public void initData(String songStr, String artistStr, String durationStr) {
//        song.textProperty().set(songStr);
//        artist.textProperty().set(artistStr);
//        duration.textProperty().set(durationStr);
//    }
//
//    public Text getSongText() {
//        return song;
//    }
//
//    public Text getArtistText() {
//        return artist;
//    }
//
//    public Text getDurationText() {
//        return duration;
//    }
//
//    public void receiveCollection(Collection collection) {
//        searchBarResultModel.setSongResult(collection);
//    }
//
//    public Collection getSongResult() {
//        return searchBarResultModel.getSongResult();
//    }
//
//    public Parent getView() {
//        return view;
//    }
//}
