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
import static tikape.runko.Main.getConnection;

/**
 *
 * @author jpssilve
 */
public class TilastoKyselyt {

    private Database db;

    public TilastoKyselyt(Database database) {
        this.db = database;
    }

    public Integer monessakoAnnoksessaEsiintyyRaakaAine(String raaka_aine_nimi) throws SQLException {
        Integer monessa = -1;
        try {
            Connection conn = getConnection();
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT (DISTINCT Smoothie.nimi) FROM Smoothie, SmoothieRaakaAine, RaakaAine "
                    + "WHERE RaakaAine.nimi = ? AND SmoothieRaakaAine.raaka_aine_id = RaakaAine.id AND SmoothieRaakaAine.smoothie_id = Smoothie.id")) {

                stmt.setString(1, raaka_aine_nimi);
                
                ResultSet rs = stmt.executeQuery();
                monessa = rs.getInt(1);
                
                rs.close();
                conn.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return monessa;
    }
}
