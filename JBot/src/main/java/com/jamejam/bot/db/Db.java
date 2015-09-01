package com.jamejam.bot.db;

import com.jamejam.api.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razzaghi on 01/09/2015.
 */
public class Db {


    Connection conn = null;
    Statement stmt = null;

    public Db() {

        try {

            conn = DriverManager.getConnection(Constants.JDBC_DRIVER, Constants.JDBC_USERNAME, Constants.JDBC_PASSWORD);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }


    }


    public boolean doInsertQuery(String query){
        try{
            return stmt.execute(query);
        }catch (Exception e){
            System.out.print(e.getMessage());
        }

        return false;
    }

    public List<Object> doSelectQuery(String query){

        try{
            ResultSet rs = stmt.executeQuery(query);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
        }catch (Exception e){
            System.out.print(e.getMessage());
        }

        return new ArrayList();
    }


}
