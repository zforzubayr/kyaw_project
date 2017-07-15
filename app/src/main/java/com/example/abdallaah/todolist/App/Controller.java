package com.example.abdallaah.todolist.App;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Controller extends LoginActivity{
    private static final String TAG = "Controller";



    public Controller() {

    }

    public void getTasks(){
        Log.d(TAG, "getTasks: starts");

        FirebaseDatabase.getInstance().
                getReference("users").
                child(LoginActivity.current_user.getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        Log.d(TAG, "onDataChange: " +value);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        Log.d(TAG, "getTasks: ends");

    }

    private void setTask(){

    }
}
