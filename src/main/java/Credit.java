package model;

import java. sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class Credit extends BaseObject  {
    private String libelle;
    private Date date_debut;
    private Date date_fin;
    private double montant;

    public Credit(){}

    public Credit(int id, String libelle, Date date_debut, Date date_fin, double montant) {
        super(id);
        this.libelle = libelle;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.montant = montant;
    }

    public Credit(String libelle, Date date_debut, Date date_fin, double montant) {
        this.libelle = libelle;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.montant = montant;
    }

    public Credit(int id, String libelle, double montant) {
        super(id);
        this.libelle = libelle;
        this.montant = montant;
    }

    public Credit(String libelle, double montant) {
        this.libelle = libelle;
        this.montant = montant;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Date getDate_debut() {
        return this.date_debut;
    }

    public Date getDate_fin() {
        return this.date_fin;
    }

    public double getMontant() {
        return this.montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }


    @Override
    public void save() throws SQLException {
        String query = "INSERT INTO credit (libelle,date_debut,date_fin,montant) VALUES (?,?,?,?)";
        try (Connection connection = new Connexion().getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.libelle);
            preparedStatement.setDate(2, this.date_debut);
            preparedStatement.setDate(3, this.date_fin);
            preparedStatement.setDouble(4, this.montant);
            preparedStatement.executeUpdate();
            System.out.println("credit ajouté avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<BaseObject> findAll() throws SQLException {
        List<BaseObject> credits = new ArrayList<>();
        String query = "SELECT * FROM credit";
        
        try (Connection connection = new Connexion().getConnexion();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Credit credit = new Credit(resultSet.getInt("id"),resultSet.getString("libelle"),resultSet.getDate("date_debut"),resultSet.getDate("date_fin"),resultSet.getInt("montant"));
                credits.add(credit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return credits;
    }

    public double getTotalDepenseById() throws SQLException {
        String query = "SELECT sum(montant) as total FROM depense WHERE id_credit = ?";
        try (Connection connection = new Connexion().getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                double total = resultSet.getDouble("total");
                return total;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return -1;
    }

    public double getResteById() throws SQLException {
        String query = "SELECT (tab.montant-tab.total) as reste FROM (SELECT c.montant,sum(d.montant) as total FROM depense d JOIN credit c on c.id=d.id_credit WHERE d.id_credit = ?) as tab";
        try (Connection connection = new Connexion().getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
               
                if (resultSet.next()) {
                double reste = resultSet.getDouble("reste");
                System.out.println("Reste"+reste);
                return reste;
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return -1;
    }

    @Override
    public Credit getById(int id) throws SQLException {
        String query = "SELECT * FROM credit WHERE id = ?";
        try (Connection connection = new Connexion().getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                Credit credit = new Credit(resultSet.getInt("id"),resultSet.getString("libelle"),resultSet.getDate("date_debut"),resultSet.getDate("date_fin"),resultSet.getInt("montant"));
                return credit;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static void serviceAjoutDepense(int id_credit, double montant, Date date) throws SQLException {
        Connection connection = null;
        try {
            connection = new Connexion().getConnexion();
            
            connection.setAutoCommit(false);
            

            // Étape 1 : Vérifier si le compte existe
            Credit c = new Credit();
            Credit credit = c.getById(id_credit);
            double reste = 0;

            Depense dep = new Depense();
            Depense d = dep.getByIdCredit(id_credit);
            if (d == null) {
                reste = credit.getMontant();
            } else{
                reste = credit.getResteById();
            }

            if (reste < montant  || reste!=0) {
                throw new SQLException("Solde insuffisant");
            }

            Depense depense = new Depense(id_credit,montant,date);
            depense.save();
         
            connection.commit();
            System.out.println("Depense valide");
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback effectué après l'erreur.");
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new SQLException("Erreur lors de l'ajout de depense : " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

  
}
