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
import java.util.List;
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
        
        // kesken
        return null;
    }

    @Override
    public List<SmoothieRaakaAine> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
