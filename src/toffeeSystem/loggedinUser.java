package toffeeSystem;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import toffeeSystem.*;
public class loggedinUser {
    loggedinUser(){}
    public void displayItems()throws Exception
    {
        // public static final String re = "\u001B[31m";   
        // System.out.println("This text is bold!");
        System.out.print("\t\033[1m*** Item Details : \033[0m\n\n");
        Connection conn = null;
         try {
             Class.forName("org.sqlite.JDBC");
             conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = conn.createStatement();
            String query = "select * from Item";
            ResultSet set = stmt.executeQuery(query);
            while (set.next()) {
                System.out.println("\u001B[31m\033[1mId : \033[0m\u001B[0m" + set.getString(1) + 
                    " | \u001B[32m\033[1mName : \033[0m\u001B[0m" + set.getString(2) + " | \u001B[32m\033[1mBrand : \033[0m\u001B[0m" + set.getString(3)
                + " | \u001B[32m\033[1mAmount : \033[0m\u001B[0m" + set.getString(4) + " | \u001B[32m\033[1mPrice : \033[0m\u001B[0m" + set.getString(5)
                 + " | \u001B[32m\033[1mCategory : \033[0m\u001B[0m" + set.getString(6) + "\n");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);}
            finally {
            // Close the connection when done
            try {
                if (conn != null) {
                    conn.close();
                }
            }
                catch (SQLException e) {
                e.printStackTrace();
            }
            }
        }

    }

