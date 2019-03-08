package Gui.Landing;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import rpc.ProxyInterface;

import java.io.IOException;

public class register {
    private Stage stage;
    private Parent view;

    private ProxyInterface proxy;
    @FXML
    private TextField user;
    @FXML
    private TextField pass;
    @FXML
    private Button signupButton;

    public register(Stage stage, ProxyInterface proxy) {
        try {
            this.proxy = proxy;
            this.stage = stage;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/Registration.fxml"));
            loader.setController(this);
            view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        signupButton.setOnMouseClicked(e -> {
            LandingService ls = LandingService.getInstance(proxy);

            String username = user.getText();
            String password = pass.getText();

            User u = new User(user.getText(), "", pass.getText());

            // TODO: REGISTER DISPATCHER
            //ls.userList.add(u);
            //ls.usersInfo.put(username+pass, u);

            stage.close();
        });
    }

    public Parent getView() {
        return view;
    }
}
