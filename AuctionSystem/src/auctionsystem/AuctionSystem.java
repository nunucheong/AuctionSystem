package auctionsystem;

import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.text.*;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import javafx.util.Pair;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author User
 */
public class AuctionSystem {
    public static String username;
    User user;
    Seller seller;
    Bidder bidder;
    Auction newAuction;
    ArrayList<String> bidderEndedBid;
    static ItemLinkedList<Date,Item> itemList = new ItemLinkedList<>();
    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    Date a;
    Date b;
    public static void main(String[] args) {
        AuctionSystem system = new AuctionSystem();
        Scanner sc = new Scanner (System.in);
        boolean runProgram = true;
        boolean logStatus;
        system.read();
        startProgram: 
        while (runProgram){
            System.out.println("\nWelcome to Watching Euuuuu ( ͡° ͜ʖ ͡°) Auction System!");
            System.out.println("(\\____/)"); 
            System.out.println("( ͡° ͜ʖ ͡°)");
            System.out.println("\\ >    \\ >");

            System.out.println("\n====== Main Menu ======");
            System.out.print("1. Log in\n2. Sign up\n3. Exit system\nPlease choose: ");
            String choice = sc.nextLine();
            
            main:
            switch(choice){
                case "1":
                    //prompt user for attempt to sign in and get the value 
                    System.out.println("\n====== Log In ======");
                    System.out.print("Please enter your username: ");
                    username = sc.nextLine();
                    logStatus = system.logIn(username);
                    if(logStatus)
                        System.out.println("Welcome back " + username);                              
                    logIn:
                    while(logStatus){
                        //create new user object
                        system.readUserProfile(username);
                        system.checkBiddingList();
                        boolean selectMode = true;
                        mode:
                        while (selectMode){
                            String choice2 = system.selectModeMenu();
                            switch(choice2){
                                //Enter seller mode
                                case "1": 
                                    boolean sellerMode = true;
                                    while(sellerMode){
                                        sellerMode = system.sellerMode();
                                    }
                                    break;
                                    
                                //Enter bidder mode
                                case "2":
                                    boolean bidderMode = true;
                                    while(bidderMode){
                                       bidderMode = system.bidderMode();
                                    }
                                    break;
                                //allow user to manage profile
                                case "3":
                                    boolean manageProfile = true;
                                    while(manageProfile){
                                    manageProfile = system.accessProfile();
                                    }
                                    break;
                                
                                //Log out system back to main menu    
                                case "4":
                                    system.updateUserdatabase(system.checkUserIdPosition(username));
                                    logStatus = false;
                                    break logIn;
                                
                                //remove account    
                                case "5":
                                    System.out.println("Are you sure you want to remove yourself from the system? [y/n]");
                                    String choice3 = sc.nextLine();
                                    if(choice3.equalsIgnoreCase("y")){
                                        system.removeAccount(username);
                                        System.out.println("Your account is removed successfully. Thank you for using our auction system.");
                                        break logIn;
                                    }
                                    else
                                        break;
                                    
                                default:
                                    System.out.println("Invalid input. Please enter again: ");  
                                    break;
                            }
                        }
                    }  
                    break;          
                
                case "2":
                    system.createAccount();
                    break;
                
                case "3":
                    runProgram = false;
                    system.write();
                    break;
                
                default:
                    System.out.println("Wrong input. Please choose again: ");
                    break;
            }  
        }
    }
    
    //allow user to create new account
    public void createAccount(){
        Scanner sc = new Scanner (System.in);
        SignUp createUser = new SignUp();
        createUser.signup();
    }
    
    //allow user to login, return boolean
    public boolean logIn(String username){
        Scanner sc = new Scanner (System.in);
        LogIn user = new LogIn(username);
        boolean log = user.loginmodule();
        return log;
    }
    
    //prompt profile management menu, return user choice
    public String selectModeMenu(){
        Scanner sc = new Scanner (System.in);
        System.out.println("\n====== Select Mode ======");
        System.out.print("1. Seller Mode\n2. Bidder Mode\n3. Manage Profile\n4. Log out\n5. Remove account\nPlease choose: ");    
        return sc.nextLine();
    }
    
    //read user profile from userdatabase.txt and create new object<User>
    public String[] readUserProfile(String userId){
        String[] userData = new String[11]; 
        try{
            Scanner inputstream = new Scanner (new FileInputStream("database/userdatabase.txt"));
            int i = 0;
            int line = checkUserIdPosition(userId);
            while(inputstream.hasNextLine()){
                i++;
                String check = inputstream.nextLine();
                if(i==line){
                    int count = 0;
                    for(String hold : check.split(",")){
                        userData[count] = hold;
                        count++;
                    }
                }
            }
        }catch(IOException e){
            System.out.println("User profile not found.");
        }
        user = new User(userData[2], userData[3], userData[4], userData[5], userData[6]);
        
        ArrayList<Item> sellerItemList = new ArrayList<>();
        for(String hold : userData[7].split(":")){
            if (!hold.equals("null")){
                sellerItemList.add(itemList.getItem(itemList.indexOfItem(hold)).getValue());
            }
        }
        seller = new Seller (user.getName(), user.getIC(), user.getPaymentType(), user.getAddress(), user.getPhone(), sellerItemList); 
        
        ArrayList<String> bidderBiddingList = new ArrayList<>();
        for(String hold : userData[8].split(":")){
            if(!hold.equals("null"))
                bidderBiddingList.add(itemList.getItem(itemList.indexOfItem(hold)).getValue().getName());
        }
        
        ArrayList<String> bidderSuccessList = new ArrayList<>();
        for(String hold : userData[9].split(":")){
            if(!hold.equals("null"))
                bidderSuccessList.add(itemList.getItem(itemList.indexOfItem(hold)).getValue().getName());
        }
        bidder = new Bidder (user.getName(), user.getIC(), user.getPaymentType(), user.getAddress(), user.getPhone(),Integer.parseInt(userData[10]),bidderBiddingList,bidderSuccessList); 
        return userData;
    }
    
