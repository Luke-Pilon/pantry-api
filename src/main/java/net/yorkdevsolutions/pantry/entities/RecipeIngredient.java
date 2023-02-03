package net.yorkdevsolutions.pantry.entities;

import jakarta.persistence.*;
import net.yorkdevsolutions.pantry.id_classes.RecipeIngredientId;

@Entity
@IdClass(RecipeIngredientId.class)
public class RecipeIngredient {
    @ManyToOne
    @Id
    private Item item;

    @ManyToOne
    @Id
    private Recipe recipe;

    private Long quantity;

    private String quantityFraction;

    private String measuredIn;

    public RecipeIngredient() {
    }

    public RecipeIngredient(Item item, Recipe recipe, Long quantity, String quantityFraction, String measuredIn) {
        this.item = item;
        this.recipe = recipe;
        this.quantity = quantity;
        this.quantityFraction = quantityFraction;
        this.measuredIn = measuredIn;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item ingredient) {
        this.item = ingredient;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getMeasuredIn() {
        return measuredIn;
    }

    public void setMeasuredIn(String measuredIn) {
        this.measuredIn = measuredIn;
    }

    public String getQuantityFraction() {
        return quantityFraction;
    }

    public void setQuantityFraction(String quantityFraction) {
        this.quantityFraction = quantityFraction;
    }
}
