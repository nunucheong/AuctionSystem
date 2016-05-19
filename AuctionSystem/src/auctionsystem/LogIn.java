
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
public class LogIn {
    
    public String username;
    private String password;
    private int passwordLocation = 1;
    private boolean logInAuthentication = false;
    private boolean usernameAuthentication = false;
    private boolean passwordAuthentication = false;
    Scanner sc = new Scanner(System.in);
    
    //empty constructor
    public LogIn(){
    }
        
    public LogIn(String username){
        this.username = username;
    }
    
    public void userLogIn(){
        System.out.print("Please enter your password: ");
        password = sc.nextLine();
    }
    
    //check whether the database file exists else create one
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
    
    //checking whether the username exists
    public void checkUsername(){
        try{
            Scanner inputstream2 = new Scanner (new FileInputStream("database/userdatabase.txt"));
            int i = 0;
            //array usernamecheck can be made public in cases if other classes were to access it, makes stuff easier in some cases but the implementation is still under consideration
            String [] usernamecheck = new String [checkDatabaseLines()];
            test:
            while (inputstream2.hasNextLine()){
                //get a line in the database
                String check = inputstream2.nextLine();
                //split the file 
                for (String ret : check.split(",")){
                    usernamecheck[i] = ret;
                    if (username.equals(usernamecheck[i])){
                        usernameAuthentication = true;
                        break test;
                    }
                    i++;
                    break;
                }
                //passwordLocation is used to check the username lies in which line so that only that line is checked when matching up the password input by the account owner, further description can be found below
                passwordLocation++;
            }
            inputstream2.close();
        } catch (IOException e){
            System.out.println("Cannot find username.");
        }       
    }
    
    //checking whether the password matches the username
    //more lines should be added if it consists of multiple users
    public void checkPassword(){
        try{
            Scanner inputstream = new Scanner (new FileInputStream("database/userdatabase.txt"));
            int i = 1;
            String passwordcheck;
            while (inputstream.hasNextLine()){
                //get a line in the database
                String check = inputstream.nextLine();
                //only perform password checking when reading the line where the authenticated username lies 
                //this ensures the account is not logged in in cases when there are different user accounts with the same password
                if (i == passwordLocation){
                    //split the file 
                    for (String ret : check.split(",")){
                        passwordcheck = ret;
                        if (password.equals(passwordcheck)){
                            passwordAuthentication = true;
                        }
                    }
                }
                i++;
            }
            inputstream.close();
        } catch (IOException e){
            System.out.println("Cannot check password");
        }
        
    }
    
    //checking the username and password to see whether to log in or not
    public void checkLogInStatus(){
        if (passwordAuthentication && usernameAuthentication){
            logInAuthentication = true;
        }
        else{
            System.out.println("Username or password error. Please log in again.");
            //loginmodule();
        }
    }
    
    //combination of all the method in this class in predetermined sequence
    public boolean loginmodule (){
        //databaseCreate();
        userLogIn();
        checkUsername();
        checkPassword();
        checkLogInStatus();
        return logInAuthentication;
    }
    
    //returns the username
    public String getUsername(){
        return username;
    }
    
    //erases all the credentials before logging out and returning to the main screen
    public boolean logOut(){
        System.out.println("Logged out successfully.");
        username = null;
        password = null;
        usernameAuthentication = false;
        passwordAuthentication = false;
        logInAuthentication = false;
        return false;
    }

}
