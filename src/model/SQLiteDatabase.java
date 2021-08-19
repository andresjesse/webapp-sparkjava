package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class SQLiteDatabase {

    private Dao<Car, Integer> dao = null;
    private ConnectionSource conn = null;

    public SQLiteDatabase() {

        try {
            conn = new JdbcConnectionSource("jdbc:sqlite:mydatabase.db");
            TableUtils.createTableIfNotExists(conn, Car.class);
            dao = DaoManager.createDao(conn, Car.class);
        }catch (SQLException ex) {
            System.out.println(ex);
        }

//        dao.create( new Car(1, "VW", "Fusca") );
//        dao.create( new Car(2, "Ford", "F1000") );
//        dao.create( new Car(3, "GM", "Corsa") );

//        Car carFromDb = dao.queryForId(1);
//        carFromDb.setBrand("Fiat");
//        carFromDb.setModel("Uno");
//
//        System.out.println(carFromDb);
//
//        dao.update(carFromDb);
//
//        for( Car car : dao.queryForAll() ) {
//            System.out.println(car);
//        }
    }

    public Dao<Car, Integer> getDao() {
        return dao;
    }
}
