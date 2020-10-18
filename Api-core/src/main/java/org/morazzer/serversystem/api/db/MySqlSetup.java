package org.morazzer.serversystem.api.db;

import org.morazzer.serversystem.api.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Morazzer
 * @since Date 06.10.2020 16:28:21
 */
public class MySqlSetup {

    public static void setup() {
        Connection connection = DataSource.getConnection();

        try {
            connection.prepareCall("create table if not exists users(id INTEGER primary key auto_increment, username varchar(45) not null,password varchar(100) not null)").execute();
            connection.prepareCall("create table if not exists ranks(id INTEGER not null primary key auto_increment, uuid varchar(36), name text not null, color text not null, prefix text not null, level integer, tab_level integer, inherit varchar(36))").execute();
            connection.prepareCall("create table if not exists players(id integer not null primary key auto_increment, uuid varchar(36), name text, `rank` text, coins integer)").execute();
            connection.prepareCall("create table if not exists permissions(id integer not null primary key auto_increment, `rank` varchar(36), permission text)").execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
