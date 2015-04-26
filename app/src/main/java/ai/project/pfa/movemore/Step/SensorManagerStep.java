package ai.project.pfa.movemore.Step;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

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



    public SensorManagerStep(SensorManager manager) {
        this.manager = manager;
        acc=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


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



        fuzzy.evaluate(x,y,z);


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
