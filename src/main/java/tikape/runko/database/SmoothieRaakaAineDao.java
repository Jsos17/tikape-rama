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
import tikape.runko.domain.SmoothieRaakaAine;

/**
 *
 * @author jpssilve
 */
public class SmoothieRaakaAineDao implements Dao<SmoothieRaakaAine, Integer> {

    private Database db;

    public SmoothieRaakaAineDao(Database database) {
        this.db = database;
    }

    @Override
    public SmoothieRaakaAine findOne(Integer key) throws SQLException {
        SmoothieRaakaAine sra;
        try (Connection conn = this.db.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine WHERE id = ?")) {
            
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            
            if (!rs.next()) {
                return null;
            }   
            
            Integer id = rs.getInt("id");
            Integer smoothie_id = rs.getInt("smoothie_id");
            Integer raaka_aine_id = rs.getInt("raaka_aine_id");
            String jarjestys = rs.getString("jarjestys");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");
            sra = new SmoothieRaakaAine(id, smoothie_id, raaka_aine_id, jarjestys, maara, ohje);
            rs.close();
        }

        return sra;
    }

    @Override
    public List<SmoothieRaakaAine> findAll() throws SQLException {
        List<SmoothieRaakaAine> sm_raaka_aineet = new ArrayList<>();
        try (Connection conn = this.db.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine")) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer smoothie_id = rs.getInt("smoothie_id");
                Integer raaka_aine_id = rs.getInt("raaka_aine_id");
                String jarjestys = rs.getString("jarjestys");
                String maara = rs.getString("maara");
                String ohje = rs.getString("ohje");
                
                sm_raaka_aineet.add(new SmoothieRaakaAine(id, smoothie_id, raaka_aine_id, jarjestys, maara, ohje));
            }   
            
            rs.close();
        }

        return sm_raaka_aineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = this.db.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM SmoothieRaakaAine WHERE id = ?")) {
            
            stmt.setInt(1, key);
            stmt.executeUpdate();
        }
    }

    @Override
    public SmoothieRaakaAine save(SmoothieRaakaAine sra) throws SQLException {
//        SmoothieRaakaAine smra = new SmoothieRaakaAine();
        try (Connection conn = this.db.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO SmoothieRaakaAine (smoothie_id, raaka_aine_id, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)")) {
            
            stmt.setInt(1, sra.getSmoothieId());
            stmt.setInt(2, sra.getRaaka_aineId());
            stmt.setString(3, sra.getJarjestys());
            stmt.setString(4, sra.getMaara());
            stmt.setString(5, sra.getOhje());
            
            stmt.executeUpdate();
            
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine WHERE smoothie_id = ? AND raaka_aine_id = ?");
            stmt2.setInt(1, sra.getSmoothieId());
            stmt2.setInt(2, sra.getRaaka_aineId());
            ResultSet rs = stmt2.executeQuery();
            
            sra.setId(rs.getInt("id"));
        }
        
        return sra;
    }

    @Override
    public SmoothieRaakaAine saveOrUpdate(SmoothieRaakaAine smra) throws SQLException {
        
        try (Connection conn = this.db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine WHERE smoothie_id = ? AND raaka_aine_id = ?")) {
            
            stmt.setInt(1, smra.getSmoothieId());
            stmt.setInt(2, smra.getRaaka_aineId());
            
            ResultSet rs = stmt.executeQuery();
            
            if (!rs.next()) {
                smra = save(smra);
            }
        }
        
        return smra;
    }

}
