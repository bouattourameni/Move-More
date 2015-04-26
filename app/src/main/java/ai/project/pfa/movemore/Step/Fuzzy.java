package ai.project.pfa.movemore.Step;

import android.os.AsyncTask;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

import java.io.InputStream;

/**
 * Created by Ameni on 13/04/2015.
 */
public class Fuzzy extends AsyncTask<Double, Void, Double> {
    private static FIS fis;
    private static InputStream ins;
//ameni
    public Fuzzy(InputStream ins) {
        this.ins = ins;
        fis = FIS.load(ins,true);
    }
    public static float evaluate(double xTrait, double yTrait, double zTrait){

        if( fis == null ) { // Error while loading?
            System.err.println("Can't load file: ''");
            return 0;
        }

        // Show ruleset
        FunctionBlock functionBlock = fis.getFunctionBlock(null);
//        functionBlock.chart();

        // Set inputs
        functionBlock.setVariable("axes_x", Math.abs(xTrait/Learn.x_axes));
        functionBlock.setVariable("axes_y", Math.abs(yTrait/Learn.y_axes));
        functionBlock.setVariable("axes_z", Math.abs(zTrait/Learn.z_axes));

        // Evaluate
        functionBlock.evaluate();
        String S= String.valueOf(functionBlock.getVariable("movement"));
        System.out.println(functionBlock.getVariable("movement"));
        //if (functionBlock.getVariable("movement").getValue()>=0.5) ModeNormal.setSteps();
        return (float) functionBlock.getVariable("movement").getValue();

    }

    @Override
    protected Double doInBackground(Double... params) {
        if( fis == null ) { // Error while loading?
            System.err.println("Can't load file: ''");
            return 0.0;
        }

        // Show ruleset
        FunctionBlock functionBlock = fis.getFunctionBlock(null);
//        functionBlock.chart();

        // Set inputs
        functionBlock.setVariable("axes_x", Math.abs(params[0]/Learn.x_axes));
        functionBlock.setVariable("axes_y", Math.abs(params[1]/Learn.y_axes));
        functionBlock.setVariable("axes_z", Math.abs(params[2]/Learn.z_axes));

        // Evaluate
        functionBlock.evaluate();
        String S= String.valueOf(functionBlock.getVariable("movement"));
        System.out.println(functionBlock.getVariable("movement"));
        //if (functionBlock.getVariable("movement").getValue()>=0.5) ModeNormal.setSteps();
        return (double) functionBlock.getVariable("movement").getValue();

    //}

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }
}
