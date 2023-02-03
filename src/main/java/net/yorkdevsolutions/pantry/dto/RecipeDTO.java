package net.yorkdevsolutions.pantry.dto;

import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.entities.Recipe;
import net.yorkdevsolutions.pantry.entities.RecipeIngredient;
import net.yorkdevsolutions.pantry.unit_conversion.CalorieCalculator;

import java.util.*;

public class RecipeDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private List<String> instructions;
    private List<RecipeIngredientDTO> ingredients;

    private Long totalCalories;

    public RecipeDTO() {
    }

    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.imageUrl = recipe.getImageUrl();
        this.instructions = Arrays.asList(recipe.getInstructions().split(";"));
        this.ingredients = new ArrayList<>();
        this.totalCalories = 0L;
        for(RecipeIngredient ingredient : recipe.getIngredients()){
            var ingredientDTO = new RecipeIngredientDTO(ingredient);
            this.totalCalories += CalorieCalculator.getIngredientCalories(ingredient);
            this.ingredients.add(ingredientDTO);
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

    public List<RecipeIngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public Long getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Long totalCalories) {
        this.totalCalories = totalCalories;
    }
}
