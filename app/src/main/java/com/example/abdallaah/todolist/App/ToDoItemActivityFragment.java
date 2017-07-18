package com.example.abdallaah.todolist.App;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdallaah.todolist.R;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ToDoItemActivityFragment extends Fragment implements RecyclerItemClickListener.OnRecyclerClickListener{

    public ToDoItemActivityFragment() {
    }


    private static final String TAG = "ToDoItemActivityFrag";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerViewAdapter recyclerViewAdapter;

    private String mParam1;
    private String mParam2;


  

    public static ToDoItemActivityFragment newInstance(String param1, String param2) {
        Log.d(TAG, "newInstance: starts");
        ToDoItemActivityFragment fragment = new ToDoItemActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        Log.d(TAG, "newInstance() returned: " + fragment);
        Log.d(TAG, "newInstance: ends");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_to_do_item, container, false);
        RecyclerView recyclerView =  view.findViewById(R.id.tasks_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //todo use recycleviewadapters load new data

        Controller newController = new Controller();

        Log.d(TAG, "onCreateView: Controller called");
        ArrayList<ToDo> toDoList = newController.getTasksList();

        if(toDoList != null && toDoList.size() > 0){
            Log.d(TAG, "onCreateView: todoList size" +  toDoList.size());
        }
        else{
            Log.d(TAG, "onCreateView:  toDoList empty");
            toDoList.add(new ToDo("You have no tasks", null, null, null));
        }
        recyclerViewAdapter = new RecyclerViewAdapter(toDoList);

        recyclerView.setAdapter(recyclerViewAdapter);


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

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
