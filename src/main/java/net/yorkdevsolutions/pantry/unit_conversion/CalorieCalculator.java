package net.yorkdevsolutions.pantry.unit_conversion;

import net.yorkdevsolutions.pantry.entities.RecipeIngredient;

public class CalorieCalculator {
    public static Long getIngredientCalories(RecipeIngredient ingredient){
        var item = ingredient.getItem();
        var quantity = ingredient.getQuantity() + FractionToDecimal.fractionToDecimal(ingredient.getQuantityFraction());
        if(item.getMeasurementType().equals(MeasurementType.WEIGHT)){
            var ingredientQtyInGrams = WeightConverter.convertToGram(quantity, WeightMeasurement.valueOf(ingredient.getMeasuredIn().toUpperCase()));
            var ingredientQtyInItemMeasurement = WeightConverter.convertFromGram(ingredientQtyInGrams, WeightMeasurement.valueOf(item.getMeasurementName().toUpperCase()));
            var calories = ingredientQtyInItemMeasurement * item.getCaloriesPerUnit();
            return (long)calories;
        }
        if(item.getMeasurementType().equals(MeasurementType.VOLUME)){
            var ingredientQtyInTsp = VolumeConverter.convertToTsp(quantity, VolumeMeasurement.valueOf(ingredient.getMeasuredIn().toUpperCase()));
            var ingredientQtyInItemMeasurement = VolumeConverter.convertFromTsp(ingredientQtyInTsp, VolumeMeasurement.valueOf(item.getMeasurementName().toUpperCase()));
            var calories = ingredientQtyInItemMeasurement * item.getCaloriesPerUnit();
            return (long)calories;
        }
        return (long)(quantity * item.getCaloriesPerUnit());
    }
}
