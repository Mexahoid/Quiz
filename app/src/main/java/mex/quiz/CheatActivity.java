package mex.quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "Sos";
     static boolean answerIsTrue;
    private Button returnButton, cheatButton;
    private final String SAS = "Lol";
    private boolean wasCheated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answerIsTrue = false;

        returnButton = findViewById(R.id.return_button);
        cheatButton = findViewById(R.id.cheat_button);

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnswerShowResult(true);
            }
        });
        setContentView(R.layout.activity_cheat);
    }


    public static Intent newIntent(Context packageContext, boolean answerTrue){
        //Intent i = new Intent(packageContext, CheatActivity.class);
        Intent i = new Intent();
        answerIsTrue = answerTrue;
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerTrue);
        return i;
    }

    private void setAnswerShowResult(boolean ias){
        Intent dataInt  = new Intent();
        dataInt.putExtra(SAS, ias);
        setResult(RESULT_OK, dataInt);
    }
}