    public void displayProfile(){
        System.out.println("\n====== Profile ======");
        System.out.println("1. Name: " + user.getName());
        System.out.println("2. IC number: " + user.getIC());
        System.out.println("3. Payment Method: " + user.getPaymentType());
        System.out.println("4. Address: " + user.getAddress());
        System.out.println("5. Phone number: " + user.getPhone());
    }
    
    public double sellerTotalIncome(){
        double sum = 0;
        for(int i = 0; i<seller.itemList.size(); i++){
            sum += seller.itemList.get(i).getPrice();
        }
        return sum;
    }
    
    public double bidderTotalPendingPayment(){
        double sum = 0;
        for(int i = 0; i<bidder.biddingList.size(); i++){
            String itemName = bidder.biddingList.get(i);
            Item item = itemList.getItem(itemList.indexOfItem(itemName)).getValue();
            sum += item.getPrice();
        }
        return sum;
    }
    
    public boolean accessProfile(){
        boolean manageProfile = true;
        Scanner sc = new Scanner (System.in);
        System.out.println("\n====== Profile Management ======");
        System.out.print("1. View Profile\n2. Edit Profile\n3. Back to previous menu\nPlease choose: ");
        String choice = sc.nextLine();
            test:
            switch(choice){
                case "1":
                    displayProfile();
                    System.out.println("6. Total income amount: " + sellerTotalIncome());
                    System.out.println("7. Total pending payment: " + bidderTotalPendingPayment());
                    System.out.println("8. Bidder Status: " + bidderStatus(bidder));
                    manageProfile = true;
                    break;
                    
                case "2":
                    boolean edit = true;
                    while(edit){
                    displayProfile();
                    System.out.print("Which one you like to edit? [Enter 6 to exit editor]: ");
                    String choice2 = sc.nextLine();
                    edit = editor(choice2); 
                    }
                    manageProfile = true;
                    break;
                
                case "3":
                    manageProfile = false;
                    break;
                    
                default:
                    System.out.println("Invalid input. Please enter again: ");     
            }
            return manageProfile;
    }
    
    public boolean editor(String choice){
        boolean edit = false;
        Scanner sc = new Scanner(System.in);
        switch(choice){
            case "1":
                System.out.print("Enter new name: ");
                String newName = sc.nextLine();
                user.setName(newName);
                edit = true;
                break;
                
            case "2":
                System.out.print("Enter new IC number: ");
                String newIC = sc.nextLine();
                user.setIC(newIC);
                edit = true;
                break;
                
            case "3":
                System.out.print("Enter new payment method (Paypal / Credit Card / Web Cash): ");
                String newPayment = sc.nextLine();
                user.setPaymentType(newPayment);
                edit = true;
                break;    
                
            case "4":
                System.out.print("Enter new address: ");
                String newAddress = sc.nextLine();
                user.setAddress(newAddress);
                edit = true;
                break;    
                
            case "5":
                System.out.print("Enter new phone number: ");
                String newPhone = sc.nextLine();
                user.setPhone(newPhone);
                edit = true;
                break;   
                
            case "6":
                edit = false;
                break;
        }
        return edit;
    }
 
    public int checkDatabaseLines(){
        int i = 0;
        try{
            Scanner inputstream = new Scanner (new FileInputStream("database/userdatabase.txt"));
            while (inputstream.hasNextLine()){
                inputstream.nextLine();
                i++;
            }
        } catch (FileNotFoundException e){
            System.out.println("Cannot count database.");
        }
        return i;
    }
    
    public int checkUserIdPosition(String userId){
        int count = 1;
        int here=0;
        try{
            Scanner inputstream = new Scanner (new FileInputStream("database/userdatabase.txt"));
            int i = 0;
            String[] userIdCheck = new String[checkDatabaseLines()];
            checkLine:
            while(inputstream.hasNextLine()){
                String check = inputstream.nextLine();
                for(String hold : check.split(",")){
                    userIdCheck[i] = hold;
                    if(userIdCheck[i].equals(userId)){
                        here = count;
                    }
                    i++;
                    break;
                }
                count++;
            }
        }catch (IOException e){
            System.out.println("User id not found.");
        }
        return here;
    }    
    
    public void updateUserdatabase(int delete){
        String deleted= null;
        try{
            File temp = new File("database/temp.txt");
            File read = new File("database/userdatabase.txt");
            PrintWriter write = new PrintWriter (new FileOutputStream(temp));
            Scanner inputstream = new Scanner (new FileInputStream(read));
            int counter = 0;
            while(inputstream.hasNextLine()){
                counter++;
                String hold = inputstream.nextLine();
                if(counter != delete){
                    write.println(hold);
                }else{
                    deleted = hold;
                }
            }
            inputstream.close();
            inputstream = null;
            write.flush();
            write.close();
            write = null;
            System.gc();
            
            read.setWritable(true);
            read.delete();
//            if (!read.delete()) {
//                System.out.println("Could not delete file");
//                return;
//            }
            temp.renameTo(read);

  //          Rename the new file to the filename the original file had.
//            if (!temp.renameTo(read)){
//                    System.out.println("Could not rename file");
//
//            }
        }catch (IOException e){
            System.out.println("Error writing to temporary file.");
        }
        
        String sellerItemList = "";
        if(seller.itemList.size() == 0)
            sellerItemList = "null";
        else{
            for(int i = 0; i<seller.itemList.size(); i++){
                Item item = seller.itemList.get(i);
                if(i==seller.itemList.size()-1)
                    sellerItemList = sellerItemList + item.getName();
                else
                    sellerItemList = sellerItemList + item.getName() + ":";
            }
        }
        
        
        String bidderBiddingList = "";
        if(bidder.biddingList.size()==0)
            bidderBiddingList = "null";
        else{
            for(int i = 0; i<bidder.biddingList.size(); i++){
                String itemName = bidder.biddingList.get(i);
                Item item = itemList.getItem(itemList.indexOfItem(itemName)).getValue();
                if(i==bidder.biddingList.size()-1)
                    bidderBiddingList = bidderBiddingList + item.getName();
                else bidderBiddingList = bidderBiddingList + item.getName() + ":";
            }
        }
        
        String bidderSuccessList = "";
        if(bidder.successBidList.size()==0)
            bidderSuccessList = "null";
        else{
            for(int i = 0; i<bidder.successBidList.size(); i++){
                String itemName = bidder.successBidList.get(i);
                Item item = itemList.getItem(itemList.indexOfItem(itemName)).getValue();
                if(i==bidder.successBidList.size()-1)
                    bidderSuccessList = bidderSuccessList + item.getName();
                else bidderSuccessList = bidderSuccessList + item.getName() + ":";
            }
        }
        
        String[] userData = new String[11];
        int count = 0;
            for(String hold : deleted.split(",")){
                userData[count] = hold;
                count++;
            }
               
        String record = userData[0] + "," + userData[1] + "," + user.getName() + "," + user.getIC() + "," + user.getPaymentType() + "," + user.getAddress() + "," + user.getPhone() + "," + sellerItemList + "," + bidderBiddingList + "," + bidderSuccessList + "," + bidder.getBidFrequency();
            
        try{
            PrintWriter inputstream = new PrintWriter (new FileOutputStream ("database/userdatabase.txt", true));
            inputstream.println(record);
            inputstream.close();
        } catch (IOException e){
            System.out.println("Cannot create record");
        }
    }
    
