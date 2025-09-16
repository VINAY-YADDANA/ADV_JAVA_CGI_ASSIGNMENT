package bankapp.model;

public abstract class BankAccount {
    private long accountNumber;
    private String accountHolderName;

    protected BankAccount(long accountNumber, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    protected void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public abstract double calculateInterest();
}
