/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.jdbc;

import com.latlab.common.jpa.CommonEntityModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcUtil {

    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String MySQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";

    private Connection conn = null;

    public String serverName = "";
    public String portNumber = "";
    public String sid = "";
    public String database = "";
   
    public String username = "";
    public String password = "";

    public String driverName;
    
    public DBDriver dbDriver = DBDriver.MYSQL;

    private static JdbcUtil connection = new JdbcUtil();

    public JdbcUtil(Connection connection) {
        this.conn = connection;
    }

    public JdbcUtil(String username, String password) {
        this.username = username;
        this.password = password;
        init();
    }
    
    public JdbcUtil(String username, String password, String sid){
        this.username = username;
        this.password = password;
        this.sid = sid;
        init();
    }
    
    public JdbcUtil(String username, String password, String portNumber, String database) {
        this.username = username;
        this.password = password;
        this.portNumber = portNumber;
        this.database = database;
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
            String url = "";

            switch (dbDriver) {
                case MYSQL:
                    Class.forName(MySQL_DRIVER);
                    url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + database;
                    break;
                case ORACLE:
                    Class.forName(ORACLE_DRIVER);
                    url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
                    break;
                case POSTGRES:
                    Class.forName(POSTGRES_DRIVER);
                    url = "jdbc:postgresql://" + serverName + ":" + portNumber + "/" + database;
                    break;
                default:
                    Class.forName(MySQL_DRIVER);
                    url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + database;
            }
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

    public void transfer(CommonEntityModel enityModel, Connection connection) {
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

    public void transfer2(CommonEntityModel enityModel, Connection connection) {
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

    public void transfer(CommonEntityModel enityModel) {
        transfer(enityModel, conn);
    }

    public void transfer2(CommonEntityModel enityModel) {
        transfer2(enityModel, conn);
    }

    public DBDriver getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(DBDriver dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    
}
