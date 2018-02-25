package tikape.runko;

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
        Database database = new Database("jdbc:sqlite:smoothiet.db");
        database.init();

        SmoothieDao smoothieDao = new SmoothieDao(database);
        RaakaAineDao raDao = new RaakaAineDao(database);
        SmoothieRaakaAineDao sraDao = new SmoothieRaakaAineDao(database);
        
//        ArrayList <Smoothie> smoothiet = new ArrayList<>();
//        ArrayList<RaakaAine> raakaAineet = new ArrayList<>();
//        ArrayList <SmoothieRaakaAine> SmoothieRaakaAineet = new ArrayList<>();
        
        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/smoothiet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDao.findAll());
            map.put("raakaAineLista", raDao.findAll());

            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());
        
        get("/raaka-aineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineLista", raDao.findAll());
            

            return new ModelAndView(map, "raakaaine");
        }, new ThymeleafTemplateEngine());
        
        
        //POST pyynnön käsittely (raaka-aineen lisääminen) raaka-aineet sivustolla
        Spark.post("/raaka-aineet", (req,res)->{
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
        Spark.post("/smoothiet", (req,res)->{
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
        
        
        
        
        
        
        

       
    }
}
