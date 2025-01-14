package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
     * @param messageId
     * @return Message
     */
    Message findByMessageId(int messageId);

    /**
     * Find all messages posted by a specific user.
     * @param postedBy
     * @return List<Message>
     */
    List<Message> findAllByPostedBy(int postedBy);
}