package models;

public class Room {
    private String number;
    private String type; // "standard" or "luxury"
    private double price;

    public Room(String number, String type, double price){
        this.number = number;
        this.type = type;
        this.price = price;
    }
    
    // Getter methods to fix PLK violation
    public String getNumber() { return number; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    
    // Setter for price (needed for discount calculation)
    public void setPrice(double price) { this.price = price; }
}
