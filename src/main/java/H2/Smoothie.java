/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package H2;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Smoothie extends AbstractNamedObject<Smoothie>{

    public Smoothie(Integer id, String nimi) {
        super(id, nimi);
    }

    @Override
    public Smoothie createFromRow(ResultSet resultSet) throws SQLException {
        Smoothie s = new Smoothie(resultSet.getInt("id"), resultSet.getString("name"));
        
        return s;
    }

    
    
    
}
