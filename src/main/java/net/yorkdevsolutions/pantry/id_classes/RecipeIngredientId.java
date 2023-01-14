package net.yorkdevsolutions.pantry.id_classes;

import net.yorkdevsolutions.pantry.entities.Item;
import net.yorkdevsolutions.pantry.entities.Recipe;

import java.io.Serializable;

public class RecipeIngredientId implements Serializable {
    private Recipe recipe;
    private Item item;
}
