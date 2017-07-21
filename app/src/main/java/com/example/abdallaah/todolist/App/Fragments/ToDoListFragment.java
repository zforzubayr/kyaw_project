package com.example.abdallaah.todolist.App.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
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

//this fragment is used to show the list of ToDoItems
//implementing RecyclerItemClickListener.OnRecyclerClickListener for gesture detector

public class ToDoListFragment extends Fragment implements
        RecyclerItemClickListener.OnRecyclerClickListener {
    private static final String TAG = "ToDoListFragment";

    static ArrayList<ToDo> toDoList = null;

    private OnFragmentInteractionListener mListener;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private TextView userName;
    private FirebaseDatabase firebaseDatabase;
    private ConstraintLayout listConstraintLayout;


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
        listConstraintLayout = view.findViewById(R.id.listConstraintLayout);
        //set up recycle view for the ToDoItem list
        userName = view.findViewById(R.id.username);
        recyclerView = view.findViewById(R.id.tasks_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //starting gesture detector
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, this));

        //call this method to update the list
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
    //onItemClick is used for viewing ToDoItem details
    public void onItemClick(View view, int position) {

        //find the ToDoItem from the ArrayList
        ToDo toDoItem = toDoList.get(position);

        //get all information for the ToDoItemDetailsFragment
        ToDoItemDetailsFragment toDoItemDetailsFragment
                = ToDoItemDetailsFragment.newInstance(
                toDoItem.getTitle(),
                toDoItem.getDescription(),
                toDoItem.getDateCreated(),
                toDoItem.getDateRemind()
        );

        //go to the ToDoItemDetailsFragment
        getActivity().
                getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, toDoItemDetailsFragment).
                addToBackStack(null).
                commit();
    }

    //onItemLongClick is used for deleting a ToDoItem from the list
    @Override
    public void onItemLongClick(View view, int position) {

        //find the ToDoItem from the ArrayList
        final ToDo toDoItem = toDoList.get(position);

        //verify with the user, about the deleting request
        //if yes then call the deleteToDoItem method
        //else cancel the alert
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

    //method used for updating the list
    private void refreshList() {
        toDoList = new ArrayList<>();
        firebaseDatabase.
                getReference("users").
                child(LoginActivity.current_user.getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //finding the username for welcoming on the list screen
                        String username = dataSnapshot.child("name").getValue().toString();

                        //update the list by parsing the datasnapshot using parseDataSnapshot function
                        toDoList = parseDataSnapshot(dataSnapshot.child("todolist"));

                        userName.setText(getString(R.string.welcome) + " " + username + "!");

                        //update the adapter
                        recyclerViewAdapter = new RecyclerViewAdapter(toDoList, getContext());
                        recyclerView.setAdapter(recyclerViewAdapter);

                        if(toDoList.size() == 0){
                            Snackbar.make(
                                    listConstraintLayout,
                                    R.string.no_items_list,
                                    Snackbar.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    //this method deletes a ToDoItem based on the id
    private void deleteToDoItem(int id) {

        //requires a final variable for inner class access
        final int access_id = id;

        firebaseDatabase.
                getReference("users").
                child(LoginActivity.current_user.getUid()).
                child("todolist").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //delete the ToDoItem based on id
                        dataSnapshot.getRef().child(access_id + "").removeValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    //this method is used for parsing the datasnapshot
    private ArrayList<ToDo> parseDataSnapshot(DataSnapshot dataSnapshot) {

        ArrayList<ToDo> parseToDoArray = new ArrayList<>();
        for (DataSnapshot item : dataSnapshot.getChildren()) {

            if (item != null) {
                JSONObject object = new JSONObject((HashMap<String, String>) item.getValue());

                try {
                    //creating new ToDoItem
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
