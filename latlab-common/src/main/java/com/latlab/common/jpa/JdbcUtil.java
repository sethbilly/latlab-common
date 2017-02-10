/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcUtil {

    public static final String oracle = "oracle.jdbc.driver.OracleDriver";
    public static final String MySQL = "com.mysql.jdbc.Driver";

    private Connection conn = null;

    public String serverName = "192.168.1.201";
    public String portNumber = "1521";
    public String sid = "MFUNDDB";
    public String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
    public String username = "ISSLIVE";
    public String password = "ISSLIVE01";

    public String driverName;

    private static JdbcUtil connection = new JdbcUtil();

    public JdbcUtil(Connection connection) {
        this.conn = connection;
    }

    public JdbcUtil(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        init();
    }

    public JdbcUtil() {

    }

    public static JdbcUtil instance() {
        return connection;
    }

    private void close(ResultSet resultSet) {
        try {
            resultSet.getStatement().close();
            resultSet.close();
            resultSet = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            Class.forName(driverName);

            conn = DriverManager.getConnection(url, username, password);

            System.out.println("Connection Established : " + conn.getClientInfo());

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JdbcUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet executeStatement(String qry) {
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(qry);

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.close();
                conn = null;

                init();

                System.out.println("Error in maintaining connection");
                System.out.println("connection re initialised");

                return executeStatement(qry);

            } catch (SQLException ex) {
                Logger.getLogger(JdbcUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.printStackTrace();
        }

        return null;
    }

    public double getDoubleResult(String qry) {

        try {
            ResultSet resultSet = executeStatement(qry);

//             System.out.println("resutt set is .. " + resultSet);
            while (resultSet.next()) {
                double value = resultSet.getDouble(1);
                close(resultSet);
                return value;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public String getStringResult(String qry) {
        try {
            ResultSet resultSet = executeStatement(qry);

            while (resultSet.next()) {
                String value = resultSet.getString(1);
                close(resultSet);
                return value;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public void transfer(CommonModel enityModel, Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String deleteQry = enityModel.deleteQuerry();

            System.out.println("processing model :::  " + enityModel.toFullInsertSQL());

            if (enityModel.deleteQuerry() != null && !enityModel.deleteQuerry().isEmpty()) {
                statement.execute(deleteQry);
            }

            int updated = statement.executeUpdate(enityModel.toFullInsertSQL());

        } catch (Exception ex) {
            Logger.getLogger(JdbcUtil.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public void transfer2(CommonModel enityModel, Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String deleteQry = enityModel.deleteQuerry();

            System.out.println("processing model :::  " + enityModel.toFullInsertSQL());

            if (enityModel.deleteQuerry() != null && !enityModel.deleteQuerry().isEmpty()) {
                statement.execute(deleteQry);
            }

            int updated = statement.executeUpdate(enityModel.toFullInsertSQL());

        } catch (Exception ex) {
            Logger.getLogger(JdbcUtil.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public void transfer(String qryString, Connection connection) {
        try {
            Statement statement = connection.createStatement();

            //String deleteQry = enityModel.deleteQuerry();
            System.out.println("processing model :::  " + qryString);
            if (qryString != null) {
                statement.execute(qryString);
            }

            //int updated = statement.executeUpdate(enityModel.toSql());
        } catch (Exception ex) {
            Logger.getLogger(JdbcUtil.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    public void transfer(CommonModel enityModel) {
        transfer(enityModel, conn);
    }

    public void transfer2(CommonModel enityModel) {
        transfer2(enityModel, conn);
    }

}
