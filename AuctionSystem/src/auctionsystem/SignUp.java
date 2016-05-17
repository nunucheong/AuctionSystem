
package auctionsystem;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
/**
 *
 * @author Wang Zhong Qi WES150037
 */
public class SignUp {
    private String newUserId;
    private String newPassword;
    private String newName;
    private String newIc;
    private String newPayment;
    private String newAddress;
    private String newPhoneNo;
    private boolean newUsernameAuthentication = true;
    private boolean newPasswordAuthentication = false;
    private boolean newAccountAuthentication = false;
    Scanner sc = new Scanner (System.in);
    
    public SignUp(){}
    
    //check whether database file exists else create
    public void databaseCheck(){
        try{
            Scanner inputstream = new Scanner (new FileInputStream("database/userdatabase.txt"));
            inputstream.close();
        } catch (FileNotFoundException e){
            databaseCreate();
        }
    }
    
    //create the database
    public void databaseCreate(){
        try{
            PrintWriter createDatabase = new PrintWriter (new FileOutputStream ("database/userdatabase.txt"));
            createDatabase.close();
        } catch (IOException e){
            System.out.println("Database is not created.");
        }
    }
    
    //check and return how many lines there is in the userdatabase.txt
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
    
    //prompt user to input ther username for the new account
    public void getUserId(){
        System.out.print("Please enter your desired user id: ");
        newUserId = sc.nextLine();
    }
    
    //check existing username in the database to ensure there is no overlapping else prompt user to input again
    public void checkExistingUserId(){
        try{
            Scanner inputstream = new Scanner (new FileInputStream("database/userdatabase.txt"));
            int i = 0;
            String [] usernamecheck = new String [checkDatabaseLines()];
            while (inputstream.hasNextLine()){
                //get a line in the database
                String check = inputstream.nextLine();
                //split the file 
                for (String ret : check.split(",")){
                    usernamecheck[i] = ret;
                    if (newUserId.equals(usernamecheck[i])){
                        System.out.print("User id already exist. Please try another one:");
                        newUserId = sc.nextLine();
                        checkExistingUserId();
                    }
                    break;
                }
            }
        } catch (IOException e){
            System.out.println("Cannot find username.");
        }     
    }
        
    //prompt user to enter and confirm their password before their account is created
    public void checkPasswordInput(){
        while (!newPasswordAuthentication){
            System.out.print("Please input your password: ");
            String password1 = sc.nextLine();
            System.out.print("Please reenter your password: ");
            String password2 = sc.nextLine();
            if (password1.equals(password2)){
                newPassword = password1;
                newPasswordAuthentication = true;
            }
        }
    }
    
    //return whether the new username and password are created successfully
    public boolean checkSignUpStatus(){
        if (newUsernameAuthentication && newPasswordAuthentication){
            newAccountAuthentication = true;
        }
        return newAccountAuthentication;
    }
        
    //adds the new username and password into userdatabase.txt
    public void addUserAccount(){
        try{
            PrintWriter inputstream = new PrintWriter (new FileOutputStream ("database/userdatabase.txt",true));
            inputstream.println(newUserId+","+newPassword+","+newName+","+newIc+","+newPayment+","+newAddress+","+newPhoneNo+",null,null,null,0");
            inputstream.close();
        } catch (IOException e){
            System.out.println("Unable to create user account.");
        }
    }
    
    //collection of the methods used to create new account
    public void signup (){
        databaseCheck();
        System.out.println("\n====== Sign Up ======");
        getUserId();
        checkExistingUserId();
        checkPasswordInput();
        if (checkSignUpStatus()){
            System.out.println("Account created successfully.");
            System.out.println("Please fill up your personal details.");
            System.out.println(" ");
            System.out.print("Name: ");
            this.newName = sc.nextLine();
            System.out.print("IC Number: ");
            this.newIc = sc.nextLine();
            System.out.print("Payment Method [Paypal / Credit Card / Web Cash]: ");
            this.newPayment = sc.nextLine();
            System.out.print("Address: ");
            this.newAddress = sc.nextLine();
            System.out.print("Phone Number: ");
            this.newPhoneNo = sc.nextLine();
            addUserAccount();
        }
    }
    

}
