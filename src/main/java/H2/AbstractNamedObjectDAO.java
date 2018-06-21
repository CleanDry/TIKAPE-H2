
package H2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNamedObjectDAO<T extends AbstractNamedObject> 
        implements Dao<T, Integer>{
    
    protected Database database;
    protected String tableName;

    public AbstractNamedObjectDAO(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }
    
    @Override
    public T findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi FROM " + tableName + " WHERE id = ?");
            stmt.setInt(1, key);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return createFromRow(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error when looking for a row in " + tableName + " with id " + key);
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<T> findAll() throws SQLException {
        List<T> haettavat = new ArrayList<>();

        try (Connection conn = database.getConnection();
            ResultSet result = conn.prepareStatement("SELECT id, nimi FROM " + tableName).executeQuery()) {

            while (result.next()) {
                haettavat.add(createFromRow(result));
            }
        }

        return haettavat;
    }
    

    @Override
    public T saveOrUpdate(T object) throws SQLException {
        T byName = findByName(object.getName());

        if (byName != null) {
            return byName;
        }
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (nimi) VALUES (?)");
            stmt.setString(1, object.getName());
            stmt.executeUpdate();
        }

        return findByName(object.getName());
    }
    

    private T findByName(String name) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi FROM " + tableName + " WHERE nimi = ?");
            stmt.setString(1, name);

            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) {
                    return null;
                }

                return createFromRow(result);
            }
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            stmt.setInt(1, key);
            stmt.executeUpdate();
        }
    }    
    
    public abstract T createFromRow(ResultSet resultSet) throws SQLException;
    
}
