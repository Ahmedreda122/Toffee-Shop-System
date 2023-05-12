package toffeeSystem;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;//
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.io.Console;

/**
 * The `userAuthentication` class represents a system for authenticating users.
 */
public class userAuthentication {

    /**
     * Constructs a userAuthentication object with no parameters.
     */
    public userAuthentication(){}
//    public String passwordReader() {
//
//        Console console = System.console();
//
//        // Read the password from the console
//        char[] passwordChars = console.readPassword("Enter password: ");
//
//        // Convert the password to a string
//        String password = new String(passwordChars);
//
//        // Print '*' instead of the actual password characters
//        for (int i = 0; i < password.length(); i++) {
//            System.out.print("*");
//        }
//
//        System.out.println();
//        System.out.println("Password entered: " + password);
//        return password;
//    }

    /**
     * Verifies the login of a user by checking if their username and password match a record in the database.
     *
     * @param username the username of the user trying to login
     * @param password the password entered by the user trying to login
     * @return the ID of the user if the login is successful, or null if it fails
     * @throws Exception if there is an error with the database connection or SQL statement
     */
    public String verifyLogin(String username, String password) throws Exception {
        Connection conn = null;
        String id = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM persons";
            ResultSet set = stmt.executeQuery(query);
            while (set.next()) {
                if (set.getString(1).equals(username) && set.getString(3).equals(password)) {
                    id = set.getString(6);
                }
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
        return id;
    }
    /**
     * Adds a new user to the database with the provided details.
     *
     * @param userN the name of the new user
     * @param email the email address of the new user
     * @param password the password of the new user
     * @param phoneNum the phone number of the new user
     * @param address the address of the new user
     * @param type the type of the new user (e.g. admin, customer)
     * @throws Exception if there is an error with the database connection or SQL statement
     */
    public void signUp(String userN, String email, String password, String phoneNum, String address, String type)
            throws Exception {
        Connection conn = null;
        System.out.println("Done!\n" + userN + " " + email + " " + password + " " + phoneNum + " " + address + " " + type);
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = conn.createStatement();
            // **to insert data in database
            String query = "insert into persons(name, email, password, phoneNum, address, type) values('" + userN
                    + "', '" + email + "', '" + password + "', '" + phoneNum + "', '" + address + "','" + type + "')";
            stmt.executeUpdate(query);
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
    /**
     * Sends an email containing the OTP to the specified email address.
     * @param email The email address of the recipient
     * @return The OTP that was generated and sent
     * @throws Exception If there was an error sending the email
     */
    public String sendOTP(String email) throws Exception {
        // Generate a random OTP
        String otp = generateOTP();

        // Recipient's email ID
        // String to = "ahmadredaby122@gmail.com";

        // Sender's email ID and password
        String from = "loginapplication12@gmail.com";
        String password = "gxsptjnhcoutosvb";

        // SMTP server details
        String host = "smtp.gmail.com";

        // Creating properties object
        Properties props = new Properties();

        // SMTP server configuration
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587"); // 587
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Creating a Session object
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Creating a Message object
            Message message = new MimeMessage(session);

            // Set From field
            message.setFrom(new InternetAddress(from));

            // Set To field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Set email subject
            message.setSubject("Your OTP for login");

            // Set email body
            message.setText("Your OTP is " + otp);

            // Send message
            Transport.send(message);

            System.out.println("\t\t**** Email sent successfully! ****\n");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return otp;
    }

    /**
     * Generates a random OTP of length 6 using digits 0-9.
     * @return The randomly generated OTP
     */
    private static String generateOTP() {
        int otpLength = 6;
        String numbers = "0123456789";
        Random random = new Random();
        char[] otp = new char[otpLength];
        for (int i = 0; i < otpLength; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return new String(otp);
    }
}
