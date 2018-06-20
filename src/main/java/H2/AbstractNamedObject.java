
package H2;

import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class AbstractNamedObject<T> {
    private Integer id;
    private String name;

    public AbstractNamedObject(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + " " + this.name;
    }
    
    public abstract T createFromRow(ResultSet resultSet) throws SQLException;
}
