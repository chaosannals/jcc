package wechat.web;

import java.io.*;
import java.util.*;
import org.json.*;

/**
 * 客户端类
 *
 * 提供客户端功能。
 */
public class Client {
    private static final String WX2_WEB_API_URL = "https://wx2.qq.com/cgi-bin/mmwebwx-bin";
    private static final String SYNC_URL = WX2_WEB_API_URL + "/webwxsync";
    private static final String SYNC_CHECK_URL = WX2_WEB_API_URL + "/synccheck";
    private static final String CONTACT_URL = WX2_WEB_API_URL + "/webwxgetcontact";
    private static final String STATUS_NOTIFY_URL = WX2_WEB_API_URL + "/webwxstatusnotify";

    private final Proof proof;
    private final JSONObject info;

    /**
     *
     * @param proof
     * @throws IOException
     * @throws JSONException
     */
    public Client(Proof proof) throws IOException, JSONException {
        this.proof = proof;
        info = proof.proof();
    }

    public JSONObject getInfo() {
        return info;
    }

    public boolean enableStatusNotify() throws IOException, JSONException {
        String username = info.getJSONObject("User").getString("UserName");
        JSONObject request = new JSONObject();
        request.put("BaseRequest", proof.getJSONProof());
        request.put("Code", 3);
        request.put("FromUserName", username);
        request.put("ToUserName", username);
        request.put("ClientMsgId", new Date().getTime());
        StringBuilder message = Requester.post(STATUS_NOTIFY_URL, request.toString());
        System.out.println(message);
        return false;
    }

    public JSONObject getContact() throws IOException, JSONException {
        JSONObject request = new JSONObject();
        request.put("BaseRequest", proof.getJSONProof());
        StringBuilder message = Requester.post(CONTACT_URL, request.toString());
        System.out.print(message);
        return new JSONObject(message);
    }

    public boolean checkSynchronization() throws IOException, JSONException {
        JSONObject request = new JSONObject();
        request.put("BaseRequest", proof.getJSONProof());
        StringBuilder message = Requester.post(SYNC_CHECK_URL, request.toString());
        System.out.println(message);
        return false;
    }

    public JSONObject synchronize() throws IOException, JSONException {
        JSONObject request = new JSONObject();
        request.put("BaseRequest", proof.getJSONProof());
        request.put("SyncKey", info.getJSONObject("SyncKey"));
        request.put("rr", new Date().getTime());
        StringBuilder message = Requester.post(SYNC_URL + "?sid=" + proof.getSid() + "&skey=" + proof.getSkey()
                + "&pass_ticket=" + proof.getPassTicket(), request.toString());
        System.out.print(message);
        return new JSONObject(message);
    }

    public boolean send(String content) {
        System.out.println(proof.toString());
        return false;
    }
}
