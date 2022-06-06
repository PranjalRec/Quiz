package com.pranjal.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView textViewTimer,textViewQuestion,textViewA,textViewB,textViewC,textViewD;
    String correctAns,userAns;
    String question,a,b,c,d;
    int questionNumber = 1, userScore = 0, questionCount,timePerQuestion=30;
    CountDownTimer countDownTimer;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    Button buttonNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.textViewTimer);
        textViewA = findViewById(R.id.textViewA);
        textViewB = findViewById(R.id.textViewB);
        textViewC = findViewById(R.id.textViewC);
        textViewD = findViewById(R.id.textViewD);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        buttonNext = findViewById(R.id.buttonNext);


//        setTime();
        setDataByFirebase();


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();

                textViewA.setClickable(true);
                textViewB.setClickable(true);
                textViewC.setClickable(true);
                textViewD.setClickable(true);

                textViewA.setBackgroundColor(getResources().getColor(R.color.teal_200));
                textViewB.setBackgroundColor(getResources().getColor(R.color.teal_200));
                textViewC.setBackgroundColor(getResources().getColor(R.color.teal_200));
                textViewD.setBackgroundColor(getResources().getColor(R.color.teal_200));

                if(questionNumber <= questionCount){
                    setDataByFirebase();
                }
                else{
                    Intent i = new Intent(MainActivity.this,ResultActivity.class);
                    i.putExtra("marks",userScore);
                    i.putExtra("totalQuestions",questionCount);

                    String uid = user.getUid();

                    reference.child("userScore").child(uid).child("score").setValue(userScore);

                    startActivity(i);
                    finish();
                }
            }
        });


        textViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAns = textViewA.getText().toString();
                countDownTimer.cancel();

                textViewA.setClickable(false);
                textViewB.setClickable(false);
                textViewC.setClickable(false);
                textViewD.setClickable(false);

                if(userAns.equals(correctAns)){
                    userScore++;
                    textViewA.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else{
                    textViewA.setBackgroundColor(getResources().getColor(R.color.red));

                    if((textViewB.getText().toString()).equals(correctAns)){
                        textViewB.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if((textViewC.getText().toString()).equals(correctAns)){
                        textViewC.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if((textViewD.getText().toString()).equals(correctAns)){
                        textViewD.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                }

            }
        });

        textViewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countDownTimer.cancel();

                textViewA.setClickable(false);
                textViewB.setClickable(false);
                textViewC.setClickable(false);
                textViewD.setClickable(false);

                userAns = textViewB.getText().toString();
                if(userAns.equals(correctAns)){
                    userScore++;
                    textViewB.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else{
                    textViewB.setBackgroundColor(getResources().getColor(R.color.red));

                    if((textViewA.getText().toString()).equals(correctAns)){
                        textViewA.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if((textViewC.getText().toString()).equals(correctAns)){
                        textViewC.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if((textViewD.getText().toString()).equals(correctAns)){
                        textViewD.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                }

            }
        });

        textViewC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();

                textViewA.setClickable(false);
                textViewB.setClickable(false);
                textViewC.setClickable(false);
                textViewD.setClickable(false);

                userAns = textViewC.getText().toString();
                if(userAns.equals(correctAns)){
                    userScore++;
                    textViewC.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else{
                    textViewC.setBackgroundColor(getResources().getColor(R.color.red));

                    if((textViewA.getText().toString()).equals(correctAns)){
                        textViewA.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if((textViewB.getText().toString()).equals(correctAns)){
                        textViewB.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if((textViewD.getText().toString()).equals(correctAns)){
                        textViewD.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                }

            }
        });

        textViewD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();

                textViewA.setClickable(false);
                textViewB.setClickable(false);
                textViewC.setClickable(false);
                textViewD.setClickable(false);

                userAns = textViewD.getText().toString();
                if(userAns.equals(correctAns)){
                    userScore++;
                    textViewD.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else{
                    textViewD.setBackgroundColor(getResources().getColor(R.color.red));

                    if((textViewA.getText().toString()).equals(correctAns)){
                        textViewA.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if((textViewC.getText().toString()).equals(correctAns)){
                        textViewC.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    else if((textViewB.getText().toString()).equals(correctAns)){
                        textViewB.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                }

            }
        });

    }

    void setDataByFirebase(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionCount = (int)snapshot.child("Question").getChildrenCount();

                question = snapshot.child("Question").child(String.valueOf(questionNumber)).child("q").getValue().toString();
                a = snapshot.child("Question").child(String.valueOf(questionNumber)).child("a").getValue().toString();
                b = snapshot.child("Question").child(String.valueOf(questionNumber)).child("b").getValue().toString();
                c = snapshot.child("Question").child(String.valueOf(questionNumber)).child("c").getValue().toString();
                d = snapshot.child("Question").child(String.valueOf(questionNumber)).child("d").getValue().toString();
                correctAns = snapshot.child("Question").child(String.valueOf(questionNumber)).child("ans").getValue().toString();

                textViewQuestion.setText(question);
                textViewA.setText(a);
                textViewB.setText(b);
                textViewC.setText(c);
                textViewD.setText(d);

                questionNumber++;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        countDownTimer = new CountDownTimer(timePerQuestion*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(""+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

                textViewA.setClickable(true);
                textViewB.setClickable(true);
                textViewC.setClickable(true);
                textViewD.setClickable(true);

                textViewA.setBackgroundColor(getResources().getColor(R.color.teal_200));
                textViewB.setBackgroundColor(getResources().getColor(R.color.teal_200));
                textViewC.setBackgroundColor(getResources().getColor(R.color.teal_200));
                textViewD.setBackgroundColor(getResources().getColor(R.color.teal_200));

                if(questionNumber <= questionCount){
                    setDataByFirebase();
                }
                else{
                    Intent i = new Intent(MainActivity.this,ResultActivity.class);
                    i.putExtra("marks",userScore);
                    i.putExtra("totalQuestions",questionCount);
                    startActivity(i);
                    finish();
                }

            }
        }.start();
    }

}