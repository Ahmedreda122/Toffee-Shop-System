package toffeeSystem;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
public class userAuthentication{
    userAuthentication(){
    }
    public boolean verifyLogin(String username, String password) throws Exception{
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM persons";
            ResultSet set = stmt.executeQuery(query);
            while (set.next()) {
                if(set.getString(1).equals(username) && set.getString(3).equals(password)){
                    return true;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public void signUp(String userN, String email, String password, String phoneNum, String address, String type)throws Exception{
         System.out.println("done " + userN + " " + email + " " + password + " " + phoneNum + "" + address + " " + type);
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = conn.createStatement();
            // **to insert data in database
            String query = "insert into persons(name, email, password, phoneNum, address, type) values('" + userN + "', '" + email + "', '" + password + "', '" + phoneNum + "', '" + address + "','" + type + "')";
            stmt.executeUpdate(query);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
