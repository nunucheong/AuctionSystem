package auctionsystem;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
   
    public void write(){        
        try{
            PrintWriter input = new PrintWriter(new FileOutputStream(".txt"));
            input.printf(this.itemName+","+this.itemPrice+","+this.itemDescription+","+this.auctionType.startTime+","+this.auctionType.endTime+","+this.auctionType.AuctionType+",");
            int i = 0;
            while(!this.auctionType.bidStack.isEmpty(this.auctionType.bidStack.bidderList)){
                input.printf(this.auctionType.bidStack.bidderList.get(i)+";"+this.auctionType.bidStack.bidPriceList.get(i)+";");
                i++;
            }
            input.close();
        }catch(IOException e){
            System.out.println("Problem with file output!");
        }
    }
    
    public void read(){
        try{
            Scanner read = new Scanner(new FileInputStream("Auction.txt"));
            while(read.hasNextLine()){
                String copy = read.nextLine();
                String[] array = copy.split("[,;]");
                this.itemName = array[0];
                this.itemPrice = Double.parseDouble(array[1]);
                this.itemDescription = array[2];
                
            }
            
            
            
        }catch(FileNotFoundException a){
            System.out.println("File was not found!");
        }
    }
}
