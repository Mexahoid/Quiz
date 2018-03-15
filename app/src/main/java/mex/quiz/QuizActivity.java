package mex.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;


public class QuizActivity extends AppCompatActivity {
    private Button quitButton, cheatButton, startOverButton;

    private Button[] buttons = new Button[4];
    private int[] answerColors = new int[15];

    private TextView question, questionInfo;
    private int counter = 0;
    private boolean locker = false;
    private boolean cheated = false;
    public static final String SAS = "QuizActivity";
    public static final String MARKER = "SAS";
    public static final String LOCKER_MARKER = "SOS";
    public static final String CHEATER_MARKER = "LOL";
    public static final String COLORS_MARKER = "KEK";
    public static int REQUEST_CHEAT = 0;

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt(MARKER, counter);
        outState.putInt(LOCKER_MARKER, locker ? 0 : 1);
        outState.putInt(CHEATER_MARKER, cheated ? 0 : 1);
        outState.putIntArray(COLORS_MARKER, answerColors);
        super.onSaveInstanceState(outState);
    }

    @Override
    // Сделать несколько вопросов
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   // Всегда самой первой
        setContentView(R.layout.activity_quiz);  // id xml внутри системы   заполняет экран макетом
        if(savedInstanceState != null &&
                savedInstanceState.containsKey(MARKER) &&
                savedInstanceState.containsKey(LOCKER_MARKER) &&
                savedInstanceState.containsKey(CHEATER_MARKER)&&
                savedInstanceState.containsKey(COLORS_MARKER))
        {
            counter = (int)savedInstanceState.get(MARKER) - 1;
            locker = (int)savedInstanceState.get(LOCKER_MARKER) == 0;
            cheated = (int)savedInstanceState.get(CHEATER_MARKER) == 0;
            answerColors = (int[])savedInstanceState.get(COLORS_MARKER);
            colorRows();
        }


        question = findViewById(R.id.question_text);
        questionInfo = findViewById(R.id.question_choices);

        initButtons();
        changeText();

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(CheatActivity.newIntent(QuizActivity.this, findAnswer()), REQUEST_CHEAT);
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        startOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(counter = 1; counter < 16; counter++)
                    colorRow(0, counter);
                counter = 0;
                switchChoiceButtons();
                startOverButton.setEnabled(false);
                startOverButton.setVisibility(View.INVISIBLE);
                changeText();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CHEAT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                cheated = true;
                boolean sas = data.getBooleanExtra("Lol", false);
            }
        }
    }

    private void changeText(){
        String naming = "question_" + ++counter;
        int qid = this.getResources().getIdentifier(naming, "string", getPackageName());
        naming = "question_" + counter + "_text";
        int qiid = this.getResources().getIdentifier(naming, "string", getPackageName());;
        if(qid == 0 || qiid == 0)
        {
            question.setText("Вопросы кончились. Действие?");
            questionInfo.setText("");
            switchChoiceButtons();
            quitButton.setEnabled(true);
            quitButton.setVisibility(View.VISIBLE);
            startOverButton.setEnabled(true);
            startOverButton.setVisibility(View.VISIBLE);
        }
        else
        {
            question.setText(qid);
            questionInfo.setText(qiid);
        }
    }

    private void switchChoiceButtons(){
        for (int i = 0; i < 4; i++)
        {
            buttons[i].setEnabled(!buttons[i].isEnabled());
            buttons[i].setVisibility(buttons[i].isEnabled() ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private String findAnswer(){
        String naming = "question_" + counter + "_truth";
        return getStringResourceValue(naming);
    }

    private void initButtons(){
        buttons[0] = findViewById(R.id.button_1);
        buttons[1] = findViewById(R.id.button_2);
        buttons[2] = findViewById(R.id.button_3);
        buttons[3] = findViewById(R.id.button_4);

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceButtonHandler(getStringResourceValue("question_button_" + 1));
            }
        });

        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceButtonHandler(getStringResourceValue("question_button_" + 2));
            }
        });
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceButtonHandler(getStringResourceValue("question_button_" + 3));
            }
        });
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceButtonHandler(getStringResourceValue("question_button_" + 4));
            }
        });

        startOverButton = findViewById(R.id.start_over_button);
        quitButton = findViewById(R.id.quit_button);
        cheatButton = findViewById(R.id.cheat_button_invoke);
    }

    private void choiceButtonHandler(String selection){
        final int resId = cheated
                ? R.string.cheated
                : (findAnswer().equals(selection)
                        ? R.string.true_ans
                        : R.string.false_ans);
        if(!locker)
        {
            locker = true;
            Toast.makeText(QuizActivity.this, resId, Toast.LENGTH_SHORT).show();
            new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    colorRow(resId, counter);
                    changeText();
                    locker = false;
                }
            }.start();
            cheated = false;
        }
    }

    private String getStringResourceValue(String naming){
        int id = this.getResources().getIdentifier(naming, "string", getPackageName());
        if(id != 0){
            return this.getResources().getString(id);
        }
        return null;
    }

    private TextView getRow(int pos){
        switch (pos){
            case 1:
                return findViewById(R.id.row_1);
            case 2:
                return findViewById(R.id.row_2);
            case 3:
                return findViewById(R.id.row_3);
            case 4:
                return findViewById(R.id.row_4);
            case 5:
                return findViewById(R.id.row_5);
            case 6:
                return findViewById(R.id.row_6);
            case 7:
                return findViewById(R.id.row_7);
            case 8:
                return findViewById(R.id.row_8);
            case 9:
                return findViewById(R.id.row_9);
            case 10:
                return findViewById(R.id.row_10);
            case 11:
                return findViewById(R.id.row_11);
            case 12:
                return findViewById(R.id.row_12);
            case 13:
                return findViewById(R.id.row_13);
            case 14:
                return findViewById(R.id.row_14);
            case 15:
                return findViewById(R.id.row_15);
        }
        return null;
    }

    private void colorRow(int type, int rowPos){
        TextView row = getRow(rowPos);

        answerColors[rowPos - 1] = type;
        switch (type){
            case 0:
                row.setBackgroundResource(R.color.question_not_answered);
                answerColors[rowPos - 1] = 0;
                break;
            case R.string.true_ans:
                row.setBackgroundResource(R.color.question_answered_true_back);
                break;
            case R.string.false_ans:
                row.setBackgroundResource(R.color.question_answered_false_back);
                break;
            case R.string.cheated:
                row.setBackgroundResource(R.color.question_cheated_back);
                break;
        }
    }

    private void colorRows(){
        for (int i = 0; i < 15; i++){
            colorRow(answerColors[i], i + 1);
        }
    }

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

}
