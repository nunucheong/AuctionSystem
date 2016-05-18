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
    ItemLinkedList<Date,Item> itemList;
    Auction newAuction;
    SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    public static void main(String[] args) {
        AuctionSystem system = new AuctionSystem();
        Scanner sc = new Scanner (System.in);
        boolean runProgram = true;
        boolean logStatus;
        
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
                    logIn:
                    while(logStatus){
                        //create new user object
                        system.readUserProfile(username);
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
                                    logStatus = false;
                                    break logIn;
                                
                                //remove account    
                                case "5":
                                    system.removeAccount(username);
                                    System.out.println("Your account is removed successfully. Thank you for using our auction system.");
                                    break logIn;
                                    
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
            sellerItemList.add(itemList.getItem(itemList.indexOfItem(hold)).getValue());
        }
        seller = new Seller (user.getName(), user.getIC(), user.getPaymentType(), user.getAddress(), user.getPhone(), sellerItemList); 
        
        ArrayList<String> bidderBiddingList = new ArrayList<>();
        for(String hold : userData[8].split(":")){
            bidderBiddingList.add(itemList.getItem(itemList.indexOfItem(hold)).getValue().getName());
        }
        
        ArrayList<String> bidderSuccessList = new ArrayList<>();
        for(String hold : userData[9].split(":")){
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
                    saveEditedProfile(checkUserIdPosition(username));
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
                        break checkLine;
                    }
                    i++;
                    break;
                }
                count++;
            }
        }catch (IOException e){
            System.out.println("User id not found.");
        }
        return count;    
    }    
    
    public void saveEditedProfile(int delete){
        String deleted=null;
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

            //Rename the new file to the filename the original file had.
//            if (!temp.renameTo(read)){
//                    System.out.println("Could not rename file");
//
//            }
        }catch (IOException e){
            System.out.println("Error writing to temporary file.");
        }
        
        String[] userData = new String[11];
        int count = 0;
            for(String hold : deleted.split(",")){
                userData[count] = hold;
                count++;
            }
            
        String record = userData[0] + "," + userData[1] + "," + user.getName() + "," + user.getIC() + "," + user.getPaymentType() + "," + user.getAddress() + "," + user.getPhone() + "," + userData[7] + "," + userData[8] + "," + userData[9];
            
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
            if (!read.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            //Rename the new file to the filename the original file had.
            if (!temp.renameTo(read)){
                    System.out.println("Could not rename file");
            }
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
        System.out.println(i+ " " +item.toString());
        itemList.add(i, date, item);  
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
                break;
                
            case "3":
                break;
                
            case "4":
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
        ArrayList<Item> allItem = ((Seller)seller).itemList;
        displayItemList(allItem);
    }
    
    public void displayItemList(ArrayList<Item> list){
        Date currentDate = new Date();
        System.out.println("Current Time: " + dateformat.format(currentDate));
        System.out.println("All auction: ");
        System.out.println("\nItem Name\t\tItem Price\t\tItem Description\t\tAuction Start Time\t\tAuction End Time\t\tAuction Type");
        for(int i = 0; i<list.size(); i++){
            Item item = list.get(i);
            System.out.println(calcTab(item.getName())+calcTab(item.getPrice()+"")+calcTab(item.getDescription())+calcTab((item.auctionType).startTime+"")+calcTab((item.auctionType).endTime+"")+calcTab(item.auctionType.AuctionType+""));
        }
    }
    
    //ask how to get information
    public void displayStringList(ArrayList<String> list){
        Date currentDate = new Date();
        System.out.println("Current Time: "+dateformat.format(currentDate));
        System.out.println("All Auction: ");
        System.out.println("\nItem Name\t\tItem Price\t\tItem Description\t\tAuction Start Time\t\tAuction End Time\t\tAuction Type");
        for(int i = 0; i<list.size(); i++){
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
        String startTime = sc.nextLine();
        System.out.print("\nAuction end time [dd-mm-yyyy hh:mm:ss]");
        String endTime = sc.nextLine();
        System.out.print("\nAuction type: \n1. English Auction\n2. Blind Auction\n3. Vickery Auction\n4. Reserve Auction\nChoose: ");
        String choice = sc.nextLine();
        switch(choice){
            case "1":
                System.out.print("\nMinimum exceed amount");
                double minExceed = sc.nextDouble();
                try{
                    newAuction = new EnglishAuction (itemPrice, dateformat.parse(startTime), dateformat.parse(endTime), minExceed);
                }catch (ParseException e){
                    System.out.println("Error parsing.");
                }
                break;
            
            case "2":
                try{
                    newAuction = new BlindAuction (itemPrice, dateformat.parse(startTime), dateformat.parse(endTime));
                }catch(ParseException e){
                    System.out.println("Error parsing.");
                }
                break;
                
            case "3":
                try{
                    newAuction = new VickeryAuction (itemPrice, dateformat.parse(startTime), dateformat.parse(endTime));
                }catch(ParseException e){
                    System.out.println("Error parsing.");
                }
                break;
                
            case "4":
                System.out.print("\nReserve price: ");
                double reservePrice = sc.nextDouble();
                try{
                    newAuction = new ReserveAuction (itemPrice, dateformat.parse(startTime), dateformat.parse(endTime), reservePrice);
                }catch(ParseException e){
                    System.out.println("Error parsing.");
                }
                break;
                
            default:
                System.out.println("Invalid input. Please enter again.");
                break;
        }
        try {
            addItem(dateformat.parse(startTime), new Item(itemName, itemPrice, itemDescription, newAuction));
        } catch (ParseException e) {
            System.out.println("Error parsing.");
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
    
    
    public void setBidderCall(Item item, Date biddingTime, Double biddingAmount, Bidder bidder){
        //English Auction 
        if (item.auctionType.AuctionType.equals("EnglishAuction"))
            ((EnglishAuction)item.auctionType).pushBid(biddingAmount, biddingTime, bidder);
        //BlindAuction
        else if (item.auctionType.AuctionType.equals("BlindAuction"))
            ((BlindAuction)item.auctionType).pushBid(biddingAmount, biddingTime, bidder);
        //ReserveAuction
        else if (item.auctionType.AuctionType.equals("ReserveAuction"))
            ((ReserveAuction)item.auctionType).pushBid(biddingAmount, biddingTime, bidder);
        //VickeryAuction
        else if (item.auctionType.AuctionType.equals("VickeryAuction"))
            ((VickeryAuction)item.auctionType).pushBid(biddingAmount, biddingTime, bidder);
    }
    
    public void displayCallingPrice(Item item){
        ArrayList<Double>holdPrice=item.auctionType.bidStack.bidPriceList;
        ArrayList<Bidder>holdBidder=item.auctionType.bidStack.bidderList;
        
        for(int i=0;i<holdPrice.size()&&i<holdBidder.size();i++){
            System.out.print("Calling Price : RM"+holdPrice.get(i));            //is this output method okay?
            System.out.println(" by " + holdBidder.get(i));
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
            
        if(freq>=1&&freq<=10)
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
        Date current=new Date();
        SimpleDateFormat simpleFormat =new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        System.out.println("Current Time: "+simpleFormat.format(current));
        try{            
            String [] itemData=new String[30];
            String [] hold=new String[30];
            Scanner input=new Scanner(new FileInputStream("itemdatabase.txt"));
            System.out.print("Available Auction(s): \nItem Name \t\tItem Price\t\tItem Description\t\tAuction Start Time\t\tAuction End Time\t\tAuction Type\n");
            while(input.hasNextLine()){
                String read=input.nextLine();
                itemData=read.split(",");
                
                Date startTime=simpleFormat.parse(itemData[3]);
                Date endTime=simpleFormat.parse(itemData[4]);//convert String into Date

                if(current.before(endTime)&&current.after(startTime)){
                    hold = itemData;
                }
                System.out.println(calcTab(hold[0])+calcTab(hold[1])+calcTab(hold[2])+calcTab(hold[3])+calcTab(hold[4])+calcTab(hold[5]));
            }
            input.close();
        }
        catch(FileNotFoundException e){
            e.getMessage();
        }
        catch(ParseException e){
        }
    }


    public String calcTab(String s){
        if(s.length()<8)
            return s+"\t\t\t";
        else if(s.length()>8&&s.length()<16)
            return s+"\t\t\t";
        else if(s.length()>16&&s.length()<24)
            return s+"\t\t";
        else if(s.length()>24&&s.length()<32)
            return s+"\t";
        else return s+"\t";
    }
  
}
