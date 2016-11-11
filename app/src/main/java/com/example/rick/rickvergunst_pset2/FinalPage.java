package com.example.rick.rickvergunst_pset2;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Rick on 11/8/2016.
 */
public class FinalPage extends AppCompatActivity {

    // Inialize variables needed troughout the application
    private Context mContext;
    Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_page);

        // Retrieves the intent containing the finalized story and initalizes variable
        // with the intent data
        Intent actCalled = getIntent();

        Bundle bundle = actCalled.getExtras();
        Story story = (Story) bundle.getSerializable("story");

        // Sets the text of the textView to the finalized story
        TextView finalStory = (TextView) findViewById(R.id.finalStory);
        finalStory.setText(story.toString());

        mContext = this;
    }

    // Function to start a new story same as goToSecondPhase
    // NOTE see goToSecondPhase in MainActivity.java for more information about the function
    public void newRandomStory (View view) {
        AssetManager am = mContext.getAssets();
        ArrayList<String> items = new ArrayList<String>();
        try {
            String[] fileList = am.list("");
            for(String name:fileList) {
                if (name.endsWith(".txt")) {
                    items.add(name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = new Random().nextInt(items.size());
        String file = (items.get(index));

        try {
            InputStream is = am.open(file);
            story = new Story(is);
            Intent cleanStory = new Intent(this, FillWords.class);
            Log.d("MainActivity", "Story: " + story);
            Bundle bundle = new Bundle();
            bundle.putSerializable("story", story);
            cleanStory.putExtras(bundle);
            startActivity(cleanStory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