    public void removeAccount(String userId){
        int delete = checkUserIdPosition(userId);
        try{
            File temp = new File("database/temp.txt");
            File read = new File("database/userdatabase.txt");
            PrintWriter write = new PrintWriter (new FileOutputStream(temp));
            Scanner inputstream = new Scanner (new FileInputStream(read));
            int counter = 0;
            while(inputstream.hasNextLine()){
                counter++;
                String hold = inputstream.nextLine();
                if(counter != delete){
                    write.println(hold);
                }
            }
            inputstream.close();
            inputstream = null;
            write.flush();
            write.close();
            write = null;
            System.gc();
            
            read.setWritable(true);
            read.delete();
            temp.renameTo(read);
//            if (!read.delete()) {
//                System.out.println("Could not delete file");
//                return;
//            }
//            //Rename the new file to the filename the original file had.
//            if (!temp.renameTo(read)){
//                    System.out.println("Could not rename file");
//            }
        }catch (IOException e){
            System.out.println("Error writing to temporary file.");
        }
    }
    
    public void addItem(Date date, Item item){
        int i = 0;
        Pair<Date,Item> hold = itemList.getItem(i);
        while(hold!= null &&hold.getKey().after(date) && i < itemList.getEntry()){                
            hold = itemList.getItem(i+1);
            i++;
        }
        itemList.add(i, date, item);  
        //seller.itemList.add(item);
    }
    
    public boolean sellerMode(){
        boolean continueMode = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n====== Seller Mode ======");
        System.out.print("1. Add new item to sell\n2. Check all item\n3. Check ongoing auction\n4. Check sold item\n5. Exit seller mode\nPlease choose: ");
        String choice = sc.nextLine();
        switch(choice){
            case "1":
                createNewItem();
                break;
                
            case "2":
                accessSellerAllItem();
                break;
                
            case "3":
                accessSellerOngoingItem();
                break;
                
            case "4":
                accessSellerEndedItem();
                break;
                
            case "5":
                continueMode = false;
                break;
                
            default:
                System.out.print("Invalid input. Please enter again: ");
                
        }
        return continueMode;
    }
    
    public void accessSellerAllItem(){
        ArrayList<Item> allItem = seller.itemList;
        System.out.println("\n====== Seller All Item ======");
        if(seller.itemList.isEmpty())
            System.out.println("No item.");
        else
            displayItemList(allItem);
    }
    
    public void accessSellerOngoingItem(){
        ArrayList<Item> hold = new ArrayList<>();
        Date currentDate = new Date();
        for(int i = 0; i<seller.itemList.size(); i++){
            Item item = seller.itemList.get(i);
            if(currentDate.after((item.auctionType).startTime) && currentDate.before((item.auctionType).endTime))
                hold.add(item);
        }
        System.out.println("====== Seller Ongoing Auction ======");
        if(hold.isEmpty())
            System.out.println("No item.");
        else
            displayItemList(hold);
    }
    
    public void accessSellerEndedItem(){
        ArrayList<Item> hold = new ArrayList<>();
        Date currentDate = new Date();
        for(int i = 0; i<seller.itemList.size(); i++){
            Item item = seller.itemList.get(i);
            if(currentDate.after((item.auctionType).endTime))
                hold.add(item);
        }
        System.out.println("====== Seller Ended Auction ======");
        if(hold.isEmpty())
            System.out.println("No item.");
        else{
            System.out.println("Current Time: " + dateformat.format(currentDate));
            System.out.println(calcTab("Item Name")+calcTab("Item Price")+calcTab("Item Description")+calcTab("Auction Start Time")+calcTab("Auction End Time")+calcTab("Auction Type")+calcTab("Auction Winner")+calcTab("Winning Price")+calcTab("Total Bid"));
            for(int i = 0; i<hold.size(); i++){
                Item item = hold.get(i);
                    if(item.auctionType.AuctionType.equals("VickeryAuction")){    
                        if(item.auctionType.bidStack.bidderList.size()<2)
                            System.out.println(calcTab(item.getName())+calcTab(item.getPrice()+"")+calcTab(item.getDescription())+calcTab((item.auctionType).startTime+"")+calcTab((item.auctionType).endTime+"")+calcTab(item.auctionType.AuctionType+"")+calcTab(item.auctionType.bidStack.peek().getValue().getName())+calcTab(((VickeryAuction)item.auctionType).getSecondHighestBid()+"")+calcTab(item.auctionType.bidStack.bidTimeList.size()+""));

                        else System.out.println(calcTab(item.getName())+calcTab(item.getPrice()+"")+calcTab(item.getDescription())+calcTab((item.auctionType).startTime+"")+calcTab((item.auctionType).endTime+"")+calcTab(item.auctionType.AuctionType+"")+calcTab(item.auctionType.bidStack.bidderList.get(item.auctionType.bidStack.bidderList.size()-2).getName())+calcTab(((VickeryAuction)item.auctionType).getSecondHighestBid()+"")+calcTab(item.auctionType.bidStack.bidTimeList.size()+""));
                    }
                    else
                        System.out.println(calcTab(item.getName())+calcTab(item.getPrice()+"")+calcTab(item.getDescription())+calcTab((item.auctionType).startTime+"")+calcTab((item.auctionType).endTime+"")+calcTab(item.auctionType.AuctionType)+calcTab(item.auctionType.bidStack.peek().getValue().getName())+calcTab(item.auctionType.getHighestBid()+"")+calcTab(item.auctionType.bidStack.bidTimeList.size()+""));
            }
        }    
    }
    
