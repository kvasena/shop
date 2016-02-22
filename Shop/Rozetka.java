package shop.Shop;

import java.sql.Connection;

/**
 * Created by kvasena on 21.02.16.
 */
public class Rozetka extends Shop {
    private static volatile Rozetka instance;

    private Rozetka(Connection connection, String shopName) {
        super(connection, shopName);
    }

    public static Rozetka getInstance(Connection connection, String shopName) {
        Rozetka localInstance = instance;
        if (localInstance == null) {
            synchronized (Rozetka.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Rozetka(connection, shopName);
                }
            }
        }
        return localInstance;
    }
}
