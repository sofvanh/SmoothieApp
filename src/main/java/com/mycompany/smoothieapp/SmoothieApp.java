/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smoothieapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 *
 * @author sofvanh
 */
public class SmoothieApp {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database db = new Database("jdbc:sqlite:smoothiedatabase.db");
        Connection conn = db.getConnection();

        // Get index page
        Spark.get("/", (req, res) -> {

            HashMap map = new HashMap<>();
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        // Get smoothiet page
        Spark.get("/smoothiet", (req, res) -> {
            // SmoothieDao smoothiedao = new SmoothieDao(db);
            RaakaAineDao RADao = new RaakaAineDao(db);
            
            HashMap map = new HashMap<>();
            // map.put("smoothiet", smoothiedao.findAll());
            map.put("raakaAineet", RADao.findAll());
            
            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());
        
        // Post add new smoothie
        Spark.post("/smoothiet/uusi", (req, res) -> {
            // SmoothieDao smoothiedao = new SmoothieDao(db);
            // Smoothie uusi = new Smoothie(0, req.queryParams("smoothie");
            // SmoothieDao.saveOrUpdate(uusi);
            
            System.out.println("DEBUG: vastaanotettiin: " + req.queryParams("smoothie"));
            
            res.redirect("/smoothiet");
            return 0;
        });

        // Get raaka-aineet page
        Spark.get("/raakaaineet", (req, res) -> {
            RaakaAineDao RADao = new RaakaAineDao(db);

            HashMap map = new HashMap<>();
            map.put("lista", RADao.findAll());

            return new ModelAndView(map, "raakaaineet");
        }, new ThymeleafTemplateEngine());
        
        // Post add new raaka-aine
        Spark.post("/raakaaineet/uusi", (req, res) -> {
            RaakaAineDao RADao = new RaakaAineDao(db);
            RaakaAine uusi = new RaakaAine(0, req.queryParams("raakaA"));
            RADao.saveOrUpdate(uusi);
            
            res.redirect("/raakaaineet");
            return 0;
        });
        
        // Post delete raaka-aine
        
        Spark.post("raakaaineet/:id/poista", (req, res) -> {
            //System.out.println("saatiin poistettava: " + req.params(":id"));
            RaakaAineDao RADao = new RaakaAineDao(db);
            RADao.delete(Integer.parseInt(req.params(":id")));
            
            res.redirect("/raakaaineet");
            return 0;
        });
    }

}
