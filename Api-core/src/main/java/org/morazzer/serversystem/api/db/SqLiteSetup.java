package org.morazzer.serversystem.api.db;

import org.morazzer.serversystem.api.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Morazzer
 * @since Date 06.10.2020 16:28:14
 */
public class SqLiteSetup {

    public static void setup() {
            Connection connection = DataSource.getConnection();

        try {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS `users` (`username` varchar(45) NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT, `password` varchar(100) NOT NULL)");
            connection.createStatement().execute("create table if not exists ranks(id INTEGER not null primary key AUTOINCREMENT, uuid varchar(36), name text not null, color text not null, prefix text not null, level integer serial default value, tab_level integer serial default value, inherit varchar(36))");
            connection.createStatement().execute("create table if not exists players(id integer not null primary key AUTOINCREMENT, uuid varchar(36), name text, `rank` text, coins integer)");
            connection.createStatement().execute("create table if not exists permissions(id integer not null primary key AUTOINCREMENT, `rank` varchar(36), permission text)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
