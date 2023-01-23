package net.yorkdevsolutions.pantry.controllers;

import net.yorkdevsolutions.pantry.dto.AccountDTO;
import net.yorkdevsolutions.pantry.entities.Account;
import net.yorkdevsolutions.pantry.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public AccountDTO login(@RequestParam String name, @RequestParam String password){
        try {
            Account account = this.accountService.login(name,password);
            return new AccountDTO(account);
        } catch(NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable Long id){
        try {
            Account account = this.accountService.findAccountById(id);
            return new AccountDTO(account);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public AccountDTO registerAccount(@RequestBody Account accountRequest){
        try {
            Account newAccount = this.accountService.registerAccount(accountRequest);
            return new AccountDTO(newAccount);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @PutMapping("/{accountId}")
    public AccountDTO updateAccount(@RequestBody Account updatedAccount, @PathVariable Long accountId){
        try {
            Account account = this.accountService.updateAccount(updatedAccount, accountId);
            return new AccountDTO(account);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId){
        try {
            this.accountService.deleteAccountById(accountId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }
}
