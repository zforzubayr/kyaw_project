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

        //this fragment shows the recyclerview with the tasks
        ToDoListFragment mainFragment
                = (ToDoListFragment) fragmentManager.findFragmentById(R.id.fragment_container);


        if(mainFragment == null){
            Log.d(TAG, "onCreate: mainFragment null");
            mainFragment = new ToDoListFragment();
            fragmentManager.beginTransaction().
                    add(R.id.fragment_container, mainFragment).
                    commit();
        }

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            loadAddTaskScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //this method starts the fragment for adding new tasks
    private void loadAddTaskScreen(){
        ToDoItemFormFragment toDoItemFormFragment = new ToDoItemFormFragment();
        Log.d(TAG, "loadPickPowerScreen: inside");

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, toDoItemFormFragment).
                addToBackStack(null).
                commit();

    }

    //destroying current user
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