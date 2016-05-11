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
    public void displayBiddingList(){
        int count = this.biddingList.size();
        for(int i = 0; i<this.biddingList.size();i++){
            System.out.println(this.biddingList.get(i));
        }
    }
    public void displaySuccessBidList(){
        int count = this.biddingList.size();
        for(int i = 0; i<this.successBidList.size();i++){
            System.out.println(this.successBidList.get(i));
        }
    }
    public void setBiddingList(int index){
        System.out.println(this.biddingList.get(index-1));
    }
    public void addBiddingList(Item item){
        biddingList.add(item);
    }
    public void setSuccessBidList(int index){
        System.out.println(this.successBidList);
    }
    public void addSuccessBidList(Item item){
        successBidList.add(item);
    }
}
