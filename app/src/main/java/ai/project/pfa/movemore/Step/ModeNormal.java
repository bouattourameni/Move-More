package ai.project.pfa.movemore.Step;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.io.InputStream;

import ai.project.pfa.movemore.R;

public class ModeNormal extends Activity {

    SensorManagerStep manager;
    InputStream ins;

    TextView steps;
    Fuzzy fuzzy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_normal);

        steps=(TextView)findViewById(R.id.steps);
        ins = getResources().openRawResource(getResources().
                getIdentifier("raw/pedometre", "raw", getPackageName()));
        fuzzy = new Fuzzy(ins);
        manager.setFuzzy(fuzzy);
        manager=new SensorManagerStep((SensorManager)getSystemService(SENSOR_SERVICE));
        manager.StartListening();

    }



}
