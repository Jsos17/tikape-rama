/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.RaakaAine;

/**
 *
 * @author jpssilve
 */
public class RaakaAineDao implements Dao<RaakaAine, Integer> {
    
    private Database db;
    
    public RaakaAineDao(Database db) {
        this.db = db;
    }

    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        
        RaakaAine ra = new RaakaAine(id, nimi);
        
        rs.close();
        stmt.close();
        conn.close();
        
        return ra;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<RaakaAine> r_aineet = new ArrayList<>(); 
        
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            
            r_aineet.add(new RaakaAine(id, nimi));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return r_aineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE * FROM RaakaAine WHERE id = ?");
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public void save(RaakaAine raaka_aine) throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
        stmt.setString(1, raaka_aine.getNimi());
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public RaakaAine saveOrUpdate(RaakaAine element) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