    public void displayItemList(ArrayList<Item> list){
        Date currentDate = new Date();
        System.out.println("Current Time: " + dateformat.format(currentDate));
        System.out.println(calcTab("Item Name")+calcTab("Item Price")+calcTab("Item Description")+calcTab("Auction Start Time")+calcTab("Auction End Time")+calcTab("Auction Type"));
        for(int i = 0; i<list.size(); i++){
            Item item = list.get(i);
            System.out.println(calcTab(item.getName())+calcTab(item.getPrice()+"")+calcTab(item.getDescription())+calcTab((item.auctionType).startTime+"")+calcTab((item.auctionType).endTime+"")+calcTab(item.auctionType.AuctionType+""));
        }
    }
    
    public void checkBiddingList(){
        Date currentDate = new Date();
        for(int i = 0; i<bidder.biddingList.size(); i++){
            String itemName = bidder.biddingList.get(i);
            Item item = itemList.getItem(itemList.indexOfItem(itemName)).getValue();
            if(currentDate.after(item.auctionType.endTime)){
                if(item.auctionType.AuctionType.equals("VickeryAuction")){    
                    if(item.auctionType.bidStack.bidderList.size()<2){
                        if((item.auctionType.bidStack.peek().getValue().getName()).equals(bidder.getName()))
                            bidder.successBidList.add(itemName);
//                        else
//                            bidderEndedBid.add(itemName);
                    }    
                    else{
                         if((item.auctionType.bidStack.bidderList.get(item.auctionType.bidStack.bidderList.size()-2).getName()).equals(bidder.getName()))
                             bidder.successBidList.add(itemName);   
//                         else
//                             bidderEndedBid.add(itemName);
                    }
                }
                else{
                    if((item.auctionType.bidStack.peek().getValue().getName()).equals(bidder.getName()))
                        bidder.successBidList.add(itemName);
//                    else
//                        bidderEndedBid.add(itemName);
                }
                bidderEndedBid.add(itemName);
                bidder.biddingList.remove(i);
            }
        }
    }
    
    //Ended Auction + Success List
    public void accessBidderEndedList(){
        System.out.println("====== Ended Auction ======");
        if(bidderEndedBid.isEmpty() && bidder.successBidList.isEmpty())
            System.out.println("No item.");
        else if(bidderEndedBid.isEmpty() && !bidder.successBidList.isEmpty()){
            System.out.println("The items' auction time has ended.");
            System.out.println(calcTab("Item Name")+calcTab("Item Price")+calcTab("Item Description")+calcTab("Auction Start Time")+calcTab("Auction End Time")+calcTab("Auction Type")+calcTab("Auction Winner")+calcTab("Winning Price")+calcTab("Total Bid"));
            displayStringList(bidderEndedBid);
        }
        else if(bidder.successBidList.isEmpty() && !bidderEndedBid.isEmpty()){
            System.out.println("The items below are suceess bid.");
            System.out.println(calcTab("Item Name")+calcTab("Item Price")+calcTab("Item Description")+calcTab("Auction Start Time")+calcTab("Auction End Time")+calcTab("Auction Type")+calcTab("Auction Winner")+calcTab("Winning Price")+calcTab("Total Bid"));
            displayStringList(bidder.successBidList);
        }
        else{
            System.out.println("The items' auction time has ended.");
            System.out.println(calcTab("Item Name")+calcTab("Item Price")+calcTab("Item Description")+calcTab("Auction Start Time")+calcTab("Auction End Time")+calcTab("Auction Type")+calcTab("Auction Winner")+calcTab("Winning Price")+calcTab("Total Bid"));
            displayStringList(bidderEndedBid);
            System.out.println("The items below are suceess bid.");
            System.out.println(calcTab("Item Name")+calcTab("Item Price")+calcTab("Item Description")+calcTab("Auction Start Time")+calcTab("Auction End Time")+calcTab("Auction Type")+calcTab("Auction Winner")+calcTab("Winning Price")+calcTab("Total Bid"));
            displayStringList(bidder.successBidList);
        }
    }
    
    public void accessBidderBiddingList(){
        System.out.println("\n====== Bidding Auction ======");
        if(bidder.biddingList.isEmpty())
            System.out.println("You  have not registered any item yet.");
        else{
            Date currentDate = new Date();
            System.out.println("Current Time: "+dateformat.format(currentDate));
            System.out.println("All Auction: ");
            System.out.println(calcTab("Item Name")+calcTab("Item Price")+calcTab("Item Description")+calcTab("Auction End Time")+calcTab("Auction Type")+calcTab("Current Highest Bid")+calcTab("Bidder"));
            for(int i = 0; i<bidder.biddingList.size(); i++){
                String itemName = bidder.biddingList.get(i);
                Item item = itemList.getItem(itemList.indexOfItem(itemName)).getValue();
                if(item.auctionType.AuctionType.equals("VickeryAuction") || item.auctionType.AuctionType.equals("BlindAuction")){
                    System.out.println(calcTab(item.getName())+calcTab(item.getPrice()+"")+calcTab(item.getDescription())+calcTab((item.auctionType).endTime+"")+calcTab(item.auctionType.AuctionType+"")+calcTab("Anonymous Price")+calcTab("Anonymous Bidder"));
                }
                else
                    System.out.println(calcTab(item.getName())+calcTab(item.getPrice()+"")+calcTab(item.getDescription())+calcTab((item.auctionType).endTime+"")+calcTab(item.auctionType.AuctionType)+calcTab(item.auctionType.getHighestBid()+"")+calcTab(item.auctionType.bidStack.peek().getValue().getName()));
            }
        }
    }
    
