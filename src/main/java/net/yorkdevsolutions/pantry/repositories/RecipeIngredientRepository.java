package net.yorkdevsolutions.pantry.repositories;

import net.yorkdevsolutions.pantry.entities.RecipeIngredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient,Long> {
}
