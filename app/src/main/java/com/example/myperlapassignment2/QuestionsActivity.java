package com.example.myperlapassignment2;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.tomer.fadingtextview.FadingTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity implements SensorEventListener {

    TextView quesTv;
    ArrayList<String> questions;
    int x = 0 ;
    boolean isFar = true;
    FadingTextView fadingTextView;
    String[] questions2;

    Sensor ACCELEROMETERsensor;
    Sensor PROXIMITYsensor;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        quesTv = findViewById(R.id.quesTv);
        fadingTextView = findViewById(R.id.fadeTv);


        Intent intent = getIntent();
        questions = intent.getStringArrayListExtra("questions");

        /*for(int i = 0 ; i < questions.size() ; i++){
            Log.d("ttt", i+"="+questions.get(i));
        }*/
        questions2 = new String[questions.size()];

        for(int i = 0 ; i < questions.size() ; i++){
            questions2[i] = questions.get(i);
        }

        fadingTextView.setTexts(questions2);
        fadingTextView.setTimeout(1, TimeUnit.MILLISECONDS);


        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!= null && sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!= null){
            ACCELEROMETERsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            PROXIMITYsensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }else{
            Toast.makeText(this, "This sensor is not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if(event.values[0] >= 1){
                    fadingTextView.restart();
                }else{
                    fadingTextView.stop();
                }

        }else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if(event.values[2] <= -7){
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, ACCELEROMETERsensor, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, PROXIMITYsensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
