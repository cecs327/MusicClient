import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpc.Proxy;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyTest {
    private static final int DEFAULT_PORT = 2223;
    private Proxy proxy;
    @BeforeEach
    void setup() {
        proxy = new Proxy(DEFAULT_PORT);
    }

    @Test
    void getRemoteMethodFromJson() {
        String remoteMethod = "getSongChunk";
        JsonObject jsonObject = proxy.getRemoteMethodFromJson(remoteMethod);

        System.out.println(jsonObject);
        assertNotNull(jsonObject);
    }

}
