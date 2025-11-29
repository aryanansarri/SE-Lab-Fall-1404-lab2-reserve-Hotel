package services;

import constants.Notifier;
import models.Customer;

public class NotificationService {
    private EmailNotifier emailNotifier;
    private SmsNotifier smsNotifier;
    
    public NotificationService(EmailNotifier emailNotifier, SmsNotifier smsNotifier) {
        this.emailNotifier = emailNotifier;
        this.smsNotifier = smsNotifier;
    }
    
    public void sendNotification(Customer customer, Notifier notifier, String message) {
        switch (notifier) {
            case EMAIL:
                emailNotifier.sendEmail(customer.getEmail(), message);
                break;
            case SMS:
                smsNotifier.sendSms(customer.getMobile(), message);
                break;
            default:
                System.out.println("There is no Message Provider");
        }
    }
}

