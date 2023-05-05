package toffeeSystem;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.*;
import toffeeSystem.*;
import java.util.Vector;

public class loggedinUser {
    private Vector<String> cart2 = new Vector<String>();
    public Map<String, Float> cart = new HashMap<String, Float>();

    loggedinUser() {
    }

    public void displayItems() throws Exception {
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
                System.out.println("\u001B[31m\033[1mID : \033[0m\u001B[0m" + set.getString(1)
                        + " | \u001B[32m\033[1mName : \033[0m\u001B[0m" + set.getString(2)
                        + " | \u001B[32m\033[1mBrand : \033[0m\u001B[0m" + set.getString(3)
                        + " | \u001B[32m\033[1mAmount : \033[0m\u001B[0m" + set.getString(4)
                        + " | \u001B[32m\033[1mPrice : \033[0m\u001B[0m" + set.getString(5)
                        + " | \u001B[32m\033[1mCategory : \033[0m\u001B[0m" + set.getString(6) + "\n");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the connection when done
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public float isExist(String itemId) throws Exception {
        float amount = 0;
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = conn.createStatement();
            String query = "select * from Item";
            ResultSet set = stmt.executeQuery(query);
            boolean isExist = false;
            while (set.next()) {
                if (itemId.equals(set.getString(1))) {
                    cart2.add(itemId);
                    isExist = true;
                    amount = Float.parseFloat(set.getString(4));
                }
            }
            if (!isExist) {
                System.out.print("\t\t*** This item not found ***\n");
                // return;
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the connection when done
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.print("\n");
        for (int i = 0; i < cart2.size(); i++) {
            System.out.print(cart2.get(i) + " ");
        }
        return amount;
    }

    // public void addItem(String itemID, float amount) throws Exception {
    // // Connection conn = null;
    // // try {
    // // Class.forName("org.sqlite.JDBC");
    // // conn = DriverManager.getConnection("jdbc:sqlite:test.db");
    // // Statement stmt = conn.createStatement();
    // // int itemId = Integer.parseInt(itemID);
    // // String query3 = "INSERT INTO OrderContains Values(" + orderID + "," +
    // itemId
    // // + "," + amount + ")";
    // // stmt.executeUpdate(query3);
    // // System.out.println("Item number:" + itemId + "has added to your order
    // number
    // // " + orderID);
    // // } catch (ClassNotFoundException ex) {
    // // Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    // // } finally {
    // // // Close the connection when done
    // // try {
    // // if (conn != null) {
    // // conn.close();
    // // }
    // // } catch (SQLException e) {
    // // e.printStackTrace();
    // // }
    // // }
    // }

    public void makeOrder(String userID) throws Exception {
        Connection conn = null;
        int orderID = -1;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            // SQL Statement Object to hold and execute SQL queries
            Statement stmt = conn.createStatement();

            // Add a new order related to userID
            int userId = Integer.parseInt(userID);
            String query = "insert into Orders(UserID) values (" + userId + ")";
            stmt.executeUpdate(query);

            // Get the order ID of this user by getting the last orderID
            String query2 = "SELECT MAX(OrderID) From Orders";
            ResultSet setOrder = stmt.executeQuery(query2);
            orderID = Integer.parseInt(setOrder.getString(1));
            int itemID;
            float amount;
            float totalPrice = 0.0f;
            for (Map.Entry<String, Float> element : cart.entrySet()) {
                // Getting Item ID from the cart
                itemID = Integer.parseInt(element.getKey());
                // Get the amount of this item from the cart
                amount = element.getValue();
                // ADD this Item (&its amount) to the Database
                String query3 = "INSERT INTO OrderContains Values(" + orderID + "," + itemID
                        + "," + amount + ")";
                stmt.executeUpdate(query3);

                System.out.println("Item Number: " + itemID + " Has Added to Your Order Number "
                        + orderID);
                // Get the Price Of this Item From the Database
                String query4 = "SELECT Price FROM Item WHERE ItemID = " + itemID + ";";
                ResultSet itemPrice = stmt.executeQuery(query4);
                // Adding this Item Price * Its Amount to the Total Price of this Order
                totalPrice += Float.parseFloat(itemPrice.getString(1)) * amount;
            }
            System.out.println("The \u001B[32m\033[1mTotal Price\033[0m\u001B[0m Of your Order #" + orderID + " is "
                    + "\u001B[32m\033[1m" + totalPrice + " EGP.\033[0m\u001B[0m");
            cart.clear();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the connection when done
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void clearCart2() {
        cart2.clear();
    }
}
