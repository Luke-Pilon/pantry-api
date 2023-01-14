package net.yorkdevsolutions.pantry.services;

import net.yorkdevsolutions.pantry.dto.RecipeDTO;
import net.yorkdevsolutions.pantry.entities.Account;
import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.entities.Recipe;
import net.yorkdevsolutions.pantry.entities.RecipeIngredient;
import net.yorkdevsolutions.pantry.repositories.AccountRepository;
import net.yorkdevsolutions.pantry.repositories.RecipeIngredientRepository;
import net.yorkdevsolutions.pantry.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final AccountRepository accountRepository;
    private final RecipeIngredientRepository ingredientRepository;
    private final ItemService itemService;

    public RecipeService(RecipeRepository recipeRepository, AccountRepository accountRepository, RecipeIngredientRepository ingredientRepository, ItemService itemService) {
        this.recipeRepository = recipeRepository;
        this.accountRepository = accountRepository;
        this.ingredientRepository = ingredientRepository;
        this.itemService = itemService;
    }

    public Recipe createRecipe(Long accountId, RecipeDTO recipeDTO) {
        Account account = this.accountRepository.findById(accountId).orElseThrow();
        Recipe newRecipe = newRecipeFromDTO(recipeDTO);
        recipeRepository.save(newRecipe);
        account.addRecipe(newRecipe);
        accountRepository.save(account);
        return newRecipe;
    }

    public Recipe newRecipeFromDTO(RecipeDTO recipeDTO){
        Recipe newRecipe = new Recipe(recipeDTO);
        for(Map.Entry<Item,Long> entry : recipeDTO.getIngredients().entrySet()){
            RecipeIngredient ingredient;
            Item item = this.itemService.getItemById(entry.getKey().getId());
            if(item.equals(null)){
                Item newItem = this.itemService.createItemFromNewRecipe(entry.getKey().getName());
                ingredient = new RecipeIngredient(newItem, entry.getValue());
            } else {
                ingredient = new RecipeIngredient(item, entry.getValue());
            }
            ingredientRepository.save(ingredient);
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
        this.recipeRepository.delete(recipeToDelete);
    }
}