    public void displayStringList(ArrayList<String> list){
        for(int i = 0; i<list.size(); i++){
            String itemName = list.get(i);
            Item item = itemList.getItem(itemList.indexOfItem(itemName)).getValue();
        if(item.auctionType.AuctionType.equals("VickeryAuction")){    
            if(item.auctionType.bidStack.bidderList.size()<2)
                 System.out.println(calcTab(item.getName())+calcTab(item.getPrice()+"")+calcTab(item.getDescription())+calcTab((item.auctionType).startTime+"")+calcTab((item.auctionType).endTime+"")+calcTab(item.auctionType.AuctionType+"")+calcTab(item.auctionType.bidStack.peek().getValue().getName())+calcTab(((VickeryAuction)item.auctionType).getSecondHighestBid()+"")+calcTab(item.auctionType.bidStack.bidTimeList.size()+""));

            else System.out.println(calcTab(item.getName())+calcTab(item.getPrice()+"")+calcTab(item.getDescription())+calcTab((item.auctionType).startTime+"")+calcTab((item.auctionType).endTime+"")+calcTab(item.auctionType.AuctionType+"")+calcTab(item.auctionType.bidStack.bidderList.get(item.auctionType.bidStack.bidderList.size()-2).getName())+calcTab(((VickeryAuction)item.auctionType).getSecondHighestBid()+"")+calcTab(item.auctionType.bidStack.bidTimeList.size()+""));
        }
        else
            System.out.println(calcTab(item.getName())+calcTab(item.getPrice()+"")+calcTab(item.getDescription())+calcTab((item.auctionType).startTime+"")+calcTab((item.auctionType).endTime+"")+calcTab(item.auctionType.AuctionType)+calcTab(item.auctionType.bidStack.peek().getValue().getName())+calcTab(item.auctionType.getHighestBid()+"")+calcTab(item.auctionType.bidStack.bidTimeList.size()+""));
        }
    }
    
    public void createNewItem(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n====== Create New Item ======");
        System.out.print("Please fill in the item details.\n\nName: ");
        String itemName = sc.nextLine();
        System.out.print("\nDescription [not more than 30 characters]: ");
        String itemDescription = sc.nextLine();
        System.out.print("\nPrice: RM");
        double itemPrice = sc.nextDouble();
        System.out.print("\nAuction start time [dd-mm-yyyy hh:mm:ss]: ");
        sc.nextLine();
        String startTime = sc.nextLine();
        System.out.print("\nAuction end time [dd-mm-yyyy hh:mm:ss]: ");
        String endTime = sc.nextLine();
        System.out.print("\nAuction type: \n1. English Auction\n2. Blind Auction\n3. Vickery Auction\n4. Reserve Auction\nChoose: ");
        String choice = sc.nextLine();
        switch(choice){
            case "1":
                System.out.print("\nMinimum exceed amount: RM");
                double minExceed = sc.nextDouble();
                try{
                    a = dateformat.parse(startTime);
                    b = dateformat.parse(endTime);
                    newAuction = new EnglishAuction (itemPrice,a , b, minExceed);
                }catch (ParseException e){
                    System.out.println("Error parsing. in createNewItem");
                }
                break;
            
            case "2":
                try{
                    a = dateformat.parse(startTime);
                    b = dateformat.parse(endTime);
                    newAuction = new BlindAuction (itemPrice, a, b);
                }catch(ParseException e){
                    System.out.println("Error parsing.in createNewItem");
                }
                break;
                
            case "3":
                try{
                    a = dateformat.parse(startTime);
                    b = dateformat.parse(endTime);
                    newAuction = new VickeryAuction (itemPrice, a, b);
                }catch(ParseException e){
                    System.out.println("Error parsing.in createNewItem");
                }
                break;
                
            case "4":
                System.out.print("\nReserve price: ");
                double reservePrice = sc.nextDouble();
                try{
                    a = dateformat.parse(startTime);
                    b = dateformat.parse(endTime);
                    newAuction = new ReserveAuction (itemPrice,  a, b, reservePrice);
                }catch(ParseException e){
                    System.out.println("Error parsing.in createNewItem");
                }
                break;
                
            default:
                System.out.println("Invalid input. Please enter again.");
                break;
        }
        try {
            newAuction.stTime = startTime;
            newAuction.enTime = endTime;
            Item newItem = new Item(itemName, itemPrice, itemDescription, newAuction);
            addItem(dateformat.parse(startTime), newItem);
            seller.itemList.add(newItem);
        } catch (ParseException e) {
            System.out.println("Error parsing.in addItem");
        }
    }
    
