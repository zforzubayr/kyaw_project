package com.example.abdallaah.todolist.App;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.abdallaah.todolist.R;

public class ToDoItemActivity extends AppCompatActivity implements 
ToDoListFragment.OnFragmentInteractionListener,
ToDoItemFormFragment.OnFragmentInteractionListener{
    private static final String TAG = "ToDoItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
       

        ToDoListFragment mainFragment
                = (ToDoListFragment) fragmentManager.findFragmentById(R.id.fragment_container);


        if(mainFragment == null){
            Log.d(TAG, "onCreate: mainFragment null");
            mainFragment = ToDoListFragment.newInstance("sd", "ad");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do_item, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.add_todo){
            Log.d(TAG, "onOptionsItemSelected: clicked");
            loadAddTaskScreen();
//            Intent intent = new Intent(this, SearchActivity.class);
//            startActivity(intent);
            return true;
        }
        Log.d(TAG, "onOptionsItemSelected() returned: options value so can't return the default true");
        return super.onOptionsItemSelected(item);
    }



    private void loadAddTaskScreen(){
        ToDoItemFormFragment toDoItemFormFragment = new ToDoItemFormFragment();
        Log.d(TAG, "loadPickPowerScreen: inside");
        //this is for replacing
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, toDoItemFormFragment).
                addToBackStack(null).
                commit();

    }


    public void loadToDoListScreen(){
        Log.d(TAG, "loadBackStoryScreen: in");
        ToDoListFragment toDoListFragment = new ToDoListFragment();
        //this is for replacing
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, toDoListFragment).
                addToBackStack(null).
                commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}