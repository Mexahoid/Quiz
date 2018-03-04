package mex.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button trueButton, falseButton;
    @Override
    // Сделать несколько вопросов
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   // Всегда самой первой
        setContentView(R.layout.activity_quiz);  // id xml внутри системы   заполняет экран макетом
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuizActivity.this, R.string.true_ans, Toast.LENGTH_SHORT).show();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuizActivity.this, R.string.false_ans, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
