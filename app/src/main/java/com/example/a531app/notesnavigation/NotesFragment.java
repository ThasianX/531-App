package com.example.a531app.notesnavigation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a531app.R;
import com.example.a531app.utilities.BaseFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends BaseFragment {


    private EditText notes;
    private final String fileName = "liftNotes";

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View providedView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes, container, false);
        notes = view.findViewById(R.id.edit_notes);
        notes.setText(open(fileName));
        return view;
    }

    @Override
    public String actionTitle() {
        return "Personal Notes";
    }

    @Override
    public boolean setBackButton() {
        return false;
    }

    private void save(String fileName){
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getActivity().openFileOutput(fileName, MODE_PRIVATE));
            outputStreamWriter.write(notes.getText().toString());
            outputStreamWriter.close();
        } catch(Throwable t){
            Toast.makeText(getActivity(), "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private String open(String fileName){
        String content = "";
        if(fileExists(fileName)){
            try{
                InputStream in = getActivity().openFileInput(fileName);
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
                Toast.makeText(getActivity(), "Exception: " + throwable.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return content;
    }

    private boolean fileExists(String fileName){
        File file = getActivity().getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    @Override
    public void onDestroyView() {
        save(fileName);
        super.onDestroyView();

    }
}
