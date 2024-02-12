package toffeeSystem;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.*;
import java.util.Vector;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The `loggedinUser` class represents a logged-in user in an online shopping
 * system.
 */
public class loggedinUser {
  /**
   * A Map that represents a shopping cart that stores the ID of each item and the
   * amount the user wants to checkout.
   */
  public Map<String, Float> cart = new HashMap<String, Float>();

  /**
   * A map that stores the remaining amount of items in the user's cart after they
   * have checked out some of them.
   */
  public Map<String, Float> reminderAmount = new HashMap<String, Float>();

  /**
   * Constructs a loggedinUser object with no parameters.
   */
  loggedinUser() {
  }

  /**
   * Displays all items in the database.
   * 
   * @throws Exception if there is an error while connecting to the database or
   *                   executing the query
   */
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
        if (conn != null)
          conn.close();

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Searches for an item with the given ID in the database and adds it to the
   * cart if it exists.
   * 
   * @param itemId the ID of the item to search for
   * @return the amount of the item found, or 0 if the item does not exist in the
   *         database
   * @throws Exception if there is an error connecting to the database or
   *                   executing the query
   */
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
          isExist = true;
          amount = Float.parseFloat(set.getString(4));
        }
      }
      if (!isExist)
        System.out.print("\t\t*** This item not found ***\n");

    } catch (ClassNotFoundException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      // Close the connection when done
      try {
        if (conn != null)
          conn.close();

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return amount;
  }

  /**
   * Creates a new order in the database for the given user and adds the items in
   * the cart to the order.
   * Calculates the total price of the order based on the items' prices and
   * amounts.
   * If the user has any vouchers, applies the applicable voucher to the total
   * price.
   * Updates the amount of each item in the database and clears the cart.
   * 
   * @param userID the ID of the user who is making the order
   * @throws Exception if there is an error connecting to the database or
   *                   executing the query
   */
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
      float amount = 0.0f;
      float totalPrice = 0.0f;

      for (Map.Entry<String, Float> element : cart.entrySet()) {
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
        System.out.print("\n=>Item Number: " + itemID + " And Amount that you take it is : " + amount
            + " Has Added to Your Order Number " + orderID);
      }

      System.out
          .print("\n\n\t\t**** The \u001B[32m\033[1mTotal Price\033[0m\u001B[0m Of your Order Without any voucher #"
              + orderID + " is " + "\u001B[32m\033[1m" + totalPrice + " EGP.\033[0m\u001B[0m ****\n\n");

      String message = this.useVoucher(conn, userId, totalPrice);
      this.updateAmount(conn);

      System.out.println(
          "\t\t**** The \u001B[32m\033[1mTotal Price\033[0m\u001B[0m Of your Order #" + orderID + " is "
              + "\u001B[32m\033[1m" + totalPrice + " EGP.\033[0m\u001B[0m ****\n\n");
      System.out.println(message);

      cart.clear();

    } catch (ClassNotFoundException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      // Close the connection when done
      try {
        if (conn != null)
          conn.close();

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Uses a voucher, if available, to reduce the total price of the cart for the
   * user.
   * It first checks if the user has any unused vouchers and asks the user to use
   * it or not.
   * If the user chooses to use the voucher, it marks the voucher as used in the
   * database and reduces the voucher value from the total price of the cart.
   * If the total price is less than the voucher, it generates a new voucher with
   * the remaining value and assigns it to the user's account.
   * 
   * @param conn       a connection object to the database
   * @param userId     the ID of the user who the voucher will be used for.
   * @param totalPrice the total price of the cart before applying any vouchers
   * @return a message indicating whether a voucher was applied or not, and if a
   *         new voucher was generated, it also includes the value of the new
   *         voucher and its code
   * @throws Exception if there is an error in executing the query
   */
  String useVoucher(Connection conn, int userId, float totalPrice) throws Exception {
    String message = "";
    try {
      // Check if user has any vouchers that isn't used
      String query6 = "SELECT VoucherID, Price, Code FROM Vouchers WHERE PersonID = (" + userId
          + ") AND isUsed = 0";
      // SQL Statement Object to hold and execute SQL queries
      Statement stmt = conn.createStatement();
      PreparedStatement preprdStatement = null;
      ResultSet voucherRS = stmt.executeQuery(query6);

      if (voucherRS.next()) {
        int voucherID = voucherRS.getInt("VoucherID");
        float voucherPrice = voucherRS.getFloat("Price");
        String voucherCode = voucherRS.getString("Code");

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
            preprdStatement = conn.prepareStatement(query8);
            // replace ? number 1 with amount
            preprdStatement.setFloat(1, (totalPrice * -1));
            // replace ? number 2 with userId
            preprdStatement.setInt(2, userId);
            // Generate Random Code
            Random random = new Random();
            int voucherLen = 6;
            String numbers = "0@1#2$3%4&5689!";
            char[] code = new char[voucherLen];
            for (int i = 0; i < voucherLen; i++) {
              code[i] = numbers.charAt(random.nextInt(numbers.length()));
            }
            // replace ? number 3 with a random code
            preprdStatement.setString(3, (new String(code)));
            preprdStatement.executeUpdate();
            message = "A New Voucher has assigned to your account with a following value: "
                + -totalPrice + "\n";
            totalPrice = 0;
          }
          System.out.print("\n\t\t\t**** Voucher successfully applied. ****\n\n");
        } else
          System.out.print("\n\t\t\t**** Voucher not applied. ****\n\n");

      }
    } catch (SQLException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
    return message;
  }

  /**
   * Updates the amount of items stored in the database based from the amount of
   * items the user has taken from the cart.
   * 
   * @param conn a Connection object to the database
   * @throws Exception if there is an error in executing the query
   */
  void updateAmount(Connection conn) throws SQLException {
    PreparedStatement preprdStatement = null;
    int itemID;
    float amount;
    // **This is rest of the items' amount that user take from them
    try {
      for (Map.Entry<String, Float> element : reminderAmount.entrySet()) {
        // Getting Item ID from the cart
        itemID = Integer.parseInt(element.getKey());
        // Get the amount of this item from the cart
        amount = element.getValue();
        // this commends to update in database
        String query5 = "UPDATE Item SET Amount = ? WHERE ItemID = ?";
        preprdStatement = conn.prepareStatement(query5);
        // replace ? number 1 with amount
        preprdStatement.setFloat(1, amount);
        // replace ? number 2 with itemID
        preprdStatement.setInt(2, itemID);
        preprdStatement.executeUpdate();
      }
    } catch (SQLException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}