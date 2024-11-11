package com.example.notesappruslan;// MainActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ListView notesListView;
    private String[] noteFiles;

    private static final int ADD_NOTE_REQUEST = 1;
    private static final int DELETE_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesListView = findViewById(R.id.notesListView);

        // Load notes from internal storage
        loadNotes();

        // Set up the ListView adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteFiles);
        notesListView.setAdapter(adapter);

        // Handle item click to view note content
        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            String noteFileName = noteFiles[position];
            openNoteContent(noteFileName);
        });
    }

    private void loadNotes() {
        // Load all note files from internal storage
        File filesDir = getFilesDir();
        File[] files = filesDir.listFiles();

        if (files != null && files.length > 0) {
            noteFiles = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                noteFiles[i] = files[i].getName().replace(".txt", "");
            }
        } else {
            noteFiles = new String[0]; // No notes found
        }
    }

    private void openNoteContent(String noteFileName) {
        // Read the content of the note from internal storage
        try {
            FileInputStream fis = openFileInput(noteFileName + ".txt");
            int character;
            StringBuilder noteContent = new StringBuilder();
            while ((character = fis.read()) != -1) {
                noteContent.append((char) character);
            }
            fis.close();

            // Pass the note content to a new Activity and display it
            Intent intent = new Intent(this, ViewNoteActivity.class);
            intent.putExtra("noteTitle", noteFileName); // Pass note title
            intent.putExtra("noteContent", noteContent.toString()); // Pass note content
            startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.error_loading_note), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_note) {
            // Start AddNoteActivity
            Intent intent = new Intent(this, AddNoteActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
            return true;
        } else if (id == R.id.delete_note) {
            // Start DeleteNoteActivity
            startActivityForResult(new Intent(this, DeleteNoteActivity.class), DELETE_NOTE_REQUEST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == ADD_NOTE_REQUEST || requestCode == DELETE_NOTE_REQUEST) && resultCode == RESULT_OK) {
            // Refresh the list of notes after adding or deleting a note
            loadNotes();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteFiles);
            notesListView.setAdapter(adapter);
        }
    }
}
