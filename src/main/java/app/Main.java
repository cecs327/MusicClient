package app;

import Gui.Homepage.HomepagePresenter;
import Gui.Landing.LandingService;
//import data.Resources;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import Gui.Landing.LandingPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rpc.Proxy;
import rpc.ProxyInterface;
//import utility.Deserializer;
//import utility.Serializer;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    private static Scene primaryScene;
    private AnchorPane rootLayout;

    private int portNumber = 2223;
    private ProxyInterface clientProxy = new Proxy(portNumber);
    public static int userToken;

   @Override
   public void start(Stage stage) throws Exception {
       //Deserializer d = new Deserializer();
       //Resources.setOwnedIDs(d.getOwnedIDs());
       //Resources.setMusicDatabase(d.deserializeMusicLibrary());

       primaryStage = stage; // does nothing atm
       primaryStage.setWidth(1024);
       primaryStage.setHeight(576);
       primaryStage.setResizable(true);

       LandingPresenter lp = new LandingPresenter(clientProxy);
       lp.showLandingPage();

//       HomepagePresenter hp = new HomepagePresenter();
//       primaryStage.setScene(hp.g);

       primaryStage.show();

       primaryStage.setOnCloseRequest(e -> {
//            try {
//                new Serializer().updateUsersJson(LandingService.getInstance(clientProxy).userList());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            } finally {
//                System.exit(1);
//            }
//        });
           // TODO: Update user json with dispatcher
           System.exit(0);
       });
   }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setCursorStyle (Cursor cursorStyle) {
        primaryStage.getScene().setCursor(cursorStyle);
    }

    public Scene getPrimaryScene() {
        return primaryScene;
    }

    public void setPrimaryScene(final Scene primaryScene) {
        this.primaryScene = primaryScene;
    }

    private void initRootLayout() {
       try {
           // Load root layout from FXML file.
           FXMLLoader loader = new FXMLLoader();
//           loader.setLocation(Main.class.getResource("gui/Landing/Landing.fxml"));
           rootLayout = (AnchorPane) loader.load();

           // Show the scene containing the root layout.
           Scene scene = new Scene(rootLayout);
           primaryStage.setScene(scene);
           primaryStage.show();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
       }
   }
}
