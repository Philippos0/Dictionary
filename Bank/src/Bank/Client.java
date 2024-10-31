package Bank;
//import files
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client  {
    public static  void main(String args[]) throws IOException, ClassNotFoundException {
        //Arguments for Socket and initialization of variables
        String hostname = "localhost";
        int port=1234;
        int choice=-1;
        Socket socket=null;
        Bank bank=null;
        Bank bank2=null;
        Scanner userInput= new Scanner(System.in);
        
        System.out.println("Trying to connect to Server...");
        try {
            socket = new Socket(hostname, port);//creating new client socket
            //Creating new I/O Streams
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printwriter = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            
            System.out.println("Connection established");
            //Showing bank menu until 9th option is selected by user 
            while (choice!=9){//keep on repeating code until client choice is 9(Exit)
                bank = new Bank();
                bank2=new Bank();
                bank.Menu();//show menu to client
                choice=userInput.nextInt();//user gives a choice
                printwriter.println(choice);//that choice is send back  to the server
                
                switch(choice){
                    case 1://Create bank account
                        System.out.println("Enter the bank account details");//user is giving the account details here
                        userInput.nextLine();
                        System.out.print("ID: ");
                        bank.setId(userInput.nextLine());
                        System.out.print("Firstname: ");
                        bank.setFirstname(userInput.nextLine());
                        System.out.print("Surname: ");
                        bank.setSurname(userInput.nextLine());
                        System.out.print("Contact: ");
                        bank.setContact(userInput.nextLine());
                        oos.writeObject(bank);//client is wring the details to the server
                        
                        try
                        {
                            System.out.println(bufferedreader.readLine());
                        }catch(IOException ex)
                        {
                            System.out.println("An error occured "+ex.toString());
                        }
                        break;
                    case 2://Deposit to account based on id
                        userInput.nextLine();
                        System.out.print("ID: ");
                        bank.setId(userInput.nextLine());
                        System.out.print("Deposit : ");
                        bank.setBalance(userInput.nextInt());
                        oos.writeObject(bank);//writing  back to the server
                        try
                        {
                            System.out.println(bufferedreader.readLine());
                        }catch(IOException ex)
                        {
                            System.out.println("An error occured "+ex.toString());
                        }
                        break;
                    case 3://Withdraw from account based on id
                        userInput.nextLine();
                        System.out.print("ID: ");
                        bank.setId(userInput.nextLine());
                        System.out.print("Withdrawal : ");
                        bank.setBalance(userInput.nextInt());
                        oos.writeObject(bank);//writing  back to the server
                        try
                        {
                            System.out.println(bufferedreader.readLine());
                        }catch(IOException ex)
                        {
                            System.out.println("An error occured "+ex.toString());
                        }
                        break;
                    case 4://Transfer money between 2 accounts

                        userInput.nextLine();
                        System.out.print("ID from : ");
                        bank.setId(userInput.nextLine());
                        System.out.print("ID to : ");
                        bank2.setId(userInput.nextLine());
                        System.out.print("Withdrawal : ");
                        bank.setBalance(userInput.nextInt());
                        oos.writeObject(bank);//writing  back to the server
                        oos.writeObject(bank2);//writing  back to the server
                        try
                        {
                            System.out.println(bufferedreader.readLine());
                        }catch(IOException ex)
                        {
                            System.out.println("An error occured "+ex.toString());
                        }
                        break;
                    case 5://Activate/Deactivate account
                        userInput.nextLine();
                        System.out.print("ID: ");
                        bank.setId(userInput.nextLine());
                        System.out.print("Frozen? 0 for frozen and any other for unfrozen : ");
                        bank.setFrozen(userInput.nextInt());
                        oos.writeObject(bank);//writing  back to the server
                        try
                        {
                            System.out.println(bufferedreader.readLine());
                        }catch(IOException ex)
                        {
                            System.out.println("An error occured "+ex.toString());
                        }
                        break;
                    case 6://Show bank account based on id
                        userInput.nextLine();
                        System.out.print("ID:");
                        bank.setId(userInput.nextLine());
                        oos.writeObject(bank);//writing  back to the server
                        try
                        {
                            String line;
                            do
                            {
                                line=bufferedreader.readLine();
                                System.out.println(line);
                            }while(!"".equals(line));
                            
                        }catch(IOException ex)
                        {
                            System.out.println("An error occured "+ex.toString());
                        }
                        break;
                    case 7://Show all bank accounts
                        try
                        {
                            String line;
                            do
                            {
                                line=bufferedreader.readLine();//reading line from server and then printing it
                                System.out.println(line);
                            }while(!"".equals(line));//this will be true when rs.next() is done and all the lines from the databases are retrieved
                            
                        }catch(IOException ex)
                        {
                            System.out.println("An error occured "+ex.toString());
                        }
                        break;
                    case 8://Delete an account based on id
                        userInput.nextLine();
                        System.out.print("Account ID to delete:");
                        bank.setId(userInput.nextLine());
                        oos.writeObject(bank); //writing  back to the server
                        try
                        {
                            System.out.println(bufferedreader.readLine());
                        }catch(IOException ex)
                        {
                            System.out.println("An error occured "+ex.toString());
                        }
                        break;
                    case 9://Exit-Close all the i.o streams, socket and connection
                        try{
                            System.out.println("Connection closed");
                            oos.close();
                            bufferedreader.close();
                            userInput.close();
                            bufferedwriter.close();
                            socket.close();
                        }catch(IOException ex)
                        {
                            System.out.println("An error occured "+ex.toString());
                        }
                }
            }
        }catch (IOException ex)
        {        
         System.out.println("An error occured "+ex.toString());//final catch 
        }
        System.exit(0);//exiting the program
    }
}

