package com.example.abdallaah.todolist.App;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        RecyclerItemClickListener.OnRecyclerClickListener{
    private static final String TAG = "ToDoListFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ArrayList<ToDo> toDoList = null;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private  Controller newController;



    public ToDoListFragment() {
        // Required empty public constructor
    }

    public static ToDoListFragment newInstance(String param1, String param2) {
        ToDoListFragment fragment = new ToDoListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_to_do_item, container, false);
        recyclerView =  view.findViewById(R.id.tasks_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshList();

//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        StationsFragment stationsFragment1;
//        StationsFragment stationsFragment2;
//        StationsFragment stationsFragment3;
//
//        stationsFragment1 = StationsFragment.newInstance(StationsFragment.STATION_TYPE_FEATURED);
//        fragmentManager.beginTransaction().add(R.id.container_top_row, stationsFragment1).commit();
//
//        stationsFragment2 = StationsFragment.newInstance(StationsFragment.STATION_TYPE_PARTY);
//        fragmentManager.beginTransaction().add(R.id.container_middle_row, stationsFragment2).commit();
//
//        stationsFragment3 = StationsFragment.newInstance(StationsFragment.STATION_TYPE_RECENT);
//        fragmentManager.beginTransaction().add(R.id.container_bottom_row, stationsFragment3).commit();
//
//        Log.d(TAG, "onCreateView: ends");
//
//        Log.d(TAG, "onCreateView() returned: " + view);
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        refreshList();
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private void refreshList(){
        //just instantiate the controller

        toDoList = new ArrayList<>();

        FirebaseDatabase.getInstance().
                getReference("users").
                child(LoginActivity.current_user.getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        toDoList = parseDataSnapshot(dataSnapshot.child("todolist"));
                        Log.d(TAG, "onDataChange: todoList" + toDoList);
                        recyclerViewAdapter = new RecyclerViewAdapter(toDoList);

                        recyclerView.setAdapter(recyclerViewAdapter);
                    }

//                    {name=zubair, todolist=[{title=new, desc=testing new}, {title=new 2, desc=testing new 2}]}

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

            JSONObject object = new JSONObject((HashMap<String, String>) item.getValue());

            Log.d(TAG, "parseDataSnapshot: JSONObject" + object);

            try {

                ToDo toDo = new ToDo(
                        object.get("title").toString(),
                        object.get("dateCreated").toString(),
                        object.get("dateRemind").toString(),
                        object.get("description").toString()
                );


                parseToDoArray.add(toDo);

            } catch (JSONException e) {

                e.printStackTrace();
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}