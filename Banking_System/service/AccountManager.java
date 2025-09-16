package bankapp.service;

import bankapp.model.Account;
import java.util.*;

public class AccountManager {
    private final Map<Long, Account> accounts = new LinkedHashMap<>();

    public Account addAccount(long accNo, String holder, double openingBalance) {
        if (accounts.containsKey(accNo)) {
            throw new IllegalArgumentException("Account number already exists: " + accNo);
        }
        Account acc = new Account(accNo, holder, openingBalance);
        accounts.put(accNo, acc);
        return acc;
    }

    public Optional<Account> find(long accNo) {
        return Optional.ofNullable(accounts.get(accNo));
    }

    public boolean deposit(long accNo, double amt) {
        Optional<Account> acc = find(accNo);
        if (acc.isEmpty()) return false;
        acc.get().deposit(amt);
        return true;
    }

    public boolean withdraw(long accNo, double amt) {
        Optional<Account> acc = find(accNo);
        return acc.isPresent() && acc.get().withdraw(amt);
    }

    public String displayAccountDetails(long accNo) {
        Optional<Account> acc = find(accNo);
        return acc.map(Account::toString).orElse("Account not found: " + accNo);
    }

    public Double calculateInterest(long accNo) {
        Optional<Account> acc = find(accNo);
        return acc.map(Account::calculateInterest).orElse(null);
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
}
