package net.yorkdevsolutions.pantry.repositories;

import net.yorkdevsolutions.pantry.entities.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountByNameAndPassword(String name, String password);
}
