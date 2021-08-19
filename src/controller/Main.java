package controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.*;
import static spark.Spark.*;

import model.Car;
import model.SQLiteDatabase;

import java.sql.SQLException;
import java.util.HashMap;

public class Main {

    private static SQLiteDatabase db = null;

    public static void main(String[] args) {
        db = new SQLiteDatabase();
        VelocityTemplateEngine engine = new VelocityTemplateEngine();
        staticFiles.location("/public");

        get("/", Main::pageHome, engine);
        get("/car/:id", Main::pageDetail, engine);
        get("/cars/new", Main::pageNew, engine);
        post("/cars", Main::createCar);
    }

    private static ModelAndView pageHome(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();

        try {
            model.put("cars", db.getDao().queryForAll());
        } catch (SQLException ex){
            System.out.println(ex);
        }

        return new ModelAndView(model, "view/home.vm");
    }

    private static ModelAndView pageDetail(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();

        Integer id = Integer.parseInt(req.params("id"));

        try {
            model.put("car", db.getDao().queryForId( id ));
        } catch (SQLException ex){
            System.out.println(ex);
        }

        return new ModelAndView(model, "view/detail.vm");
    }

    private static ModelAndView pageNew(Request req, Response res) {
        HashMap<String, Object> model = new HashMap<>();
        return new ModelAndView(model, "view/new.vm");
    }

    private static Object createCar(Request req, Response res) {
        Integer id = Integer.parseInt(req.queryParams("id"));
        String brand = req.queryParams("brand");
        String model = req.queryParams("model");

        try {
            db.getDao().create( new Car(id, brand, model) );
        } catch (SQLException ex){
            System.out.println(ex);
            return "Internal server error!";
        }

        res.redirect("/");

        return "ok";
    }
}
