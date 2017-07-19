package com.example.abdallaah.todolist.App;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller extends LoginActivity{
    private static final String TAG = "Controller";
    private ArrayList<ToDo> toDoControllerList;

    public Controller() {
        this.toDoControllerList = new ArrayList<>();
    }

    private void parseDataSnapshot(DataSnapshot dataSnapshot) {
        Log.d(TAG, "parseDataSnapshot: starts");


        for (DataSnapshot item : dataSnapshot.getChildren()) {

            JSONObject object = new JSONObject((HashMap<String, String>) item.getValue());

            Log.d(TAG, "parseDataSnapshot: JSONObject" + object);

            try {

                ToDo toDo = new ToDo(
                        object.get("title").toString(),
                        object.get("dateCreated").toString(),
                        object.get("dateRemind").toString(),
                        object.get("description").toString()
                );


                toDoControllerList.add(toDo);

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
        Log.d(TAG, "parseDataSnapshot: ends");

    }


    public ArrayList<ToDo> getTasksList(){
        Log.d(TAG, "getTasksList: starts");

        FirebaseDatabase.getInstance().
                getReference("users").
                child(LoginActivity.current_user.getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        parseDataSnapshot(dataSnapshot.child("todolist"));
                    }

//                    {name=zubair, todolist=[{title=new, desc=testing new}, {title=new 2, desc=testing new 2}]}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        Log.d(TAG, "getTasksList: ends" + toDoControllerList);

        return toDoControllerList;

    }

    public void setTask(ToDo todo){
//
//        int task_id =  0;
//        if(ToDoListFragment.getToDoListSize() > 0){
//            task_id = ToDoListFragment.getToDoListSize() - 1;
//        }
//        FirebaseDatabase.getInstance().
//                getReference("users").
//                child(current_user.getUid()).
//                child("todolist").child(Integer.toString(task_id)).setValue(todo);

    }
}
