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
import tikape.runko.domain.Smoothie;

/**
 *
 * @author jpssilve
 */
public class SmoothieDao implements Dao<Smoothie, Integer> {
    private Database db;
    
    public SmoothieDao(Database db) {
        this.db = db;
    }

    @Override
    public Smoothie findOne(Integer key) throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie WHERE = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        
        Smoothie smoothie = new Smoothie(id, nimi);
        
        rs.close();
        stmt.close();
        conn.close();
        
        return smoothie;
    }

    @Override
    public List<Smoothie> findAll() throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie");

        ResultSet rs = stmt.executeQuery();
        List<Smoothie> smoothiet = new ArrayList<>(); 
        
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            
            smoothiet.add(new Smoothie(id, nimi));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return smoothiet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE * FROM Smoothie WHERE id = ?");
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public void save(Smoothie smoothie) throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Smoothie (nimi) VALUES (?)");
        
        stmt.setString(1, smoothie.getNimi());
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();
//        saveOrUpdate(smoothie);
    }

    @Override
    public Smoothie saveOrUpdate(Smoothie element) throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie WHERE id = ?");
        stmt.setInt(1, element.getId());
        
        ResultSet rs = stmt.executeQuery();
        Smoothie sm  = null;
        
        if (!rs.next()) {
            try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO Smoothie (nimi) VALUES (?)")) {
                    stmt2.setString(1, element.getNimi());
                    stmt2.executeUpdate();
                }
        } 
               
        return new  Smoothie(element.getId(), element.getNimi());
    }
    
}
