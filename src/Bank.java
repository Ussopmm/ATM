
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bank {
    private String name;
    private List<User> owners;
    private List<BankAccount> accounts;
    public Bank(String name) {
        this.name = name;
        this.owners = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }
    public void addAccount(BankAccount acc) {
        this.accounts.add(acc);
    }
    public String getNewUserID() {
        String ID;
        Random rnd = new Random();
        int len = 6;
        Boolean nonUnique;
        do {
            ID = "";
            for (int i = 0; i < len; i++) {
                ID += ((Integer)rnd.nextInt(10)).toString();

            }

            // check it to unique
            nonUnique = false;
            for (User u : this.owners) {
                if (ID.compareTo(u.getIdentifier()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return ID;
    }

    public String getNewAccountID() {
        String accID;
        Random rnd = new Random();
        int len = 10;
        Boolean nonUnique;
        do {
            accID = "";
            for (int i = 0; i < len; i++) {
                accID += ((Integer)rnd.nextInt(10)).toString();

            }

            // check it to unique
            nonUnique = false;
            for (BankAccount a : this.accounts) {
                if (accID.compareTo(a.getIdentifier()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return accID;
    }
    public User addUser(String firstname, String lastname, String pin) {
        // create a new user and add it to our list
        User newUser = new User(firstname, lastname, pin, this);
        this.owners.add(newUser);

        // create a savings acc for the user
        BankAccount newAccount = new BankAccount("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String ID, String pin) {
        for (User u : this.owners) {
            if (u.getIdentifier().compareTo(ID) == 0 && u.verifyPin(pin)) {
                return u;
            }
        }
        return null;
    }
    public String getName() {
        return this.name;
    }
}
