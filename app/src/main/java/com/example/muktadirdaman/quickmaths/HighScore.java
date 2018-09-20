package com.example.muktadirdaman.quickmaths;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HighScore extends AppCompatActivity {


    SQLiteDatabase highScoreDB;
    Cursor cursor;
    String names[] = new String[5];
    int hscores[] = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);


        try {

            SQLiteDatabase eventsDB = this.openOrCreateDatabase("HighScores", MODE_PRIVATE, null);

            eventsDB.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, year INT(4))");

            eventsDB.execSQL("INSERT INTO events (name, year) VALUES ('End Of WW2', 1945)");

            eventsDB.execSQL("INSERT INTO events (name, year) VALUES ('Wham split up', 1986)");

            Cursor c = eventsDB.rawQuery("SELECT * FROM events", null);

            int nameIndex = c.getColumnIndex("name");
            int yearIndex = c.getColumnIndex("year");

            c.moveToFirst();
            int counter = 0;

            while (c != null) {

                //Log.i("Results - event", c.getString(nameIndex));
                names[counter] = c.getString(nameIndex);
                //Log.i("Results - year", Integer.toString(c.getInt(yearIndex)));
                hscores[counter] = c.getInt(yearIndex);
                counter++;
                c.moveToNext();
            }


        }
        catch (Exception e) {

            e.printStackTrace();

        }

        TextView text = (TextView)findViewById(R.id.textView);
        TextView text2 = (TextView)findViewById(R.id.textView2);
        String s = names[0];
        String s2 = Integer.toString(hscores[0]);
        text.setText(s);
        text2.setText(s2);
    }
}
