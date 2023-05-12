package toffeeSystem;
/**
 * The `Person` class represents a logged-in user in an online shopping system.
 */
public class Person {
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String type;
    private int id;
    /**
     * Constructs a Person object with no parameters.
     */
    Person() {

    }
    /**
     * Constructs a Person object with the given parameters.
     * @param userN the username of the person
     * @param pass the password of the person
     * @param ema the email of the person
     * @param phoneNum the phone number of the person
     * @param addr the address of the person
     * @param t the type of the person's account
     */
    Person(String userN, String pass, String ema, String phoneNum, String addr, String t) {
        userName = userN;
        phoneNumber = phoneNum;
        address = addr;
        type = t;
        email = ema;
        password = pass;
    }

    /**
     * Returns the username of the person.
     * @return the username of the person
     */
    public String getName() {
        return userName;
    }

    /**
     * Returns the email of the person.
     * @return the email of the person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the phone number of the person.
     * @return the phone number of the person
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the address of the person.
     * @return the address of the person
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the ID of the person.
     * @return the ID of the person
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the type of the person's account.
     * @return the type of the person's account
     */
    public String getType() {
        return type;
    }
}