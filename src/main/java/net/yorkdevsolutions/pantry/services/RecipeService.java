package net.yorkdevsolutions.pantry.services;

import net.yorkdevsolutions.pantry.dto.RecipeDTO;
import net.yorkdevsolutions.pantry.dto.RecipeIngredientDTO;
import net.yorkdevsolutions.pantry.entities.Account;
import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.entities.Recipe;
import net.yorkdevsolutions.pantry.entities.RecipeIngredient;
import net.yorkdevsolutions.pantry.repositories.AccountRepository;
import net.yorkdevsolutions.pantry.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final AccountRepository accountRepository;
    private final ItemService itemService;

    public RecipeService(RecipeRepository recipeRepository, AccountRepository accountRepository, ItemService itemService) {
        this.recipeRepository = recipeRepository;
        this.accountRepository = accountRepository;
        this.itemService = itemService;
    }

    public Recipe createRecipe(Long accountId, RecipeDTO recipeDTO) {
        Account account = this.accountRepository.findById(accountId).orElseThrow();
        Recipe newRecipe = newRecipeFromDTO(recipeDTO);
        account.addRecipe(newRecipe);
        accountRepository.save(account);
        return newRecipe;
    }

    public Recipe newRecipeFromDTO(RecipeDTO recipeDTO){
        Recipe newRecipe = new Recipe(recipeDTO);
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

    public Recipe putRecipe(Long recipeId, Long accountId, RecipeDTO updatedRecipe){
        Recipe existingRecipe = this.recipeRepository.findById(recipeId).orElse(null);
        if(existingRecipe == null){
             return this.createRecipe(accountId, updatedRecipe);
        }
        if(!existingRecipe.getId().equals(updatedRecipe.getId())){
            throw new IllegalArgumentException();
        }
        return this.recipeRepository.save(createRecipe(accountId,updatedRecipe));
    }

    public void deleteRecipeById(Long accountId, Long recipeId){
        Recipe recipeToDelete = this.recipeRepository.findById(recipeId).orElseThrow();
        Account account = this.accountRepository.findById(accountId).orElse(null);
        if(account == null | !account.getRecipes().contains(recipeToDelete)){
            throw new IllegalArgumentException();
        }
        account.getRecipes().remove(recipeToDelete);
        this.recipeRepository.delete(recipeToDelete);
    }
}
