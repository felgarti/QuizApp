package com.example.quizappcontrole;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class questions extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static String correctAnswer=""  ;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private static int page=1 ;
    private  static  int score=0 ;
    private  static  String answer ;
    private static int count=0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count=0 ;
        countQuestions() ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ShowQuestions() ;
        System.out.println("YUKGJBJGUBLIK.HULIYUKG.HBKJNKL/J.");
        Button answer1 = findViewById(R.id.answer1) ;
        Button answer2 = findViewById(R.id.answer2) ;
        Button answer3 = findViewById(R.id.answer3) ;
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer!=answer1.getText().toString())
                {
                    answer1.setBackgroundResource(R.drawable.edittextbackgroundchecked);
                    answer2.setBackgroundResource(R.drawable.edittextbackground);
                    answer3.setBackgroundResource(R.drawable.edittextbackground);
                    answer=answer1.getText().toString() ;
                }
                else  { answer1.setBackgroundResource(R.drawable.edittextbackground);
                    answer="" ;}

                }
        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer!=answer2.getText().toString())
                {
                    answer2.setBackgroundResource(R.drawable.edittextbackgroundchecked);
                    answer3.setBackgroundResource(R.drawable.edittextbackground);
                    answer1.setBackgroundResource(R.drawable.edittextbackground);
                    answer=answer2.getText().toString() ;
                }
                else   {answer2.setBackgroundResource(R.drawable.edittextbackground);
                    answer="" ;}

                }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer!=answer3.getText().toString())
                {
                    answer3.setBackgroundResource(R.drawable.edittextbackgroundchecked);
                    answer2.setBackgroundResource(R.drawable.edittextbackground);
                    answer1.setBackgroundResource(R.drawable.edittextbackground);
                    answer=answer3.getText().toString() ;
                }
                else  {
                    answer3.setBackgroundResource(R.drawable.edittextbackground);
                    answer="" ;
                }
            } });


        Button next = findViewById(R.id.nextBtn) ;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(answer.equals(correctAnswer)) {

                    score++;
                }


                if(page==count) {
                    Intent i = new Intent(questions.this , Score.class) ;
                    i.putExtra("score" , score) ;

                    startActivity(i);
                    score=0 ;
                    page=1 ;

                }
                else{
                    page++ ;
                    Intent i = new Intent(questions.this , questions.class) ;
                    startActivity(i);

                    ;}

            }
        });
        Button logout = (Button) findViewById(R.id.logout) ;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(questions.this, MainActivity.class);
                startActivity(i);

            }
        });

    }


public void ShowQuestions()
{

    DocumentReference docRef = db.collection("questions").document(String.valueOf(page));
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    TextView question = findViewById(R.id.question) ;
                    Button answer1 = findViewById(R.id.answer1) ;
                    Button answer2 = findViewById(R.id.answer2) ;
                    Button answer3 = findViewById(R.id.answer3) ;

        question.setText(document.getString("question"));
        answer1.setText(document.getString("answer1"));
        answer2.setText(document.getString("answer2"));
        answer3.setText(document.getString("answer3"));
        correctAnswer= document.getString("correct") ;




                } else {
                    Log.d(TAG, "No such document");

                }
            } else {

                Log.d(TAG, "Get failed with ", task.getException());
            }
        }
    });
}


public void countQuestions()
{

    db.collection("questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {

                for (DocumentSnapshot document : task.getResult()) {
                    count++;
                }
                Log.d("TAG", count + "");

            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        }
    });
}
}