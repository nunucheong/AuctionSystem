package auctionsystem;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author Hanyang
 */
public class Item {
    protected String itemName;
    protected String itemDescription;
    protected double itemPrice;
    protected Auction auctionType;
    
    public Item(){
        
    }
    public Item(String name,double price,String description,Auction type){
        this.itemName=name;
        this.itemPrice=price;
        this.itemDescription=description;
        this.auctionType=type;
    }
    public void setName(String name){
        this.itemName=name;
    }
    public void setPrice(double price){
       this.itemPrice=price;
    }
    public void setDescription(String description){
        this.itemDescription=description;
    }
    public String getName(){
        return this.itemName;
    }
    public double getPrice(){
        return this.itemPrice;
    }
    public String getDescription(){
        return this.itemDescription;
    }
    public String toString(){
        return getName();
    }
   
//    public void write(){        
//        try{
//            SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//            PrintWriter input = new PrintWriter(new FileOutputStream("database/item.txt",true));
//            input.printf(this.itemName+","+this.itemPrice+","+this.itemDescription+","+this.auctionType.startTime+","+this.auctionType.endTime+","+this.auctionType.AuctionType+",");
//            int i = 0;
//            while(i < this.auctionType.bidStack.bidPriceList.size()){
//                if(i == this.auctionType.bidStack.bidPriceList.size()-1)
//                     input.printf(this.auctionType.bidStack.bidderList.get(i).name+";"+this.auctionType.bidStack.bidPriceList.get(i)+";"+dateformat.parse(this.auctionType.bidStack.bidTimeList.get(i).toString())+",");
//                else
//                    input.printf(this.auctionType.bidStack.bidderList.get(i).name+";"+this.auctionType.bidStack.bidPriceList.get(i)+";"+dateformat.parse(this.auctionType.bidStack.bidTimeList.get(i).toString())+";");
//                i++;
//               
//            }
//            if(this.auctionType.AuctionType.equalsIgnoreCase("EnglishAuction")){
//                input.println(((EnglishAuction)auctionType).getMinExceed());
//            }else if(this.auctionType.AuctionType.equalsIgnoreCase("ReserveAuction")){
//                input.println(((ReserveAuction)auctionType).getReservePrice());
//            }else{
//                input.println("null");
//            }
//            input.close();
//        }catch(IOException e){
//            System.out.println("Problem with file output!");
//        }catch(ParseException f){
//            System.out.println("Error parsing!");
//        }
//    }    
}
