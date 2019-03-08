package Gui.SearchBar;

import Gui.MainDisplay.MainDisplayPresenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public class SearchBarPresenter {

    private Parent view;
    private MainDisplayPresenter mainDisplayPresenter;

    @FXML
    TextField searchField;

    public SearchBarPresenter(MainDisplayPresenter mainDisplayPresenter) {
        try {
            this.mainDisplayPresenter = mainDisplayPresenter;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/SearchBar.fxml"));
            loader.setController(this);
            view = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        searchField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onSearchSubmit();
            }
        });
    }

    public void onSearchSubmit() {
        sendResultsToMainDisplay(searchField.getText());
    }

    public Parent getView() {
        return view;
    }

    private void sendResultsToMainDisplay(String searchText) {
        try {
            this.mainDisplayPresenter.receiveSearchText(this, searchField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
