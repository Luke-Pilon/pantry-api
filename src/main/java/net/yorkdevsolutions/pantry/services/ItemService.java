package net.yorkdevsolutions.pantry.services;

import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.entities.Recipe;
import net.yorkdevsolutions.pantry.entities.RecipeIngredient;
import net.yorkdevsolutions.pantry.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
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

    public ArrayList<Item> updatePantryQuantitiesFromRecipe(Recipe recipe) {
        //TODO
        //Make this a transaction instead
        ArrayList<Item> itemsMissing = new ArrayList<>();
        for(RecipeIngredient ingredient : recipe.getIngredients()){
            if(ingredient.getQuantity() > ingredient.getItem().getUnitsAvailable()){
                itemsMissing.add(ingredient.getItem());
            }
        }
        if(itemsMissing.size() > 0){
            return itemsMissing;
        }
        for(RecipeIngredient ingredient : recipe.getIngredients()) {
            Long newUnitsAvailable = ingredient.getItem().getUnitsAvailable() - ingredient.getQuantity();
            ingredient.getItem().setUnitsAvailable(newUnitsAvailable);
            itemRepository.save(ingredient.getItem());
        }
        return null;
    }

    public Iterable<Item> updateMultipleItemQuantities(Map<Long, Long> itemsToAdd) {
        //Items to add is a map where the item id is the key and quantity to add is the number to increase inventory by
        var itemsUpdated = new ArrayList<Item>();
        for(Map.Entry<Long,Long> entry : itemsToAdd.entrySet()){
            var _item = this.itemRepository.findById(entry.getKey());
            var item = _item.get();
            if(item != null && entry.getValue() > 0){
                Long newQuantity = item.getUnitsAvailable() + entry.getValue();
                item.setUnitsAvailable(newQuantity);
            }
            itemsUpdated.add(item);
            itemRepository.save(item);
        }
        return itemsUpdated;
    }
}
