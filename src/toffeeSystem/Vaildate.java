package toffeeSystem;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

public class Vaildate {
    // check vaildate password
    public boolean checkPassWord(String pass) {
        Pattern filter = Pattern.compile(
                "^(?:[#-&@*$]{2,15}[a-zA-Z0-9]{5,20}|[a-zA-Z0-9]{5,20}[#-&@$]{2,15}|[#-&@*$]{2,15}[a-zA-Z0-9]{5,20}[#-&@*$]{2,15}|[a-zA-Z0-9]{5,20}[#-&@*$]{2,15}[a-zA-Z0-9]{5,20})$");
        if (filter.matcher(pass).matches()) {
            return true;
        } else {
            return false;
        }
    }

    // check vaildate email
    public boolean checkEmail(String email) {
        Pattern filter = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:gmail)\\." +
                "[a-zA-Z]{2,7}$");
        if (filter.matcher(email).matches()) {
            return true;
        } else {
            return false;
        }
    }

    // check vaildate phone number
    public boolean checkPhoneNumber(String phoneNumber) {
        Pattern filter = Pattern.compile("(01)+(0|1|2|5){1}[0-9]{8}$");
        if (filter.matcher(phoneNumber).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUsed(String data, String type) throws Exception {
        Connection conn = null;
        try {
            int index = 0;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM persons";
            ResultSet set = stmt.executeQuery(query);

            if (type.equals("username")) {
                index = 1;
            } else if (type.equals("email")) {
                index = 2;
            }
            while (set.next()) {
                if (set.getString(index).equals(data)) {
                    return true;
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
        return false;
    }
}