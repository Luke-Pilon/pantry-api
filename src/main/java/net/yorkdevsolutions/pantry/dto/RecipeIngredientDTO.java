package net.yorkdevsolutions.pantry.dto;

import net.yorkdevsolutions.pantry.entities.RecipeIngredient;

public class RecipeIngredientDTO {
    private Long itemId;
    private String itemName;
    private Long quantity;

    private String measuredIn;

    public RecipeIngredientDTO() {
    }

    public RecipeIngredientDTO(RecipeIngredient ingredient) {
        this.itemId = ingredient.getItem().getId();
        this.itemName = ingredient.getItem().getName();
        this.quantity = ingredient.getQuantity();
        this.measuredIn = ingredient.getItem().getUnitMeasuredIn();
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getMeasuredIn() {
        return measuredIn;
    }

    public void setMeasuredIn(String measuredIn) {
        this.measuredIn = measuredIn;
    }
}
