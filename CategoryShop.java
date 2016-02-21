package shop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kvasena on 20.02.16.
 */
public class CategoryShop {
    private String categoryName;
    private int categoryId;
    private Connection connection;
    private Shop shop;

    public CategoryShop(Connection connection, String categoryName, Shop shop) {
        this.categoryName = categoryName;
        this.connection = connection;
        this.shop = shop;

        String queryInsert = String.format("INSERT INTO category(category_name, shop_id) " +
                "VALUES (\'%s\', %d)", categoryName,shop.getShopId());
        String queryCheck = String.format("SELECT category_id from category WHERE category_name = \'%s\' AND shop_id = %d",
                categoryName, shop.getShopId());

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(queryCheck);
            if ( resultSet.next() ) {
                    categoryId = resultSet.getInt(1);
            } else {
                System.out.println(resultSet.getFetchSize());
                statement.executeUpdate(queryInsert, Statement.RETURN_GENERATED_KEYS);
                ResultSet resultId = statement.getGeneratedKeys();
                if(resultId.next()) {
                    categoryId = resultId.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCategoryId() {
        return categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public Shop getShop() {
        return shop;
    }
    public List<Item> getItems() {
        List<Item> items = new ArrayList<Item>();
        String query = String.format("SELECT category.category_name, item.item_id, item_title, item_price, item_status " +
                "FROM category LEFT JOIN category_item ON category.category_id = category_item.category_id " +
                "LEFT JOIN item ON item.item_id = category_item.item_id WHERE category.category_id = %d", categoryId);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String categoryName= resultSet.getString("category_name");
                int itemId = Integer.valueOf(resultSet.getString("item_id"));
                String itemName = resultSet.getString("item_title");
                double itemPrice = Double.valueOf(resultSet.getString("item_price"));
                String itemStatus = resultSet.getString("item_status");

                Item item= new Item(connection,itemName, itemPrice,itemStatus, this);
                items.add(item);
            }

            return items;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public Item setItem(Connection connection, String itemName, double itemPrice, String itemStatus) {
        return new Item(connection, itemName, itemPrice,itemStatus,this);
    }

    public String toString() {
        return  "\ncategory id: " + categoryId + ", category name: " + categoryName;
    }

}
