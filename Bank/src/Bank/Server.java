package Bank;
//importing files
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Server {
    
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        Server server = new Server();
        server.startServer();
    }
    //Establish a connection to the database named bank with username:Athanasopoulos and no password
    public Connection getConnection(){
        Connection con=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");//driver name
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "Athanasopoulos", "");//connection url
            
        }catch(ClassNotFoundException | SQLException ex){
           System.out.println("Class for name Error/SQL Error"+ex.toString());
        }
    return con;
}
    
    private void startServer()throws IOException, ClassNotFoundException, SQLException{
        ServerSocket server=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Bank bank=null;
        Bank bank2=null;
        int port = 1234;
        int choice=-1; 
        
        //creating the server socket 
        server= new ServerSocket(port);
        System.out.println("Server started and is listening on port "+port);
        Socket socket = server.accept();//initiate session with the client  
        System.out.println("Client connected");//print session info (locally)
        
        // Creating new I/O streams
        Scanner ss=new Scanner(System.in);
        Scanner si=new Scanner(socket.getInputStream());//used to get choice from client - server is always aware of client's choice
        PrintStream printstream= new PrintStream(socket.getOutputStream());//Used for sending messages to client's terminal
        
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());//used to send objects back to client
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());//used to read objects from client's input
        
        Connection con=getConnection();//Connection start
        while(choice!=9)
        {
            System.out.println("Waiting for client input...");
            choice=si.nextInt();
            System.out.println("Client's choice was: "+ choice);
            
            switch(choice){
                    case 1://Create bank account
                        bank = (Bank)ois.readObject();  //read incoming info and convert to bank object
                        //print received details
                        System.out.println("Bank account details received:");
                        System.out.println("ID:" + bank.getId());
                        System.out.println("Firstname:" + bank.getFirstname());
                        System.out.println("Surname:" + bank.getSurname());
                        System.out.println("Contact:" + bank.getContact());

                        try {
                            ps = con.prepareStatement("INSERT INTO accounts(id,firstname,surname,contact) VALUES (?, ?, ?, ?)");
                            ps.setString(1,bank.getId());  
                            ps.setString(2,bank.getFirstname());  
                            ps.setString(3,bank.getSurname());  
                            ps.setString(4,bank.getContact());
                            if (ps.executeUpdate()!=0)
                            {
                                printstream.println("Account Created Successfully");
                            }
                            else
                            {
                                printstream.println("Account was not created Successfully");
                            }
                            
                        } catch (SQLException ex) 
                        {
                            System.out.println(ex);//print error locally
                            printstream.println("Account was not created Successfully");
                        }
                        break;
                    case 2://Deposit to account based on id
                        bank = (Bank) ois.readObject();//read incoming info and convert to bank object
                        try
                        {
                            ps = con.prepareStatement("SELECT frozen FROM accounts where id=?");
                            ps.setString(1,bank.getId());
                            rs=ps.executeQuery();
                            if (rs.next())
                            {
                                if (rs.getInt("frozen")==0)
                                {
                                    printstream.println("Transaction  Error  – Account is not active");
                                    break;
                                }
                            }
                            else
                            {
                                printstream.println("Transaction  Error  –  Account  not  found");
                                break;
                            }
                            
                            
                            ps = con.prepareStatement("UPDATE accounts SET balance=balance+? WHERE id=?");
                            ps.setInt(1,bank.getBalance());
                            ps.setString(2,bank.getId());
                            if (ps.executeUpdate() != 0)
                            {
                                printstream.println("Successful Deposit");
                            }
                            else
                            {
                                printstream.println("Deposit Error");
                            }
                        }catch(SQLException ex)
                        {
                            System.out.println(ex);//print error locally
                            printstream.println("Deposit Error ");
                        }
                        break;
                    case 3://Withdraw from account based on id
                        bank = (Bank) ois.readObject();//read incoming info and convert to bank object
                        try
                        {
                            ps = con.prepareStatement("SELECT frozen,balance FROM accounts where id=?");
                            ps.setString(1,bank.getId());
                            rs=ps.executeQuery();
                            if (rs.next())
                            {
                                if (rs.getInt("frozen")==0)
                                {
                                    printstream.println("Transaction  Error  – Account is not active");
                                    break;
                                }
                                if (rs.getInt("balance")<bank.getBalance())//questioning if balance is enough for the action
                                {
                                    printstream.println("Insufficient Balance");
                                    break;
                                }
                            }
                            else
                            {
                                printstream.println("Transaction  Error  –  Account  not  found");
                                break;
                            }
                        }catch(SQLException ex)
                        {
                            System.out.println(ex);//print error locally
                            printstream.println("Transaction  Error  –  Account  not  found ");
                        }
                            
                           
                        
                        
                        try
                        {
                            ps = con.prepareStatement("UPDATE accounts SET balance=balance-? WHERE id=?");
                            ps.setInt(1,bank.getBalance());
                            ps.setString(2,bank.getId());
                            if (ps.executeUpdate() != 0)
                            {
                                printstream.println("Successful  Withdrawal");
                            }
                            else
                            {
                                printstream.println("Withdrawal Error");
                            }
                        }catch(SQLException ex)
                        {
                            System.out.println(ex);//print error locally
                            printstream.println("Withdrawal Error ");
                        }
                        
                        break;
                    case 4://Transfer money between 2 accounts
                        bank = (Bank) ois.readObject();//read incoming info and convert to bank object
                        bank2 = (Bank) ois.readObject();//read incoming info and convert to bank object
                        
                        try
                        {
                            ps = con.prepareStatement("SELECT frozen,balance FROM accounts where id=?");
                            ps.setString(1,bank.getId());
                            rs=ps.executeQuery();
                            if (rs.next())
                            {
                                if (rs.getInt("frozen")==0)//if the account is frozen
                                {
                                    printstream.println("Transaction  Error  – Account is not active");
                                    break;
                                }
                                if (rs.getInt("balance")<bank.getBalance())//if the ammount is not enough
                                {
                                    printstream.println("Insufficient Balance");
                                    break;
                                }
                            }
                            else
                            {
                                printstream.println("Transaction  Error  –  (from) account  not  found");
                                break;
                            }
                        }catch(SQLException ex)
                        {
                            System.out.println(ex);//print error locally
                            printstream.println("Transaction  Error  –  Account  not  found");
                        }
                          
                        
                        
                        //check status of bank2 ID
                        try
                        {
                            ps = con.prepareStatement("SELECT frozen,balance FROM accounts where id=?");
                            ps.setString(1,bank2.getId());
                            rs=ps.executeQuery();
                            if (!rs.next())
                            {
                                printstream.println("Transaction  Error  –  (to) Account  not  found");
                                break;
                            }
                        }catch(SQLException ex)
                        {
                            System.out.println(ex);//print error locally
                            printstream.println("Transaction  Error ");
                        }
                        
                        
                        try
                        {
                            ps = con.prepareStatement("UPDATE accounts SET balance=balance-? WHERE id=?");
                            ps.setInt(1,bank.getBalance());
                            ps.setString(2,bank.getId());
                            if (ps.executeUpdate() != 0)
                            {
                                
                                ps = con.prepareStatement("UPDATE accounts SET balance=balance+? WHERE id=?");
                                ps.setInt(1,bank.getBalance());
                                ps.setString(2,bank2.getId());
                                if (ps.executeUpdate() != 0)
                                {
                                    printstream.println("Successful  Transfer");
                                }
                                else
                                {
                                    printstream.println(bank.getBalance()+" "+bank2.getId());
                                }
                            }
                            else
                            {
                                printstream.println("Transfer Error2");
                            }
                        }catch(SQLException ex)
                        {
                            System.out.println(ex);//print error locally
                            printstream.println("Transfer Error"+ex.toString());
                        }
                        break;
                    case 5://Activate/Deactivate account
                        bank = (Bank) ois.readObject();//read incoming info and convert to bank object
                        try
                        {
                            ps = con.prepareStatement("UPDATE accounts SET frozen=? WHERE id=?");
                            ps.setInt(1,bank.getFrozen());
                            ps.setString(2,bank.getId());
                            if (ps.executeUpdate() != 0)
                            {
                                if (bank.getFrozen()!=0)
                                    printstream.println("Account Activated");
                                else
                                    printstream.println("Account Deactivated");
                            }
                            else
                            {
                                printstream.println("User not found");
                            }
                        }catch(SQLException ex)
                        {
                            System.out.println(ex);//print error locally
                            printstream.println("User not found");
                        }
                        break;
                    case 6://Show bank account based on id
                        bank = (Bank) ois.readObject();//read incoming info and convert to bank object
                        try
                        {
                            ps = con.prepareStatement("SELECT * FROM accounts where id=?");
                            ps.setString(1,bank.getId());
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                                printstream.println("ID: "+rs.getString("id")+ " | Firstname: " +rs.getString("firstname")+ " | Surname : " +rs.getString("surname")
                                        + " | Contact: " +rs.getString("contact")+ " | Balance: " +rs.getInt("balance")+ " | Frozen: " +rs.getInt("frozen"));
                            }
                            printstream.println("");
                        }catch(SQLException ex)
                        {
                            System.out.println(ex);//print error locally
                            printstream.println("An error occured ");
                        }
                        break;
                    case 7://Show all bank accounts
                        try
                        {
                            ps = con.prepareStatement("SELECT * FROM accounts");
                            rs=ps.executeQuery();  
                            while(rs.next())//until there are still lines in database
                            {
                                printstream.println("ID: "+rs.getString("id")+ " | Firstname: " +rs.getString("firstname")+ " | Surname : " +rs.getString("surname")
                                        + " | Contact: " +rs.getString("contact")+ " | Balance: " +rs.getInt("balance")+ " | Frozen: " +rs.getInt("frozen"));
                            } 
                            printstream.println("");//printing a blank to show its done fetching lines from database
                            
                        }catch(SQLException ex)
                        {
                            System.out.println(ex);//print error locally
                            printstream.println("An error occured ");
                        }
                        break;
                    case 8://Delete an account based on id
                        bank = (Bank)ois.readObject();//read incoming info and convert to bank object
                        try
                        {
                            ps = con.prepareStatement("DELETE FROM accounts WHERE id=?");
                            ps.setString(1,bank.getId());
                            if (ps.executeUpdate() !=0 )
                            {
                                printstream.println("Bank account with ID: " + bank.getId() + " successfully deleted");
                            }
                            else
                            {
                                printstream.println("User not found");
                            }
                        }catch(SQLException ex)
                        {   
                            System.out.println(ex);//print error locally
                            printstream.println("User not found");
                        }
                        
                        break;
                    case 9://Exit-Close all the i.o streams, socket and connection
                        try{
                            oos.close();
                            socket.close();
                            ois.close();
                            ss.close();
                            con.close();
                        }catch(SQLException ex)
                        {
                            
                            System.out.println(ex);//print error locally
                        }
                        System.out.println("Client disconnected");
                        break;
            }
        }
    }
}
