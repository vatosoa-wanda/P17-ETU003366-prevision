package controller;

import java. sql.*;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.*;
import java.util.List;
import java.util.ArrayList;

public class DepenseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            int id_credit = Integer.parseInt(req.getParameter("idCredit"));
            double montant = Double.parseDouble(req.getParameter("montant"));
            Date date =  Date.valueOf(req.getParameter("date"));

            // Depense depense = new Depense(id_credit,montant,date);
            Credit.serviceAjoutDepense(id_credit,montant,date);
            // depense.save();
            res.sendRedirect("formDepense");
        } catch (Exception e) {
            e.printStackTrace();
            out.print(e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            System.out.println("doGet() est bien appelé !");
            Credit credit = new Credit();
            List<BaseObject> baseObjects = credit.findAll();
            List<Credit> credits = new ArrayList<>();
            for (BaseObject baseObject : baseObjects) {
                if (baseObject instanceof Credit) {
                    credits.add((Credit) baseObject);
                }
            }
            req.setAttribute("credits", credits);
            req.getRequestDispatcher("listeDepense.jsp").forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
    }
    
}


    