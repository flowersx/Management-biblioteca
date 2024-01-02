/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Florin
 */
public class database {
 
    public static Connection connectDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // ne conectam la baza de date locala folosin conenction string
            Connection connect  = DriverManager.getConnection("jdbc:mysql://localhost/library", "root","");
            return connect;
        }catch(Exception e){
            e.printStackTrace() ;
        }
        return null;
    }
}
