import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank("Halyk Bank");

        User newUser = bank.addUser("Eldos", "Mukanov", "1111");

        BankAccount newAccount = new BankAccount("Checking", newUser, bank);

        newUser.addAccount(newAccount);
        bank.addAccount(newAccount);

        User curUser;
        while (true) {

            curUser = ATM.mainMenuPrompt(bank, sc);

            ATM.printUserMenu(curUser, sc);
        }
    }

    private static void printUserMenu(User curUser, Scanner sc) {
        //print summary of current account
        curUser.printSummaryAccount();

        System.out.printf("Welcome %s. Choose something: ", curUser.getFirst_name());

        int choice;
        do{
        System.out.println("1.Show Transaction history");
        System.out.println("2.Withdraw money from bank account");
        System.out.println("3.Top up your bank account"); //deposit
        System.out.println("4.Transfer"); //перевод
        System.out.println("5.Quit");
            System.out.println();
            System.out.print("Enter choice: ");
        choice = sc.nextInt();
        if (choice < 1 && choice < 5) {
            System.out.println("Invalid choice. Please enter 1-5");
        }
        } while (choice <1 && choice > 5);
        switch (choice) {
            case 1:
                 ATM.showTrans(curUser, sc);
                break;
            case 2:
                ATM.withdraw(curUser, sc);
                break;
            case 3:
                ATM.deposit(curUser, sc);
                break;
            case 4:
                ATM.transfer(curUser, sc);
                break;
        }
        if (choice != 5) {
            ATM.printUserMenu(curUser, sc);
        }
    }

    private static void deposit(User curUser, Scanner sc) {
        int toAcct;
        double amount;
        double acctBal;
        String memo;
        do {
            System.out.printf("Enter the number of account %d\n"
                    + "from which you want to transfer money");
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= curUser.numAcct()) {
                System.out.printf("Invalid account. Please try again");
            }
        } while (toAcct < 0 || toAcct >= curUser.numAcct());
        acctBal = curUser.getAccountBalance(toAcct);
        do {
            System.out.printf("Print the amount to transfer (max sum: $%.02f) : $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.printf("Invalid amount. it must be more than 0");
            } else if (amount > acctBal) {
                System.out.printf("Amount must be less than balance in account. Balance: $%.02f", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        sc.nextLine();

        System.out.println("Enter the memo:");
        memo = sc.nextLine();

        curUser.addAcctTrans(toAcct, amount, memo);
    }

    private static void withdraw(User curUser, Scanner sc) {
        int fromacct;
        double amount;
        double acctBal;
        String memo;
        do {
            System.out.printf("Enter the number of account \n"
                    + "from which you want to transfer money");
            fromacct = sc.nextInt()-1;
            if (fromacct < 0 || fromacct >= curUser.numAcct()) {
                System.out.printf("Invalid account. Please try again");
            }
        } while (fromacct < 0 || fromacct >= curUser.numAcct());
        acctBal = curUser.getAccountBalance(fromacct);
        do {
            System.out.printf("Print the amount to transfer (max sum: $%.02f) : $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.printf("Invalid amount. it must be more than 0");
            } else if (amount > acctBal) {
                System.out.printf("Amount must be less than balance in account. Balance: $%.02f", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        sc.nextLine();

        System.out.println("Enter the memo:");
        memo = sc.nextLine();

        curUser.addAcctTrans(fromacct, -1*amount, memo);
    }

    private static void showTrans(User curUser, Scanner sc) {
        int acct;
        do {
            System.out.printf("Print the number (1-%d) of accounts\n"+
                    "whose transaction you want to see", curUser.numAcct());
            acct = sc.nextInt()-1;
            if (acct < 0 || acct >= curUser.numAcct()) {
                System.out.println("Invalid account. Write again");
            }
        } while (acct < 0 || acct >= curUser.numAcct());
        curUser.printAcctTransHistory(acct);
    }
    private static void transfer(User curUser, Scanner sc) {
        int fromacct;
        int toAcct;
        double amount;
        double acctBal;
        do {
            System.out.printf("Enter the number of account %d\n"
            + "from which you want to transfer money");
            fromacct = sc.nextInt()-1;
            if (fromacct < 0 || fromacct >= curUser.numAcct()) {
                System.out.printf("Invalid account. Please try again");
            }
            System.out.printf("Enter the account %d\n"
             + "that you want to top up");
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= curUser.numAcct()) {
                System.out.printf("Invalid account. Please try again");
            }
        } while (fromacct < 0 && toAcct < 0 || fromacct >= curUser.numAcct() && toAcct >= curUser.numAcct());
        acctBal = curUser.getAccountBalance(fromacct);
        do {
            System.out.printf("Print the amount to transfer (max sum: $%.02f) : $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.printf("Invalid amount. it must be more than 0");
            } else if (amount > acctBal) {
                System.out.printf("Amount must be less than balance in account. Balance: $%.02f", acctBal);
            }
        } while (amount < 0 || amount > acctBal);
        curUser.addAcctTrans(fromacct, -1*amount, String.format("Transfer to account %s", curUser.getAcctID(toAcct)));
        curUser.addAcctTrans(toAcct, amount, String.format("Transfer to account %s", curUser.getAcctID(fromacct)));
    }
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User aUser;
        // ID/Pin combination to login account
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.println("Write your ID:");
            userID = sc.nextLine();
            System.out.println("Write your pin code");
            pin = sc.nextLine();

            aUser = theBank.userLogin(userID,pin);
            if (aUser == null) {
                System.out.println("Your ID/pin combination was incorrect.\nTry again");
            }
            return aUser;
        } while (aUser == null);
    }
}
