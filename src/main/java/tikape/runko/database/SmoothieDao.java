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
        Smoothie smoothie;
        try (Connection conn = this.db.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie WHERE = ?")) {
            
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }   
            
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            smoothie = new Smoothie(id, nimi);
            rs.close();
        }
        
        return smoothie;
    }

    @Override
    public List<Smoothie> findAll() throws SQLException {
        List<Smoothie> smoothiet = new ArrayList<>();
        
        try (Connection conn = this.db.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie")) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nimi = rs.getString("nimi");
                
                smoothiet.add(new Smoothie(id, nimi));
            }   
            
            rs.close();
        }
        
        return smoothiet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = this.db.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement("DELETE * FROM Smoothie WHERE id = ?")) {
            
            stmt.setInt(1, key);
            stmt.executeUpdate();            
        }
    }

    @Override
    public Smoothie save(Smoothie smoothie) throws SQLException {
        try (Connection conn = this.db.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO Smoothie (nimi) VALUES (?)")) {
            
            stmt.setString(1, smoothie.getNimi());
            stmt.executeUpdate();
            
            
            PreparedStatement stmt_2 = conn.prepareStatement("SELECT * FROM Smoothie WHERE nimi = ?");
            stmt_2.setString(1, smoothie.getNimi());
            
            ResultSet rs = stmt_2.executeQuery();
            smoothie.setId(rs.getInt("id"));
//            smoothie.setNimi(rs.getString("nimi"));
        }
        
        return smoothie;
    }

    @Override
    public Smoothie saveOrUpdate(Smoothie smoothie) throws SQLException {
        
        try (Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie WHERE nimi = ?")) {
            stmt.setString(1, smoothie.getNimi());

            ResultSet rs = stmt.executeQuery();
            
            if (!rs.next()) {
                smoothie = save(smoothie);
            } 
        }
               
        return smoothie;
    }
    
}
