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

    public Worker(Shop shop) {
        this.shop = shop;
    }


    public void run() {
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
    }
}