    public static void checkAvailableAuction(){
        Date current=new Date();
        SimpleDateFormat simpleFormat =new SimpleDateFormat("dd-M-yyyy hh:mm:ss");            //date format can change according to whole system date format
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
    
    
    public void setBidderCall(Item item, Date biddingTime, Double biddingAmount){
        //English Auction 
        if (item.auctionType.AuctionType.equals("EnglishAuction"))
            ((EnglishAuction)item.auctionType).pushBid(biddingAmount, biddingTime, this.bidder);
        //BlindAuction
        else if (item.auctionType.AuctionType.equals("BlindAuction"))
            ((BlindAuction)item.auctionType).pushBid(biddingAmount, biddingTime, this.bidder);
        //ReserveAuction
        else if (item.auctionType.AuctionType.equals("ReserveAuction"))
            ((ReserveAuction)item.auctionType).pushBid(biddingAmount, biddingTime, this.bidder);
        //VickeryAuction
        else if (item.auctionType.AuctionType.equals("VickeryAuction"))
            ((VickeryAuction)item.auctionType).pushBid(biddingAmount, biddingTime, this.bidder);
        boolean newBidItem = true;
        for(int i = 0; i < bidder.biddingList.size() && newBidItem; i++){
            if(item.itemName.equalsIgnoreCase(this.bidder.biddingList.get(i)))
                newBidItem = false;
        }
        if(newBidItem)
            this.bidder.biddingList.add(item.itemName);
    }
    
    public void displayCallingPrice(Item item){
        ArrayList<Double>holdPrice=item.auctionType.bidStack.bidPriceList;
        ArrayList<Bidder>holdBidder=item.auctionType.bidStack.bidderList;
        
        for(int i=0;i<holdPrice.size()&&i<holdBidder.size();i++){
            System.out.print("Calling Price : RM"+holdPrice.get(i));            //is this output method okay?
            System.out.println(" by " + holdBidder.get(i).name);
            System.out.println(" at " + item.auctionType.bidStack.bidTimeList.get(i));
        }
    }
    
    public void sortDateAsc(){
            int x = checkDatabaseLines();
            String[]name = new String[x];
            double[]price = new double[x];
            String[]description = new String[x];
            Date[]startTime = new Date[x];
            Date[]endTime = new Date[x];
            String[]auction = new String[x];
            String[]copyFromText = new String[6];
            int counter2 = 0;
        try{
            Scanner input = new Scanner(new FileInputStream("itemdatabase.txt"));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            while(input.hasNextLine()){
                String copy = input.nextLine();
                int counter1 = 0;
                for(String rekt : copy.split(",")){
                    copyFromText[counter1] = rekt;
                    counter1++;
                }
                   name[counter2]=copyFromText[0];
                   price[counter2]=Double.parseDouble(copyFromText[1]);
                   description[counter2]=copyFromText[2];
                   startTime[counter2]=formatter.parse(copyFromText[3]);
                   endTime[counter2]=formatter.parse(copyFromText[4]);
                   auction[counter2]=copyFromText[5];
                   counter2++;
                
            }
             for(int i=0;i<startTime.length-1;i++){
                 for(int j=i+1;j<startTime.length;j++){
                     if(startTime[i].after(startTime[j])){
                         String hold = name[i];
                         name[i] = name[j];
                         name[j] = hold;
                         double hold1 = price[i];
                         price[i] = price[j];
                         price[j] = hold1;
                         String hold2 = description[i];
                         description[i] = description[j];
                         description[j] = hold2;
                         Date hold3 = startTime[i];
                         startTime[i] = startTime[j];
                         startTime[j] = hold3;
                         Date hold4 = endTime[i];
                         endTime[i] = endTime[j];
                         endTime[j] = hold4;
                         String hold5 = auction[i];
                         auction[i] = auction[j];
                         auction[j] = hold5;
                     }
                 }
             }
            
        }catch(FileNotFoundException e){
            System.out.println("File was not found!");
        }catch(ParseException f){
            System.out.println("Error Parsing!");
        }
    }
    
     public void sortDateDsc(){
            int x = checkDatabaseLines();
            String[]name = new String[x];
            double[]price = new double[x];
            String[]description = new String[x];
            Date[]startTime = new Date[x];
            Date[]endTime = new Date[x];
            String[]auction = new String[x];
            String[]copyFromText = new String[6];
            int counter2 = 0;
        try{
        
            Scanner input = new Scanner(new FileInputStream("itemdatabase.txt"));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            while(input.hasNextLine()){
                String copy = input.nextLine();
                int counter1 = 0;
                for(String rekt : copy.split(",")){
                    copyFromText[counter1] = rekt;
                    counter1++;
                }
                   name[counter2]=copyFromText[0];
                   price[counter2]=Double.parseDouble(copyFromText[1]);
                   description[counter2]=copyFromText[2];
                   startTime[counter2]=formatter.parse(copyFromText[3]);
                   endTime[counter2]=formatter.parse(copyFromText[4]);
                   auction[counter2]=copyFromText[5];
                   counter2++;
                
            }
             for(int i=0;i<startTime.length-1;i++){
                 for(int j=i+1;j<startTime.length;j++){
                     if(startTime[i].before(startTime[j])){
                         String hold = name[i];
                         name[i] = name[j];
                         name[j] = hold;
                         double hold1 = price[i];
                         price[i] = price[j];
                         price[j] = hold1;
                         String hold2 = description[i];
                         description[i] = description[j];
                         description[j] = hold2;
                         Date hold3 = startTime[i];
                         startTime[i] = startTime[j];
                         startTime[j] = hold3;
                         Date hold4 = endTime[i];
                         endTime[i] = endTime[j];
                         endTime[j] = hold4;
                         String hold5 = auction[i];
                         auction[i] = auction[j];
                         auction[j] = hold5;
                     }
                 }
             }
            
        }catch(FileNotFoundException e){
            System.out.println("File was not found!");
        }catch(ParseException f){
            System.out.println("Error Parsing!");
        }
    }
     
     public String bidderStatus(Bidder bidder){
        int freq = bidder.getBidFrequency();
        String status;
            
        if(freq>=0&&freq<=10)
            status="Newbie";
            
        else if(freq>=11&&freq<=20)
            status="Intermediate";
            
        else status="Pro";
            
        return status;
    }
    
    public static String formatTime(){
        Date current=new Date();
        SimpleDateFormat simpleFormat =new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String currentTime=simpleFormat.format(current);
        return currentTime;
    }
    
    public void onGoingAuction(){
        Item holdItem;
        Date current=new Date();
        Scanner kb=new Scanner(System.in);
        SimpleDateFormat simpleFormat =new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        System.out.println("\nCurrent Time: "+simpleFormat.format(current));
        System.out.println("Available in Auction: ");
        System.out.println(calcTab("Item Name")+calcTab("Item Price")+calcTab("Item Description")+calcTab("Start Time")+calcTab("End Time")+calcTab("Type"));
        for(int i=0; i<itemList.getEntry();i++){
            if(itemList.getItem(i).getKey().before(current)&&itemList.getItem(i).getValue().auctionType.endTime.after(current)){
                holdItem=itemList.getItem(i).getValue();
                System.out.println(calcTab(holdItem.getName())+calcTab(Double.toString(holdItem.getPrice()))+calcTab(holdItem.getDescription())+calcTab(simpleFormat.format(holdItem.auctionType.startTime))+calcTab(simpleFormat.format(holdItem.auctionType.endTime))+calcTab(holdItem.auctionType.AuctionType));
            }
        }
        System.out.println();
    }
    
    public void displayAllAuction(){
        Item holdItem;
        if(itemList.getEntry()==0)
            System.out.println("No item.");
        else{
            SimpleDateFormat simpleFormat =new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            System.out.println("All Auction: ");
            System.out.println(calcTab("Item Name")+calcTab("Item Price")+calcTab("Item Description")+calcTab("Auction Start Time")+calcTab("Auction End Time")+calcTab("Auction Type"));
            for(int i=0; i<itemList.getEntry();i++){
                holdItem=itemList.getItem(i).getValue();
                System.out.println(calcTab(holdItem.getName())+calcTab(Double.toString(holdItem.getPrice()))+calcTab(holdItem.getDescription())+calcTab(simpleFormat.format(holdItem.auctionType.startTime))+calcTab(simpleFormat.format(holdItem.auctionType.endTime))+calcTab(holdItem.auctionType.AuctionType));
            }
        }
        
    }

    public void accessBiddingList(){
        String hold1;
        Item hold2;
        System.out.println("Item(s) you are currently bidding: ");
        System.out.println(calcTab("Item Name")+calcTab("Item Price")+calcTab("Current Highest Bid")+calcTab("Bidder"));
        for(int i=0;i<bidder.biddingList.size();i++){
            hold1=bidder.biddingList.get(i);
            for(int j=0;j<itemList.getEntry();j++){
                hold2=itemList.getItem(j).getValue();
                if(hold1.equalsIgnoreCase(hold2.getName()))
                    System.out.println(calcTab(hold1)+calcTab(Double.toString(hold2.getPrice()))+calcTab(Double.toString(hold2.auctionType.getHighestBid())+" by ")+calcTab(hold2.auctionType.bidStack.peek().getValue().getName()));
            }
        }
    }

    public void successBidList(){
        String hold1;
        Item hold2;
        System.out.println("Item(s) that "+bidder.getName() +" successfully bid: ");
        System.out.println(calcTab("Item Name")+calcTab("Item Description")+calcTab("User have to pay (RM)"));
        for(int i=0;i<bidder.successBidList.size();i++){
            hold1=bidder.successBidList.get(i);
            for(int j=0;j<itemList.getEntry();j++){
                hold2=itemList.getItem(j).getValue();
                if(hold1.equalsIgnoreCase(hold2.getName()))
                    System.out.println(calcTab(hold1)+calcTab(hold2.getDescription())+calcTab(Double.toString(bidderTotalPendingPayment())));
            }
        }
    }
    
    public String calcTab(String s){
        if(s.length()<=4)
            return s+"\t\t\t";
        else if(s.length()>4&&s.length()<8)
            return s+"\t\t\t";
        else if(s.length()>=8&&s.length()<16)
            return s+"\t\t";
        else if(s.length()>=16&&s.length()<24)
            return s+"\t";
        else return s+"\t";
    }
    
    public void registerNewItem(){
        String choice;
        
        do{
            Scanner scan = new Scanner(System.in);
            System.out.print("Choose item to bid [Enter 0 to cancel]: ");
            choice=scan.nextLine();
            if(choice.equals("0"))
                break;
            test:
            for(int i=0;i<itemList.getEntry();i++){
                if(choice.equalsIgnoreCase(itemList.getItem(i).getValue().getName())){
                    Item hold = itemList.getItem(i).getValue();
                    if(hold.auctionType.bidStack.bidPriceList.isEmpty()){
                        System.out.println("This item is currently no user bidding.");
                    }
                        
                    else{
                        if(hold.auctionType.AuctionType.equals("VickeryAuction") || hold.auctionType.AuctionType.equals("BlindAuction")){
                            System.out.println("Anonymous bidder ( ͡° ͜ʖ ͡°)");
                        }
                        else 
                            System.out.println("Current highest bid: "+hold.auctionType.getHighestBid()+" by "+hold.auctionType.bidStack.peek().getValue().getName());
                    }
                        System.out.print("Continue to bid?[Y/n] : ");
                        Scanner kb2=new Scanner(System.in);
                        String reply=kb2.nextLine();
                        if(reply.equalsIgnoreCase("y")){
                            if(bidder.biddingList.contains(hold.getName())){
                                System.out.println("This item has already been registered into your bidding list.");  
                            }
                            else{
                                bidder.biddingList.add(itemList.getItem(i).getValue().getName());
                                System.out.println(itemList.getItem(i).getValue().getName()+" is registered to your bidding list.");
                                System.out.println("Your bidding items : "+bidder.biddingList.toString());
                            }
                            break test;
                        }
                        else if(reply.equalsIgnoreCase("n")){
                            break test;
                        }
                }
                //else
                    //System.out.println("Item you choose not available.");
            }
        }while(!choice.equalsIgnoreCase("0"));
    }
    
    public void write(){
        try{
            PrintWriter input = new PrintWriter(new FileOutputStream("database/item.txt"));
            for(int i = 0; i < itemList.getEntry(); i++){
                writeItem(itemList.getItem(i).getValue());
            }
        }catch(IOException e){
            System.out.println("Problem with file output!");
        }
    }
    
    public void read(){
        try{
            Scanner read = new Scanner(new FileInputStream("database/item.txt"));
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            while(read.hasNextLine()){
                String copy = read.nextLine();
                String[] array = copy.split("[,;]");
                String itemName = array[0];
                Double itemPrice = Double.parseDouble(array[1]);
                String itemDescription = array[2];
                String holdStartTime = array[3];
                String holdEndTime = array[4];
                Date startTime1 = dateformat.parse(array[3]);
                Date endTime1 = dateformat.parse(array[4]);
                

                int i = 5;
                Bidder tempBidderObj;
                BiddingStack<Double,Bidder, Date> biddingStackTemp = new BiddingStack<>();
                try{
                    Scanner read1 = new Scanner(new FileInputStream("database/userdatabase.txt"));
                    int count = 6;    
                    while(count<array.length-2){
                            while(read1.hasNextLine()){
                                String[] arrayData = read1.nextLine().split(",");
                                if(array[count].equals("null")){
                                    break;
                                }
                                else{
                                    if(array[count].equalsIgnoreCase(arrayData[2])){
                                        tempBidderObj = new Bidder(arrayData[2],arrayData[3],arrayData[4],arrayData[5],arrayData[6]);
                                        biddingStackTemp.bidderList.add(tempBidderObj);
                                    }
                                }
                            }
                        count+=3;
                    }
                    
                }catch (FileNotFoundException e){
                        System.out.println("File was not found!");
                }
                if(array[7].equals("null") && array[8].equals("null")){
                    
                }
                else{
                    biddingStackTemp.bidPriceList.add(Double.parseDouble(array[7]));
                    biddingStackTemp.bidTimeList.add(dateformat.parse(array[8]));
                }
               
//                int counter1 = 0;
//                while(counter1<this.auctionType.bidStack.bidPriceList.size()-1){
//                biddingStackTemp.push(this.auctionType.bidStack.bidPriceList.get(i), this.auctionType.bidStack.bidderList.get(i), this.auctionType.bidStack.bidTimeList.get(i));
//                }
                Auction auctionTemp;
                if(array[5].equalsIgnoreCase("EnglishAuction")){
                    auctionTemp = new EnglishAuction(itemPrice,biddingStackTemp,startTime1,endTime1,Double.parseDouble(array[array.length-1]));
                }else if(array[5].equalsIgnoreCase("ReserveAuction")){
                    auctionTemp =  new ReserveAuction(itemPrice,biddingStackTemp,startTime1,endTime1,Double.parseDouble(array[array.length-1]));
                }else if(array[5].equalsIgnoreCase("VickeryAuction")){
                    auctionTemp = new VickeryAuction(itemPrice,biddingStackTemp,startTime1,endTime1);
                }else{
                    auctionTemp = new BlindAuction(itemPrice,biddingStackTemp,startTime1, endTime1);
                }
                auctionTemp.stTime = holdStartTime;
                auctionTemp.enTime = holdEndTime;
                Item retrieveItem = new Item(itemName, itemPrice,itemDescription, auctionTemp);
                addItem(startTime1, retrieveItem);
            }       
        }catch(FileNotFoundException a){
            System.out.println("File was not found!");
        }catch(ParseException b){
            System.out.println("Error parsing!");
        }
    }
    
    public void writeItem(Item item){        
        try{
            Date date;
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            PrintWriter input = new PrintWriter(new FileOutputStream("database/item.txt",true));
            String dateString;
            
            input.printf(item.itemName+","+item.itemPrice+","+item.itemDescription+","+item.auctionType.stTime+","+item.auctionType.enTime+","+item.auctionType.AuctionType+",");
            int i = 0;
            
            if(item.auctionType.bidStack.bidPriceList.size() == 0){
                input.printf("null;null;null,");
            }
            else{
                while(i < item.auctionType.bidStack.bidPriceList.size()){
                    if(i == item.auctionType.bidStack.bidPriceList.size()-1){
    //                     
    //                     System.out.println(dateString);
                         input.printf(item.auctionType.bidStack.bidderList.get(i).name+";"+item.auctionType.bidStack.bidPriceList.get(i)+";"+item.auctionType.bidStack.bidTimeList.get(i)+",");
                    }else{                    
    //                     date=dateformat.parse(item.auctionType.bidStack.bidTimeList.get(i).toString());
    ////                     dateString = dateformat.format(date);
    //                     System.out.println(dateString);
                        input.printf(item.auctionType.bidStack.bidderList.get(i).name+";"+item.auctionType.bidStack.bidPriceList.get(i)+";"+item.auctionType.bidStack.bidTimeList.get(i)+";");
                    i++;
                    }
                }
            }
            if(item.auctionType.AuctionType.equalsIgnoreCase("EnglishAuction")){
                input.println(((EnglishAuction)item.auctionType).getMinExceed());
            }else if(item.auctionType.AuctionType.equalsIgnoreCase("ReserveAuction")){
                input.println(((ReserveAuction)item.auctionType).getReservePrice());
            }else{
                input.println("null");
            }
            input.close();
        }catch(IOException e){
            System.out.println("Problem with file output!");
        }
//        }catch(ParseException f){
//            System.out.println("Error parsing!a");
//        }
    }  
    
    public boolean bidderMode(){
        boolean continueMode=true;
        Scanner scan = new Scanner(System.in);
        System.out.println("\n======Bidder Mode======");
        System.out.println("1. Display All Auction\n2. Check My Bidding List\n3. Check Ongoing Auction\n4. Exit bidder mode\\nPlease choose: ");
        String choice=scan.nextLine();
        switch(choice){
            case "1":
                displayAllAuction();
                registerNewItem();
                break;
            case "2":
                accessBidderBiddingList();
                break;
            case "3":
                onGoingAuction();
                break;
            case "4":
                continueMode=false;
            default:
                System.out.print("Invalid input. Please enter again:");
        }
        return continueMode;
    }
    
    
    /*public Double bidderHasToPay(Item item){
        Item hold;
        Double pay=0.00;
        for(int i=0;i<itemList.getEntry();i++){
            if(item.equals(itemList.getItem(i).getValue())){
                hold=itemList.getItem(i).getValue();
                if(item.auctionType.AuctionType.equalsIgnoreCase("EnglishAuction"))
                    pay = hold.auctionType.getHighestBid();
                
                else if(item.auctionType.AuctionType.equalsIgnoreCase("JapaneseAuction"))
                    pay = hold.auctionType.getHighestBid();
                
                else if(item.auctionType.AuctionType.equalsIgnoreCase("BlindAuction"))
                    pay = hold.auctionType.getHighestBid();
                
                else if(item.auctionType.AuctionType.equalsIgnoreCase("VickeryAuction"))
                    pay = hold.auctionType.bidStack.bidPriceList.get(hold.auctionType.bidStack.bidPriceList.size()-2); //ask if this correct or not
                
                else if(item.auctionType.AuctionType.equalsIgnoreCase("ReserveAuction")){
                    pay = hold.auctionType.getHighestBid();
                }
            }
        }
        return pay;
    }*/
}
