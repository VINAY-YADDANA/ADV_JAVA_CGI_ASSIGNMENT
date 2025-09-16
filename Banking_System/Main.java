package bankapp;

import bankapp.model.Account;
import bankapp.service.AccountManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountManager accountManager = new AccountManager();
        Scanner sc = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("!!BANK ACCOUNT MGMT!!");
            System.out.println("1. Add New Account");
            System.out.println("2. Deposit Amount");
            System.out.println("3. Withdraw Amount");
            System.out.println("4. Display Customer Account Details");
            System.out.println("5. Calculate Rate of Interest");
            System.out.println("6. Get Account Count in the bank");
            System.out.println("7. Get Balance amount");
            System.out.println("8. List All Accounts in the bank");
            System.out.println("0. Exit");
            System.out.print("Choose an Option: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception ignored) {}

            switch (choice) {
                case 1 -> {
                    try {
                        System.out.print("Account Number of customer: ");
                        long accNo = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Account Holder Name : ");
                        String name = sc.nextLine();
                        System.out.print("Opening Balance : ");
                        double bal = Double.parseDouble(sc.nextLine().trim());
                        Account acc = accountManager.addAccount(accNo, name, bal);
                        System.out.println("Created Account : " + acc);
                    } catch (Exception e) {
                        System.out.println("Error in creating account: " + e.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        System.out.print("Account Number of customer: ");
                        long accNo = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Amount to deposit amount in: ");
                        double amt = Double.parseDouble(sc.nextLine().trim());
                        boolean ok = accountManager.deposit(accNo, amt);
                        System.out.println(ok ? "Deposited successfully." : "Account not exist.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        System.out.print("Account Number of customer:  ");
                        long accNo = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Amount to withdraw: ");
                        double amt = Double.parseDouble(sc.nextLine().trim());
                        boolean ok = accountManager.withdraw(accNo, amt);
                        System.out.println(ok ? "Withdrawal successful." : "Failed cuz of no account or invalid details.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        System.out.print("Account Number of customer:  ");
                        long accNo = Long.parseLong(sc.nextLine().trim());
                        System.out.println(accountManager.displayAccountDetails(accNo));
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 5 -> {
                    try {
                        System.out.print("Account Number of customer:  ");
                        long accNo = Long.parseLong(sc.nextLine().trim());
                        Double interest = accountManager.calculateInterest(accNo);
                        if (interest == null) {
                            System.out.println("Account Number of customer doesnot exist: ");
                        } else {
                            System.out.println("Yearly interest (not credited): " + interest);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 6 -> System.out.println("Total accounts: " + Account.getAccountCount());
                case 7 -> {
                    try {
                        System.out.print("Account Number of customer:  ");
                        long accNo = Long.parseLong(sc.nextLine().trim());
                        var acc = accountManager.find(accNo);
                        if (acc.isPresent()) {
                            System.out.println("Balance: " + acc.get().getBalance());
                        } else {
                            System.out.println("Account Number of customer doesnot exist: ");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 8 -> {
                    for (var a : accountManager.getAllAccounts()) {
                        System.out.println(a);
                    }
                }
                case 0 -> {
                    running = false;
                    System.out.println("Thank you for banking with us!");
                }
                default -> System.out.println("Please enter valid choice.Thanks!");
            }
        }
        sc.close();
    }
}
