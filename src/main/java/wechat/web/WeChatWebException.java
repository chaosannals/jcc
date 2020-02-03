package wechat.web;

class WeChatWebException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public WeChatWebException(String message) {
        super(message);
    }

    public WeChatWebException(String message, Throwable cause) {
        super(message, cause);
    }
}