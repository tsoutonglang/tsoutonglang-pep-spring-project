package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Find all messages posted by a specific user.
     * @param postedBy the ID of the user who posted the messages.
     * @return a list of messages posted by the user.
     */
    List<Message> findByPostedBy(Integer postedBy);
}