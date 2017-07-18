package com.example.abdallaah.todolist.App;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.abdallaah.todolist.R;

public class ToDoItemActivity extends AppCompatActivity {
    private static final String TAG = "ToDoItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
       

        ToDoItemActivityFragment mainFragment = (ToDoItemActivityFragment) fragmentManager.findFragmentById(R.id.fragment_container);


        if(mainFragment == null){
            Log.d(TAG, "onCreate: mainFragment null");
            mainFragment = ToDoItemActivityFragment.newInstance("sd", "ad");
            fragmentManager.beginTransaction().
                    add(R.id.fragment_container, mainFragment).
                    commit();
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        Log.d(TAG, "onCreate: ends");
    }

}
