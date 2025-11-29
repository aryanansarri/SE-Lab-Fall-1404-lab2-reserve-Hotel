package services;

import constants.Notifier;
import constants.PaymentMethods;

public class ReservationService {
    private DiscountCalculator discountCalculator;
    private InvoiceGenerator invoiceGenerator;
    private NotificationService notificationService;
    
    public ReservationService(DiscountCalculator discountCalculator, 
                             InvoiceGenerator invoiceGenerator,
                             NotificationService notificationService) {
        this.discountCalculator = discountCalculator;
        this.invoiceGenerator = invoiceGenerator;
        this.notificationService = notificationService;
    }

    public void makeReservation(Reservation res, PaymentMethods paymentType, Notifier notifier){
        System.out.println("Processing reservation for " + res.getCustomer().getName());

        // Apply discount using DiscountCalculator (SRP)
        discountCalculator.applyCityDiscount(res.getRoom(), res.getCustomer().getCity());

        // Process payment using Strategy pattern (OCP, DIP)
        PaymentStrategy paymentStrategy = PaymentProcessorFactory.createPaymentStrategy(paymentType);
        paymentStrategy.pay(res.totalPrice());

        // Generate invoice using InvoiceGenerator (SRP)
        invoiceGenerator.generateInvoice(res);

        // Send notification using NotificationService (SRP, DIP)
        notificationService.sendNotification(res.getCustomer(), notifier, "Your reservation confirmed!");
    }
}