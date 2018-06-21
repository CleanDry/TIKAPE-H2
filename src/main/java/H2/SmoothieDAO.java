
package H2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SmoothieDAO extends AbstractNamedObjectDAO<Smoothie> {

    public SmoothieDAO(Database database, String tableName) {
        super(database, tableName);
    }

    @Override
    public Smoothie createFromRow(ResultSet resultSet) throws SQLException {
        Smoothie s = new Smoothie(resultSet.getInt("id"), resultSet.getString("nimi"));
        
        return s;
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmtEnsin = conn.prepareStatement(
                    "DELETE FROM SmoothieRaakaAine WHERE SmoothieRaakaAine.smoothie_id = ?");
            stmtEnsin.setInt(1, key);
            
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            stmt.setInt(1, key);
            stmt.executeUpdate();
        }
    } 
}
