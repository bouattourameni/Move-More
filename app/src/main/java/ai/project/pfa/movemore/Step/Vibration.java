package ai.project.pfa.movemore.Step;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;


/**
 * Created by Ameni on 13/04/2015.
 */
public class Vibration extends Service {
static double x_axes=0;
static double y_axes=0;
static double z_axes=0;
static int essai=0;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class IncomingHandle extends Handler {

        private int nbre_msg=0;
        @Override
        public void handleMessage(Message msg){

        }
    }
}
