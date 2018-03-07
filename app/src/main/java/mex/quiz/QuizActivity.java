package mex.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;

public class QuizActivity extends AppCompatActivity {
    private Button trueButton, falseButton, quitButton;
    private int counter = 0;
    private boolean locker = false;
    public static final String SAS = "QuizActivity";
    public static final String MARKER = "SAS";
    public static final String LOCKER_MARKER = "SOS";

    @Override
    protected void onStart(){
        Log.d(SAS, "On start");
        super.onStart();
    }


    @Override
    protected void onStop(){
        Log.d(SAS, "On stop");
        super.onStop();
    }

    @Override
    protected void onResume(){
        Log.d(SAS, "On resume");
        super.onResume();
    }


    @Override
    protected void onPause(){
        Log.d(SAS, "On pause");
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        Log.d(SAS, "On destroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt(MARKER, counter);
        outState.putInt(LOCKER_MARKER, locker ? 0 : 1);
        super.onSaveInstanceState(outState);
    }

    @Override
    // Сделать несколько вопросов
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   // Всегда самой первой
        if(savedInstanceState != null && savedInstanceState.containsKey(MARKER))
        {
            counter = (int)savedInstanceState.get(MARKER) - 1;
            locker = (int)savedInstanceState.get(LOCKER_MARKER) == 0;
        }
        setContentView(R.layout.activity_quiz);  // id xml внутри системы   заполняет экран макетом
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        quitButton = findViewById(R.id.quit_button);
        changeText();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!locker)
                {
                    locker = true;
                    Toast.makeText(QuizActivity.this, R.string.true_ans, Toast.LENGTH_SHORT).show();
                    new CountDownTimer(2000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        changeText();
                        locker = false;
                    }
                }.start();
                }
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!locker)
                {
                    locker = true;
                    Toast.makeText(QuizActivity.this, R.string.false_ans, Toast.LENGTH_SHORT).show();
                    new CountDownTimer(2000, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {
                            changeText();
                            locker = false;
                        }
                    }.start();
                }
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

    }

    private void changeText(){
        TextView tv = findViewById(R.id.question_view);
        String naming = "question_" + ++counter;
        int id = this.getResources().getIdentifier(naming, "string", getPackageName());
        if(id == 0)
        {
            tv.setText("Вопросы кончились.");
            trueButton.setEnabled(false);
            trueButton.setVisibility(View.INVISIBLE);
            falseButton.setEnabled(false);
            falseButton.setVisibility(View.INVISIBLE);
            quitButton.setEnabled(true);
            quitButton.setVisibility(View.VISIBLE);
        }
        else
            tv.setText(id);
    }
}
