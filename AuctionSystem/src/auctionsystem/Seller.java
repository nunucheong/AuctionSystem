/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmente;
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
    public void displayList(){
        int count = this.itemList.size();
        for(int i = 0; i<this.itemList.size();i++){
            System.out.println(this.itemList.get(i));
        }
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
