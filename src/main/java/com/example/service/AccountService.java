package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Username can't be blank.
     * Password must be at least 4 chars.
     * Username must not already exist.
     * 
     * @param Account
     * @return Account
     * @throws IllegalArgumentException if the username is blank or the password is less than 4 characters
     * @throws IllegalStateException if the username already exists
     */
    public Account registerAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        System.out.println(username);
        System.out.println(password);

        if (username.isBlank()) {
            System.out.println("Username is blank.");
            throw new IllegalArgumentException("Username cannot be blank.");
        }

        if (password.length() < 4) {
            System.out.println("Password is too short.");
            throw new IllegalArgumentException("Password is too short.");
        }

        if (accountRepository.findByUsername(username) != null) {
            System.out.println("Username already exists.");
            throw new IllegalStateException("Username is already taken.");
        }

        System.out.println("Account created.");
        return accountRepository.save(account);
    }

    /**
     * Username and password provided must exist in the database.
     *
     * @param username
     * @param password
     * @return Account
     * @throws IllegalArgumentException if the credentials are invalid
     */
    public Account login(String username, String password) {
        System.out.println(username);
        System.out.println(password);

        Account foundAccount = accountRepository.findByUsername(username);

        if (foundAccount == null) {
            System.out.println("Account was not found.");
            throw new IllegalArgumentException("Invalid username.");
        }

        if (!password.equals(foundAccount.getPassword())) {
            System.out.println("Password does not match.");
            throw new IllegalArgumentException("Invalid password.");
        }

        return foundAccount;
    }
}