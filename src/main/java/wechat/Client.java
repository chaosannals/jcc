package wechat;

import java.net.*;
import java.util.function.*;
import java.net.http.*;

/**
 * 客户端类
 *
 *
 */
public class Client {
    public static final String LOGIN_URL = "https://login.weixin.qq.com/";

    private final HttpClient client;

    private Client() {
        this.client = HttpClient.newHttpClient();
    }

    /**
     *
     */
    public static void connect(Consumer<Client> action) throws URISyntaxException {
        Client result = new Client();
        HttpRequest request = HttpRequest.newBuilder(new URI(LOGIN_URL + "jslogin"))

                .GET()
                .build();
    }
}
