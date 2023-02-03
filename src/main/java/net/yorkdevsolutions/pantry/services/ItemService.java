package net.yorkdevsolutions.pantry.services;

import jakarta.transaction.Transactional;
import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.entities.Recipe;
import net.yorkdevsolutions.pantry.entities.RecipeIngredient;
import net.yorkdevsolutions.pantry.repositories.ItemRepository;
import net.yorkdevsolutions.pantry.unit_conversion.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    public void updatePantryQuantitiesFromRecipe(Recipe recipe) {
        ArrayList<String> itemsMissing = new ArrayList<>();
        for(RecipeIngredient ingredient : recipe.getIngredients()){
            double quantity = ingredient.getQuantity() + FractionToDecimal.fractionToDecimal(ingredient.getQuantityFraction());
            Item item = ingredient.getItem();
            double newQuantity;
            if(item.getMeasurementName().equals(ingredient.getMeasuredIn())){
                newQuantity = item.getUnitsAvailable() - quantity;
            } else {
                var measurementType = item.getMeasurementType();
                switch(measurementType){
                    case VOLUME: {
                        var ingredientMeasurement = VolumeMeasurement.valueOf(ingredient.getMeasuredIn().toUpperCase());
                        var ingredientInTsp = VolumeConverter.convertToTsp(quantity, ingredientMeasurement);
                        var itemMeasurement = VolumeMeasurement.valueOf(item.getMeasurementName().toUpperCase());
                        var inventoryInTsp = VolumeConverter.convertToTsp(item.getUnitsAvailable(), itemMeasurement);
                        var difference = inventoryInTsp - ingredientInTsp;
                        newQuantity = VolumeConverter.convertFromTsp(difference, itemMeasurement);
                        break;
                    }
                    case WEIGHT: {
                        var ingredientMeasurement = WeightMeasurement.valueOf(ingredient.getMeasuredIn().toUpperCase());
                        var ingredientInGram = WeightConverter.convertToGram(quantity, ingredientMeasurement);
                        var itemMeasurement = WeightMeasurement.valueOf(item.getMeasurementName().toUpperCase());
                        var inventoryInGram = WeightConverter.convertToGram(item.getUnitsAvailable(), itemMeasurement);
                        var difference = inventoryInGram - ingredientInGram;
                        newQuantity = WeightConverter.convertFromGram(difference, itemMeasurement);
                        break;
                    }
                    default: {
                        newQuantity = item.getUnitsAvailable() - quantity;
                        break;
                    }
                }
            }
            newQuantity = newQuantity * 100;
            newQuantity = Math.round(newQuantity);
            newQuantity = newQuantity / 100;
            item.setUnitsAvailable(newQuantity);
            if(item.getUnitsAvailable() < 0){
                itemsMissing.add(item.getName());
            }
        }
        if(itemsMissing.size() > 0){
            String itemsMissingMessage = String.join(", ", itemsMissing);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, itemsMissingMessage);
        }
    }

    public Iterable<Item> updateMultipleItemQuantities(Map<Long, Long> itemsToAdd) {
        //Items to add is a map where the item id is the key and quantity to add is the number to increase inventory by
        var itemsUpdated = new ArrayList<Item>();
        for(Map.Entry<Long,Long> entry : itemsToAdd.entrySet()){
            var _item = this.itemRepository.findById(entry.getKey());
            var item = _item.get();
            if(item != null && entry.getValue() > 0){
                double newQuantity = item.getUnitsAvailable() + entry.getValue();
                item.setUnitsAvailable(newQuantity);
            }
            itemsUpdated.add(item);
            itemRepository.save(item);
        }
        return itemsUpdated;
    }
}
