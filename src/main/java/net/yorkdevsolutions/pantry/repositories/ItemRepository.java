package net.yorkdevsolutions.pantry.repositories;

import net.yorkdevsolutions.pantry.entities.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item,Long> {
    boolean existsByName(String name);
}
