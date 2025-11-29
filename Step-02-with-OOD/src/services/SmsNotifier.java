package services;

public interface SmsNotifier {
    void sendSms(String to, String message);
}

