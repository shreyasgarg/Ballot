package com.example.shreyas.ballotapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class JudgePage extends ActionBarActivity {
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge_page);
        final EditText editText = (EditText)findViewById(R.id.enterJudgeID);
        final TextView textView = (TextView)findViewById(R.id.tournName);
        String s =getIntent().getStringExtra("Judges");
        final String[] arr = s.split("\n");
        textView.setText("Welcome to " + "" + new String(getIntent().getStringExtra("Name")));
        Button button = (Button)findViewById(R.id.judgeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Ballot.class);
                boolean correct = false;
                for(int x = 0; x < arr.length; x++)
                    if(arr[x].equals(ID))
                        correct = true;
                if(correct)
                {
                    intent.putExtra("ID",ID);
                    String temp = getIntent().getStringExtra("Pairings");
                    intent.putExtra("Pairings", temp);
                    Log.i("TAG", temp);
                    startActivity(intent);
                }
                else
                {
                    TextView textView = (TextView)findViewById(R.id.textView3);
                    textView.setText("Sorry invalid ID");
                }
            }
        });
    }

public boolean validateID()
{
    return false;
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_judge_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
