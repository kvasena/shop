package shop.Worker;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import shop.CategoryShop.CategoryShop;
import shop.Creator.Creator;
import shop.Creator.CreatorMobilluck;
import shop.Creator.CreatorRozetka;
import shop.Item.Item;
import shop.Shop.Shop;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kvasena on 21.02.16.
 */
public class Worker extends Thread {
    private Shop shop;
    private List<Item> items;
    private Connection connection;

    public Worker(Connection connection, Shop shop, List<Item> items) {
        this.shop = shop;
        this.items = items;
        this.connection = connection;
        run();
    }

    public void run() {
        //Записать по 3-4 продукта в категорию
        for (Item item : items) {
            CategoryShop category = item.getItemCategory();
            Item samsung = category.setItem(connection, "samsung", 4500.55, "Available");
            Item lenovo = category.setItem(connection, "lenovo", 4500, "Available");
            Item iphone = category.setItem(connection, "iphone", 4500.55, "Available");
        }


        //В какой-то из категорий изменить статусы всех товаров на «Absent»
        Item someItem = items.get(0);
        CategoryShop someCategory = someItem.getItemCategory();
        for (Item eachItem : someCategory.getItems()) {
            eachItem.setItemStatus("Absent");
            System.out.println(eachItem);

        }

        //половине товаров, из оставшихся категорий, изменить статус на «Expected».
        int count = 0;
        for (Item eachItem : items) {
            if (eachItem.getItemCategory() != someCategory && count % 2 == 0) {
                    eachItem.setItemStatus("Expected");
            }
            count++;
            System.out.println(eachItem);
        }

        //По товарам, что доступны увеличить цену на 20%;
        for (Item eachItem : shop.getItemsByStatus("Available")) {
            eachItem.setItemPrice(eachItem.getItemPrice() * 1.2);
            System.out.println(eachItem);
        }
    }
}
