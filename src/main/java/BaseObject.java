package model;
import java.util.List;
import java.sql.SQLException; 

public abstract class BaseObject {
    private int id ;

    public BaseObject(int id) {
        this.id = id;
    }

    // Constructeur sans paramètre si nécessaire
    public BaseObject() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract void save() throws SQLException;

    public abstract  List<BaseObject> findAll() throws SQLException;

    public abstract BaseObject getById(int id) throws SQLException ;

    // public abstract void update() throws SQLException ;

}