package services;

public class SmsSender implements SmsNotifier {
    public void sendSms(String to, String message) {
        System.out.println("Sending SMS to " + to + ": " + message);
    }
}

