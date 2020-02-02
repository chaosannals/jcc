package web;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;

/**
 * 主程序类
 *
 */
public class Main {

    /**
     * 入口方法。
     *
     * @param arguments 命令行参数。
     */
    public static void main(String[] arguments) throws Exception {
        HandlerList handlers = new HandlerList();
        ResourceHandler resource = new ResourceHandler();
        resource.setResourceBase(".");
        handlers.addHandler(resource);
        handlers.addHandler(new HomeHandler());

        Server server = new Server(80);
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
