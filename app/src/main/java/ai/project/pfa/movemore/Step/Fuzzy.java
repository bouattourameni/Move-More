package ai.project.pfa.movemore.Step;

import android.util.Log;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

import java.io.InputStream;

/**
 * Created by Ameni on 13/04/2015.
 */
public class Fuzzy extends Thread {

    private static FIS fis;
    private static InputStream ins;
//ameni
    public Fuzzy(InputStream ins) {
        this.ins = ins;
        fis = FIS.load(ins,true);
    }



    public synchronized static float evaluate(double xTrait, double yTrait, double zTrait){

        if( fis == null ) { // Error while loading?
            System.err.println("Can't load file: ''");
            return 0;
        }

        // Show ruleset
        FunctionBlock functionBlock = fis.getFunctionBlock(null);
//        functionBlock.chart();

        // Set inputs
        functionBlock.setVariable("axes_x", Math.abs(xTrait));
        functionBlock.setVariable("axes_y", Math.abs(yTrait));
        functionBlock.setVariable("axes_z", Math.abs(zTrait));

        // Evaluate
        functionBlock.evaluate();
        String S= String.valueOf(functionBlock.getVariable("movement"));
        Log.i("mouvement", Double.toString(functionBlock.getVariable("movement").getValue()));
        System.out.println(S);
        String S1= String.valueOf(functionBlock.getVariable("axes_x"));
        Log.i("x", Double.toString(functionBlock.getVariable("axes_x").getValue()));
        System.out.println(S1);
        String S2= String.valueOf(functionBlock.getVariable("axes_y"));
        Log.i("y", Double.toString(functionBlock.getVariable("axes_y").getValue()));
        System.out.println(S2);
        String S3= String.valueOf(functionBlock.getVariable("axes_z"));
        Log.i("z", Double.toString(functionBlock.getVariable("axes_z").getValue()));
        System.out.println(S3);

        //if (functionBlock.getVariable("movement").getValue()>=0.5) ModeNormal.setSteps();
        return (float) functionBlock.getVariable("movement").getValue();

    }





}
