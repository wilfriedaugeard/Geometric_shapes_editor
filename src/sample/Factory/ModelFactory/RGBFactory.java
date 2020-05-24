package sample.Factory.ModelFactory;

import sample.Model.RGB;

public class RGBFactory {

    /**
     * @param r Red color
     * @param g Green color
     * @param b Blue color
     * @return An object RGB
     */
    public static RGB getRGB(double r, double g, double b){
        return new RGB(r, g, b);
    }

    /**
     * @param r Red color
     * @param g Green color
     * @param b Blue color
     * @param a Alpha coefficient
     * @return An object RGB
     */
    public static RGB getRGB(double r, double g, double b, double a){
        return new RGB(r, g, b, a);
    }
}
