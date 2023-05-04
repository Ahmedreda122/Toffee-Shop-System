package toffeeSystem;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.search.AddressStringTerm;
import javax.mail.search.IntegerComparisonTerm;

import java.sql.*;
import toffeeSystem.*;
import java.util.Vector;
public class loggedinUser {
    private Vector<String>cart = new Vector<String>();
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
        public float isExist(String itemId, String userId) throws Exception
        {
            float amount = 0;
             Connection conn = null;
         try {
             Class.forName("org.sqlite.JDBC");
             conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = conn.createStatement();
            String query = "select * from Item";
            ResultSet set = stmt.executeQuery(query);
            boolean isExit = false;
            while (set.next()) {
                if(itemId.equals(set.getString(1))){
                    cart.add(itemId);
                    isExit = true;
                    amount = Float.parseFloat(set.getString(4));
                }
            }
            if(!isExit){
                System.out.print("\t\t*** This item not found ***\n");
                // return;
            }

            } catch (ClassNotFoundException ex) 
            {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            System.out.print("\n");
            for(int i = 0; i < cart.size();i++){
                System.out.print(cart.get(i) + " ");
            }
            return amount;
        }
        public void  addItem(String userID, String itemId, float amount) throws Exception{
              Connection conn = null;
         try {
             Class.forName("org.sqlite.JDBC");
             conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = conn.createStatement();
            // add order with userID 
            int userI = Integer.parseInt(userID);
            String query = "insert into Orders(UserID) values (" + userI + ")";
            stmt.executeUpdate(query);
            // get last number order
            String query2 = "SELECT MAX(OrderID) From Orders";
            ResultSet setOrder =  stmt.executeQuery(query2);
            int orderID = Integer.parseInt(setOrder.getString(1));
            int itemI = Integer.parseInt(itemId);
            String query3 = "INSERT INTO OrderContains Values(" + orderID + "," + itemI +","+ amount +")";
            stmt.executeUpdate(query3);
            } catch (ClassNotFoundException ex) 
            {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
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


