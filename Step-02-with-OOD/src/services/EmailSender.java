package services;

public class EmailSender implements EmailNotifier{
    public void sendEmail(String to, String message){
        System.out.println("Sending email to " + to + ": " + message);
    }
}
