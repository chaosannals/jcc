package wechat.web;

import java.io.*;
import java.util.regex.*;
import java.util.function.*;
import java.lang.reflect.*;

/**
 * 登录器类
 *
 * 提供基础的登录操作。
 */
public abstract class Signer {
    private static final String WX_WEB_LOGIN_URL = "https://login.weixin.qq.com";
    private static final String LOGIN_URL = WX_WEB_LOGIN_URL + "/cgi-bin/mmwebwx-bin/login";
    private static final String LOGIN_REGEXP = "window.code\\s*=\\s*(\\d+);\\s*(window.redirect_uri\\s*=\\s*\"(\\S+)\")?";

    private final String uuid;              // UUID。
    public final String loginUrl;           // 二维码代表的链接，登录链接。
    public final String qrcodeUrl;          // 二维码图的链接。

    /**
     * 构造方法，从 UUID 开始构造。
     *
     * @param uuid UUID
     */
    public Signer(String uuid){
        this.uuid = uuid;
        loginUrl = WX_WEB_LOGIN_URL + "/l/" + uuid;
        qrcodeUrl = WX_WEB_LOGIN_URL + "/qrcode/" + uuid;
    }

    /**
     * 登录，在完成登录的时候回调方法。
     *
     * @param action
     * @param type
     * @param recoverer
     * @param <T>
     */
    public <T> void signin(Consumer<T> action,Class<T> type, Predicate<Exception> recoverer){
        new Thread(()->{
            try {
                String url = waitLogin(uuid);
                Proof proof = Proof.get(url);
                Constructor<T> constructor = type.getConstructor(new Class[]{Proof.class});
                action.accept(constructor.newInstance(proof));
            } catch (Exception ex) {
                if (recoverer == null || !recoverer.test(ex))
                    throw new WeChatWebException("Signin Fault",ex);
            }
        }).start();
    }

    public <T> void signin(Consumer<T> setter, Class<T> type){
        signin(setter,type,null);
    }

    /**
     * 等待登录请求得到回复。
     *
     * @param uuid
     * @return
     * @throws IOException
     */
    private static String waitLogin(String uuid) throws IOException{
        int tip = 1;
        while(true){
            StringBuilder message = Requester.post(LOGIN_URL,
                    "tip=" + tip +
                            "&uuid=" + uuid +
                            "&_=" + System.currentTimeMillis()
            );
            Pattern pattern = Pattern.compile(LOGIN_REGEXP);
            Matcher match = pattern.matcher(message);
            if(!match.find())throw new WeChatWebException(message.toString());
            String code = match.group(1);
            if ("201".equals(code)) tip = 0;
            else if ("200".equals(code))
                return match.group(3)+"&fun=new";
            else if ("408".equals(code))
                throw new WeChatWebException(message.toString());
            else throw new WeChatWebException("Unknown(login)");
        }
    }
}
