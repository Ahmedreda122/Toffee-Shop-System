import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendOTP {
  public static void sendOTP(String email) {
    // Generate a random OTP
    String otp = generateOTP();

    // Recipient's email ID
    String to = "ahmalolireda12@gmail.com";

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
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

      // Set email subject
      message.setSubject("Your OTP for login");

      // Set email body
      message.setText("Your OTP is " + otp);

      // Send message
      Transport.send(message);

      System.out.println("Email sent successfully!");
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  // Method to generate a random OTP
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
