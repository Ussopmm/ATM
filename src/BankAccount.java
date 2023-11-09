import java.util.ArrayList;
import java.util.List;
public class BankAccount {
    private String name;
    private String identifier;
    private User holder;
    private List<Transaction> transactionList;
    public BankAccount (String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;

        // get new unique ID
        identifier = theBank.getNewAccountID();

        // init transaction list
        transactionList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    public String getSummaryLine() {
        double balance = this.getBalance();

        if (balance >= 0)
            return String.format("%s : $%.02f : %s", this.identifier, balance, this.name);
        else {
            return String.format("%s : $(%.02f) : %s", this.identifier, balance, this.name);
        }
    }

    public double getBalance() {
        double balance=0;
        for (Transaction t : this.transactionList) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransHistory() {
        System.out.printf("Transaction history of the account %d", this.identifier);
        for (int i = transactionList.size()-1; i >=0 ; i--) {
            System.out.println(this.transactionList.get(i).getSummaryLine());
        }
    }

    public void addTransaction(double amount, String memo) {
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactionList.add(newTrans);
    }


}