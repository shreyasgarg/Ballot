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

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity {

    String TournID;
    int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView)findViewById(R.id.textView2);
        final EditText editText = (EditText)findViewById(R.id.editText);
        Button button = (Button)findViewById(R.id.button);
        Parse.initialize(this, "XtAio2fcWnN8RJeUdKWtOuIaldnwEfjQypcr9d0d", "wwl1UrYcjvlZHLBCe10GlLeFIORVXxLRJRhV7Dm0");
        final ArrayList<ParseObject> array = new ArrayList<ParseObject>();
        final ArrayList<String> judges = new ArrayList<String>();
        final ArrayList<String> pairings = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("TournamentID");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e)
            {
                if (e == null)
                    for(ParseObject o: objects)
                    {
                        array.add(o);
                        ParseFile file = (ParseFile)o.get("JudgeID");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, ParseException e) {
                                if(e == null)
                                {
                                    judges.add(new String(bytes));
                                }
                            }
                        });
                        ParseFile file2 = (ParseFile)o.get("Pairings");
                        file2.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, ParseException e) {
                                if(e == null)
                                {
                                    pairings.add(new String(bytes));
                                }
                            }
                        });
                    }
            }
        });
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    int tournID=0;
                    TournID = editText.getText().toString();
                    int place = 0;
                    for(ParseObject o: array)
                    {
                        place++;
                        tournID = Integer.parseInt(o.get("TournID").toString());
                        if(Integer.parseInt(TournID) == tournID)
                        {
                            num = 1;
                            final Intent intent = new Intent(getApplicationContext(), JudgePage.class);
                            intent.putExtra("Name",o.get("TournName").toString());
                            String judge = judges.get(place-1);
                            intent.putExtra("Judges", judge);
                            intent.putExtra("Pairings", pairings.get(place - 1));
                            startActivity(intent);
                        }
                    }
                    if(num == 0)
                    textView.setText("Sorry Invalid ID");
            };

    });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
