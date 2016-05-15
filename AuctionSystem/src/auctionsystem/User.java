package auctionsystem;
/**
 *
 * @author Hanyang
 */
public class User {
    protected String name;
    protected String IC;
    protected String paymentType;
    protected String address;
    protected String phone;
    
    public User(){
        
    }
    public User(String Name,String Ic, String PaymentType,String Address,String Phone){
        this.name = Name;
        this.IC = Ic;
        this.paymentType = PaymentType;
        this.address = Address;
        this.phone = Phone;
    }
    public void setName(String input){
        this.name=input;
    }
    public void setIC(String input){
        this.IC=input;
    }
    public void setPaymentType(String input){
        this.paymentType=input;
    }
    public void setAddress(String input){
        this.address=input;
    }
    public void setPhone(String input){
        this.phone=input;
    }
    public String getName(){
       return this.name;
    }
    public String getIC(){
        return this.IC;
    }
    public String getPaymentType(){
        return this.paymentType;
    }
    public String getAddress(){
        return this.address;
    }
    public String getPhone(){
        return this.phone;
    }
//    public void edit(int number){
//        
//    }
//    public void display(){
//        System.out.println("Name           : "+this.name);
//        System.out.println("IC             : "+this.IC);
//        System.out.println("Type of payment: "+this.payment_type);
//        System.out.println("Address        : "+this.address);
//        System.out.println("Phone          : "+this.phone);
//    }
    
}
