package com.example.abdallaah.todolist.App.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdallaah.todolist.R;


//this fragment displays ToDoItems stored in firebase
public class ToDoItemDetailsFragment extends Fragment {

    //these attributes are displayed
    private static final String ARG_title = "title";
    private static final String ARG_description = "description";
    private static final String ARG_dateCreated = "dateCreated";
    private static final String ARG_dateRemind = "dateRemind";

    private String mTitle;
    private String mDescription;
    private String mDateCreated;
    private String mdateRemind;


    private OnFragmentInteractionListener mListener;

    public ToDoItemDetailsFragment() {
        // Required empty public constructor
    }

    public static ToDoItemDetailsFragment newInstance(
            String title, 
            String description,
            String dateCreated,
            String dateRemind) {
        ToDoItemDetailsFragment fragment = new ToDoItemDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_title, title);
        args.putString(ARG_description, description);
        args.putString(ARG_dateCreated, dateCreated);
        args.putString(ARG_dateRemind, dateRemind);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_title);
            mDescription = getArguments().getString(ARG_description);
            mDateCreated = getArguments().getString(ARG_dateCreated);
            mdateRemind = getArguments().getString(ARG_dateRemind);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //initializing the layout file, where ToDoItem information will be displayed
        View view = inflater.inflate(R.layout.fragment_to_do_item_details, container, false);

        TextView titleText = view.findViewById(R.id.title);
        TextView descriptionText = view.findViewById(R.id.description);
        TextView dateCreatedText = view.findViewById(R.id.date_created);
        TextView dateRemindText = view.findViewById(R.id.date_remind);

        titleText.setText(mTitle);
        descriptionText.setText(mDescription);
        dateCreatedText.setText(getString(R.string.task_crated) + " " + mDateCreated);
        dateRemindText.setText(mdateRemind);

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


    //implementation of fragment interfaces
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
