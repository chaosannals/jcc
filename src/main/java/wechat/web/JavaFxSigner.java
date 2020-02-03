package wechat.web;

import java.util.function.*;
import javafx.application.*;
import javafx.scene.image.*;

/**
 * JavaFx 登录器类
 *
 * 提供 JavaFx 相关的登录操作。
 */
public class JavaFxSigner extends Signer {
    private Image qrcode;

    public JavaFxSigner(String uuid) {
        super(uuid);
        qrcode = new Image(qrcodeUrl);
    }

    public Image getQrcode() {
        return qrcode;
    }

    @Override
    public <T> void signin(Consumer<T> action, Class<T> type, Predicate<Exception> recoverer) {
        super.signin((T value) -> Platform.runLater(() -> action.accept(value)), type, recoverer);
    }

    @Override
    public <T> void signin(Consumer<T> setter, Class<T> type) {
        signin(setter, type, null);
    }
}
