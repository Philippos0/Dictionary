package Bank;
import java.io.Serializable;


public class Bank implements Serializable {
    private String firstname,surname,id,contact;
    private int balance,frozen;
    
    //Client's menu
    public void Menu(){
        System.out.println("Bank account options");
        System.out.println("--------------------");
        System.out.println("1.Create bank account");
        System.out.println("2.Deposit to account");
        System.out.println("3.Withdraw from account");
        System.out.println("4.Transfer money to account");
        System.out.println("5.Activate/Deactivate account");
        System.out.println("6.View an account");
        System.out.println("7.View ALL accounts");
        System.out.println("8.Delete an account");
        System.out.println("9.Exit");
        System.out.print("Choice: ");
    }
    
    public Bank(){
    }
    
    public Bank(String firstname,  String surname,
                String  id, String contact, int balance, int  frozen){
        this.firstname=firstname;
        this.surname=surname;
        this.id=id;
        this.contact=contact;
        this.balance=balance;
        this.frozen=frozen;
       
    }
    //Get-Set section
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getFrozen() {
        return frozen;
    }

    public void setFrozen(int frozen) {
        this.frozen = frozen;
    } 
}
