package shop.Shop;

import shop.CategoryShop.CategoryShop;
import shop.Item.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by kvasena on 20.02.16.
 */
public class Shop {

    protected String shopName;
    protected int shopId;
    protected Connection connection;

    public Shop(Connection connection, String shopName) {
        this.shopName = shopName;
        this.connection = connection;
        String query = String.format("SELECT shop_id FROM shop WHERE shop_name = \'%s\'", shopName);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultId = statement.executeQuery(query);
            if(resultId.next()) {
                shopId = resultId.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getShopId() { return shopId; }

    public String getShopName() { return shopName; }

    public Connection getConnection() { return connection; }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<Item>();
        String query = String.format("SELECT category.category_name, item.item_id, item_title, item_price, item_status " +
                "FROM category LEFT JOIN category_item ON category.category_id = category_item.category_id " +
                "LEFT JOIN item ON item.item_id = category_item.item_id WHERE category.shop_id = %d", shopId);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String categoryName= resultSet.getString("category_name");
                int itemId = Integer.valueOf(resultSet.getString("item_id"));
                String itemName = resultSet.getString("item_title");
                double itemPrice = Double.valueOf(resultSet.getString("item_price"));
                String itemStatus = resultSet.getString("item_status");

                CategoryShop category = new CategoryShop(connection, categoryName, this);
                Item item= new Item(connection,itemName, itemPrice,itemStatus, category);
                items.add(item);
            }

            return items;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }
    public List<Item> getItemsByStatus(String item_status) {
        List<Item> items = new ArrayList<Item>();
        String query = String.format("SELECT category.category_name, item.item_id, item_title, item_price, item_status " +
                "FROM category LEFT JOIN category_item ON category.category_id = category_item.category_id " +
                "LEFT JOIN item ON item.item_id = category_item.item_id WHERE item.item_status =\'%s\'", item_status);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String categoryName= resultSet.getString("category_name");
                int itemId = Integer.valueOf(resultSet.getString("item_id"));
                String itemName = resultSet.getString("item_title");
                double itemPrice = Double.valueOf(resultSet.getString("item_price"));
                String itemStatus = resultSet.getString("item_status");

                CategoryShop category = new CategoryShop(connection, categoryName, this);
                Item item= new Item(connection,itemName, itemPrice,itemStatus, category);
                items.add(item);
            }

            return items;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public Item setItem(Connection connection, String itemName, double itemPrice, String itemStatus, CategoryShop category) {
        return new Item(connection, itemName, itemPrice,itemStatus,category);
    }

    public void setItemStatus(Item item, String newStatus) {
        item.setItemStatus(newStatus);
    }
    public void setItemPrice(Item item, String newPrice) {
        item.setItemStatus(newPrice);
    }

    public String toString() {
            return  "Shop id: " + shopId + ", shop name: " + shopName;
    }

}
