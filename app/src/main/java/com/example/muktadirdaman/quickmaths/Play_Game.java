package com.example.muktadirdaman.quickmaths;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Play_Game extends AppCompatActivity {


    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView time;
    TextView points;
    TextView sumtextView;
    ArrayList<Integer> answer = new ArrayList<Integer>();
    TextView result;
    int score = 0;
    int questionNum = 0;
    Button playAgain;
    int locationOfCorretAnswer;
    int highscores[] = new int[5];

    SQLiteDatabase eventsDB;
    Cursor c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__game);
        for(int k = 0; k<5; k++){
            highscores[k] = 0;
        }

        try {

            eventsDB = this.openOrCreateDatabase("HighScores", MODE_PRIVATE, null);
            eventsDB.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, year INT(4))");
            c = eventsDB.rawQuery("SELECT * FROM events", null);

            int yearIndex = c.getColumnIndex("year");

            c.moveToFirst();
            int counter = 0;

            while (c != null) {

                highscores[counter] = c.getInt(yearIndex);
                counter++;
                c.moveToNext();
            }
            c.moveToFirst();
        }
        catch (Exception e) {

            e.printStackTrace();

        }

        button0 = (Button)findViewById(R.id.button00);
        button1 = (Button)findViewById(R.id.button11);
        button2 = (Button)findViewById(R.id.button22);
        button3 = (Button)findViewById(R.id.button33);
        result = (TextView)findViewById(R.id.result);
        points = (TextView)findViewById(R.id.points);
        time = (TextView)findViewById(R.id.time);
        playAgain= (Button)findViewById(R.id.playAgain);
        playAgain.setVisibility(View.INVISIBLE);
        restart(findViewById(R.id.result));
    }

    public void restart(View view){

        result.setText("");
        time.setText("30s");
        score = 0;
        questionNum = 0;
        new CountDownTimer(31100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {

                highest();
                result.setText("Thou Hast Scoreth " + score + "/" + questionNum);
                time.setText("GAME");
                sumtextView.setText("OVER!");
                points.setText("KO");
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);


                playAgain.setVisibility(View.VISIBLE);

            }
        }.start();
        generateNewQuestion();

    }





    public void generateNewQuestion(){

        answer.clear();
        Random rand= new Random();
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        sumtextView = (TextView)findViewById(R.id.sumTextView);
        int a = rand.nextInt(21);

        int b = rand.nextInt(21);

        locationOfCorretAnswer= rand.nextInt(4);
        int incorrectAnswer;
        for(int i=0; i<4; i++){

            if(i==locationOfCorretAnswer){
                answer.add(a+b);
            }
            else{
                incorrectAnswer = rand.nextInt(41);
                if(incorrectAnswer==(a+b)){ incorrectAnswer+=1;}
                answer.add(incorrectAnswer);
            }
        }


        button0.setText(Integer.toString(answer.get(0)));
        button1.setText(Integer.toString(answer.get(1)));
        button2.setText(Integer.toString(answer.get(2)));
        button3.setText(Integer.toString(answer.get(3)));

        sumtextView.setText(Integer.toString(a) + "+" + Integer.toString(b));

    }


    public void chooseAnswer(View view){

        questionNum++;

        if(view.getTag().toString().equals(Integer.toString(locationOfCorretAnswer))){

            score++;
            result.setText("CORRECT!");

        }
        else{result.setText("WRONG!");}

        points.setText(score + "/" + questionNum);

        generateNewQuestion();

    }

        public void highest(){

        int lowest = 0;

        for(int k=0; k<5; k++){
            if(highscores[k]<score){
                lowest = highscores[k];
                Toast.makeText(this, "You have a new high score", Toast.LENGTH_SHORT).show();
                eventsDB.execSQL("INSERT INTO events (name, year) VALUES ('End Of WW2', lowest)");
                break;
            }
        }

        }



}
