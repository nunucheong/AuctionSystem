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
        SimpleDateFormat simpleFormat =new SimpleDateFormat("dd/MM/yyyy");            //date format can change according to whole system date format
        System.out.println("Today's date : " + simpleFormat.format(current));
        ArrayList availAuction=new ArrayList();
        try{
            String [] split=new String[30];
            Scanner input=new Scanner(new FileInputStream("d:/AuctionSystem.txt"));   //just replace the correct text file
            while(input.hasNextLine()){
                String read=input.nextLine();
                split=read.split(",");                                                //change the appropriate separate-character
                
                Date endTime=simpleFormat.parse(split[2]);                            //convert String into Date
                
                if(current.before(endTime))
                    availAuction.add(split[0]);                                       // split[0] , I assume item name is in first place
            }
            System.out.println("Available Auction(s): "+availAuction.toString());     // this availAuction arraylist will only show available item to bid.
            input.close();
        }
        catch(FileNotFoundException e){
            e.getMessage();
        }
        catch(ParseException e){
        }
    }
    
    public void setBidderCall(Item item, Date biddingTime, Double biddingAmount, Bidder bidder){
        item.auctionType.bidStack.push(biddingAmount, bidder);      // how to do with the biddingTime?
    }
    
    public void displayCallingPrice(Item item){
        ArrayList<Double>holdPrice=item.auctionType.bidStack.bidPriceList;
        ArrayList<Bidder>holdBidder=item.auctionType.bidStack.bidderList;
        
        for(int i=0;i<holdPrice.size()&&i<holdBidder.size();i++){
            System.out.print("Calling Price : RM"+holdPrice.get(i));            //is this output method okay?
            System.out.println(" by " + holdBidder.get(i));
        }
    }
}
