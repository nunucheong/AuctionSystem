/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionsystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
/**
 *
 * @author User
 */
public class AuctionSystem {
    /**
     * @param args the command line arguments
     */
    public static String username;
    User user;
    ItemLinkedList<Date,Item> itemList;
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
                                system.createSeller();
                                //Enter bidder mode
                                case "2":
                            
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
        String[] userData = new String[10]; 
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
            sellerItemList.add(itemList.getItem(itemList.indexOfItem(hold)));
        }
        User seller = new Seller (user.getName(), user.getIC(), user.getPaymentType(), user.getAddress(), user.getPhone(), sellerItemList); 
        
        ArrayList<Item> bidderBiddingList = new ArrayList<>();
        for(String hold : userData[8].split(":")){
            bidderBiddingList.add(itemList.getItem(itemList.indexOfItem(hold)));
        }
        
        ArrayList<Item> bidderSuccessList = new ArrayList<>();
        for(String hold : userData[9].split(":")){
            bidderSucceddList.add(itemList.getItem(itemList.indexOfItem(hold)));
        }
        return userData;
    }
    
    //constructor to be amend
    public void createSeller(){
       
    }
    
    //constructor to be amend
    public void createBidder(){
        User bidder = new Bidder (user.getName(), user.getIC(), user.getPaymentType(), user.getAddress(), user.getPhone()); 
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
        
        String[] userData = new String[10];
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
}
