package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
//        List<String> lauseet = sqliteLauseet();
//
//        // "try with resources" sulkee resurssin automaattisesti lopuksi
//        try (Connection conn = getConnection()) {
//            Statement st = conn.createStatement();
//
//            // suoritetaan komennot
//            for (String lause : lauseet) {
//                System.out.println("Running command >> " + lause);
//                st.executeUpdate(lause);
//            }
//
//        } catch (Throwable t) {
//            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
//            System.out.println("Error >> " + t.getMessage());
//        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Smoothie (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("CREATE TABLE RaakaAine (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("CREATE TABLE SmoothieRaakaAine (id integer PRIMARY KEY, "
                + "smoothie_id integer, raaka_aine_id integer, "
                + "jarjestys varchar(255), maara varchar(255), ohje varchar(1000), "
                + "FOREIGN KEY (smoothie_id) REFERENCES Smoothie(id), FOREIGN KEY (raaka_aine_id) REFERENCES RaakaAine(id)");

        return lista;
    }
}
