package com.example.notesappruslan;// ViewNoteActivity.java
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {

    private TextView noteTitleTextView;
    private TextView noteContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        noteTitleTextView = findViewById(R.id.noteTitleTextView);
        noteContentTextView = findViewById(R.id.noteContentTextView);

        // Get the note title and content from the Intent
        String noteTitle = getIntent().getStringExtra("noteTitle");
        String noteContent = getIntent().getStringExtra("noteContent");

        // Set the title and content to the TextViews
        noteTitleTextView.setText(noteTitle);
        noteContentTextView.setText(noteContent);
    }
}
