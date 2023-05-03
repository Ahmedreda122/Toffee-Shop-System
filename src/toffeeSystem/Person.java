package toffeeSystem;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
public class Person {
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String type;
    private int id;
    Person(){
        
    }
    Person(String userN,String pass, String ema, String phoneNum, String addr, String t){
        userName = userN;
        phoneNumber = phoneNum;
        address = addr;
        type = t;
        email = ema;
        password = pass;
    }    
    public String getName(){
        return userName;
    }
    public String getEmail(){
        return email;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String address(){
        return address;
    }
    public int getId(){
        return id;
    }
    public String getType(){
        return type;
    }
}
