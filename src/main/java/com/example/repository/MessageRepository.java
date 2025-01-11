package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * messageId integer primary key auto_increment,
 * postedBy integer,
 * messageText varchar(255),
 * timePostedEpoch long,
 * foreign key (postedBy) references Account(accountId)
 */

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Find a message by its id.
     * @param message_id
     * @return Optional<Message>
     */
    @Query("FROM Message WHERE messageId = :messageID")
    Message findByMessageId(@Param("messageID") int message_id);

    /**
     * Find all messages posted by a specific user.
     * @param postedBy
     * @return List<Message>
     */
    @Query("FROM Message WHERE postedBy = :accountID")
    List<Message> findByAccountId(@Param("accountID") int postedBy);
}