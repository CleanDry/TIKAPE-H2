
package H2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Tilastot {
    private Database database;

    public Tilastot(Database database) {
        this.database = database;
    }
    
    public Integer montakoRaakaAinettaYhteensa() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM RaakaAine");
            
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                return result.getInt(1);
            } 
            
            return null;
        }
    }
    
    public Integer montakoSmoothietaYhteensa() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM Smoothie");
            
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                return result.getInt(1);
            }  
            
            return null;
        }
    }
}
