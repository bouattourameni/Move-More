package ai.project.pfa.movemore.Step;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ai.project.pfa.movemore.R;


public class StepActivity extends Activity {


SensorManagerStep manager;

Button start;
Button ending;

static  TextView x;
static TextView y;
static TextView z;

Intent normal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        //intialisation du sensor manager
      //  manager=new SensorManagerStep((SensorManager)getSystemService(SENSOR_SERVICE));

        //faire la liaison entre les composants du XML et java
        start=(Button)findViewById(R.id.start);
        ending=(Button)findViewById(R.id.ending);
        
        x=(TextView)findViewById(R.id.x_axes);
        y=(TextView)findViewById(R.id.y_axes);
        z=(TextView)findViewById(R.id.z_axes);

        normal=new Intent(this,ModeNormal.class);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.StartListening();
            }
        });
        ending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.EndListening();
                startActivity(normal);
                onDestroy();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
