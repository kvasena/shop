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

/**
 * Created by kvasena on 20.02.16.
 */
public class ShopMain {
    private final int timer = 10000;
    private final int maxThreads = 2;
    private static final String host = "jdbc:mysql://localhost:3306/shop";
    private static final String user = "test";
    private static final String password = "test";

    public static void main(String[] args) {
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            Connection connection = DriverManager.getConnection(host, user, password);
            Creator[] creators = {new CreatorRozetka(), new CreatorMobilluck()};
            List<Shop> shops = new ArrayList<Shop>();

            for (Creator creator : creators) {
                Shop newShop = creator.factoryMethod(connection);
                shops.add(newShop);
            }

            Shop shop1 = shops.get(1);
            Shop shop2 = shops.get(0);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        final Worker rClass = new Worker(shop1);
        
        final Runnable runThreads = new Runnable() {
            public void run() {
                try{
                    System.out.println("Start thread");
                    for ( int i = 0; i < maxThreads; i++ ) {
                        (new Thread(rClass)).start();
                        Thread.sleep(timer);
                    }
                    System.out.println("End");
                }   catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        (new Thread(runThreads)).start();
    }

}
