/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mzida
 */
public class Connector {
    private static String jdbc_driver = "com.mysql.cj.jdbc.Driver";
    private static String nama_db = "perbankan_db";
    private static String url_db = "jdbc:mysql://localhost:3306/" + nama_db;
    private static String usn_db = "root";
    private static String pass_db = "";

    static Connection conn;

    public static Connection Connect() {
        try {
            Class.forName(jdbc_driver);

            conn = DriverManager.getConnection(url_db, usn_db, pass_db);

            System.out.println("Connection Success!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection Failed!" + e.getLocalizedMessage());
        }

        return conn;
    }
}
