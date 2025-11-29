package services;

import models.Customer;
import models.Room;
import services.Reservation;

public class InvoiceGenerator {
    public void generateInvoice(Reservation reservation) {
        Customer customer = reservation.getCustomer();
        Room room = reservation.getRoom();
        
        System.out.println("----- INVOICE -----");
        System.out.println("hotel.Customer: " + customer.getName());
        System.out.println("hotel.Room: " + room.getNumber() + " (" + room.getType() + ")");
        System.out.println("Total: " + reservation.totalPrice());
        System.out.println("-------------------");
    }
}

