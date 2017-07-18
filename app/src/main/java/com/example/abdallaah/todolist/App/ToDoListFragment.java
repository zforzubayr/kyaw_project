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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToDoListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ToDoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoListFragment extends Fragment implements
        RecyclerItemClickListener.OnRecyclerClickListener{
    private static final String TAG = "ToDoListFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerViewAdapter recyclerViewAdapter;

    public ToDoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToDoListFragment.
     */
    // TODO: Rename and change types and number of parameters
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
