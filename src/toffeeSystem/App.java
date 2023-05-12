package toffeeSystem;

import java.util.Scanner;

/**
 * The `App` class is the main class of the application. It contains the main
 * method, which is the entry point of the program.
 */
public class App {
  /**
   * Constructs an App object with no parameters.
   */
  public App() {
  }

  /**
   * This is the main class for the application. It contains the main method that
   * is executed when the program is run.
   * 
   * @param args an array of command-line arguments that can be passed to the
   *             application
   * @throws Exception if an error occurs during the execution of the program
   */
  public static void main(String[] args) throws Exception {
    Scanner in = new Scanner(System.in);
    boolean isLoggedIn = false;

    String userId = "";
    String name = "";
    System.out.print("\033[H\033[2J");// to clean console
    System.out.flush();// to clean console
    loggedinUser logUser = new loggedinUser();
    userAuthentication authorize = new userAuthentication();
    System.out.println("\t\t\t\t<<!========== Welcome in Toffee-Store ==========!>>\n\n");
    while (true) {
      if (!isLoggedIn && name == "")
        System.out.println("\t\t\t**** Welcome Visitor in Toffee-Store *****\n\n");
      else
        System.out.println("\n\t\t\t***** Welcome " + name + " in Toffee-Store *****\n\n");
      System.out.print("\t(1) Registration.\n");
      System.out.print("\t(2) Login.\n");
      System.out.print("\t(3) Displaying a catalog of items.\n");
      System.out.print("\t(4) Shopping for items and Adding them to cart.\n");
      System.out.print("\t(5) Making an order to be paid upon delivery in cash.\n");
      System.out.print("\t(6) Exit.\n");
      System.out.print("\n<<<Please, Enter your choice: \n>>>");
      String choiceI = in.nextLine();
      System.out.println();
      if (choiceI.equals("1")) {
        if (isLoggedIn) {
          System.out.println("\t\t**** You are already in the system and your Id is : " + userId + " ****\n");
          Thread.sleep(5000);// to sleep console
          System.out.print("\033[H\033[2J");// to clean console
          System.out.flush();// to clean console
          continue;
        }
        String password, email, address, phoneNumber, type;
        Vaildate v = new Vaildate();
        while (true) {
          System.out.print("\n<<< Enter your name: \n>>>");
          name = in.nextLine();

          if (v.isUsed(name, "username")) {
            System.out.println("\t\t**** This Username is already Used, Try another one. ****\n");
          } else {
            break;
          }
        }
        while (true) {
          System.out.print("\n<<< Enter your email: \n>>>");
          email = in.nextLine();
          if (v.checkEmail(email)) {
            if (v.isUsed(email, "email")) {
              System.out.println("\t\t**** This E-mail is already Used, Try another one. ****\n");
            } else {
              String actualOTP = authorize.sendOTP(email);
              System.out.print("<<< Please enter the OTP that was sent to your email:\n>>>");
              String inputOTP = in.nextLine();
              while (true) {
                if (actualOTP.equals(inputOTP)) {
                  System.out.println("\t\t*** Your Email has been verified. ***\n");
                  break;
                } else {
                  System.out.println("\n<<< Please Try Again,\nOR Enter '5' To resend the OTP:\n>>>");
                  String input = in.nextLine();
                  if (input.equals("5")) {
                    actualOTP = authorize.sendOTP(email);
                    System.out.print("\n<<< Please enter the OTP that was sent to your email:\n>>>");
                    inputOTP = in.nextLine();
                  } else {
                    inputOTP = input;
                  }
                }
              }
              break;
            }
          } else {
            System.out.print(
                "\t\t*** Try again.Please enter email like this form (chars&numbers + @ + gmail.com) Or maybe its used ***\n");
          }
        }
        while (true) {
          System.out.print("\n<<< Enter your number phone: \n>>>");
          phoneNumber = in.nextLine();
          if (v.checkPhoneNumber(phoneNumber))
            break;
          else {
            System.out.print(
                "\t\t**** Please enter the mobile number formats for Egyptian mobile numbers or maybe its used****\n");
          }
        }
        System.out.print("\n<<< Enter your Address: \n>>>");
        address = in.nextLine();
        System.out.print("\n<<< Enter your type(User Or Admin): \n>>>");
        type = in.nextLine();
        while (true) {
          System.out.print("\n<<< Enter your Password: \n>>>");
          password = in.nextLine();
          if (v.checkPassWord(password))
            break;
          else {
            System.out.print(
                "\t\t**** Please, Enter a stronger password,\nStart with a mix of letters and numbers(at least 5) then symbols(at least 2)****\n");
          }
        }
        authorize.signUp(name, email, password, phoneNumber, address, type);
        Thread.sleep(5000);// to sleep console
        System.out.print("\033[H\033[2J");// to clean console
        System.out.flush();// to clean console
        System.out.println("\n\n\t\t**** Welcome, " + name + " In Toffee-Store ****\n");
      }
      // ** Option 2
      else if (choiceI.equals("2")) {
        if (isLoggedIn) {
          System.out.println("\t\t**** You are already in the system and your Id is : " + userId + " ****\n");
          Thread.sleep(5000);// to sleep console
          System.out.print("\033[H\033[2J");// to clean console
          System.out.flush();// to clean console
          continue;
        }
        String password;
        System.out.print("\n<<< Please, Enter your Username: \n>>>");
        name = in.nextLine();
        System.out.print("\n<<< Please, Enter your Password: \n>>>");
        password = in.nextLine();
        userId = authorize.verifyLogin(name, password);
        if (userId != null) {
          logUser.cart.clear();
          logUser.clearCart2();
          System.out.println("\n\t\t**** Welcome, " + name + " In Toffee-Store ****\n");
          isLoggedIn = true;
        } else {
          System.out.print("\n\t\t**** This data not in system. Please Try Again ****\n");
          System.out.print("\n<<<Press Enter to back to Home pages:\n>>>");
          in.nextLine();
        }
        Thread.sleep(2000);// to sleep console
        System.out.print("\033[H\033[2J");// to clean console
        System.out.flush();// to clean console
      }
      // Option 3
      else if (choiceI.equals("3")) {
        logUser.displayItems();
        System.out.print("<<<Press Enter to back to Home page:\n>>>");
        in.nextLine();
        System.out.print("\033[H\033[2J");// to clean console
        System.out.flush();// to clean console
      }
      // **Option 4 shopping cart
      else if (choiceI.equals("4")) {
        boolean willAdd = true;
        // First login to the system
        // String name, password;
        if (!isLoggedIn) {
          System.out.print("\n<<< Please, Enter your Username: \n>>>");
          name = in.nextLine();
          System.out.print("\n<<< Please, Enter your Password: \n>>>");
          String password = in.nextLine();
          userId = authorize.verifyLogin(name, password);
          if (userId != null) {
            System.out.print("\n\n\t\t**** Welcome, " + name + " In Toffee-Store ****\n");
            logUser.cart.clear();
            logUser.reminderAmount.clear();
            logUser.clearCart2();
            isLoggedIn = true;
          } else {
            System.out.print("\n\t\t**** This data not in system. Please Try Again ****\n");
            System.out.print("\n<<<Press Enter to back to Home page: \n>>>");
            in.nextLine();
            // in.close();
            System.out.print("\033[H\033[2J");// to clean console
            System.out.flush();// to clean console
            continue;
            // ************* */ return;
          }
        }
        logUser.displayItems();
        // To Reinput(item) if is wrong
        while (true) {
          System.out.print("\n<<< Please, Enter item that you want from this items: \n>>>");
          String itemID = in.nextLine();
          // amountA -> avilable amount in database
          // logUser -> this object type of loggedinUser

          // This take item that not taken previously
          float amountA = 0;
          if (!logUser.cart.containsKey(itemID))
            amountA = logUser.isExist(itemID);
          else
            amountA = logUser.reminderAmount.get(itemID);

          // To Reinput(amount) if is wrong
          // **this while for enter the amount
          while (true) {
            System.out.print("\n<<< Please, Enter The amount of this item: \n>>>");
            float amount = Float.parseFloat(in.nextLine());
            if (amountA < amount)
              System.out
                  .print("\t\t**** This Amount is Not Available But The Available amount is " + amountA + " ***\n");
            else {
              // Add this Item to the cart.
              if (!logUser.cart.containsKey(itemID)) {
                logUser.cart.put(itemID, amount);
                // decrease entered amount from amount in database
                amountA -= amount;
                logUser.reminderAmount.put(itemID, amountA);
              }
              // f this item is already exist in the cart
              else {
                // By **Abdallah Hussein** Excellent // Appreciation by Ahmad Reda
                /*
                 * This item is already in the cart so
                 * We increment its amount only
                 */
                float newAmount = logUser.cart.get(itemID) + amount;
                // Updates the item amount
                logUser.cart.replace(itemID, newAmount);
                amountA -= amount;
                // this map for save what reminded of this item
                logUser.reminderAmount.put(itemID, amountA);
              }
              break;
            }
          }
          // **This while for options(add another item or no)
          while (true) {
            System.out.print("\n<== If you want to add another item click (1) else click (2)?\n");
            String _choice = in.nextLine();
            if (_choice.equals("1"))
              break;
            else if (_choice.equals("2")) {
              willAdd = false;
              // logUser.clearCart2();
              // logUser.reminderAmount.clear();
              break;
            } else
              System.out.print("\t\t*** Invalid Input This item not Exist. Please Try Again ***\n");

          }
          if (!willAdd)
            break;

        }
        Thread.sleep(1000);
        System.out.print("\033[H\033[2J");// to clean console
        System.out.flush();// to clean console
      }
      // ** option 5
      else if (choiceI.equals("5")) {
        if (isLoggedIn) {
          if (logUser.cart.isEmpty()) {
            System.out.print("\n\t\t**** Your cart is empty! ****\n");
            System.out.print("\n<<<Press Enter to back to Home page : \n>>>");
            in.nextLine();
            System.out.print("\033[H\033[2J");// to clean console
            System.out.flush();// to clean console
          } else {
            // Make an Order to the user and put it all the items in the cart.
            logUser.makeOrder(userId);
            while (true) {
              System.out.print("\n<<<Do you want to deliver the order to your existing address or a new address?");
              System.out.print("\n\t1.Existing address");
              System.out.print("\n\t2.New address\n>>>");
              String deliveryOption = in.nextLine();
              if (deliveryOption.equals("1")) {
                System.out.print("\t\t**** The order will be delivered to your existing address. ****\n");
                break;
              } else if (deliveryOption.equals("2")) {
                System.out.print("\n<<<Please Enter Your New Address: \n>>>");
                String newAddress = in.nextLine();
                System.out.print("\n\t\t**** The order will be delivered to the new address you provided => "
                    + newAddress + " ****\n\n");
                break;
              } else
                System.out.print("\n\t\t**** Invalid input. Please Try Again. ****\n");

            }
          }
        } else {
          System.out.print("\n\t\t**** You must Log-in First and add items to your cart, to Checkout ****\n");
          System.out.print("\n<<<Press Enter to back to Home page : \n>>>");
          in.nextLine();
        }
        Thread.sleep(2000);
        System.out.print("\033[H\033[2J");// to clean console
        System.out.flush();// to clean console
      } else if (choiceI.equals("6"))
        break;
      else
        System.out.print("\t\t*** Invalid input. Please Try Again ***\n");

    }
    in.close();
  }
}

