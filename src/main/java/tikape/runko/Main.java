package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;
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
        Database database = new Database("jdbc:sqlite:smoothiet.db");
        database.init();

        SmoothieDao smoothieDao = new SmoothieDao(database);
                ArrayList <Smoothie> smoothiet = new ArrayList<>();
        RaakaAineDao raDao = new RaakaAineDao(database);
                ArrayList<RaakaAine> raakaAineet = new ArrayList<>();
        SmoothieRaakaAineDao sraDao = new SmoothieRaakaAineDao(database);
                ArrayList <SmoothieRaakaAine> SmoothieRaakaAineet = new ArrayList<>();
        
        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/smoothiet/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());
        
        get("/raaka-aineet/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "raakaaine");
        }, new ThymeleafTemplateEngine());
        
        
        //POST pyynnön käsittely (raaka-aineen lisääminen) raaka-aineet sivustolla
        Spark.post("/raaka-aineet/", (req,res)->{
            String aineNimi = req.queryParams("aine");
            RaakaAine ra = new RaakaAine(raakaAineet.size() + 1, aineNimi);

            raakaAineet.add(ra);
            raDao.save(ra);
            res.redirect("/raaka-aineet/");
            return "";
        });
        
       //POST pyynnön käsittely (smoothien lisääminen) smoothiet sivustolla
        Spark.post("/smoothiet/", (req,res)->{
            String smoothieNimi = req.queryParams("nimi");
            Smoothie s = new Smoothie(smoothiet.size() + 1, smoothieNimi);

            smoothiet.add(s);
            smoothieDao.save(s);
            res.redirect("/smoothiet/");
            return "";
        });
        
        
        
        
        
        

       
    }
}
