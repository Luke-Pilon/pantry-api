package net.yorkdevsolutions.pantry.dto;

import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.entities.Recipe;
import net.yorkdevsolutions.pantry.entities.RecipeIngredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RecipeDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private List<String> instructions;
    private Map<Item,Long> ingredients;

    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.imageUrl = recipe.getImageUrl();
        this.instructions = Arrays.asList(recipe.getInstructions().split("^;"));
        for(RecipeIngredient ingredient : recipe.getIngredients()){
            this.ingredients.put(ingredient.getItem(), ingredient.getQuantity());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public Map<Item, Long> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Item, Long> ingredients) {
        this.ingredients = ingredients;
    }
}
