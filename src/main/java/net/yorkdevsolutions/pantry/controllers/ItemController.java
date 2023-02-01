package net.yorkdevsolutions.pantry.controllers;

import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.services.ItemService;
import net.yorkdevsolutions.pantry.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/items")
@CrossOrigin
public class ItemController {
    private final ItemService itemService;
    private final RecipeService recipeService;

    public ItemController(ItemService itemService, RecipeService recipeService) {
        this.itemService = itemService;
        this.recipeService = recipeService;
    }

    @GetMapping
    public Iterable<Item> getAllItems(){
        return this.itemService.getAllItems();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item){
        try {
            return this.itemService.createItem(item);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @PutMapping("/{itemId}")
    public Item updateItem(@RequestBody Item item, @PathVariable Long itemId){
        try {
            return this.itemService.updateItem(itemId, item);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable Long itemId){
        try {
            this.itemService.deleteItemById(itemId);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/recipe/{recipeId}")
    public Iterable<Item> updatePantryQuantitiesFromRecipe(@PathVariable Long recipeId){
        try {
            return itemService.updatePantryQuantitiesFromRecipe(recipeService.findRecipeById(recipeId));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/shop", consumes = "application/json")
    public Iterable<Item> updateMultipleItemQuantities(@RequestBody Map<Long,Long> itemsToAdd){
        try {
            return this.itemService.updateMultipleItemQuantities(itemsToAdd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
