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

    public RecipeIngredient() {
    }

    public RecipeIngredient(Item item, Long quantity) {
        this.item = item;
        this.quantity = quantity;
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
}
