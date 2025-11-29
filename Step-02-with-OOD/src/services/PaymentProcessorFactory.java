package services;

import constants.PaymentMethods;

public class PaymentProcessorFactory {
    public static PaymentStrategy createPaymentStrategy(PaymentMethods paymentType) {
        switch (paymentType) {
            case CARD:
                return new CardPayment();
            case CASH:
                return new CashPayment();
            case PAYPAL:
                return new PayPalPayment();
            default:
                throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        }
    }
}

