package auctionsystem;
import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.text.*;
import java.util.ArrayList;

public class AuctionSystem {

    public static void main(String[] args) {
        AuctionSystem.checkAvailableAuction();
    }
    public static void checkAvailableAuction(){
        Date current=new Date();
        SimpleDateFormat simpleFormat =new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Today's date : " + simpleFormat.format(current));
        ArrayList availAuc=new ArrayList();
        try{
            String [] split=new String[30];
            Scanner input=new Scanner(new FileInputStream("d:/AuctionSystem.txt"));
            while(input.hasNextLine()){
                String read=input.nextLine();
                split=read.split(",");
                
                Date endTime=simpleFormat.parse(split[2]); //convert String into Date.
                
                if(current.before(endTime))
                    availAuc.add(split[0]); //i assume item name is in first place
            }
            System.out.println("Available Auction(s): "+availAuc.toString());
            input.close();
        }
        catch(FileNotFoundException e){
            e.getMessage();
        }
        catch(ParseException e){
            e.printStackTrace();
        }
    }
    
    public void setBidderCall(Date biddingTime, Double biddingAmount, String itemID,Bidder bidder){
        BiddingStack<Double,Bidder> bid=new BiddingStack<>(); //??
        bid.push(biddingAmount, bidder);
    }
    
    public void displayCallingPrice(){
        BiddingStack<Double,Bidder> bid=new BiddingStack<>();
        System.out.println(bid.toString());
        //OR  
        try{
            Scanner input=new Scanner(new FileInputStream("d:/AuctionSystem.txt"));
            while(input.hasNextLine()){
                String read=input.nextLine();
            }
           /*....................*/
        } 
        catch(FileNotFoundException e){
            e.getMessage();
        }
    }
}
