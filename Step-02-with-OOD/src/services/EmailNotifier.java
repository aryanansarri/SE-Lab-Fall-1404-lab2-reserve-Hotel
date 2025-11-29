package services;

public interface EmailNotifier {
    void sendEmail(String to, String message);
}

