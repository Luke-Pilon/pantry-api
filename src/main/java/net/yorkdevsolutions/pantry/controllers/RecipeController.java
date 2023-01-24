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

    @PostMapping()
    public RecipeDTO createRecipe(@RequestHeader("Authorization") UUID authToken, @RequestBody RecipeDTO recipeDTO){
        try {
            Recipe recipe = this.service.createRecipe(authToken,recipeDTO);
            return new RecipeDTO(recipe);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @GetMapping()
    public Iterable<RecipeDTO> getRecipesByAccount(@RequestHeader("Authorization") UUID authToken){
        return this.service.getRecipesByUser(authToken);
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

    @PutMapping("/{recipeId}")
    public RecipeDTO editRecipe(@PathVariable Long recipeId, @RequestHeader("Authorization") UUID authToken, @RequestBody RecipeDTO updatedRecipe){
        try {
            Recipe recipe = service.putRecipe(recipeId, authToken, updatedRecipe);
            return new RecipeDTO(recipe);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @DeleteMapping("/{recipeId}")
    public void deleteRecipe(@RequestHeader("Authorization") UUID authToken, @PathVariable Long recipeId){
        try {
            service.deleteRecipeById(authToken, recipeId);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
