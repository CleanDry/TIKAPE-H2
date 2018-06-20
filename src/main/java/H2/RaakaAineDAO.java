
package H2;

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
    
}
