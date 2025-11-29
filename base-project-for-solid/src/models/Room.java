package models;

public class Room {
    public String number;
    public String type; // "standard" or "luxury"
    public double price;

    public Room(String number, String type, double price){
        this.number = number;
        this.type = type;
        this.price = price;
    }
}
