package net.yorkdevsolutions.pantry.services;

import net.yorkdevsolutions.pantry.dto.RecipeDTO;
import net.yorkdevsolutions.pantry.dto.RecipeIngredientDTO;
import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.entities.Recipe;
import net.yorkdevsolutions.pantry.entities.RecipeIngredient;
import net.yorkdevsolutions.pantry.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ItemService itemService;
    private final RestTemplate client;
    @Value("${net.yorksolutions.authUrl}")
    private String authUrl;

    public RecipeService(RecipeRepository recipeRepository, ItemService itemService) {
        this.recipeRepository = recipeRepository;
        this.itemService = itemService;
        this.client = new RestTemplate();
    }

    public Iterable<RecipeDTO> getRecipesByUser(UUID authToken){
        var accountId = this.getAccountIdFromAuthToken(authToken);
        var recipes = this.recipeRepository.findAllByAccountId(accountId);
        var DTOs = new ArrayList<RecipeDTO>();
        for(Recipe recipe : recipes){
            DTOs.add(new RecipeDTO(recipe));
        }
        return DTOs;
    }

    public Recipe createRecipe(UUID authToken, RecipeDTO recipeDTO) {
        UUID accountId = this.getAccountIdFromAuthToken(authToken);
        //ERROR is being thrown somewhere after this
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

    public Recipe putRecipe(Long recipeId, UUID authToken, RecipeDTO updatedRecipe){
        Recipe existingRecipe = this.recipeRepository.findById(recipeId).orElse(null);
        UUID accountId = getAccountIdFromAuthToken(authToken);
        if(existingRecipe == null){
             return this.createRecipe(accountId, updatedRecipe);
        }
        if(!existingRecipe.getId().equals(updatedRecipe.getId())){
            throw new IllegalArgumentException();
        }
        if(!existingRecipe.getAccountId().equals(accountId)){
            throw new IllegalArgumentException();
        }
        return this.recipeRepository.save(createRecipe(accountId,updatedRecipe));
    }

    public void deleteRecipeById(UUID authToken, Long recipeId){
        Recipe recipeToDelete = this.recipeRepository.findById(recipeId).orElseThrow();
        UUID accountId = getAccountIdFromAuthToken(authToken);
        try {
            if(!recipeToDelete.getAccountId().equals(accountId)){
                throw new IllegalArgumentException();
            }
            this.recipeRepository.delete(recipeToDelete);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public UUID getAccountIdFromAuthToken(UUID authToken){
        var url = String.format("%s/validateToken?authToken=%s",this.authUrl, authToken);
        try {
            ResponseEntity<UUID> response = this.client.getForEntity(url, UUID.class);
            return response.getBody();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
