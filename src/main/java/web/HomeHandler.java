package web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;

public class HomeHandler extends AbstractHandler {

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Home</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>Welcome</h1>");
        writer.println("<h2>Target:</h2>");
        writer.println("<p>");
        writer.println(target);
        writer.println("</p>");
        writer.println("</body>");
        writer.println("</html>");
        writer.close();
    }
}
