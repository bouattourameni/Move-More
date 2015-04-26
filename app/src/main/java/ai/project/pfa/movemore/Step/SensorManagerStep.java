package ai.project.pfa.movemore.Step;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by Ameni on 13/04/2015.
 */
public class SensorManagerStep implements SensorEventListener {

    private SensorManager manager;
    private Sensor acc;

    private double[] gravity={9.81,9.81,9.81};
    private final float alpha = (float) 0.3;

    double x;
    double y;
    double z;
    Fuzzy fuzzy;

    Messenger mService = null;
    boolean mBound;

    public void setmConnection(ServiceConnection mConnection) {
        this.mConnection = mConnection;
    }

    private ServiceConnection mConnection;

    public SensorManagerStep(SensorManager manager,ServiceConnection mConnection) {
        this.manager = manager;
        acc=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.mConnection=mConnection;

    }

    public void StartListening(){
        manager.registerListener(this,
        acc,
        SensorManager.SENSOR_DELAY_UI);
        setDelay();

    }


    public void EndListening(){

        manager.unregisterListener(this,
               acc
       );

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        x = event.values[0] - gravity[0];
        y = event.values[1] - gravity[1];
        z = event.values[1] - gravity[2];


        Bundle b = new Bundle();
        b.putString("x",Double.toString(x));
        b.putString("y",Double.toString(y));
        b.putString("z",Double.toString(z));
        if (mBound){
            Message msg = Message.obtain();
            msg.setData(b);

            try {
                mService.send(msg);
            }
            catch (RemoteException e){
                e.printStackTrace();
            }
        }





    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public void setDelay(){
        acc.getMinDelay();
    }

    public Fuzzy getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(Fuzzy fuzzy) {
        this.fuzzy = fuzzy;
    }
}
