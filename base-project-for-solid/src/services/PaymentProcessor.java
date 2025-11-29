package services;

class PaymentProcessor {
    public void payByCard(double amount){ System.out.println("Paid by card: " + amount); }
    public void payByCash(double amount){ System.out.println("Paid by cash: " + amount); }
    public void payByPayPal(double amount){ System.out.println("Paid by PayPal: " + amount); }
}