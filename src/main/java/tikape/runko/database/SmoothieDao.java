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
import tikape.runko.domain.Smoothie;

/**
 *
 * @author jpssilve
 */
public class SmoothieDao implements Dao<Smoothie, Integer> {

    private Database db;

    public SmoothieDao(Database database) {
        this.db = database;
    }

    @Override
    public Smoothie findOne(Integer key) throws SQLException {
        Smoothie smoothie;
        try {
            Connection conn = getConnection();

            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie WHERE = ?")) {

                stmt.setInt(1, key);
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    return null;
                }

                Integer id = rs.getInt("id");
                String nimi = rs.getString("nimi");
                smoothie = new Smoothie(id, nimi);
                rs.close();
                
                return smoothie;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Smoothie> findAll() throws SQLException {
        List<Smoothie> smoothiet = new ArrayList<>();

        try {
            Connection conn = getConnection();

            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie")) {

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    String nimi = rs.getString("nimi");

                    smoothiet.add(new Smoothie(id, nimi));
                }

                rs.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return smoothiet;
    }

    @Override
    public void delete(Integer key) throws SQLException {

        try {
            Connection conn = getConnection();

            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Smoothie WHERE id = ?")) {

                stmt.setInt(1, key);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Smoothie save(Smoothie smoothie) throws SQLException {
        try {
            Connection conn = getConnection();

            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Smoothie (nimi) VALUES (?)")) {

                stmt.setString(1, smoothie.getNimi());
                stmt.executeUpdate();

                PreparedStatement stmt_2 = conn.prepareStatement("SELECT * FROM Smoothie WHERE nimi = ?");
                stmt_2.setString(1, smoothie.getNimi());

                ResultSet rs = stmt_2.executeQuery();
                smoothie.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return smoothie;
    }

    @Override
    public Smoothie saveOrUpdate(Smoothie smoothie) throws SQLException {
        try {
            Connection conn = getConnection();

            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Smoothie WHERE nimi = ?")) {
                stmt.setString(1, smoothie.getNimi());

                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    smoothie = save(smoothie);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return smoothie;
    }
}
