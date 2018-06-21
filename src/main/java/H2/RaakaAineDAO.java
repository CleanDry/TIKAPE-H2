
package H2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RaakaAineDAO extends AbstractNamedObjectDAO<RaakaAine> {

    public RaakaAineDAO(Database database, String tableName) {
        super(database, tableName);
    }

    @Override
    public RaakaAine createFromRow(ResultSet resultSet) throws SQLException {
        RaakaAine ra = new RaakaAine(resultSet.getInt("id"), resultSet.getString("nimi"));
        
        return ra;
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmtEnsin = conn.prepareStatement(
                    "DELETE FROM SmoothieRaakaAine WHERE SmoothieRaakaAine.raaka_aine_id = ?");
            stmtEnsin.setInt(1, key);
            
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            stmt.setInt(1, key);
            stmt.executeUpdate();
        }
    } 
}
