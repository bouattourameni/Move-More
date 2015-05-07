package ai.project.pfa.movemore.graphics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import ai.project.pfa.movemore.R;
import ai.project.pfa.movemore.Step.ModeStep;
import ai.project.pfa.movemore.stat.Statistics;

public class Home extends Activity implements AdapterView.OnItemSelectedListener {

    Button buttonNormal;
    Button buttonSportif;
    Button buttonStat;
    Button buttonAbout;
    int currentType = -1;
    Intent intentModes;
    Intent intentStat;
    static int stepping = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        buttonNormal=(Button)findViewById(R.id.buttonNormal);
        buttonSportif=(Button)findViewById(R.id.buttonSportif);
        buttonStat=(Button)findViewById(R.id.buttonStatistique);
        buttonAbout=(Button)findViewById(R.id.buttonAboutMe);
        intentModes = new Intent(this, ModeStep.class);
        intentStat = new Intent (this, Statistics.class);
        buttonNormal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (currentType == -1) {
                    intentModes.putExtra("type", 0);
                    startActivity(intentModes);
                    setCurrentType(0);
                }
                else if (currentType == 0){
                }
            }
        });
        buttonSportif.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (currentType == -1) {
                    intentModes.putExtra("type", 1);
                    startActivity(intentModes);
                    setCurrentType(1);
                }
            }
        });
        buttonStat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(intentStat);
            }
        });
        buttonAbout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Ameni Bouattour, GDG Sfax, #Study_Jams",Toast.LENGTH_LONG).show();
            }
        });


    }

    public int getCurrentType() {
        return currentType;
    }

    public void setCurrentType(int currentType) {
        this.currentType = currentType;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}