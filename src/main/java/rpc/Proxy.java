package rpc; /**
 * The Proxy implements ProxyInterface class. The class is incomplete
 *
 * @author Oscar Morales-Ponce
 * @version 0.15
 * @since 2019-01-24
 */

import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Proxy implements ProxyInterface {
    private static final Logger LOGGER = Logger.getLogger(Proxy.class);
    private ClientCommunicationProtocol communicate;
    private static final URL MUSIC_URL = Proxy.class.getResource("/appdata/methods.json");

    // Constructor
    public Proxy(int portNumber) {
        this.communicate = new ClientCommunicationProtocol();
        communicate.connect(portNumber);
    }

    /*
     * Executes the  remote method "remoteMethod". The method blocks until
     * it receives the reply of the message.
     */
    public synchronized JsonObject syncExecution(String remoteMethod, Map<String, String> params) {
        JsonObject remoteMethodJO = getRemoteMethodFromJson(remoteMethod);

        attachParams(remoteMethodJO, params);

        attachUUID(remoteMethodJO);
        String callSemantic = getCallSemanticFromJson(remoteMethodJO);

        System.out.println("Sending request: " + remoteMethodJO.toString());
        String strRet = communicate.sendRequest(remoteMethodJO.toString().trim(), callSemantic);
        System.out.println("Returning response from server to inputstream: " + strRet);
        String myReturn = strRet.trim();

        JsonParser parser = new JsonParser();

        return parser.parse(myReturn).getAsJsonObject();
    }

    public synchronized void asyncExecution(String remoteMethod, Map<String, String> params) {
        JsonObject remoteMethodJO = getRemoteMethodFromJson(remoteMethod);

        attachParams(remoteMethodJO, params);
        attachUUID(remoteMethodJO);

        communicate.sendAsyncRequest(remoteMethodJO.toString().trim());
    }

    public JsonObject getRemoteMethodFromJson(String remoteMethod) {
        Gson gson = new Gson();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(MUSIC_URL.openStream()));
            JsonObject jsonObject = gson.fromJson(br, JsonObject.class).getAsJsonObject();

            return jsonObject.get(remoteMethod).getAsJsonObject();
        } catch (IOException e) {
            LOGGER.error("ERROR: Proxy.getRemoteMethod: " + e);
            return null;
        }
    }

    private void attachParams(JsonObject methodObject, Map<String, String> params) {
        JsonObject paramsObject = methodObject.get("params").getAsJsonObject();

        Set<Map.Entry<String, String>> entrySet = params.entrySet();

        for(Map.Entry<String, String> entry : entrySet) {
            paramsObject.addProperty(entry.getKey(), entry.getValue());
        }

        methodObject.add("params", paramsObject);
    }

    private void attachUUID(JsonObject methodObject) {
        UUID uuid = Generators.timeBasedGenerator().generate();
        methodObject.addProperty("requestId", uuid.toString());
    }

    private String getCallSemanticFromJson(JsonObject methodObject) {
        return methodObject.get("callSemantic").getAsString();
    }

    /*
     * Executes the  remote method remoteMethod and returns without waiting
     * for the reply. It does similar to syncExecution but does not
     * return any value
     *
     */
    public void asynchExecution(String remoteMethod, String[] param) {
        return;
    }
}


