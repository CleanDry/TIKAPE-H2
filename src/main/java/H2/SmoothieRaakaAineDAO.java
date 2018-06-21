/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package H2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SmoothieRaakaAineDAO implements Dao<SmoothieRaakaAine, Integer> {
    
    private Database database;

    public SmoothieRaakaAineDAO(Database database) {
        this.database = database;
    }
    
    @Override
    public SmoothieRaakaAine findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM SmoothieRaakaAine WHERE id = ?");
            stmt.setInt(1, key);
            
            ResultSet result = stmt.executeQuery();
            
            if (result != null) {
                return this.createFromRow(result);
            } 
            
            return null;
        }
    }
    
    @Override
    public List<SmoothieRaakaAine> findAll() throws SQLException {
        List<SmoothieRaakaAine> smoothieRaakaAineet = new ArrayList<>();
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM SmoothieRaakaAine");
            
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                smoothieRaakaAineet.add(this.createFromRow(result));
            }
        }
        
        return smoothieRaakaAineet;
    }

    @Override
    public SmoothieRaakaAine saveOrUpdate(SmoothieRaakaAine object) throws SQLException {
        SmoothieRaakaAine SRA = this.findByIds(object.getSmoothieId(), object.getRaakaAineId());
        
        if (SRA != null) {
            return this.update(object);
        } else {
            return this.save(object);
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        SmoothieRaakaAine SRA = this.findOne(key);
        
        if (SRA != null) {
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM SmoothieRaakaAine WHERE id = ?");
                stmt.setInt(1, key);

                stmt.executeUpdate();            
            }
        }
    }
    
    
    public List<RaakaAine> findAllResources(Integer key) throws SQLException {
        List<RaakaAine> raakaAineet = new ArrayList<>();
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM RaakaAine, SmoothieRaakaAine, Smoothie "
                            + "WHERE RaakaAine.id = SmoothieRaakaAine.raaka_aine_id "
                            + "AND SmoothieRaakaAine.smoothie_id = Smoothie.id "
                            + "AND Smoothie.id = ? "
                            + "GROUP BY SmoothieRaakaAine.jarjestys, RaakaAine.id, SmoothieRaakaAine.id, Smoothie.id");
            stmt.setInt(1, key);
            
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                raakaAineet.add(new RaakaAine(result.getInt("id"), result.getString("nimi")));
            }
        }
        
        return raakaAineet;
    }

    
    private SmoothieRaakaAine createFromRow(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
           return null; 
        }
        
        SmoothieRaakaAine sra = new SmoothieRaakaAine(
                resultSet.getInt("id"),
                resultSet.getInt("smoothie_id"),
                resultSet.getInt("raaka_aine_id"),
                resultSet.getInt("jarjestys"),
                resultSet.getString("maara"),
                resultSet.getString("ohje")
        );
        
        return sra;
    }
    
    public SmoothieRaakaAine findByIds(Integer smoothieId, Integer raakaAineId) throws SQLException {
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM SmoothieRaakaAine WHERE smoothie_id = ? AND raaka_aine_id = ?");
            stmt.setInt(1, smoothieId);
            stmt.setInt(2, raakaAineId);
            
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                return this.createFromRow(result);
            }
            
            return null;
        }
    }
    
    private SmoothieRaakaAine update(SmoothieRaakaAine sra) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE SmoothieRaakaAine SET jarjestys = ?, maara = ?, ohje = ? WHERE smoothie_id = ? AND raaka_aine_id = ?");
            
            stmt.setInt(1, sra.getJarjestys());
            stmt.setString(2, sra.getMaara());
            stmt.setString(3, sra.getOhje());
            stmt.setInt(4, sra.getSmoothieId());
            stmt.setInt(5, sra.getRaakaAineId());
            
            stmt.executeUpdate();
        }
        
        return this.findByIds(sra.getSmoothieId(), sra.getRaakaAineId());
    }
    
    private SmoothieRaakaAine save(SmoothieRaakaAine sra) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO SmoothieRaakaAine (smoothie_id, raaka_aine_id, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, sra.getSmoothieId());
            stmt.setInt(2, sra.getRaakaAineId());
            stmt.setInt(3, sra.getJarjestys());
            stmt.setString(4, sra.getMaara());
            stmt.setString(5, sra.getOhje());
            
            stmt.executeUpdate();
        }
        
        return this.findByIds(sra.getSmoothieId(), sra.getRaakaAineId());
    }
    
    public void deleteByResourceId(Integer raakaAineId) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM SmoothieRaakaAine WHERE raaka_aine_id = ?");
            stmt.setInt(1, raakaAineId);
            
            stmt.executeUpdate();
        }
    }
    
    public void deleteBySmoothieId(Integer smoothieId) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM SmoothieRaakaAine WHERE smoothie_id = ?");
            stmt.setInt(1, smoothieId);
            
            stmt.executeUpdate();
        }
    }
}
