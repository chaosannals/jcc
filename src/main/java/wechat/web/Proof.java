package wechat.web;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.json.*;
import org.w3c.dom.*;

/**
 * 凭证类
 *
 * 登录成功后可以获取的凭证。
 */
public class Proof {
    private static final String INIT_URL = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit";

    private final String passTicket;
    private final JSONObject proof;

    private Proof(
            String skey,
            String sid,
            String uin,
            String passTicket) throws JSONException {
        this.passTicket = passTicket;
        proof = new JSONObject();
        proof.put("Uin", uin);
        proof.put("Sid", sid);
        proof.put("Skey", skey);
        proof.put("DeviceID", UUID.randomUUID().toString());
    }

    public static Proof get(String url) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(new URL(url).openStream());
        XPath path = XPathFactory.newInstance().newXPath();
        String ret = path.evaluate("/error/ret", document);
        if (!ret.equals("0")){
            String message = path.evaluate("/error/message", document);
            throw new WeChatWebException(message);
        }
        return new Proof(
                path.evaluate("/error/skey", document),
                path.evaluate("/error/wxsid", document),
                path.evaluate("/error/wxuin", document),
                path.evaluate("/error/pass_ticket", document)
        );
    }

    @Override
    public String toString(){
        return proof.toString();
    }

    public String getUin() throws JSONException {
        return proof.getString("Uin");
    }

    public String getSid() throws JSONException {
        return proof.getString("Sid");
    }

    public String getSkey() throws JSONException {
        return proof.getString("Skey");
    }

    public String getDeviceId() throws JSONException {
        return proof.getString("DeviceID");
    }

    public String getPassTicket() {
        return passTicket;
    }

    public JSONObject getJSONProof() {
        return proof;
    }

    public JSONObject proof() throws IOException, JSONException {
        JSONObject request = new JSONObject();
        request.put("BaseRequest", proof);
        String url = INIT_URL + "?pass_ticket=" + passTicket +
                "&skey=" + getSkey() + "&r=" + System.currentTimeMillis();
        StringBuilder message = Requester.post(url, request.toString());
        JSONObject result = new JSONObject(message.toString());
        if (result.getJSONObject("BaseResponse").getInt("Ret") != 0)
            throw new WeChatWebException(message.toString());
        return result;
    }
}
