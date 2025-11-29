package models;

public class Customer {
    private String name;
    private String email;
    private String city;
    private String mobile;
    
    public Customer(String name, String email, String mobile, String city){
        this.name = name; 
        this.email = email; 
        this.city = city;
        this.mobile = mobile;
    }
    
    // Getter methods to fix PLK violation
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCity() { return city; }
    public String getMobile() { return mobile; }
}