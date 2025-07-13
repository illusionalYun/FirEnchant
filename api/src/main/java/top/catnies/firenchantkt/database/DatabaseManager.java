package top.catnies.firenchantkt.database;

public interface DatabaseManager {

    // 连接并初始化数据库
    void initDatabase();

    // 关闭数据库连接源
    void close();

    // 获取数据库连接实例
    Database getConnectionSource();

}
