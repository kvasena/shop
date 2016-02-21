package shop;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kvasena on 21.02.16.
 */
public class RunnableClass extends Thread {
    private static final String host = "jdbc:mysql://localhost:3306/shop";
    private static final String user = "test";
    private static final String password = "test";

    public void run() {
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
            Shop shop = shops.get(1);
            CategoryShop category = new CategoryShop(connection, "pc", shop);
            Item item = new Item(connection, "samsung", 4500.55, "Avaliable", category);

            //Записать по 3-4 продукта в категорию
            Item samsung = category.setItem(connection, "samsung", 4500.55, "Avaliable");
            Item lenovo = category.setItem(connection, "lenovo", 4500.55, "Avaliable");
            Item iphone = category.setItem(connection, "iphone", 4500.55, "Avaliable");
            System.out.println(samsung);
            System.out.println(lenovo);
            System.out.println(iphone);

            //В какой-то из категорий изменить статусы всех товаров на «Absent»
            for (Item eachItem : category.getItems()) {
                eachItem.setItemStatus("Absent");
                System.out.println(eachItem);

            }

            //половине товаров, из оставшихся категорий, изменить статус на «Expected».
            for (Item eachItem : shop.getItems()) {
                int count = 0;
                if (eachItem.getItemCategory() != category && count % 2 == 0) {
                    eachItem.setItemStatus("Expected");
                    count++;
                    System.out.println(eachItem);
                }
            }

            //По товарам, что доступны увеличить цену на 20%;
            for (Item eachItem : shop.getItemsByStatus("Available")) {
                eachItem.setItemPrice(eachItem.getItemPrice() * 1.2);
                System.out.println(eachItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
