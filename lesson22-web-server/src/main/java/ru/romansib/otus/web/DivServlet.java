package ru.romansib.otus.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DivServlet", urlPatterns = "/div.html")
public class DivServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        Float firstParam = Float.valueOf(req.getParameter("first"));
        Float secondParam = Float.valueOf(req.getParameter("second"));
        float result = firstParam / secondParam;
        out.println("<html><body><h1>" + firstParam + " / " + secondParam + " = " + result + "</h1><br><a href=\"javascript:history.back()\">Back to calculator</a></body></html>");
        out.close();
    }
}
