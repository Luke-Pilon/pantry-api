package net.yorkdevsolutions.pantry.repositories;


import net.yorkdevsolutions.pantry.entities.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Iterable<Recipe> findAllByAccountId(UUID accountId);
}
