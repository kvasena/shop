package shop.Item;

import shop.CategoryShop.CategoryShop;
import shop.Shop.Shop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by kvasena on 20.02.16.
 */
public class Item {
    private String itemName;
    private int itemId;
    private double itemPrice;
    private String itemStatus;
    private CategoryShop category;
    private Connection connection;

    public Item(Connection connection, String itemName, double itemPrice, String itemStatus, CategoryShop category) {
        this.itemName = itemName;
        this.connection = connection;
        this.itemPrice = itemPrice;
        this.itemStatus = itemStatus;
        this.category = category;

        String query = String.format("INSERT INTO item(item_title, item_price, item_status) " +
                "VALUES (\'%s\', %.2f, \'%s\')", itemName, itemPrice, itemStatus);
        String queryCheck = String.format("SELECT item.item_id, item.item_price, item.item_status" +
                " FROM item LEFT JOIN category_item " +
                "ON item.item_id = category_item.item_id LEFT JOIN category " +
                "ON category.category_id = category_item.category_id " +
                "WHERE item.item_title = \'%s\'",itemName);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryCheck);
            if (resultSet.next()) {
                itemId = resultSet.getInt(1);
                double price = resultSet.getInt(2);
                String status = resultSet.getString(3);
                if (price != itemPrice) {
                    this.setItemPrice(itemPrice);
                }
                if (status != "Available") {
                    this.setItemStatus(itemStatus);
                }
            } else {
                statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                ResultSet resultId = statement.getGeneratedKeys();
                if (resultId.next()) {
                    itemId = resultId.getInt(1);
                }
                String queryRelations = String.format("INSERT INTO category_item(item_id, category_id) " +
                        "VALUES (%d,%d)", itemId, category.getCategoryId());
                statement.executeUpdate(queryRelations);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public CategoryShop getItemCategory() {
        return category;
    }

    public int getItemId() {
        return itemId;
    }
    public Shop getShop() {
        return category.getShop();
    }

    public void setItemStatus(String newStatus) {
        String query = String.format("UPDATE item SET item_status = \'%s\' WHERE item_id = %d", newStatus, itemId);

        try  {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setItemPrice(double newPrice) {
        String query = String.format("UPDATE item SET item_price = %.2f WHERE item_id = %d", newPrice, itemId);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "\nitem id: " + itemId + ", item title: " + itemName + ", item price: " + itemPrice +
                ", item status: " + itemStatus;
    }
}
