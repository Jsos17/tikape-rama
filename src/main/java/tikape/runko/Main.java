package tikape.runko;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.database.SmoothieDao;
import tikape.runko.database.SmoothieRaakaAineDao;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Smoothie;
import tikape.runko.domain.SmoothieRaakaAine;

public class Main {

//    public static void main(String[] args) throws Exception {
//        Database database = new Database("jdbc:sqlite:opiskelijat.db");
//        database.init();
//
//        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
//
//        get("/", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("viesti", "tervehdys");
//
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());
//    }
    public static void main(String[] args) throws Exception {
        
//        if (System.getenv("PORT") != null) {
//            Spark.port(Integer.valueOf(System.getenv("PORT")));
//        }
        
        Database database = new Database("jdbc:sqlite:smoothiet.db");
        database.init();
        
        SmoothieDao smoothieDao = new SmoothieDao(database);
        RaakaAineDao raDao = new RaakaAineDao(database);
        SmoothieRaakaAineDao sraDao = new SmoothieRaakaAineDao(database);

//        ArrayList <Smoothie> smoothiet = new ArrayList<>();
//        ArrayList<RaakaAine> raakaAineet = new ArrayList<>();
//        ArrayList <SmoothieRaakaAine> SmoothieRaakaAineet = new ArrayList<>();
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/smoothiet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDao.findAll());
            map.put("raakaAineLista", raDao.findAll());

            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/raaka-aineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineLista", raDao.findAll());

            return new ModelAndView(map, "raakaaine");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/smoothiereseptit", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Smoothie> smoothiet = smoothieDao.findAll();
            List<SmoothieRaakaAine> srat = sraDao.findAll();
            
            List<SmoothieRaakaAine> matchit = new ArrayList<>();
            for (int i = 0; i < smoothiet.size(); i++) {
                for (int j = 0; j < srat.size(); j++) {
                    
                    if (smoothiet.get(i).getId().equals(srat.get(j).getSmoothieId())) {
                        matchit.add(srat.get(j));
                    }
                }
            }
            
            map.put("smoothieRaakaAineet", matchit);
            
            return new ModelAndView(map, "smoothiereseptit");
        }, new ThymeleafTemplateEngine());

        //POST pyynnön käsittely (raaka-aineen lisääminen) raaka-aineet sivustolla
        Spark.post("/raaka-aineet", (req, res) -> {
            String aineNimi = req.queryParams("aine");
            
            if (!aineNimi.equals("")) {
                RaakaAine ra = new RaakaAine();
                ra.setNimi(aineNimi);

                //            RaakaAine ra = new RaakaAine(raakaAineet.size() + 1, aineNimi);
                //            raakaAineet.add(ra);
                raDao.saveOrUpdate(ra);
            }
            
            res.redirect("/raaka-aineet");
            return "";
        });

        //POST pyynnön käsittely (smoothien lisääminen) smoothiet sivustolla
        Spark.post("/smoothiet", (req, res) -> {
            String smoothieNimi = req.queryParams("nimi");

            if (!smoothieNimi.equals("")) {
                Smoothie s = new Smoothie();
                s.setNimi(smoothieNimi);

                //            Smoothie s = new Smoothie(smoothiet.size() + 1, smoothieNimi);
                //            smoothiet.add(s);
                smoothieDao.saveOrUpdate(s);
            } 

            res.redirect("/smoothiet");
            return "";
        });

        Spark.post("/raaka-aineet/:id/delete", (req, res) -> {

            raDao.delete(Integer.parseInt(req.params(":id")));

            res.redirect("/raaka-aineet");
            return "";
        });
        
        
        // ongelma saada dropdown-valikon parametrit ulos 
        // alla oleva ei toimi
        Spark.post("/smoothiereseptit", (req, res) -> {
            String smoothie_nimi = req.params("smoothie");
            String raaka_aine_nimi = req.params("raakaaine");
            String jarjestys = req.queryParams("jarjestys");
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");
            
            if (!jarjestys.equals("") && !maara.equals("") && !ohje.equals("")) {
               
                List<Smoothie> smoothiet = smoothieDao.findAll();
                List<RaakaAine> rat = raDao.findAll();

                Integer smoothie_id = -1;
                Integer raaka_aine_id = -1;

                for (int i = 0; i < smoothiet.size(); i++) {
                    if (smoothiet.get(i).getNimi().equals(smoothie_nimi)) {
                        smoothie_id = smoothiet.get(i).getId();
                        break;
                    }
                }
                for (int i = 0; i < rat.size(); i++) {
                    if (rat.get(i).getNimi().equals(raaka_aine_nimi)) {
                        raaka_aine_id = rat.get(i).getId();
                        break;
                    }
                }

                SmoothieRaakaAine sra = new SmoothieRaakaAine();
                sra.setSmoothie_id(smoothie_id);
                sra.setRaaka_aine_id(raaka_aine_id);
                sra.setJarjestys(jarjestys);
                sra.setMaara(maara);
                sra.setOhje(ohje);
                
                sraDao.saveOrUpdate(sra);
            }
            
            res.redirect("smoothiet");
            return "";
        });
        
        
    }
    
//    public static Connection getConnection() throws Exception {
//        String dbUrl = System.getenv("JDBC_DATABASE_URL");
//        if (dbUrl != null && dbUrl.length() > 0) {
//            return DriverManager.getConnection(dbUrl);
//        }
//
//        return DriverManager.getConnection("jdbc:sqlite:huonekalut.db");
//    }
}
