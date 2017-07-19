package com.example.abdallaah.todolist.App.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdallaah.todolist.App.Activities.LoginActivity;
import com.example.abdallaah.todolist.App.Model.ToDo;
import com.example.abdallaah.todolist.App.RecyclerItemClickListener;
import com.example.abdallaah.todolist.App.RecyclerViewAdapter;
import com.example.abdallaah.todolist.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ToDoListFragment extends Fragment implements
        RecyclerItemClickListener.OnRecyclerClickListener {
    private static final String TAG = "ToDoListFragment";

    static ArrayList<ToDo> toDoList = null;


    private OnFragmentInteractionListener mListener;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private TextView userName;
    private FirebaseDatabase firebaseDatabase;



    public ToDoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: starts");
        firebaseDatabase = FirebaseDatabase.getInstance();
        View view = inflater.inflate(R.layout.fragment_to_do_item, container, false);

        //set up recycle view for the ToDoItem list

        userName = view.findViewById(R.id.username);
        recyclerView =  view.findViewById(R.id.tasks_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //starting gesture detector
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, this));

        refreshList();
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: inside");
        ToDo toDoItem = toDoList.get(position);

        ToDoItemDetailsFragment toDoItemDetailsFragment
                = ToDoItemDetailsFragment.newInstance(
                        toDoItem.getTitle(),
                        toDoItem.getDescription(),
                        toDoItem.getDateCreated(),
                        toDoItem.getDateRemind()
        );

        getActivity().
                getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, toDoItemDetailsFragment).
                addToBackStack(null).
                commit();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: inside");

        final ToDo toDoItem = toDoList.get(position);

        new AlertDialog.Builder(getContext()).
                setIcon(android.R.drawable.ic_dialog_alert).
                setTitle(R.string.delete).
                setMessage(getString(R.string.are_you_sure)
                        + " " + toDoItem.getTitle() + " " +
                        getString(R.string.from_list)).
                setPositiveButton(getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteToDoItem(toDoItem.getId());
                            }
                        }).
                setNegativeButton(getString(R.string.no), null).show();

    }

    private void refreshList(){

        toDoList = new ArrayList<>();
        firebaseDatabase.
                getReference("users").
                child(LoginActivity.current_user.getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String username = dataSnapshot.child("name").getValue().toString();
                        toDoList = parseDataSnapshot(dataSnapshot.child("todolist"));
                        Log.d(TAG, "onDataChange: todoList" + toDoList);
                        userName.setText(getString(R.string.welcome) + " " + username + "!");
                        recyclerViewAdapter = new RecyclerViewAdapter(toDoList, getContext());
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private void deleteToDoItem(int id){
        toDoList = new ArrayList<>();

        final int access_id = id;
        firebaseDatabase.
                getReference("users").
                child(LoginActivity.current_user.getUid()).
                child("todolist").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child(access_id+"").removeValue();
                        Log.d(TAG, "onDataChange: delete" + dataSnapshot.getRef());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private ArrayList<ToDo> parseDataSnapshot(DataSnapshot dataSnapshot) {
        Log.d(TAG, "parseDataSnapshot: starts");

        ArrayList<ToDo> parseToDoArray = new ArrayList<>();
        for (DataSnapshot item : dataSnapshot.getChildren()) {

            if(item != null){
                JSONObject object = new JSONObject((HashMap<String, String>) item.getValue());

                Log.d(TAG, "parseDataSnapshot: JSONObject" + object);

                try {

                    ToDo toDo = new ToDo(
                            object.get("title").toString(),
                            object.get("dateCreated").toString(),
                            object.get("dateRemind").toString(),
                            object.get("description").toString(),
                            Integer.parseInt(object.get("id").toString())
                    );


                    parseToDoArray.add(toDo);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }
        Log.d(TAG, "parseDataSnapshot: ends");
        Log.d(TAG, "parseDataSnapshot: " + parseToDoArray);
        return parseToDoArray;

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
