package com.example.quizappcontrole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Score extends AppCompatActivity {
   private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int scorevalue = extras.getInt("score");
            TextView score = findViewById(R.id.score) ;
            score.setText("Score : "+scorevalue);
            ProgressBar progressBar = findViewById(R.id.progress_bar) ;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                        progressBar.setProgress(scorevalue/3);

                }
            }, scorevalue*50);




            //The key argument here must match that used in the other activity
        }
        Button logout = (Button) findViewById(R.id.logout) ;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(Score.this, MainActivity.class);
                startActivity(i);


            }
        });

        Button tryagain = findViewById(R.id.tryagain) ;
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Score.this , questions.class) ;
                startActivity(i);
            }
        });
    }


}