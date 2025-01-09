package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Creates a new message.
     *
     * @param message the message to be created
     * @return the created message
     * @throws IllegalArgumentException if messageText is blank, too long, or postedBy is null
     */
    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be blank.");
        }
        if (message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text cannot exceed 255 characters.");
        }
        if (message.getPostedBy() == null) {
            throw new IllegalArgumentException("PostedBy cannot be null.");
        }

        // Set the time the message is posted
        message.setTimePostedEpoch(Instant.now().getEpochSecond());

        return messageRepository.save(message);
    }

    /**
     * Retrieves all messages.
     *
     * @return a list of all messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId the ID of the message
     * @return an Optional containing the message if found, or empty otherwise
     */
    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    /**
     * Deletes a message by its ID.
     *
     * @param messageId the ID of the message to be deleted
     * @return the number of rows affected (1 if deleted, 0 if not found)
     */
    public int deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    /**
     * Updates the text of a message by its ID.
     *
     * @param messageId    the ID of the message to be updated
     * @param messageText  the new message text
     * @return the number of rows affected (1 if updated, 0 if not found or invalid input)
     * @throws IllegalArgumentException if the new messageText is blank or too long
     */
    public int updateMessageText(Integer messageId, String messageText) {
        if (messageText == null || messageText.trim().isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be blank.");
        }
        if (messageText.length() > 255) {
            throw new IllegalArgumentException("Message text cannot exceed 255 characters.");
        }

        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    /**
     * Retrieves all messages posted by a specific user.
     *
     * @param userId the ID of the user
     * @return a list of messages posted by the user
     */
    public List<Message> getMessagesByUserId(Integer userId) {
        return messageRepository.findByPostedBy(userId);
    }
}