package sample.Factory.ModelFactory;

import sample.Model.RGB;

public class RGBFactory {
    public static RGB getRGB(double r, double g, double b){
        return new RGB(r, g, b);
    }

    public static RGB getRGB(double r, double g, double b, double a){
        return new RGB(r, g, b, a);
    }
}
