package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /*
     * Handler to post a new user.
     * The response status should be 200 OK, which is the default. The new account should be persisted to the database.
     * If the registration is not successful due to a duplicate username, the response status should be 409. (Conflict)
     * If the registration is not successful for some other reason, the response status should be 400. (Client error)
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.registerAccount(account);
            return ResponseEntity.ok(createdAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /* 
     * Handler to login a user.
     * The response status should be 200 OK, which is the default.
     * If the login is not successful, the response status should be 401. (Unauthorized)
     */ 

    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account) {
        try {
            String username = account.getUsername();
            String password = account.getPassword();
            
            Account loggedInAccount = accountService.login(username, password);
            return ResponseEntity.ok(loggedInAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /* 
     * Handler to create a new message.
     * The response status should be 200, which is the default. The new message should be persisted to the database.
     * If the creation of the message is not successful, the response status should be 400. (Client error)
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /* 
     * Handler to retrieve all messages.
     * The response status should always be 200, which is the default.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    /*
     * Handler to retrieve a message by its ID.
     * The response status should always be 200, which is the default.
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    /* 
     * Handler to delete a message.
     * The response status should be 200, which is the default.
     * If the message did not exist, the response status should be 200, but the response body should be empty.
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        int rowsAffected = messageService.deleteMessage(messageId);

        if (rowsAffected == 0) {
            return ResponseEntity.ok(0);
        }
        return ResponseEntity.ok(1);
    }

    /* 
     * Handler to update a message.
     * If the update is successful, the response body should contain the number of rows updated (1), and the response status should be 200, which is the default.
     * If the update of the message is not successful for any reason, the response status should be 400. (Client error)
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageText(@PathVariable Integer messageId, @RequestBody Message message) {
        try {
            int rowsAffected = messageService.updateMessageText(messageId, message.getMessageText());
            return ResponseEntity.ok(rowsAffected);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /* 
     * Handler to retrieve all messages by a user.
     * The response status should always be 200, which is the default.
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByUserId(accountId);
        return ResponseEntity.ok(messages);
    }
}