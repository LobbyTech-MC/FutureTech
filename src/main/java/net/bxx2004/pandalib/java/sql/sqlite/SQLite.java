package net.bxx2004.pandalib.java.sql.sqlite;

import net.bxx2004.pandalib.java.sql.SQLConnection;
import net.bxx2004.pandalib.java.sql.SQLData;
import net.bxx2004.pandalib.java.sql.SQLHandler;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLite implements SQLHandler {
    private SQLiteConnection connection;

    @Override
    public void set(SQLConnection connection) {
        this.connection = (SQLiteConnection) connection;
    }

    @Override
    public void createTable(String name, boolean primary_key_ID, SQLData... data) {
        String datalang = "";
        if (primary_key_ID) {
            datalang += "ID INT PRIMARY KEY AUTO_INCREMENT, ";
            for (SQLData d : data) {
                SQLiteData mySQLData = (SQLiteData) d;
                if (mySQLData.addData != 0) {
                    datalang += mySQLData.name + " " + mySQLData.type.name() + "(" + mySQLData.addData + "), ";
                } else {
                    datalang += mySQLData.name + " " + mySQLData.type.name() + ", ";
                }
            }
        } else {
            for (SQLData d : data) {
                SQLiteData mySQLData = (SQLiteData) d;
                if (mySQLData.addData != 0) {
                    datalang += mySQLData.name + " " + mySQLData.type.name() + "(" + mySQLData.addData + "), ";
                } else {
                    datalang += mySQLData.name + " " + mySQLData.type.name() + ", ";
                }
            }
        }
        datalang = datalang.substring(0, datalang.length() - 2);
        String sql = "CREATE TABLE " + name + "(" + datalang + ")";
        run(sql);
    }

    private void run(String sql) {
        try {
            Statement statement = this.connection.getConnection().createStatement();
            statement.execute(sql);
            statement.close();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    @Override
    public void deleteTable(String name) {
        run("DROP TABLE " + name);
    }

    @Override
    public boolean exist(String tableName) {
        boolean b;
        try {
            DatabaseMetaData meta = connection.getConnection().getMetaData();
            ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
            b = resultSet.next();
            resultSet.close();
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void execute(String sql) {
        try {
            Statement statement = this.connection.getConnection().createStatement();
            statement.execute(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得MySQLBase对象
     *
     * @param tableName 要操作的表
     * @return 获得MySQLBase对象
     */
    public SQLiteBase getBase(String tableName) {
        return new SQLiteBase(this.connection, tableName);
    }
}
