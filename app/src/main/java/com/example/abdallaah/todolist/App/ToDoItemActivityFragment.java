package com.example.abdallaah.todolist.App;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdallaah.todolist.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ToDoItemActivityFragment extends Fragment {

    public ToDoItemActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_do_item, container, false);
    }
}
