package net.yorkdevsolutions.pantry.unit_conversion;

public class VolumeConverter {
    final private static long TBSP_TO_TSP = 3;
    final private static long CUP_TO_TSP = 48;

    public static double convertToTsp(double quantity, VolumeMeasurement initialMeasurement){
        switch(initialMeasurement) {
            case CUPS:
                return quantity * CUP_TO_TSP;
            case TABLESPOONS:
                return quantity * TBSP_TO_TSP;
            default:
                return quantity;
        }
    }

    public static double convertFromTsp(double quantity, VolumeMeasurement returnedMeasurement){
        switch(returnedMeasurement) {
            case CUPS:
                return quantity / CUP_TO_TSP;
            case TABLESPOONS:
                return quantity / TBSP_TO_TSP;
            default:
                return quantity;
        }
    }
}
