package net.yorkdevsolutions.pantry.controllers;

import net.yorkdevsolutions.pantry.dto.RecipeDTO;
import net.yorkdevsolutions.pantry.entities.Recipe;
import net.yorkdevsolutions.pantry.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/recipes")
@CrossOrigin
public class RecipeController {
    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @PostMapping("/accounts/{accountId}")
    public RecipeDTO createRecipe(@PathVariable UUID accountId, @RequestBody RecipeDTO recipeDTO){
        try {
            Recipe recipe = this.service.createRecipe(accountId,recipeDTO);
            return new RecipeDTO(recipe);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @GetMapping("/{recipeId}")
    public RecipeDTO getRecipeById(@PathVariable Long recipeId){
        try {
            Recipe recipe = this.service.findRecipeById(recipeId);
            return new RecipeDTO(recipe);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/accounts/{accountId}/recipes/{recipeId}")
    public RecipeDTO editRecipe(@PathVariable Long recipeId, @PathVariable UUID accountId, @RequestBody RecipeDTO updatedRecipe){
        try {
            Recipe recipe = service.putRecipe(recipeId, accountId, updatedRecipe);
            return new RecipeDTO(recipe);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @DeleteMapping("/accounts/{accountId}/recipes/{recipeId}")
    public void deleteRecipe(@PathVariable UUID accountId, @PathVariable Long recipeId){
        try {
            service.deleteRecipeById(accountId, recipeId);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
