package rpc; /**
 * The Proxy implements ProxyInterface class. The class is incomplete
 *
 * @author Oscar Morales-Ponce
 * @version 0.15
 * @since 2019-01-24
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Proxy implements ProxyInterface {
    clientCommunicationProtocol communicate = null;

    // Constructor
    public Proxy(int portNumber) {
        this.communicate = new clientCommunicationProtocol();
        communicate.connect(portNumber);
    }

    /*
     * Executes the  remote method "remoteMethod". The method blocks until
     * it receives the reply of the message.
     */
    public synchronized JsonObject synchExecution(String remoteMethod, String[] param) {
        JsonObject jsonRequest = new JsonObject();
        JsonObject jsonParam = new JsonObject();

        jsonRequest.addProperty("remoteMethod", remoteMethod);


        if (remoteMethod.equals("getSongChunk") || remoteMethod.equals("getFileSize")) {
            jsonRequest.addProperty("objectName", "SongServices");
            // It is hardcoded. Instead it should be dynamic using  RemoteRef
            if (remoteMethod.equals("getSongChunk")) {

                jsonParam.addProperty("song", param[0]);
                jsonParam.addProperty("fragment", param[1]);

            }
            if (remoteMethod.equals("getFileSize")) {
                jsonParam.addProperty("song", param[0]);
            }
        }

        // Search result dispatcher
        else if (remoteMethod.equals("getSize") || remoteMethod.equals("getSearchResultChunk")) {
            jsonRequest.addProperty("objectName", "SearchResultServices");
            if (remoteMethod.equals("getSearchResultChunk")) {
                jsonParam.addProperty("fragment", param[0]);
            }
            if (remoteMethod.equals("getSize")) {
                jsonParam.addProperty("query", param[0]);
            }
        }

        // Login Dispatcher
        else if (remoteMethod.equals("login")) {
            jsonRequest.addProperty("objectName", "LoginServices");
            jsonParam.addProperty("username", param[0]);
            jsonParam.addProperty("password", param[1]);
        }

        // Playlists Dispatcher
        else if (remoteMethod.equals("getPlaylistsChunk") || remoteMethod.equals("getPlaylistsSize")) {
            jsonRequest.addProperty("objectName", "PlaylistServices");
            if (remoteMethod.equals("getPlaylistsChunk")) {
                jsonParam.addProperty("fragment", param[0]);
            }
            if (remoteMethod.equals("getPlaylistsSize")) {
                jsonParam.addProperty("userToken", param[0]);
            }
        }

        jsonRequest.add("param", jsonParam);

        JsonParser parser = new JsonParser();
        System.out.println("Sending request: " + jsonRequest.toString());
        String strRet = communicate.sendRequest(jsonRequest.toString().trim());
        System.out.println("Returning response from server to inputstream: " + strRet);
        String myReturn = strRet.trim();
        return parser.parse(myReturn).getAsJsonObject();
    }

    /*
     * Executes the  remote method remoteMethod and returns without waiting
     * for the reply. It does similar to synchExecution but does not
     * return any value
     *
     */
    public void asynchExecution(String remoteMethod, String[] param) {
        return;
    }
}


