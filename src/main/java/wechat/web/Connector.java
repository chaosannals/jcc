package wechat.web;

import java.io.*;
import java.net.URI;
import java.util.regex.*;
import java.lang.reflect.*;
import java.net.http.*;
import java.nio.charset.*;

/**
 * 连接器类
 *
 * 发起连接并得到登录器。
 */
public abstract class Connector {
    private static final String SNI_KEY = "jsse.enableSNIExtension";
    private static final String APPID = "wx782c26e4c19acffb"; // 微信网页版的 APPID。
    private static final String JSLOGIN_URL = "https://login.weixin.qq.com/jslogin";

    private static final String UUID_REGEXP = "window.QRLogin.code = (\\d+);\\s*window.QRLogin.uuid = \"(\\S+?)\"";

    /**
     * 连接，生成对应的连接器。
     *
     * @param type 连接器类型。
     * @param <T>  连接器类型。
     * @return 连接器。
     * @throws Exception 可能的异常。
     */
    public static <T extends Signer> T connect(Class<T> type) throws Exception {
        Constructor<T> constructor = type.getConstructor(String.class);
        HttpClient client = HttpClient.newHttpClient();
        StringBuilder builder = new StringBuilder();
        builder.append("appid=").append(APPID).append("&fun=new&lang=zh_CN&_=").append(System.currentTimeMillis());
        HttpRequest request = HttpRequest.newBuilder(URI.create(JSLOGIN_URL))
                .POST(HttpRequest.BodyPublishers.ofString(builder.toString())).build();
        HttpResponse<String> response = client.send(request, rf -> {
            return HttpResponse.BodySubscribers.ofString(Charset.forName("UTF-8"));
        });
        Pattern pattern = Pattern.compile(UUID_REGEXP);
        Matcher match = pattern.matcher(response.body());
        if (match.find() && "200".equals(match.group(1)))
            return constructor.newInstance(match.group(2));
        throw new WeChatWebException(response.body());
    }

    /**
     * 获取 UUID 。
     *
     * @return UUID
     * @throws IOException I/O 异常
     */
    private static String getUUID() throws IOException {
        System.setProperty(SNI_KEY, "false");
        StringBuilder builder = new StringBuilder();
        builder.append("appid=").append(APPID).append("&fun=new&lang=zh_CN&_=").append(System.currentTimeMillis());
        StringBuilder message = Requester.post(JSLOGIN_URL, builder.toString());
        Pattern pattern = Pattern.compile(UUID_REGEXP);
        Matcher match = pattern.matcher(message);
        if (match.find() && "200".equals(match.group(1)))
            return match.group(2);
        throw new WeChatWebException(message.toString());
    }
}
