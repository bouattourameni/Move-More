package ai.project.pfa.movemore.Step;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;


/**
 * Created by Ameni on 13/04/2015.
 */
public class Vibration extends Service {
static double x_axes=0;
static double y_axes=0;
static double z_axes=0;
static int essai=0;


public double getMax(double x, double y){
    if (x>=y) return x;
    return y;
}


    class IncomingHandle extends Handler {

        private int nbre_msg=0;
        private int debut = 0;
        @Override
        public void handleMessage(Message msg){

            Bundle b= msg.getData();
            double x = b.getDouble("x");
            double y = b.getDouble("y");
            double z = b.getDouble("z");
            nbre_msg++;
            if (nbre_msg < 20){
                x_axes = getMax(x,x_axes);
                y_axes = getMax(y,y_axes);
                z_axes = getMax(z, z_axes);
            }
            else {
                float f = Fuzzy.evaluate(x_axes,y_axes,z_axes);
                if (f>0.4){
                    ModeNormal.steps.setText(Integer.parseInt(String.valueOf(ModeNormal.steps.getText()))+1);
                }

                nbre_msg = 0;
            }
            super.handleMessage(msg);

        }
    }
    final Messenger mMessenger = new Messenger(new IncomingHandle());
    @Override
    public IBinder onBind(Intent intent) {

        return mMessenger.getBinder();
    }
}
