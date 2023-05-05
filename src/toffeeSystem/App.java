package toffeeSystem;

import java.util.Scanner;
import toffeeSystem.*;
import java.util.Arrays;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.validation.Validator;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.*;
import java.io.Console;

public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        boolean isLoggedIn = false;
        String userId = "";
        System.out.print("\033[H\033[2J");// to clean console
        System.out.flush();// to clean console
        loggedinUser logUser = new loggedinUser();
        userAuthentication authorize = new userAuthentication();
        while (true) {
            System.out.println("\t\t     <<!========== Welcome in Toffee System ==========!>>\n\n");
            System.out.print("\t(1) Registration.\n");
            System.out.print("\t(2) Login.\n");
            System.out.print("\t(3) Displaying a catalog of items.\n");
            System.out.print("\t(4) Shopping for items and Adding them to cart.\n");
            System.out.print("\t(5) Making an order to be paid upon delivery in cash.\n");
            System.out.print("\t(6) Exit.\n");
            System.out.print("\n<<<Enter your choiceI : \n>>>");
            String choiceI = in.nextLine();
            System.out.print("\n");
            if (choiceI.equals("1")) {
                String name, password, email, address, phoneNumber, type;
                Vaildate v = new Vaildate();
                while (true) {
                    System.out.print("\n<<<Enter your name : \n>>>");
                    name = in.nextLine();

                    if (v.isUsed(name, "username")) {
                        System.out.println("\t\t**** This Username is used already, Try another one. ****\n");
                    } else {
                        break;
                    }
                }
                while (true) {
                    System.out.print("\n<<<Enter your email : \n>>>");
                    email = in.nextLine();
                    if (v.checkEmail(email)) {
                        if (v.isUsed(email, "email")) {
                            System.out.println("\t\t**** This E-mail is used already, Try another one. ****\n");
                        } else {
                            String actualOTP = authorize.sendOTP(email);
                            System.out.print("<<<Please, Enter the OTP that sent you the email:\n>>>");
                            String inputOTP = in.nextLine();
                            while (true) {
                                if (actualOTP.equals(inputOTP)) {
                                    System.out.println("\t\t*** Your Email has been verified. ***\n");
                                    break;
                                } else {
                                    System.out.println("\n<<<Please Try Again,\nOR Enter '5' To resend the OTP:\n>>>");
                                    String input = in.nextLine();
                                    if (input.equals("5")) {
                                        actualOTP = authorize.sendOTP(email);
                                        System.out.print("\n<<<Please, Enter the OTP that sent you the email:\n>>>");
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
                    System.out.print("\n<<<Enter your number phone : \n>>>");
                    phoneNumber = in.nextLine();
                    if (v.checkPhoneNumber(phoneNumber)) {
                        break;
                    } else {
                        System.out.print(
                                "\t\t**** Please enter the mobile number formats for Egyptian mobile numbers or maybe its used****\n");
                    }
                }
                System.out.print("\n<<<Enter your Address : \n>>>");
                address = in.nextLine();
                // while(true){
                System.out.print("\n<<<Enter your type(User Or Admin) : \n>>>");
                type = in.nextLine();
                while (true) {
                    System.out.print("\n<<<Enter your Password : \n>>>");
                    password = in.nextLine();
                    if (v.checkPassWord(password)) {
                        break;
                    } else {
                        System.out.print(
                                "\t\t**** Please, Enter a stronger password,\nTry a mix of letter, numbers(at least 5), and symbols(at laest 2)****\n");
                    }
                }
                authorize.signUp(name, email, password, phoneNumber, address, type);
            }
            // option 2
            else if (choiceI.equals("2")) {
                String name, password;
                System.out.print("\n<<<Please, Enter your Username : \n>>>");
                name = in.nextLine();
                System.out.print("\n<<<Please, Enter your Password : \n>>>");
                password = in.nextLine();
                userId = authorize.verifyLogin(name, password);
                if (userId != null) {
                    logUser.cart.clear();
                    System.out.println("you are in this system");
                    isLoggedIn = true;
                }
            }
            // option 3
            else if (choiceI.equals("3")) {
                logUser.displayItems();
            }
            // option 4
            else if (choiceI.equals("4")) {
                // first login system
                // String name, password;
                if (!isLoggedIn) {
                    System.out.print("\n<<<Please, Enter your Username : \n>>>");
                    String name = in.nextLine();
                    System.out.print("\n<<<Please, Enter your Password : \n>>>");
                    String password = in.nextLine();
                    userId = authorize.verifyLogin(name, password);
                    if (userId != null) {
                        System.out.println("you are in this system");
                        logUser.cart.clear();
                        isLoggedIn = true;
                    } else {
                        System.out.println("you are not in this system");
                        in.close();
                        return;
                    }
                }
                logUser.displayItems();

                // To Reinput(item) if is wrong
                while (true) {
                    System.out.print("\n<<<Please, Enter item that you want from this items : \n>>>");
                    String itemID = in.nextLine();
                    float amountA = logUser.isExist(itemID);
                    // To Reinput(amount) if is wrong
                    while (true) {
                        System.out.print("\n<<<Please, Enter The amount of this item : \n>>>");
                        float amount = Float.parseFloat(in.nextLine());
                        if (amountA < amount) {
                            System.out
                                    .print("\t\t**** This Amount not Available but that available amount is " + amountA
                                            + " ***\n");
                            continue;
                        } else {
                            // Add this Item to the cart.
                            logUser.cart.put(itemID, amount);
                            break;
                        }
                    }
                    System.out.print("\n<==You want to add another item click (1) else click (2)?\n");
                    String _choice = in.nextLine();
                    if (_choice.equals("1"))
                        continue;
                    else if (_choice.equals("2")) {
                        logUser.clearCart2();
                        break;
                    } else {
                        System.out.print("\t\t*** Invaild Input This item not . Please Try Again ***\n");
                        continue;
                    }
                }
            }
            // option 5
            else if (choiceI.equals("5")) {
                if (isLoggedIn) {
                    if (logUser.cart.isEmpty()) {
                        System.out.println("Your cart is empty!");
                    } else {
                        // Make an Order to the user and put it all the items in the cart.
                        logUser.makeOrder(userId);
                    }
                } else {
                    System.out.println("You must Log-in First and add items to your cart to Checkout");
                }

            } else if (choiceI.equals("6")) {
                break;
            } else {
                System.out.print("\t\t*** Invalid input. Please Try Again ***\n");
            }
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