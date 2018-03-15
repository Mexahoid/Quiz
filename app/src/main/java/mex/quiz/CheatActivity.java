package mex.quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER = "mex.quiz.ait";
    private String answer;
    private Button cheatButton;
    private TextView question, cheatyAnswer;
    private final String SAS = "Lol";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cheat);
        cheatButton = findViewById(R.id.cheat_button);
        question = findViewById(R.id.question_cheat_answer);
        cheatyAnswer = findViewById(R.id.question_cheat_question);

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnswerShowResult(true);

                question.setText(String.format("%s %s", CheatActivity.this.getResources().getString(R.string.cheat_true), answer));
                cheatButton.setEnabled(false);
                cheatButton.setVisibility(View.INVISIBLE);

                cheatyAnswer.setText(R.string.cheat_cheated);
            }
        });

        answer = getIntent().getStringExtra(EXTRA_ANSWER);
    }


    public static Intent newIntent(Context packageContext, String answer){
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER, answer);
        return i;
    }

    private void setAnswerShowResult(boolean ias){
        Intent dataInt  = new Intent();
        dataInt.putExtra(SAS, ias);
        setResult(RESULT_OK, dataInt);
    }
}