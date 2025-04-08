package controller;

import java. sql.*;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.*;

public class CreditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            String libelle = req.getParameter("libelle");
            Date date_debut =  Date.valueOf(req.getParameter("dateDebut"));
            Date date_fin =  Date.valueOf(req.getParameter("dateFin"));
            double montant = Double.parseDouble(req.getParameter("montant"));
            // Connection connection = Connexion.getConnection();
            Credit credit = new Credit(libelle,date_debut,date_fin,montant);
            credit.save();
            
            req.setAttribute("credit", credit);
            req.getRequestDispatcher("index.html").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            out.print(e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }
    
    
}


    