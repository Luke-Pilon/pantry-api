package net.yorkdevsolutions.pantry.services;

import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.entities.Recipe;
import net.yorkdevsolutions.pantry.entities.RecipeIngredient;
import net.yorkdevsolutions.pantry.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Iterable<Item> getAllItems(){
        return this.itemRepository.findAll();
    }

    public Item createItemFromNewRecipe(String itemName) {
        var newItem = new Item(itemName);
        return this.itemRepository.save(newItem);
    }

    public Item getItemById(Long itemId){
        return this.itemRepository.findById(itemId).orElse(null);
    }

    public Item createItem(Item item) {
        if(!this.itemRepository.existsByName(item.getName())){
            return this.itemRepository.save(item);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Item updateItem(Long itemId, Item item){
        Item existingItem = this.itemRepository.findById(itemId).orElse(null);
        if(existingItem == null){
            return this.createItem(item);
        }
        if(!existingItem.getId().equals(item.getId())){
            throw new IllegalArgumentException();
        }
        return this.itemRepository.save(item);
    }

    public void deleteItemById(Long itemId) {
        itemRepository.delete(itemRepository.findById(itemId).orElseThrow());
    }

    public void updatePantryQuantitiesFromRecipe(Recipe recipe) {
        //TODO
        //Make this a transaction instead
        for(RecipeIngredient ingredient : recipe.getIngredients()){
            if(ingredient.getQuantity() > ingredient.getItem().getUnitsAvailable()){
                throw new IllegalArgumentException();
            }
        }
        for(RecipeIngredient ingredient : recipe.getIngredients()) {
            Long newUnitsAvailable = ingredient.getItem().getUnitsAvailable() - ingredient.getQuantity();
            ingredient.getItem().setUnitsAvailable(newUnitsAvailable);
            itemRepository.save(ingredient.getItem());
        }
    }

    public Iterable<Item> updateMultipleItemQuantities(Map<Item, Long> itemsToAdd) {
        var itemsUpdated = new ArrayList<Item>();
        for(Map.Entry<Item,Long> entry : itemsToAdd.entrySet()){
            Item item = entry.getKey();
            if(entry.getValue() > 0){
                Long newQuantity = item.getUnitsAvailable() + entry.getValue();
                item.setUnitsAvailable(newQuantity);
            }
            itemsUpdated.add(item);
            itemRepository.save(item);
        }
        return itemsUpdated;
    }
}
