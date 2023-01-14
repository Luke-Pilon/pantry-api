package net.yorkdevsolutions.pantry.repositories;


import net.yorkdevsolutions.pantry.entities.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
