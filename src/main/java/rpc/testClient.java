//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class testClient extends Thread {
//    Long file;
//    ProxyInterface proxy;
//    public void start(){
//        try {
//            // It uses CECS327InputStream as InputStream to play the song
//            InputStream is = new CECS327InputStream(this.file, this.proxy);
//            Player mp3player = new Player(is);
//            mp3player.play();
//        }
//        catch (JavaLayerException ex) {
//            ex.printStackTrace();
//        }
//        catch (IOException ex) {
//            System.out.println("Error playing the audio file.");
//            ex.printStackTrace();
//        }
//    }
//
//    void mp3play(Long f, ProxyInterface p) {
//        this.file = f;
//        this.proxy = p;
//    }
//
//}
