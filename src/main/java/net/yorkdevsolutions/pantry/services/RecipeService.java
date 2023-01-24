package net.yorkdevsolutions.pantry.services;

import net.yorkdevsolutions.pantry.dto.RecipeDTO;
import net.yorkdevsolutions.pantry.dto.RecipeIngredientDTO;
import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.entities.Recipe;
import net.yorkdevsolutions.pantry.entities.RecipeIngredient;
import net.yorkdevsolutions.pantry.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ItemService itemService;

    public RecipeService(RecipeRepository recipeRepository, ItemService itemService) {
        this.recipeRepository = recipeRepository;
        this.itemService = itemService;
    }

    public Recipe createRecipe(UUID accountId, RecipeDTO recipeDTO) {
          Recipe recipe = newRecipeFromDTO(recipeDTO, accountId);
          return recipeRepository.save(recipe);
    }

    public Recipe newRecipeFromDTO(RecipeDTO recipeDTO, UUID accountId){
        Recipe newRecipe = new Recipe(recipeDTO, accountId);
        recipeRepository.save(newRecipe);
        for(RecipeIngredientDTO ingredientDTO : recipeDTO.getIngredients()){
            if(ingredientDTO.getQuantity() < 1){
                throw new IllegalArgumentException();
            }
            Item item = null;
            if(ingredientDTO.getItemId() != null){
                item = this.itemService.getItemById(ingredientDTO.getItemId());
            }
            RecipeIngredient ingredient;
            if(item == null){
                Item newItem = this.itemService.createItemFromNewRecipe(ingredientDTO.getItemName());
                ingredient = new RecipeIngredient(newItem, newRecipe, ingredientDTO.getQuantity());
            } else {
                ingredient = new RecipeIngredient(item, newRecipe, ingredientDTO.getQuantity());
            }
            newRecipe.addIngredient(ingredient);
        }
        return newRecipe;
    }

    public Recipe findRecipeById(Long recipeId) {
        return this.recipeRepository.findById(recipeId).orElseThrow();
    }

    public Recipe putRecipe(Long recipeId, UUID accountId, RecipeDTO updatedRecipe){
        Recipe existingRecipe = this.recipeRepository.findById(recipeId).orElse(null);
        if(existingRecipe == null){
             return this.createRecipe(accountId, updatedRecipe);
        }
        if(!existingRecipe.getId().equals(updatedRecipe.getId())){
            throw new IllegalArgumentException();
        }
        return this.recipeRepository.save(createRecipe(accountId,updatedRecipe));
    }

    public void deleteRecipeById(UUID accountId, Long recipeId){
        Recipe recipeToDelete = this.recipeRepository.findById(recipeId).orElseThrow();
        try {
            if(!recipeToDelete.getAccountId().equals(accountId)){
                throw new IllegalArgumentException();
            }
            this.recipeRepository.delete(recipeToDelete);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
