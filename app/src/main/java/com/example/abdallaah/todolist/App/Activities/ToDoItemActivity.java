package com.example.abdallaah.todolist.App.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.abdallaah.todolist.App.Fragments.ToDoItemDetailsFragment;
import com.example.abdallaah.todolist.App.Fragments.ToDoItemFormFragment;
import com.example.abdallaah.todolist.App.Fragments.ToDoListFragment;
import com.example.abdallaah.todolist.R;
import com.google.firebase.auth.FirebaseAuth;

public class ToDoItemActivity extends AppCompatActivity implements 
    ToDoListFragment.OnFragmentInteractionListener,
    ToDoItemFormFragment.OnFragmentInteractionListener,
    ToDoItemDetailsFragment.OnFragmentInteractionListener{
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
            mainFragment = ToDoListFragment.newInstance("", "");
            fragmentManager.beginTransaction().
                    add(R.id.fragment_container, mainFragment).
                    commit();
        }

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

        int id = item.getItemId();

        if (id == R.id.sign_out) {
            signOut();
            return true;
        }
        if(id == R.id.add_todo){
            Log.d(TAG, "onOptionsItemSelected: clicked");
            loadAddTaskScreen();
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

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        LoginActivity.current_user = null;
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}