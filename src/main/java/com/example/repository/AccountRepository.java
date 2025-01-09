package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query("FROM Account WHERE username = :nameVar")
    Account findByUsername(@Param("nameVar") String username);

    /**
     * Find an account by its id.
     * @param id
     * @return Account
     */
    @Query("FROM Account WHERE accountId = :idNum")
    Account findById(@Param("idNum") int accountId);
}