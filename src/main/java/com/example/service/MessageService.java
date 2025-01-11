package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Creates a new message.
     * @param Message
     * @return Message
     * @throws IllegalArgumentException if messageText is blank, too long, or postedBy is null
     */
    public Message createMessage(Message message) {
        String messageText = message.getMessageText();

        if (messageText.isEmpty()) {
            System.out.println("Message is empty.");
            throw new IllegalArgumentException("Message cannot be blank.");
        }

        if (messageText.length() > 255) {
            System.out.println("Message is too long.");
            throw new IllegalArgumentException("Message cannot exceed 255 characters.");
        }
        
        int postedBy = message.getPostedBy();
        System.out.println("Account ID: " + postedBy);

        if (accountRepository.findById(postedBy) == null) {
            System.out.println("Not a valid user.");
            throw new IllegalArgumentException("User ID does not exist.");
        }

        return messageRepository.save(message);
    }

    /**
     * Retrieves all messages.
     * @return a list of all messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a message by its ID.
     * @param messageId
     * @return Message
     */
    public Message getMessageById(int messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    /**
     * Deletes a message by its ID.
     * @param messageId
     * @return 1 if deleted, 0 if not found
     */
    public int deleteMessage(int messageId) {
        Message message = messageRepository.findByMessageId(messageId);

        if (message == null) {
            System.out.println("No message to delete.");
            return 0;
        }

        messageRepository.delete(message);
        System.out.println("Message deleted.");
        return 1;
    }

    /**
     * Updates the text of a message.
     * @param messageId
     * @param messageText
     * @return 1 if updated, 0 if not found or invalid input
     * @throws IllegalArgumentException if the new messageText is blank or too long
     */
    public int updateMessageText(int messageId, String messageText) {
        if (messageText.isEmpty()) {
            System.out.println("Message is empty.");
            throw new IllegalArgumentException("Message cannot be blank.");
        }

        if (messageText.length() > 255) {
            System.out.println("Message is too long.");
            throw new IllegalArgumentException("Message cannot exceed 255 characters.");
        }

        Message message = messageRepository.findByMessageId(messageId);
        
        if (message == null) {
            System.out.println("No message to update.");
            return 0;
        }

        message.setMessageText(messageText);
        System.out.println("Message updated.");
        return 1;
    }

    /**
     * Retrieves all messages posted by a specific user.
     * @param postedBy
     * @return List<Message>
     */
    public List<Message> getMessagesByUserId(int postedBy) {
        return messageRepository.findByAccountId(postedBy);
    }
}