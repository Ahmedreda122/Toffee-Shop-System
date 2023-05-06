package toffeeSystem;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;

import java.sql.*;
import toffeeSystem.*;
import java.util.Vector;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class loggedinUser {
    // this to put items that user choose it
    private Vector<String> cart2 = new Vector<String>();
    // this to put id of item and amount that he want it to checkout
    public Map<String, Float> cart = new HashMap<String, Float>();
    // this to put id of item and amount that he want it to checkout
    public Map<String, Float> reminderAmount = new HashMap<String, Float>();

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
        // System.out.print("\n");
        // for (String s : cart2) {
        //     System.out.print(s + " ");
        // }
        return amount;
    }

    public void makeOrder(String userID) throws Exception {
        Connection conn = null;
        int orderID = -1;
        PreparedStatement statement = null;
        try 
        {
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
            float amount = 0.0f;
            float totalPrice = 0.0f;

            for (Map.Entry<String, Float> element : cart.entrySet()) 
            {
                // Getting Item ID from the cart
                itemID = Integer.parseInt(element.getKey());
                // Get the amount of this item from the cart
                amount = element.getValue();
                // ADD this Item (& its amount) to the Database
                String query3 = "INSERT INTO OrderContains Values(" + orderID + "," + itemID + "," + amount + ")";
                stmt.executeUpdate(query3);
                // Get the Price Of this Item From the Database
                String query4 = "SELECT Price FROM Item WHERE ItemID = " + itemID + ";";
                ResultSet itemPrice = stmt.executeQuery(query4);
                // Adding this Item Price * Its Amount to the Total Price of this Order
                totalPrice += Float.parseFloat(itemPrice.getString(1)) * amount;
                System.out.print("\n=>Item Number: " + itemID + " And Amount that you take it is : " + amount + " Has Added to Your Order Number " + orderID);
            }

            // Check if user has any vouchers that isn't used
            String query6 = "SELECT VoucherID, Price, Code FROM Vouchers WHERE PersonID = (" + userId
                    + ") AND isUsed = 0";
            ResultSet voucherRS = stmt.executeQuery(query6);

            String message = "";
            if (voucherRS.next()) {
                int voucherID = voucherRS.getInt("VoucherID");
                float voucherPrice = voucherRS.getFloat("Price");
                String voucherCode = voucherRS.getString("Code");

                System.out
                        .print("\n\n\t\t**** The \u001B[32m\033[1mTotal Price\033[0m\u001B[0m Of your Order Without any voucher #"
                                + orderID + " is "
                                + "\u001B[32m\033[1m" + totalPrice + " EGP.\033[0m\u001B[0m ****\n\n");
                System.out.print("\n<<<You have a voucher worth " + voucherPrice + " EGP with the code " + voucherCode
                        + ". Would you like to use it? (Y/N)\n>>>");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                scanner.reset();
                if (answer.equalsIgnoreCase("Y")) {
                    // Mark voucher as used
                    String query7 = "UPDATE Vouchers SET isUsed = 1 WHERE VoucherID = " + voucherID;
                    stmt.executeUpdate(query7);
                    // reduce voucher value from total price
                    totalPrice -= voucherPrice;
                    if (totalPrice < 0) {
                        String query8 = "INSERT INTO Vouchers(Price, PersonID, Code) VALUES (?,?,?)";
                        statement = conn.prepareStatement(query8);
                        // replace ? number 1 with amount
                        statement.setFloat(1, (totalPrice * -1));
                        // replace ? number 2 with userId
                        statement.setInt(2, userId);
                        // replace ? number 3 with a random code
                        Random random = new Random();
                        String code = random.ints(8).toString();
                        statement.setString(3, code);
                        statement.executeUpdate();
                        message = "A New Voucher has assigned to your account with a following value: "
                                + -totalPrice + "\n";
                        totalPrice = 0;
                    }
                    System.out.print("\n\t\t\t**** Voucher successfully applied. ****\n\n");
                } 
                else 
                {
                    System.out.print("\n\t\t\t**** Voucher not applied. ****\n\n");
                }
                // scanner.close();
            }

            // **this is remind amount of items that users take from it
            for (Map.Entry<String, Float> element : reminderAmount.entrySet()) 
            {
                // Getting Item ID from the cart
                itemID = Integer.parseInt(element.getKey());
                // Get the amount of this item from the cart
                amount = element.getValue();
                // this commends to update in database
                String query5 = "UPDATE Item SET Amount = ? WHERE ItemID = ?";
                statement = conn.prepareStatement(query5);
                // replace ? number 1 with amount
                statement.setFloat(1, amount);
                // replace ? number 2 with itemID
                statement.setInt(2, itemID);
                statement.executeUpdate();
            }
            System.out.println("\t\t**** The \u001B[32m\033[1mTotal Price\033[0m\u001B[0m Of your Order #" + orderID + " is "
                    + "\u001B[32m\033[1m" + totalPrice + " EGP.\033[0m\u001B[0m ****\n\n");
            System.out.print(message);
            cart.clear();
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally 
        {
            // Close the connection when done
            try 
            {
                if (conn != null)
                 {
                    conn.close();
                }
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
    }
    void clearCart2() {
        cart2.clear();
    }
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