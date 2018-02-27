package tikape.runko;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
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
import tikape.runko.database.TilastoKyselyt;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Smoothie;
import tikape.runko.domain.SmoothieRaakaAine;
import tikape.runko.domain.SmoothieRaakaAineTulostusApu;

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
        TilastoKyselyt tilastokyselyt = new TilastoKyselyt(database); 

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
        
        Spark.get("/reseptinlisays", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDao.findAll());
            map.put("raakaAineLista", raDao.findAll());

            return new ModelAndView(map, "reseptinlisays");
        }, new ThymeleafTemplateEngine());

        Spark.get("/raaka-aineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineLista", raDao.findAll());

            return new ModelAndView(map, "raakaaine");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.get("/smoothiereseptit", (req, res) -> {
            HashMap map = new HashMap<>();
            List<SmoothieRaakaAine> srat = sraDao.findAll();
            
            HashMap<Integer, String> smoothieNimet = new HashMap<>();
            HashMap<Integer, String> raaka_aineNimet = new HashMap<>();
            smoothieDao.findAll().stream().forEach(sm -> smoothieNimet.put(sm.getId(), sm.getNimi()));
            raDao.findAll().stream().forEach(ra -> raaka_aineNimet.put(ra.getId(), ra.getNimi()));
            
            List<SmoothieRaakaAineTulostusApu> smraTulAvut = new ArrayList<>();
            for (int i = 0; i < srat.size(); i++) {
               SmoothieRaakaAine sra = srat.get(i);
               
               String smoothieNimi = smoothieNimet.get(sra.getSmoothieId());
               String raaka_aineNimi = raaka_aineNimet.get(sra.getRaaka_aineId());
               String jarjestys = sra.getJarjestys();
               String maara = sra.getMaara();
               String ohje = sra.getOhje();
               
               smraTulAvut.add(new SmoothieRaakaAineTulostusApu(smoothieNimi, raaka_aineNimi, jarjestys, maara, ohje));
            }
            
            Collections.sort(smraTulAvut);
            
            map.put("smoothieRaakaAineet", smraTulAvut);
            
            return new ModelAndView(map, "smoothiereseptit");
        }, new ThymeleafTemplateEngine());
        
        List <Integer> maarat = new ArrayList <>();
        
        Spark.get("/tilastokyselyt", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineLista", raDao.findAll());
            map.put("maarat", maarat);
            return new ModelAndView(map, "tilastokyselyt");
            
        }, new ThymeleafTemplateEngine());
        
        
        Spark.post("/tilastokyselyt", (req, res) -> {
            String raakaAine = req.queryParams("raakaaine");
            
            maarat.add(tilastokyselyt.monessakoAnnoksessaEsiintyyRaakaAine(raakaAine));
            res.redirect("/tilastokyselyt");
            return "";      
        });
        //POST pyynnön käsittely (raaka-aineen lisääminen) raaka-aineet sivustolla
        
        
        Spark.post("/raaka-aineet", (req, res) -> {
            String aineNimi = req.queryParams("aine");
            
           
            res.redirect("/raaka-aineet");
            
            return "";
        });
        
        //POST pyynnön käsittely (smoothie reseptin lisääminen) reseptinlisays sivustolla
        Spark.post("/reseptinlisays", (req, res) -> {
            String smoothienNimi = req.queryParams("smoothie");
            String raakaAine = req.queryParams("raakaaine");
            String jarjestys = req.queryParams("jarjestys");
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");

            if (!jarjestys.equals("") && !maara.equals("") && !ohje.equals("")) {
                Integer smoothieId = smoothieDao.findAll().stream().filter(s -> s.getNimi().equals(smoothienNimi)).findFirst().get().getId();
                Integer raId = raDao.findAll().stream().filter(s -> s.getNimi().equals(raakaAine)).findFirst().get().getId();

                SmoothieRaakaAine uusi = new SmoothieRaakaAine();
                uusi.setJarjestys(jarjestys);
                uusi.setMaara(maara);
                uusi.setOhje(ohje);
                uusi.setRaaka_aine_id(raId);
                uusi.setSmoothie_id(smoothieId);

                sraDao.saveOrUpdate(uusi);
            }
            
            res.redirect("/reseptinlisays");
            return "";
        });

        //POST pyynnön käsittely (smoothien lisääminen) smoothiet sivustolla
        Spark.post("/smoothiet", (req, res) -> {
            String smoothieNimi = req.queryParams("nimi");

            if (!smoothieNimi.equals("")) {
                Smoothie s = new Smoothie();
                s.setNimi(smoothieNimi);

                smoothieDao.saveOrUpdate(s);
            } 

            res.redirect("/smoothiet");
            return "";
        });

        Spark.post("/raaka-aineet/:id/delete", (req, res) -> {
            raDao.delete(Integer.parseInt(req.params(":id")));
            sraDao.deleteBasedOnRaakaAineId(Integer.parseInt(req.params(":id")));

            res.redirect("/raaka-aineet");
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
