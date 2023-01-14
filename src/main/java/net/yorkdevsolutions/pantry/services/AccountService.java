package net.yorkdevsolutions.pantry.services;

import net.yorkdevsolutions.pantry.entities.Account;
import net.yorkdevsolutions.pantry.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account login(String name, String password) {
        return this.repository.findAccountByNameAndPassword(name,password).orElseThrow();
    }

    public Account registerAccount(Account account){
        return this.repository.save(account);
    }

    public Account findAccountById(Long accountId){
        return this.repository.findById(accountId).orElseThrow();
    }

    public Account updateAccount(Account updatedAccount, Long accountId) {
        var accountToUpdate = this.repository.findById(accountId).orElseThrow();
        accountToUpdate.setPassword(updatedAccount.getPassword());
        accountToUpdate.setName((updatedAccount.getName()));
        return this.repository.save(accountToUpdate);
    }

    public void deleteAccountById(Long accountId) {
        var accountToDelete = this.repository.findById(accountId).orElseThrow();
        this.repository.delete(accountToDelete);
    }
}
