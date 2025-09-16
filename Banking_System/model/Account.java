package bankapp.model;

import bankapp.util.Transaction;

public class Account extends BankAccount implements Transaction {

    private double balance;

    public static final String BANK_NAME = "VINAY BANK";

    private static int accountCount = 0;

    public Account(long accountNumber, String accountHolderName, double openingBalance) {
        super(accountNumber, accountHolderName);
        if (openingBalance < 0) throw new IllegalArgumentException("Opening balance cannot be less than zero :(");
        this.balance = openingBalance;
        accountCount++;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public final String getBankName() {
        return BANK_NAME;
    }

    public static int getAccountCount() {
        return accountCount;
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be greater than zero :)");
        this.balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) return false;
        if (amount > this.balance) return false;
        this.balance -= amount;
        return true;
    }

    @Override
    public double calculateInterest() {
        final double RATE = 0.04;
        return this.balance * RATE;
    }

    @Override
    public String toString() {
        return "Account{" +
                "bank='" + BANK_NAME + '\'' +
                ", number=" + getAccountNumber() +
                ", holder='" + getAccountHolderName() + '\'' +
                ", balance=" + balance +
                '}';
    }
}
