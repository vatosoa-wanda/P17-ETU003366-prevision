package model;

import java. sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class Depense extends BaseObject  {
    private int id_credit;
    private double montant;
    private Date date;

    public Depense(){}

    public Depense(int id, int id_credit, double montant, Date date) {
        super(id);
        this.id_credit = id_credit;
        this.montant = montant;
        this.date = date;
    }

    public Depense(int id_credit, double montant, Date date) {
        this.id_credit = id_credit;
        this.montant = montant;
        this.date = date;
    }

    public Depense(int id_credit, double montant) {
        this.id_credit = id_credit;
        this.montant = montant;
    }

    public int getIdCredit() {
        return this.id_credit;
    }

    public void setIdCredit(int id_credit) {
        this.id_credit = id_credit;
    }

    public double getMontant() {
        return this.montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return this.date;
    }
 
    public Depense getById(int id) throws SQLException {
        String query = "SELECT * FROM depense WHERE id = ?";
        try (Connection connection = new Connexion().getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                Depense depense = new Depense(resultSet.getInt("id"),resultSet.getInt("id_credit"),resultSet.getDouble("montant"),resultSet.getDate("date"));
                return depense;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public Depense getByIdCredit(int id_credit) throws SQLException {
        String query = "SELECT * FROM depense WHERE id_credit = ?";
        try (Connection connection = new Connexion().getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_credit);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                Depense depense = new Depense(resultSet.getInt("id"),resultSet.getInt("id_credit"),resultSet.getDouble("montant"),resultSet.getDate("date"));
                return depense;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }


    public List<BaseObject> getTotalDepenseParCredit() throws SQLException {
        List<BaseObject> depenses = new ArrayList<>();
        String query = "SELECT id_credit,sum(montant) as montant FROM depense GROUP BY id_credit";
        try (Connection connection = new Connexion().getConnexion();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Depense depense = new Depense(resultSet.getInt("id_credit"),resultSet.getDouble("montant"));
                depenses.add(depense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return depenses;
    }

    @Override
    public void save() throws SQLException {
        String query = "INSERT INTO depense (id_credit,montant,date) VALUES (?,?,?)";
        try (Connection connection = new Connexion().getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.id_credit);
            preparedStatement.setDouble(2, this.montant);
            preparedStatement.setDate(3, this.date);
            preparedStatement.executeUpdate();
            System.out.println("depense ajouté avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<BaseObject> findAll() throws SQLException {
        List<BaseObject> depenses = new ArrayList<>();
        String query = "SELECT * FROM depense";
        
        try (Connection connection = new Connexion().getConnexion();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Depense depense = new Depense(resultSet.getInt("id"),resultSet.getInt("id_credit"),resultSet.getDouble("montant"),resultSet.getDate("date"));
                depenses.add(depense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return depenses;
    }


}