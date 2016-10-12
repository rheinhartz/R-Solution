/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Richard
 */
public class ConnectDB {

    public void connect() {
        Connection conn = null;

        ///////////////////////////////////////////////////////////////
        //Establishing of connection to MySql Database - Begin
        ///////////////////////////////////////////////////////////////
        //For JDBC connection - Begin
        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
                System.out.print("connectData-a1=No Error" + "\n");

                try {
                    String sql = "CREATE DATABASE rsolutiondev ";
                    Statement stmt = conn.createStatement();
                    stmt.execute(sql);
                } catch (Exception e) {
                }
            } catch (Exception a1) {
                try {
                    conn.close();
                    System.out.print("connectData-c1=" + a1 + "\n");
                } catch (Exception m) {
                    System.out.print("connectData-m=" + m + "\n");
                }
            }
        }

    }
}
