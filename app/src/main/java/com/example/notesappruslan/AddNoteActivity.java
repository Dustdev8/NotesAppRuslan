package com.example.notesappruslan;// AddNoteActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class AddNoteActivity extends AppCompatActivity {

    private EditText noteNameEditText, noteContentEditText;
    private Button saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteNameEditText = findViewById(R.id.noteNameEditText);
        noteContentEditText = findViewById(R.id.noteContentEditText);
        saveNoteButton = findViewById(R.id.saveNoteButton);

        saveNoteButton.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String noteTitle = noteNameEditText.getText().toString().trim();
        String noteContent = noteContentEditText.getText().toString().trim();

        if (noteTitle.isEmpty() || noteContent.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_note_title), Toast.LENGTH_SHORT).show();
        } else {
            try (FileOutputStream fos = openFileOutput(noteTitle + ".txt", MODE_PRIVATE)) {
                fos.write(noteContent.getBytes());
                Toast.makeText(this, getString(R.string.note_saved_successfully), Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, getString(R.string.error_loading_note), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
