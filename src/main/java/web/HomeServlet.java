package web;

import java.io.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/home.view")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Home</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>Welcome</h1>");
        writer.println("</body>");
        writer.println("</html>");
        writer.close();
    }
}
