package top.catnies.firenchantkt.config


class SettingsConfig private constructor():
    AbstractConfigFile("settings.yml")
{

    companion object {
        @JvmStatic
        val instance by lazy { SettingsConfig() }
    }


    lateinit var LANGUAGE: String
    lateinit var DATABASE_TYPE: String;
    lateinit var DATABASE_MYSQL_HOST: String
    lateinit var DATABASE_MYSQL_DATABASE: String
    lateinit var DATABASE_MYSQL_USER: String
    lateinit var DATABASE_MYSQL_PASSWORD: String
    lateinit var DATABASE_H2_FILE: String


    override fun loadConfig() {
        LANGUAGE = config().getString("language", "zh_CN")!!

        DATABASE_TYPE = config().getString("database.type", "h2")!!
        DATABASE_MYSQL_HOST = config().getString("database.mysql.host", "127.0.0.1:3306")!!
        DATABASE_MYSQL_DATABASE = config().getString("database.mysql.database", "firenchant")!!
        DATABASE_MYSQL_USER = config().getString("database.mysql.username", "root")!!
        DATABASE_MYSQL_PASSWORD = config().getString("database.mysql.password", "root")!!
        DATABASE_H2_FILE = config().getString("database.h2.file", "database.db")!!
    }

}
