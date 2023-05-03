package toffeeSystem;

import java.util.Scanner;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.validation.Validator;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.*;
import toffeeSystem.*;

public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
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
        // System.out.println(user.verifyLogin("abdelrhmanSayed", "abdelrhamn123###"));
        // user.signUp("abdelrhmanSayed", "abdelrhman1220@gmail.com",
        // "abdelrhamn123###", "01017262334", "harem", "user");

        System.out.print("\033[H\033[2J");// to clean console
        System.out.flush();// to clean console
        System.out.println("\t\t     <<!========== Welcome in Toffee System ==========!>>\n\n");
        System.out.print("\t(1) Registration\n");
        System.out.print("\t(2) Login\n");
        System.out.print("\t(3) displaying a catalog of items\n");
        System.out.print("\t(4) shopping for items and adding them to cart\n");
        Scanner in = new Scanner(System.in);
        System.out.print("<<<Enter your choice : \n>>>");
        String choice = in.nextLine();
        userAuthentication user = new userAuthentication();
        if (choice.equals("1")) {
            String name, password, email, address, phoneNumber, type;
            Vaildate v = new Vaildate();
            System.out.print("<<<Enter your name : \n>>>");
            name = in.nextLine();
            while (true) {
                System.out.print("<<<Enter your email : \n>>>");
                email = in.nextLine();
                if (v.checkEmail(email)) {
                    break;
                } else {
                    System.out.print(
                            "\t\t*** Try again.Please enter email like this form (chars + @ + (char) + . + com) ***\n");
                }
            }
            while (true) {
                System.out.print("<<<Enter your number phone : \n>>>");
                phoneNumber = in.nextLine();
                if (v.checkPhoneNumber(phoneNumber)) {
                    break;
                } else {
                    System.out.print(
                            "\t\t**** Please enter the mobile number formats for Egyptian mobile numbers ****\n");
                }
            }
            System.out.print("<<<Enter your Address : \n>>>");
            address = in.nextLine();
            // while(true){
            System.out.print("<<<Enter your type(User Or Admin) : \n>>>");
            type = in.nextLine();
            while (true) {
                System.out.print("<<<Enter your Password : \n>>>");
                password = in.nextLine();
                if (v.checkPassWord(password)) {
                    break;
                } else {
                    System.out.print(
                            "\t\t**** Please enter Strong password with this format(At least 7 char and At least 2 spcail char) ****\n");
                }
                // if(v.checkEmail(type)){
                // break;
                // }else{
                // System.out.print("\t\t**** Please enter the mobile number formats for
                // Egyptian mobile numbers ****\n");
                // }
                // }

            }
            user.signUp(name, email, password, phoneNumber, address, type);
        } else if (choice.equals("2")) {
            String name, password;
            System.out.print("<<<Enter your Username : \n>>>");
            name = in.nextLine();
            System.out.print("<<<Enter your Password : \n>>>");
            password = in.nextLine();

            if (user.verifyLogin(name, password)) {
                System.out.println("you are in this system");
            }

        } else {
            System.out.print("invalid input");
        }
        // System.out.println("");
    }
}

// (1) registering a user and login,
// (2) displaying a catalog of items loaded from somewhere,
// (3) shopping for items and adding them to cart, and
// (4) making an order to be paid upon delivery in cash.