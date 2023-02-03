package net.yorkdevsolutions.pantry.unit_conversion;

public class FractionToDecimal {
    public static double fractionToDecimal(String fraction){
        var array = fraction.split("/");
        if(array.length != 2){return 0;}
        var numerator = Double.parseDouble(array[0]);
        var denominator = Double.parseDouble(array[1]);
        return numerator / denominator;
    }
}
