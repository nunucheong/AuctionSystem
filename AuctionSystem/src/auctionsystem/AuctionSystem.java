/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionsystem;
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
        String username;
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
        }
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
    
}
