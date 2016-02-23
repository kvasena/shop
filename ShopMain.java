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
    private final int interval = 10000;
    private final String host = "jdbc:mysql://localhost:3306/shop";
    private final String user = "test";
    private final String password = "test";

    public static void main(String[] args) {
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            Connection connection = DriverManager.getConnection(host, user, password);
            Creator[] creators = {new CreatorRozetka(), new CreatorMobilluck()};
            List<Item> items;
            Shop shop;
            int numShops = creators.length;

            for (Creator creator : creators) {
                numShops -= 1;
                System.nanoTime();
                shop = creator.factoryMethod(connection);
                items = shop.getItems();
                ExecutorService schedule = Executors.newFixedThreadPool(1);
                schedule.submit(new Worker(connection, shop, items));
                schedule.shutdown();
                System.out.println("Runnable task stop");
                if ( numShops > 0 ) {
                    Thread.sleep(interval);
                }
            }
        }catch(InterruptedException e) {
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
