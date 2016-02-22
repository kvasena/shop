package shop.Creator;

import shop.Shop.Shop;

import java.sql.Connection;

/**
 * Created by kvasena on 21.02.16.
 */
public abstract class Creator {
    public abstract Shop factoryMethod(Connection connection);
}
