package com.example.rick.rickvergunst_pset2;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    // Define the context as well as the main story variable
    private Context mContext;
    public Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make context, the context of this particular page
        mContext = this;
    }

    // Function that handles the intent to the new activity
    protected  void goToSecondPhase(View view) {

        // Creates the asset manager with the context of this instance
        AssetManager am = mContext.getAssets();

        //Creates an array to hold the files of assets
        ArrayList<String> items = new ArrayList<String>();

        try {
            //Create a list of the files inside the assets folder
            String[] fileList = am.list("");

            // Loops through the files
            for(String name:fileList) {
                // Filter the files that do not end with .txt and adds the files that do end with .txt to the array
                if (name.endsWith(".txt")) {
                    items.add(name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Generate a random int within the range of the array
        int index = new Random().nextInt(items.size());

        // Selects a random file within the array using the generated random index number
        String file = (items.get(index));

        try {
            //Opens an input stream with the randomly chosen file
            InputStream is = am.open(file);

            // Create a new story object with the generated input stream
            story = new Story(is);

            // Create a new intent to go to the next activity
            Intent cleanStory = new Intent(this, FillWords.class);

            // Create a bundle with the story object and adds that bundle to the intent
            Bundle bundle = new Bundle();
            bundle.putSerializable("story", story);
            cleanStory.putExtras(bundle);

            // Starts the new activity
            startActivity(cleanStory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
