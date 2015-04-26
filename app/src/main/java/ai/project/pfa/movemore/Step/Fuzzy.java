package ai.project.pfa.movemore.Step;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

import java.io.InputStream;

/**
 * Created by Ameni on 13/04/2015.
 */
public class Fuzzy extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

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
        functionBlock.setVariable("axes_x", Math.abs(xTrait));
        functionBlock.setVariable("axes_y", Math.abs(yTrait));
        functionBlock.setVariable("axes_z", Math.abs(zTrait));

        // Evaluate
        functionBlock.evaluate();
        String S= String.valueOf(functionBlock.getVariable("movement"));
        System.out.println(S);
        //if (functionBlock.getVariable("movement").getValue()>=0.5) ModeNormal.setSteps();
        return (float) functionBlock.getVariable("movement").getValue();

    }





}
