package services;

public interface MessageSender {
    public void sendEmail(String to, String message);
    public void sendSmsMessage(String to, String message);
}
