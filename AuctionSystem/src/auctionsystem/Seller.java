package auctionsystem;

import java.util.ArrayList;
/**
 *
 * @author Hanyang
 */
public class Seller extends User {
    ArrayList<Item> itemList = new ArrayList();
    
    public Seller(){
        
    }
    public Seller(String Name,String Ic, String PaymentType,String Address,String Phone){
        super(Name,Ic,PaymentType,Address,Phone);
        
    }
    public Seller(String Name,String Ic, String PaymentType,String Address,String Phone,ArrayList<Item> itemList){
        super(Name,Ic,PaymentType,Address,Phone);
        this.itemList = itemList;
    }
    public void addItem(String itemName,double itemPrice,String itemDescription,Auction auctionType){
        itemList.add(new Item(itemName,itemPrice,itemDescription,auctionType));
    }
    
   
//    public void editSellerItem(int number){
//        
//    }
//    public void displayItemForSale(){
//        System.out.println("Sale item   : "+this.item_name);
//        System.out.println("Price       : "+this.price);
//        System.out.println("Description : "+this.description);
//    }
}
