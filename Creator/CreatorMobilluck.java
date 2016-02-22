package shop.Creator;

import shop.Shop.Mobilluck;
import shop.Shop.Shop;

import java.sql.Connection;

/**
 * Created by kvasena on 21.02.16.
 */
public class CreatorMobilluck extends Creator {
    @Override
    public Shop factoryMethod(Connection connection) { return Mobilluck.getInstance(connection, "Mobilluck"); }
}
