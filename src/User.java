import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String first_name;
    private String last_name;
    // Identifier FOR User
    private String identifier;
    // Для безопасности данных мы преобразуем значение кода в хеш код и сохраним его в массиве
    private byte pin_Hash[];
    private List<BankAccount> bankAccountList;

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public User(String first_name, String last_name, String pin, Bank bank) {
        this.first_name = first_name;
        this.last_name = last_name;

        // Hashing pin code
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pin_Hash =  md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        // getting new unique id for user
        this.identifier = bank.getNewUserID();

        this.bankAccountList = new ArrayList<>();

        // message
        System.out.printf("New user %s %s with ID %s created. \n", first_name, last_name, this.identifier);
    }

    public void addAccount(BankAccount acc) {
        bankAccountList.add(acc);
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public byte[] getPin_Hash() {
        return pin_Hash;
    }

    public void setPin_Hash(byte[] pin_Hash) {
        this.pin_Hash = pin_Hash;
    }

    public boolean verifyPin (String aPin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pin_Hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
       return false;
    }


    public void printSummaryAccount() {
        System.out.printf("%s's Accounts summary", this.first_name);

        for (int i=0;i<bankAccountList.size();i++) {
            System.out.printf("%d) %s\n", (i+1),
                    this.bankAccountList.get(i).getSummaryLine());
        }
        System.out.println();
    }
    public int numAcct() {
        return this.bankAccountList.size();
    }

    public void printAcctTransHistory(int acctIdx) {
        this.bankAccountList.get(acctIdx).printTransHistory();
    }

    public double getAccountBalance(int acctIdx) {
        return this.bankAccountList.get(acctIdx).getBalance();
    }

    public String getAcctID(int fromacct) {
        return this.bankAccountList.get(fromacct).getIdentifier();
    }

    public void addAcctTrans(int fromacct, double amount, String memo) {
        this.bankAccountList.get(fromacct).addTransaction(amount, memo);
    }


}