// (1) registering a user and login,
// (2) displaying a catalog of items loaded from somewhere,
// (3) shopping for items and adding them to cart, and
// (4) making an order to be paid upon delivery in cash.

// System.out.println("Hello, World!");
// try {
// Class.forName("org.sqlite.JDBC");
// Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
// Statement stmt = conn.createStatement();
// ResultSet set = stmt.executeupdate(query);
// **to insert data in database
// String query = "insert into persons(name, email, password, phoneNum, address,
// type) values('ahmedReda', 'ahmalolyreda@gmail.com', 'ahmed123sd#@',
// '01123568974', 'ahmedredashahtabayoumi','admin')";
// **print table
// String query = "select * from persons";
// ResultSet set = stmt.executeQuery(query);
// while (set.next()) {
// System.out.println(set.getString(1) + " " + set.getString(2)
// + " " + set.getString(3));
// }
// } catch (ClassNotFoundException ex) {
// Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
// }
// Person p = new Person();
// System.out.println(authorize.verifyLogin("abdelrhmanSayed",
// "abdelrhamn123###"));
// authorize.signUp("abdelrhmanSayed", "abdelrhman1220@gmail.com",
// "abdelrhamn123###", "01017262334", "harem", "user");
// -- amount from db
// if zero amount delete it from db
// implement interface console Reader
// password shown as ****
