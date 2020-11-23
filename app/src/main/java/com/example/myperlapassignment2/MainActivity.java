package com.example.myperlapassignment2;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText quesET;
    Button addBtn;
    TextView quesNoTv;
    Button startBtn;

    ArrayList<String> questions;
    int quesNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quesET = findViewById(R.id.enterQuesEt);
        addBtn = findViewById(R.id.addQuesBtn);
        quesNoTv = findViewById(R.id.noOfQuesTv);
        startBtn = findViewById(R.id.startBtn);

        questions = new ArrayList<>();
        quesNo = 0;

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ques = quesET.getText().toString().trim();
                if(ques.matches("")){
                    Toast.makeText(getApplicationContext(), "Please enter a question", Toast.LENGTH_LONG).show();
                }else {
                    questions.add(ques);
                    quesNo+=1;
                    quesNoTv.setText(quesNo+"");
                    quesET.setText("");
                }
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quesNo < 2){
                    Toast.makeText(getApplicationContext(), "Please enter two questions at least", Toast.LENGTH_LONG).show();
                }else {
                    Intent i = new Intent(getApplicationContext(), QuestionsActivity.class);
                    i.putStringArrayListExtra("questions", questions);
                    startActivity(i);
                }
            }
        });


    }
}
