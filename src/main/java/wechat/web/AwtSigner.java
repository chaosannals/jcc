package wechat.web;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.image.*;
import java.util.function.*;
import javax.imageio.*;

/**
 * AWT 注册类
 */
public class AwtSigner extends Signer {
    private BufferedImage qrcode;

    /**
     *
     * @param uuid 从微信Web获取的UUID
     * @throws IOException IO异常
     */
    public AwtSigner(String uuid) throws IOException {
        super(uuid);
        URL url = new URL(qrcodeUrl);
        qrcode = ImageIO.read(url);
    }

    /**
     *
     * @return 二维码图像
     */
    public Image getQrcode() {
        return qrcode;
    }

    /**
     *
     * @return 二维码图像，BufferedImage类型
     */
    public BufferedImage getBufferedQrcode() {
        return qrcode;
    }

    /**
     *
     * @param action    回调
     * @param type      类型
     * @param recoverer
     * @param <T>
     */
    @Override
    public <T> void signin(Consumer<T> action, Class<T> type, Predicate<Exception> recoverer) {
        super.signin((T value) -> EventQueue.invokeLater(() -> action.accept(value)), type, recoverer);
    }

    @Override
    public <T> void signin(Consumer<T> setter, Class<T> type) {
        signin(setter, type, null);
    }
}
