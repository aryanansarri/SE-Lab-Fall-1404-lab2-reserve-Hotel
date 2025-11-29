import constants.PaymentMethods;
import models.Customer;
import models.LuxuryRoom;
import constants.Notifier;
import services.*;

public class Main {
    public static void main(String[] args){
        Customer customer = new Customer("Ali", "ali@example.com","09124483765", "Paris");
        LuxuryRoom room = new LuxuryRoom("203", 150);
        Reservation res = new Reservation(room, customer, 2);

        // Dependency Injection to fix DIP violation
        DiscountCalculator discountCalculator = new DiscountCalculator();
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        EmailNotifier emailNotifier = new EmailSender();
        SmsNotifier smsNotifier = new SmsSender();
        NotificationService notificationService = new NotificationService(emailNotifier, smsNotifier);
        
        ReservationService service = new ReservationService(
            discountCalculator, 
            invoiceGenerator, 
            notificationService
        );
        
        service.makeReservation(res, PaymentMethods.PAYPAL, Notifier.EMAIL);
    }
}