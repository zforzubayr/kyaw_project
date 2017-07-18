package com.example.abdallaah.todolist.App;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller extends LoginActivity{
    private static final String TAG = "Controller";

    private ArrayList<ToDo> toDoList = null;


    public Controller() {
        toDoList = new ArrayList<>();

        FirebaseDatabase.getInstance().
                getReference("users").
                child(LoginActivity.current_user.getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG, "onDataChange: " + dataSnapshot.child("todolist"));
//                        parseDataSnapshot(dataSnapshot);
                        Log.d(TAG, "onDataChange: " + dataSnapshot.getValue().toString());
                    }

//                    {name=zubair, todolist=[{title=new, desc=testing new}, {title=new 2, desc=testing new 2}]}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private void parseDataSnapshot(DataSnapshot dataSnapshot) {
        Log.d(TAG, "parseDataSnapshot: starts");

        for (DataSnapshot item : dataSnapshot.getChildren()) {

            JSONObject object = new JSONObject((HashMap<String, String>) item.getValue());

            try {

                ToDo toDo = new ToDo(
                        object.get("title").toString(),
                        object.get("date_created").toString(),
                        object.get("date_remind").toString(),
                        object.get("description").toString()
                );


                toDoList.add(toDo);

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
        Log.d(TAG, "parseDataSnapshot() returned: " + toDoList);
        Log.d(TAG, "parseDataSnapshot: ends");

    }


    public void getTasksList(){
        Log.d(TAG, "getTasksList: starts");

        Log.d(TAG, "getTasksList: ends");

    }

    private void setTask(){

    }
}
