package auctionsystem;

import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.text.*;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author User
 */
public class AuctionSystem {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*String username;
        Scanner sc = new Scanner (System.in);
        boolean runProgram = true;
        mainMenu: 
        while (runProgram){
        
            System.out.println("====== Main Menu ======");
            System.out.print("1. Log in\n2. Sign up\n3. Exit system\nPlease choose: ");
            String choice = sc.nextLine();
            switch(choice){
                case "1":
                    
                    boolean logStatus = logIn();
                    while(logStatus){
                        System.out.println("\n====== ???? ======");
                        System.out.print("1. Sell\n2. Bid\n3. Display profile\n4. Log out\n5. Remove account\nPlease choose: ");
                        String choice2 = sc.nextLine();
                        switch(choice2){
                            case "1":
                                
                            case "3":
                                //how to read username
                                displayProfile("zhongqi");
                                System.out.print("\n1. Edit Profile\n2. Back to previous menu\nPlease choose: ");
                                break;
                            case "5":
                                runProgram = false;
                                break;
                                
                                
                        }
                        
                    }
                        
                            
                    break mainMenu;
                
                case "2":
                    createAccount();
                    break mainMenu;
                
                case "3":
                    runProgram = false;
                    break mainMenu;
                
                default:
                    System.out.println("Wrong input. Please choose again: ");
            }  
        }*/
        
        AuctionSystem.onGoingAuction();
    }
    
    public static void createAccount(){
        Scanner sc = new Scanner (System.in);
        SignUp createUser = new SignUp();
        createUser.signup();
    }
    
    public static boolean logIn(){
        Scanner sc = new Scanner (System.in);
        LogIn user = new LogIn();
        boolean log = user.loginmodule();
        return log;
    }
    
    public static void displayProfile(String userId){
        String[] userData = new String[7]; 
        try{
            Scanner inputstream = new Scanner (new FileInputStream("userdatabase.txt"));
            int i = 0;
            int line = checkUserIdPosition(userId);
            while(inputstream.hasNextLine()){
                i++;
                String check = inputstream.nextLine();
                if(i==line){
                    int count = 0;
                    for(String hold : check.split(",")){
                        userData[i] = hold;
                        count++;
                    }
                }
            }
        }catch(IOException e){
            System.out.println("User profile not found.");
        }
        
        System.out.println("Name: " + userData[2]);
        System.out.println("IC Number: " + userData[3]);
        System.out.println("Payment Method: " + userData[4]);
        System.out.println("Address: " + userData[5]);
        System.out.println("Phone Number: " + userData[6]);
        
    }
        
    public static int checkUserIdPosition(String userId){
        
        int count = 1;
        
        try{
            Scanner inputstream = new Scanner (new FileInputStream("userdatabase.txt"));
            int i = 0;
            String[] userIdCheck = null;
            while(inputstream.hasNextLine()){
                
                String check = inputstream.nextLine();
                for(String hold : check.split(",")){
                    userIdCheck[i] = hold;
                    if(userIdCheck[i].equals(userId)){
                        break;
                    }
                    i++;
                }
                count++;
            }
        }catch (IOException e){
            System.out.println("User id not found.");
        }
        return count;    
    }    
    
    public static void onGoingAuction(){
        Date current=new Date();
        SimpleDateFormat simpleFormat =new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");       //date format can change according to whole system date format
        System.out.println("Today's date : " + simpleFormat.format(current));
        
        try{
            String [] itemData=new String[30];
            String [] hold=new String[30];
            Scanner input=new Scanner(new FileInputStream("itemdatabase.txt"));          //just replace the correct text file
            System.out.print("Available Auction(s): \nItem Name \tItem Price\t\tItem Description\t\tAuction Start Time\tAuction End Time\tAuction Type");
            while(input.hasNextLine()){
                String read=input.nextLine();
                itemData=read.split(",");                                                //change the appropriate separate-character
                
                Date startTime=simpleFormat.parse(itemData[3]);
                Date endTime=simpleFormat.parse(itemData[4]);                            //convert String into Date

                if(current.before(endTime)&&current.after(startTime)){
                    hold = itemData;                                           // split[0] , I assume item name is in first place
                }
                System.out.print("\n"+hold[0]+"\t\t"+hold[1]+"\t\t\t"+hold[2]+"\t\t\t"+hold[3]+"\t"+hold[4]+"\t"+hold[5]);
            }
                    // this availAuction arraylist will only show available item to bid.
            
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
