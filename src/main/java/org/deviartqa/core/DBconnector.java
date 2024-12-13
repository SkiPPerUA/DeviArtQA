package org.deviartqa.core;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;

public class DBconnector {

    private final Logger logger = Logger.getLogger(DBconnector.class);
    private static Connection conn;
    private String schema = "terraleads";
    public DBconnector(){
        openConn();
    }

    public DBconnector(String schema){
        this.schema = schema;
        openConn();
    }

    private void openConn(){
        Map creeds = new Credentials().getDBcred();
        try {
            conn = DriverManager.getConnection("jdbc:mariadb://212.224.112.44:"+creeds.get("port")+"/"+schema+"?user="+creeds.get("user")+"&password="+creeds.get("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet select(String sql){
        Statement st = null;
        logger.info(sql);
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            return st.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String sql){
        Statement st = null;
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConn(){
        try {
            if (!(conn == null)){
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSchema() {
        return schema;
    }
}
