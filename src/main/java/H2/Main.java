
package H2;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {
    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:smoothiearkisto.db");
        SmoothieDAO smoothieDAO = new SmoothieDAO(database, "Smoothie");
        RaakaAineDAO raakaAineDAO = new RaakaAineDAO(database, "RaakaAine");
        SmoothieRaakaAineDAO SRADAO = new SmoothieRaakaAineDAO(database);
        Tilastot tilasto = new Tilastot(database);
        
        // etusivun lataaminen
        Spark.get("/index", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDAO.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        // smoothiesivun lataaminen
        Spark.get("/smoothiet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDAO.findAll());
            map.put("raakaAineet", raakaAineDAO.findAll());

            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());
        
        // smoothien lisääminen
        Spark.post("/smoothiet", (req, res) -> {
            String nimi = req.queryParams("nimi").trim().toLowerCase();
            
            if (!nimi.isEmpty()) {
                smoothieDAO.saveOrUpdate(new Smoothie(null, nimi));
            }
            
            res.redirect("/smoothiet");
            return "";
        });

        // yksittäisen smoothien tarkastelu
        Spark.get("/smoothiet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer smoothieId = Integer.parseInt(req.params(":id"));
            map.put("smoothie", smoothieDAO.findOne(smoothieId));
            map.put("raakaAineet", SRADAO.findAllResources(smoothieId));
            
            return new ModelAndView(map, "smoothie");
        }, new ThymeleafTemplateEngine());

        // smoothien poistaminen
        Spark.get("/smoothie/:id", (req, res) -> {
            Integer smoothieId = Integer.parseInt(req.params(":id"));
            SRADAO.deleteBySmoothieId(smoothieId);
            smoothieDAO.delete(smoothieId);

            res.redirect("/smoothiet");
            return "";
        });
        
        // raaka-aineiden lisääminen reseptiin
        Spark.post("/smoothieraakaaineet", (req, res) -> {
            Integer smoothieId = Integer.parseInt(req.queryParams("smoothie"));
            Integer raakaAineId = Integer.parseInt(req.queryParams("raakaAine"));
            Integer jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");
            
            SRADAO.saveOrUpdate(new SmoothieRaakaAine(null, smoothieId, raakaAineId, jarjestys, maara, ohje));
            
            res.redirect("/smoothiet");
            return "";
        });
        
        // raaka-ainesivun lataaminen
        Spark.get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineet", raakaAineDAO.findAll());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        // raaka-aineen lisääminen
        Spark.post("/ainekset", (req, res) -> {
            String nimi = req.queryParams("nimi").trim().toLowerCase();
            
            if (!nimi.isEmpty()) {
                raakaAineDAO.saveOrUpdate(new RaakaAine(null, nimi));
            }
            
            res.redirect("/ainekset");
            return "";        
        });
        
        // raaka-aineen poistaminen
        Spark.get("/ainekset/:id", (req, res) -> {
            Integer raakaAineId = Integer.parseInt(req.params(":id"));
            SRADAO.deleteByResourceId(raakaAineId);
            raakaAineDAO.delete(raakaAineId);

            res.redirect("/ainekset");
            return "";
        });
        
        // tilastosivun lataaminen
        Spark.get("/tilastot", (req, res) -> {
            HashMap map = new HashMap<>();
            
            map.put("raakaAineita", tilasto.montakoRaakaAinettaYhteensa());
            map.put("smoothieita", tilasto.montakoSmoothietaYhteensa());          
            
            return new ModelAndView(map, "tilastot");
        }, new ThymeleafTemplateEngine());
    }
}
