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

/**
 *
 * @author jpssilve
 */
public class TilastoKyselyt {
    private Database db;

    public TilastoKyselyt(Database database) {
        this.db = database;
    }
    
    public int monessakoAnnoksessaEsiintyyRaakaAine(String raaka_aine_nimi) throws SQLException {
        
        int monessa = 0;
        
        try (Connection conn = this.db.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM SmoothieRaakaAine, RaakaAine "
                            + "WHERE SmoothieRaakaAine.raaka_aine_id = RaakaAine.id AND RaakaAine.nimi = ?")) {
            stmt.setString(1, raaka_aine_nimi);
            
            ResultSet rs =  stmt.executeQuery();
            
            if (!rs.next()) {
                monessa = rs.getInt(1);
            }    
            
            return monessa;
        }
    }
    
    
    
}
