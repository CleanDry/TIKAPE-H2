
package H2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RaakaAine extends AbstractNamedObject<RaakaAine> {

    public RaakaAine(Integer id, String name) {
        super(id, name);
    }

    @Override
    public RaakaAine createFromRow(ResultSet resultSet) throws SQLException {
        RaakaAine ra = new RaakaAine(resultSet.getInt("id"), resultSet.getString("name"));
        
        return ra;
    }

}
