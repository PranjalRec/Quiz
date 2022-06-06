package com.pranjal.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView textViewResult;
    int result,totalQuestions;
    Button buttonLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Result");
        setContentView(R.layout.activity_result);
        buttonLeaderboard = findViewById(R.id.buttonLeaderboard);

        textViewResult = findViewById(R.id.textViewResult);
        Intent intent = getIntent();
        result = intent.getIntExtra("marks",0);
        totalQuestions = intent.getIntExtra("totalQuestions",0);

        textViewResult.setText(result+"/"+totalQuestions);

        buttonLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this,LeaderboardActivity.class);
                startActivity(i);
            }
        });

    }
}