package ai.project.pfa.movemore.graphics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ai.project.pfa.movemore.R;
import ai.project.pfa.movemore.Step.ModeNormal;

public class Splash extends Activity implements AdapterView.OnItemSelectedListener{
GridView g;
public   String[] items={"lorem", "ipsum", "dolor", "sit", "amet",
            "consectetuer", "adipiscing", "elit", "morbi", "vel",
            "ligula", "vitae", "arcu", "aliquet", "mollis",
            "etiam", "vel", "erat", "placerat", "ante",
            "porttitor", "sodales", "pellentesque", "augue", "purus"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GridView g=(GridView) findViewById(R.id.gridView);

        g.setAdapter(new ModeAdapterView(this,
                R.layout.images,R.layout.itemname,
                items));
        g.setOnItemSelectedListener(this);

        Intent i = new Intent (this, ModeNormal.class);
     //   startActivity(i);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

class ModeAdapterView extends ArrayAdapter{
Context ctxt;
    public ModeAdapterView(Context ctxt, int resource, int resources,
                            String[] items) {
        super(ctxt, resource,resources, items);
        this.ctxt=ctxt;
    }
    public View getView(int position, View convertView,
                         ViewGroup parent) {
        TextView label = (TextView) convertView;
        if (convertView == null) {
            convertView = new TextView(ctxt);
            convertView..           label = (TextView) convertView;
     }
        label.setText(items[position]);
        ImageView pic = (ImageView) convertView;

        return (convertView);
    }

}}