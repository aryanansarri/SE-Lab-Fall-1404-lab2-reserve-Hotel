package services;

class SmsSender implements MessageSender{
    public void sendEmail(String to, String message){
        // SmsSender doesn't send email
    }
    
    public void sendSmsMessage(String to, String message){
        System.out.println("Sending SMS to " + to + ": " + message);
    }
}

