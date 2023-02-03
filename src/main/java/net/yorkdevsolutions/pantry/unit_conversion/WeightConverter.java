package net.yorkdevsolutions.pantry.unit_conversion;

public class WeightConverter {
    private static double OUNCE_TO_GRAM = 28.35;
    private static long POUND_TO_OUNCE = 16;

    public static double convertToGram(double quantity, WeightMeasurement initialMeasurement){
        switch(initialMeasurement) {
            case OUNCES:
                return quantity * OUNCE_TO_GRAM;
            case POUNDS:
                return quantity * POUND_TO_OUNCE * OUNCE_TO_GRAM;
            default:
                return quantity;
        }
    }

    public static double convertFromGram(double quantity, WeightMeasurement desiredMeasurement){
        switch(desiredMeasurement) {
            case OUNCES:
                return quantity / OUNCE_TO_GRAM;
            case POUNDS:
                return quantity / OUNCE_TO_GRAM / POUND_TO_OUNCE;
            default:
                return quantity;
        }
    }
}
