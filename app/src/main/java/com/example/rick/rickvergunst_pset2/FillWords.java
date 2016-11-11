package com.example.rick.rickvergunst_pset2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

import static java.lang.Boolean.TRUE;

public class FillWords extends AppCompatActivity {

    // Initialize the variables needed
    Story story;
    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_in_words);

        // Checks for a saved instance and sets the story object to that story
        // if a saved instance is present
        if (savedInstanceState != null) {
            story = (Story) savedInstanceState.getSerializable("story");
        }
        // If no saved instance is available it will check for an intent
        else {
            // Create Intent variable
            Intent actCalled = getIntent();

            // Retrieves data from the intent and initalizes the story object
            Bundle bundle = actCalled.getExtras();
            story = (Story) bundle.getSerializable("story");
        }

        // Fills the app with variables that are defined by the story
        TextView wordsLeft = (TextView) findViewById(R.id.wordsLeftText);
        TextView typeOfWord = (TextView) findViewById(R.id.tipText);
        EditText hintWordText = (EditText) findViewById(R.id.userWords);
        wordsLeft.setText(story.getPlaceholderRemainingCount()+ " " + wordsLeft.getText());
        typeOfWord.append(" " + story.getNextPlaceholder());
        hintWordText.setHint(story.getNextPlaceholder());

        // Makes the editText field findable
        mEdit = (EditText) findViewById(R.id.userWords);
    }

    // Function that creates a saved instance if the orientation changes
    // Uses that story object as data for hte instance
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("story", story);
    }

    // Function that handles a new word that the user send through clicking the button
    public void fillWithUserWord (View view) {

        // Retrieves the users word and replacese the next placeholder inside the story object
        // with that particular word
        String word = mEdit.getText().toString();
        story.fillInPlaceholder(word);

        // Checks if the complete story is filled in
        if (story.isFilledIn() == TRUE) {

            // Creates a new intent with the story object and starts the finalPage activity
            // to get to the final page
            Intent finalPage = new Intent(this, FinalPage.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("story", story);
            finalPage.putExtras(bundle);
            startActivity(finalPage);
        }
        // If more words are present
        else {

            // Creates a new intent and uses this activity as new activity as there are more
            // words to fill in
            Intent newWord = new Intent(this, FillWords.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("story", story);
            newWord.putExtras(bundle);
            startActivity(newWord);
        }
    }

}
