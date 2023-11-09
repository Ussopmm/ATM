import java.sql.Time;
import java.util.Date;

public class Transaction {
    private double amount;
    private Date timestamp;
    // memo for transaction
    private String memo;
    // account where transaction was performed
    private BankAccount inAccount;
    public Transaction (double amount, String memo, BankAccount inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.memo = memo;
    }
    public Transaction(double amount, Date timestamp, BankAccount inAccount) {
        this.amount = amount;
        this.timestamp= timestamp;
        this.inAccount =inAccount;
        this.memo = "";
    }
    public Transaction(double amount, Date timestamp, BankAccount inAccount, String memo) {
        this.amount = amount;
        this.timestamp= timestamp;
        this.inAccount = inAccount;
        this.memo = memo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BankAccount getInAccount() {
        return inAccount;
    }

    public void setInAccount(BankAccount inAccount) {
        this.inAccount = inAccount;
    }

    public String getSummaryLine() {
        if (amount > 0) {
            return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), this.amount, this.memo);
        }
    }
}
