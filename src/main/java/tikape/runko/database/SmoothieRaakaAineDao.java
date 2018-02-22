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
import tikape.runko.domain.SmoothieRaakaAine;

/**
 *
 * @author jpssilve
 */
public class SmoothieRaakaAineDao implements Dao<SmoothieRaakaAine, Integer> {

    private Database db;

    public SmoothieRaakaAineDao(Database database) {
        this.db = db;
    }

    @Override
    public SmoothieRaakaAine findOne(Integer key) throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer smoothie_id = rs.getInt("smoothie_id");
        Integer raaka_aine_id = rs.getInt("raaka_aine_id");
        Integer jarjestys = rs.getInt("jarjestys");
        String maara = rs.getString("maara");
        String ohje = rs.getString("ohje");

        SmoothieRaakaAine sra = new SmoothieRaakaAine(id, smoothie_id, raaka_aine_id, jarjestys, maara, ohje);

        rs.close();
        stmt.close();
        conn.close();

        return sra;
    }

    @Override
    public List<SmoothieRaakaAine> findAll() throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<SmoothieRaakaAine> sm_raaka_aineet = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer smoothie_id = rs.getInt("smoothie_id");
            Integer raaka_aine_id = rs.getInt("raaka_aine_id");
            Integer jarjestys = rs.getInt("jarjestys");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");

            sm_raaka_aineet.add(new SmoothieRaakaAine(id, smoothie_id, raaka_aine_id, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        conn.close();

        return sm_raaka_aineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE * FROM SmoothieRaakaAine WHERE id = ?");
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }

    @Override
    public void save(SmoothieRaakaAine sra) throws SQLException {
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO SmoothieRaakAine (smoothie_id, raaka_aine_ id, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, sra.getSmoothieId());
        stmt.setInt(2, sra.getRaaka_aineId());
        stmt.setInt(3, sra.getJarjestys());
        stmt.setString(4, sra.getMaara());
        stmt.setString(5, sra.getOhje());
        
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    @Override
    public SmoothieRaakaAine saveOrUpdate(SmoothieRaakaAine smra) throws SQLException {
        
        Connection conn = this.db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SmoothieRaakaAine WHERE id = ?");
        
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
