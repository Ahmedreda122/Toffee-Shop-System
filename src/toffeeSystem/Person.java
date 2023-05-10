package toffeeSystem;

public class Person {
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String type;
    private int id;

    Person() {

    }

    Person(String userN, String pass, String ema, String phoneNum, String addr, String t) {
        userName = userN;
        phoneNumber = phoneNum;
        address = addr;
        type = t;
        email = ema;
        password = pass;
    }

    public String getName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}