package com.example.shreyas.ballotapp;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;


public class Ballot extends ActionBarActivity {
    TextView room;
    TextView affDebater;
    TextView negDebater;
    EditText affSpeaks;
    EditText negSpeaks;
    CheckBox affWins;
    CheckBox negWins;
    CheckBox lowPointWin;
    EditText rfd;
    Button submit;
    CharSequence text;
    int duration = Toast.LENGTH_SHORT;
    //Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballot);
        Parse.initialize(this, "oKR9sq6fjKiuccX7VERQXQykbdTmj23XtSAS7qPa", "sL3X36Goy7A2ZkTO2j6utIrVyJAje7fmtcIRY2nJ");
        room= (TextView) findViewById(R.id.room);
        affDebater= (TextView) findViewById(R.id.affDebater);
        negDebater= (TextView) findViewById(R.id.negDebater);
        affSpeaks= (EditText)findViewById(R.id.affSpeaks);
        negSpeaks= (EditText)findViewById(R.id.negSpeaks);
        affWins=(CheckBox) findViewById(R.id.affCheck);
        negWins= (CheckBox) findViewById(R.id.negCheck);
        lowPointWin= (CheckBox)findViewById(R.id.lowpointWin);
        rfd= (EditText) findViewById(R.id.rfd);
        submit= (Button) findViewById(R.id.submit);
        String[] pairings = getIntent().getStringExtra("Pairings").split("break");
        String ID = getIntent().getStringExtra("ID");
        String ans = "";
        for(int x = 0; x < pairings.length; x++)
            if(pairings[x].contains(ID))
                 ans = pairings[x];
        final String[] split = ans.split("\n");
        final int num = split.length;
        room.setText("Room: " + split[num - 1]);
        affDebater.setText(split[num - 3]);
        negDebater.setText(split[num - 2]);
        final String judge = split[num - 4];
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (affWins.isChecked() && negWins.isChecked()) {
                    text="Both debaters may not win this debate";
                    Toast.makeText(getApplicationContext(),text,duration).show();
                }
                else if (!affWins.isChecked() && !negWins.isChecked()) {
                    text="Please select a winner";
                    Toast.makeText(getApplicationContext(),text,duration).show();
                }
                else if(!isInteger(affSpeaks.getText().toString()) ||!isInteger(affSpeaks.getText().toString())) {
                    text="Please enter speaker points";
                    Toast.makeText(getApplicationContext(),text,duration).show();
                }
                else if (affSpeaksLess() && affWins.isChecked() && !lowPointWin.isChecked() ){
                    text="A low point win was not intended";
                    Toast.makeText(getApplicationContext(),text,duration).show();
                }
                else if (negSpeaksLess() && negWins.isChecked() && !lowPointWin.isChecked() ){
                    text="A low point win was not intended";
                    Toast.makeText(getApplicationContext(),text,duration).show();
                }
                else if(rfd.getText().toString().equals(null)){
                    text="Please enter an RFD";
                    Toast.makeText(getApplicationContext(),text,duration).show();
                }
                else{
                    String rfdText= rfd.getText().toString();
                    int affSpeakerPoints=Integer.parseInt(affSpeaks.getText().toString());
                    int negSpeakerPoints= Integer.parseInt(negSpeaks.getText().toString());
                    boolean winner=  affWins.isChecked();
                    boolean lowPointWinIntended=lowPointWin.isChecked();
                    text="Your ballot has been recorded";

                    String result = "";
                    result += judge + "\n";
                    result += split[num - 3] + ": " + affSpeakerPoints;
                    result += split[num - 2] + ": " + negSpeakerPoints;
                    if(winner)
                        result += "Affirmative Win\n";
                    else
                        result += "Negative Win\n";
                    result += rfdText + "\n\n";
                    ParseObject upload = new ParseObject("Result");
                    upload.put("Result", result);
                    upload.saveInBackground();
                    Intent intent = new Intent(getApplicationContext(), End.class);
                    startActivity(intent);
                }
            }
        });


    }
    public boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean affSpeaksLess(){
        return Integer.parseInt(affSpeaks.getText().toString())<Integer.parseInt(negSpeaks.getText().toString());
    }
    private boolean negSpeaksLess(){
        return Integer.parseInt(negSpeaks.getText().toString())<Integer.parseInt(affSpeaks.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ballot, menu);
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