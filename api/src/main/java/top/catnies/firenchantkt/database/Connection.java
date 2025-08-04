package top.catnies.firenchantkt.database;


import com.j256.ormlite.support.ConnectionSource;

/**
 * 数据库接口
 */
public interface Connection {

    void connect();

    void close();

    ConnectionSource getConnectionSource();
}
