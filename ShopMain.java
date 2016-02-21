package shop;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by kvasena on 20.02.16.
 */
public class ShopMain {

    public static void main(String[] args) {
        final RunnableClass rClass = new RunnableClass();

        final Runnable runThreads = new Runnable() {
            public void run() {
                try{
                    System.out.println("Start thread");
                    for ( int i = 0; i < 2; i++ ) {
                        (new Thread(rClass)).start();
                        Thread.sleep(5000);
                    }
                    System.out.println("End");
                }   catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        (new Thread(runThreads)).start();
    }

}
