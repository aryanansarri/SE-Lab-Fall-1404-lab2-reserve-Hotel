package services;

import models.Room;

public class DiscountCalculator {
    public void applyCityDiscount(Room room, String city) {
        if (city.equals("Paris")) {
            System.out.println("Apply city discount for Paris!");
            room.setPrice(room.getPrice() * 0.9);
        }
    }
}

