package toffeeSystem;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Vaildate {
    // check vaildate password
    public boolean checkPassWord(String pass){
        Pattern filter = Pattern.compile("$([#-&@]{2,15}[a-zA-Z0-9]{7,20})|([a-zA-Z0-9]{7,20}[#-&@]{2,15})$");
        if(filter.matcher(pass).matches()) {
            return true;
        } else {
            return false;
        }
    }
    // check vaildate email
    public boolean checkEmail(String email){
        Pattern filter = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\." + 
                        "[a-zA-Z0-9_+&*-]+)*@" + 
                        "(?:gmail)\\." + 
                        "[a-zA-Z]{2,7}$");
        if(filter.matcher(email).matches()){
            return true;
        } else {
            return false;
        }
    }
    // check vaildate phone number
    public boolean checkPhoneNumber(String phoneNumber){
        Pattern filter = Pattern.compile("(01)+(0|1|2|5){1}[0-9]{8}$");
        if(filter.matcher(phoneNumber).matches()){
            return true;
        }else{
            return false;
        }
    } 
}
