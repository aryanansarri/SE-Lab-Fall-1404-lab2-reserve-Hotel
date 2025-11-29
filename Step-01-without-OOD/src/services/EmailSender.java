package services;

class EmailSender implements MessageSender{
    public void sendEmail(String to, String message){
        System.out.println("Sending email to " + to + ": " + message);
    }
    
    public void sendSmsMessage(String to, String message){
        // EmailSender doesn't send SMS
    }
}
