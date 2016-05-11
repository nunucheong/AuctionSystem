package auctionsystem;

import java.util.ArrayList;
/**
 *
 * @author Hanyang
 */
public class Bidder extends User{
    ArrayList<Item> successBidList=new ArrayList();
    ArrayList<Item> biddingList=new ArrayList();
    
    public Bidder(){
        
    }
    public Bidder(String Name,String Ic, String PaymentType,String Address,String Phone){
        super(Name,Ic,PaymentType,Address,Phone);
    }
    public Bidder(String Name,String Ic, String PaymentType,String Address,String Phone,ArrayList<Item> bidList,ArrayList<Item> success){
        super(Name,Ic,PaymentType,Address,Phone);
        this.biddingList = bidList;
        this.successBidList = success;
    }
    
}
