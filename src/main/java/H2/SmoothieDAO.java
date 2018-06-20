
package H2;

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
    
}
