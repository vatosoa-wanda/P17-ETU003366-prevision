package controller;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import model.*;

public class FormDepenseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            Credit credit = new Credit();
            List<BaseObject> baseObjects = credit.findAll();
            List<Credit> credits = new ArrayList<>();
            for (BaseObject baseObject : baseObjects) {
                if (baseObject instanceof Credit) {
                    credits.add((Credit) baseObject);
                }
            }
            req.setAttribute("credits", credits);
            req.getRequestDispatcher("FormDepense.jsp").forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
    }
    
    
}


    