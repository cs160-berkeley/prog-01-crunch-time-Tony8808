package cs160.crunchtime;

import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText.*;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.*;
import android.widget.TextSwitcher;
import android.widget.TextSwitcher.*;
import android.widget.TextView;
import android.widget.TextView.*;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {

    private double cals = 0;
    private int base = 0;
    private final double ratePushups = 3.5;
    private final double rateSitups = 2;
    private final double rateJJ = 0.1;
    private final double rateJog = 0.12;

    private int mode = R.id.pushup;
    private int whatMetric = R.id.work;

    private TextSwitcher calChange;
    private EditText numField;
    private TextView textPushups;
    private TextView textSitups;
    private TextView textJJ;
    private TextView textJogging;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_land);
        }
        //setContentView(R.layout.activity_main);

        RadioGroup exercise = (RadioGroup) findViewById(R.id.exercise);
        RadioButton radPush = (RadioButton) findViewById(R.id.pushup);
        RadioButton radSit = (RadioButton) findViewById(R.id.situp);
        RadioButton radJog = (RadioButton) findViewById(R.id.jog);
        RadioButton radJJ = (RadioButton) findViewById(R.id.JJ);

        RadioGroup metric = (RadioGroup) findViewById(R.id.metric);
        RadioButton mWork = (RadioButton) findViewById(R.id.work);
        RadioButton mCal = (RadioButton) findViewById(R.id.min);

        numField = (EditText) findViewById(R.id.editNum);
        calChange = (TextSwitcher) findViewById(R.id.calCount);
        calChange.setFactory(mFactory);

        textPushups = (TextView) findViewById(R.id.textPushup);
        textSitups = (TextView) findViewById(R.id.textSitup);
        textJJ = (TextView) findViewById(R.id.textJJ);
        textJogging = (TextView) findViewById(R.id.textJog);

        image = (ImageView) findViewById(R.id.image);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String input = numField.getText().toString();
                if (input.isEmpty()){return;}

                base = Integer.valueOf(input);
                double rate = 1;
                if(mode == R.id.pushup){
                    rate = ratePushups;
                } else if (mode == R.id.situp){
                    rate = rateSitups;
                } else if (mode == R.id.JJ){
                    rate = rateJJ;
                } else if (mode == R.id.jog){
                    rate = rateJog;
                }

                cals = base / rate;

                //If user input is calories
                if(whatMetric == R.id.min){
                    cals = base;
                }


                textPushups.setText(String.format("%.2f", cals * ratePushups) + " repetitions");
                textSitups.setText(String.format("%.2f", cals * rateSitups) + " repetitions");
                textJJ.setText(String.format("%.2f", cals * rateJJ) + " minutes");
                textJogging.setText(String.format("%.2f", cals * rateJog) + " minutes");

                calChange.setText(String.format("%.2f", cals));

            }
        });

        metric.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.work){
                    whatMetric = R.id.work;
                    return;

                } else if(checkedId == R.id.min){
                    whatMetric = R.id.min;
                    return;
                }
            }
        });

        exercise.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.pushup){
                    mode = R.id.pushup;
                    image.setImageResource(R.drawable.pushup);
                    return;
                } else if(checkedId == R.id.situp){
                    mode = R.id.situp;
                    image.setImageResource(R.drawable.situp);
                    return;
                } else if(checkedId == R.id.JJ){
                    mode = R.id.JJ;
                    image.setImageResource(R.drawable.jumpjack);
                    return;
                } else if(checkedId == R.id.jog){
                    mode = R.id.jog;
                    image.setImageResource(R.drawable.jog);
                    return;
                }

            }


        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {

            // Create a new TextView
            TextView t = new TextView(MainActivity.this);
            t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            t.setTextAppearance(MainActivity.this, android.R.style.TextAppearance_Large);
            return t;
        }
    };
}
