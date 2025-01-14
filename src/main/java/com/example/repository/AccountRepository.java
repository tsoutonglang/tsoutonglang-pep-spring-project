package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* 
 * accountId integer primary key auto_increment,
 * username varchar(255) not null unique,
 * password varchar(255)
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Find an account by its username.
     * @param username
     * @return Account
     */
    Account findByUsername(String username);

    /**
     * Find an account by its id.
     * @param accountId
     * @return Account
     */
    Account findByAccountId(int accountId);
}