package ai.project.pfa.movemore.Step;

/**
 * Created by Ameni on 13/04/2015.
 */
public class Learn {
    static double x_axes=0;
    static double y_axes=0;
    static double z_axes=0;

    public Learn () {

    }

    public static void learnStep(double x,double y,double z){
        if (Math.abs(x)>x_axes) x_axes=Math.abs(x);
        if (Math.abs(y)>y_axes) y_axes=Math.abs(y);
        if (Math.abs(z)>z_axes) z_axes=Math.abs(z);
    }
}
