package com.example.a531app.notesnavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a531app.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Notes extends AppCompatActivity {

    private EditText notes;
//    private String date;
    private final String fileName = "liftNotes";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);

//        date = getIntent().getStringExtra("date");
        notes = findViewById(R.id.edit_notes);
        notes.setText(open(fileName));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notesmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_done){
            save(fileName);
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save(String fileName){
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE));
            outputStreamWriter.write(notes.getText().toString());
            outputStreamWriter.close();
            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        } catch(Throwable t){
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private String open(String fileName){
        String content = "";
        if(fileExists(fileName)){
            try{
                InputStream in = openFileInput(fileName);
                if(in!=null){
                    InputStreamReader isreader = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(isreader);
                    String str;
                    StringBuilder buf = new StringBuilder();
                    while((str=reader.readLine()) != null){
                        buf.append(str);
                    }
                    in.close();
                    content = buf.toString();
                }
            } catch(FileNotFoundException e){

            } catch(Throwable throwable){
                Toast.makeText(this, "Exception: " + throwable.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return content;
    }

    private boolean fileExists(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }
}
