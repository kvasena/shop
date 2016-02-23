package shop;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import shop.CategoryShop.CategoryShop;
import shop.Creator.Creator;
import shop.Creator.CreatorMobilluck;
import shop.Creator.CreatorRozetka;
import shop.Item.Item;
import shop.Shop.Shop;
import shop.Worker.Worker;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by kvasena on 20.02.16.
 */
public class ShopMain {
    private static final int interval = 10000;
    private static final String host = "jdbc:mysql://localhost:3306/shop";
    private static final String user = "test";
    private static final String password = "test";

    public static void main(String[] args) {

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            Connection connection = DriverManager.getConnection(host, user, password);
            Creator[] creators = {new CreatorRozetka(), new CreatorMobilluck()};
            List<Item> items;
            Shop shop;
            int numShops = creators.length;
            ExecutorService schedule = Executors.newSingleThreadExecutor();

            for (Creator creator : creators) {
                numShops -= 1;
                shop = creator.factoryMethod(connection);
                items = shop.getItems();
                schedule.execute(new Worker(connection, shop, items));
                if ( numShops > 0 ) {
                    Thread.sleep(interval);
                }
            }
            schedule.shutdown();
        }catch(InterruptedException e) {
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
