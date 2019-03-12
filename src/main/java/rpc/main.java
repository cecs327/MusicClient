//import java.io.*;
//
//import javazoom.jl.player.*;
//import javazoom.jl.decoder.JavaLayerException;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import rpc.ProxyInterface;
//
//import static java.lang.Thread.sleep;
//
//public class main {
//
//    void mp3play(Long file, ProxyInterface proxy) {
//        try {
//            // It uses CECS327InputStream as InputStream to play the song
//            InputStream is = new CECS327InputStream(file, proxy);
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
//    public static void main(String [ ] args) {
//        int portNumber = 2222;
//
//        File f = new File(System.getProperty("user.dir") + "\\src\\" +1337);
//        System.out.println("File exists: "+f.exists());
//
//        // Set up server
//        serverCommunicationProtocol myServer = new serverCommunicationProtocol(portNumber);
//        myServer.start();
//
//        // Set up clients
//        Proxy client1 = new Proxy(portNumber);
//        Proxy client2 = new Proxy(portNumber);
//
//        //Start client1
//        main player1 = new main();
//        System.out.println("Starting client1");
//        player1.mp3play(1337L, client1);
//
//        // Start client2
//        main player2 = new main();
//        System.out.println("Starting client2");
//        player2.mp3play(490183L, client2);
//
//    }
//
//}
