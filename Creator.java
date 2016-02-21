package shop;

import java.sql.Connection;

/**
 * Created by kvasena on 21.02.16.
 */
abstract class Creator {
    public abstract Shop factoryMethod(Connection connection);
}
