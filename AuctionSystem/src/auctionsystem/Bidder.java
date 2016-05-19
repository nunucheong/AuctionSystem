package auctionsystem;

import java.util.ArrayList;
/**
 *
 * @author Hanyang
 */
public class Bidder extends User{
    ArrayList<String> successBidList=new ArrayList();
    ArrayList<String> biddingList=new ArrayList();
    private int bidFrequency;
    
    public Bidder(){
        
    }
    public Bidder(String Name,String Ic, String PaymentType,String Address,String Phone){
        super(Name,Ic,PaymentType,Address,Phone);
        bidFrequency = 0;
    }
    
    public Bidder(String Name,String Ic, String PaymentType,String Address,String Phone,int bidFrequency,ArrayList<String> bidList,ArrayList<String> success){
        super(Name,Ic,PaymentType,Address,Phone);
        this.biddingList = bidList;
        this.successBidList = success;
        this.bidFrequency = bidFrequency;
    }
    
    public void addBidFrequency(){
        bidFrequency++;
    }
    
    public int getBidFrequency(){
        return bidFrequency;
    }
}
