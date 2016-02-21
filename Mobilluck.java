package shop;

import java.sql.Connection;

/**
 * Created by kvasena on 21.02.16.
 */
public class Mobilluck extends Shop{
    private static volatile Mobilluck instance;

    private Mobilluck(Connection connection, String shopName) {
        super(connection, shopName);
    }

    public static Mobilluck getInstance(Connection connection, String shopName) {
        Mobilluck localInstance = instance;
        if (localInstance == null) {
            synchronized (Mobilluck.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Mobilluck(connection, shopName);
                }
            }
        }
        return localInstance;
    }
}
