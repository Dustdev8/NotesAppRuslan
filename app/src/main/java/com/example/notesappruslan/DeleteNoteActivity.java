package com.example.notesappruslan;// DeleteNoteActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class DeleteNoteActivity extends AppCompatActivity {

    private ListView notesListView;
    private String[] noteFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        notesListView = findViewById(R.id.notesListView);

        // Load notes from internal storage
        loadNotes();

        // Set up the ListView adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteFiles);
        notesListView.setAdapter(adapter);

        // Handle item click to delete a note
        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            String noteFileName = noteFiles[position];
            deleteNoteFile(noteFileName);
            Toast.makeText(this, "Note deleted: " + noteFileName, Toast.LENGTH_SHORT).show();

            // Notify MainActivity to refresh the list of notes
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);

            // Close DeleteNoteActivity and return to MainActivity
            finish();
        });
    }

    private void loadNotes() {
        File filesDir = getFilesDir();
        File[] files = filesDir.listFiles();

        if (files != null && files.length > 0) {
            noteFiles = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                noteFiles[i] = files[i].getName().replace(".txt", "");
            }
        } else {
            noteFiles = new String[0];
        }
    }

    private void deleteNoteFile(String noteFileName) {
        File file = new File(getFilesDir(), noteFileName + ".txt");
        if (file.exists()) {
            file.delete();
        }
    }
}
