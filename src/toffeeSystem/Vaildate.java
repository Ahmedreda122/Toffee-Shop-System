package toffeeSystem;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

/**
 * The `Vaildate` class represents a system for validating user inputs.
 */
public class Vaildate {
    /**
     * Constructs a Vaildate object with no parameters.
     */
    public Vaildate() {
    }

    /**
     * This method validates a given password according to a regular expression.
     * 
     * @param pass the password to be validated
     * @return true if the password matches the regular expression, false otherwise
     */
    public boolean checkPassWord(String pass) {
        Pattern filter = Pattern.compile(
                "^(?:[#-&@*$]{2,15}[a-zA-Z0-9]{5,20}|[a-zA-Z0-9]{5,20}[#-&@$]{2,15}|[#-&@*$]{2,15}[a-zA-Z0-9]{5,20}[#-&@*$]{2,15}|[a-zA-Z0-9]{5,20}[#-&@*$]{2,15}[a-zA-Z0-9]{5,20})$");
        return filter.matcher(pass).matches();
    }

    /**
     * This method validates a given email address according to a regular
     * expression.
     * 
     * @param email the email address to be validated
     * @return true if the email address matches the regular expression, false
     *         otherwise
     */
    public boolean checkEmail(String email) {
        Pattern filter = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:gmail)\\." +
                "[a-zA-Z]{2,7}$");
        return filter.matcher(email).matches();
    }

    /**
     * This method validates a given phone number according to a regular expression.
     * 
     * @param phoneNumber the phone number to be validated
     * @return true if the phone number matches the regular expression, false
     *         otherwise
     */
    public boolean checkPhoneNumber(String phoneNumber) {
        Pattern filter = Pattern.compile("(01)+(0|1|2|5){1}[0-9]{8}$");
        return filter.matcher(phoneNumber).matches();
    }

    /**
     * This method checks whether a given data of a specified type is already used
     * or not.
     * 
     * @param data the input data to be checked
     * @param type the type of the data (either "username" or "email")
     * @return true if the data is already used, false otherwise
     * @throws Exception if an error occurs while accessing the database
     */
    public boolean isUsed(String data, String type) throws Exception {
        Connection conn = null;
        try {
            int index = 0;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM persons";
            ResultSet set = stmt.executeQuery(query);

            if (type.equals("username"))
                index = 1;
             else if (type.equals("email"))
                index = 2;

            while (set.next()) {
                if (set.getString(index).equals(data))
                    return true;

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
        return false;
    }
}