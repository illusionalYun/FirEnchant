package top.catnies.firenchantkt.database.impl;

import top.catnies.firenchantkt.database.Database;

public class SQLiteDatabase implements Database {

    private final String url;

    public SQLiteDatabase(String url) {
        this.url = url;
    }

    @Override
    public void close() {
    }
}
