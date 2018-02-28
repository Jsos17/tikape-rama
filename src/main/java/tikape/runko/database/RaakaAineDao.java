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
import static tikape.runko.Main.getConnection;
import tikape.runko.domain.RaakaAine;

/**
 *
 * @author jpssilve
 */
public class RaakaAineDao implements Dao<RaakaAine, Integer> {

    private Database db;

    public RaakaAineDao(Database database) {
        this.db = database;
    }

    @Override
    public RaakaAine findOne(Integer key) throws SQLException {

        RaakaAine ra = null;

        try {
            Connection conn = getConnection();

            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE id = ?")) {

                stmt.setInt(1, key);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    return null;
                }
                Integer id = rs.getInt("id");

                String nimi = rs.getString("nimi");
                ra = new RaakaAine(id, nimi);
                rs.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return ra;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        List<RaakaAine> r_aineet = new ArrayList<>();

        try {
            Connection conn = getConnection();

            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine")) {

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    String nimi = rs.getString("nimi");
                    r_aineet.add(new RaakaAine(id, nimi));
                }

                rs.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return r_aineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {

        try {
            Connection conn = getConnection();

            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id = ?")) {

                stmt.setInt(1, key);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public RaakaAine save(RaakaAine raaka_aine) throws SQLException {

        try {
            Connection conn = getConnection();

            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)")) {

                stmt.setString(1, raaka_aine.getNimi());
                stmt.executeUpdate();

                PreparedStatement stmt_2 = conn.prepareStatement("SELECT * FROM RaakaAine WHERE nimi = ?");
                stmt_2.setString(1, raaka_aine.getNimi());

                ResultSet rs = stmt_2.executeQuery();
                raaka_aine.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return raaka_aine;
    }

    @Override
    public RaakaAine saveOrUpdate(RaakaAine raaka_aine) throws SQLException {

        try {
            Connection conn = getConnection();

            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE nimi = ?")) {
                stmt.setString(1, raaka_aine.getNimi());

                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    raaka_aine = save(raaka_aine);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return raaka_aine;
    }
}
