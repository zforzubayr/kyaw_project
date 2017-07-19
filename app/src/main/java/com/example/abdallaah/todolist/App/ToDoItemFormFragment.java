package com.example.abdallaah.todolist.App;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.abdallaah.todolist.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToDoItemFormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ToDoItemFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoItemFormFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText title;
    private EditText toDoDescription;
    private EditText remind;
    private FloatingActionButton save;


    public ToDoItemFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToDoItemFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToDoItemFormFragment newInstance(String param1, String param2) {
        ToDoItemFormFragment fragment = new ToDoItemFormFragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_to_do_item_form, container, false);

        title = view.findViewById(R.id.title);
        toDoDescription = view.findViewById(R.id.description);
        remind = view.findViewById(R.id.date_remind);
        save = view.findViewById(R.id.save_task);


        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        title.setError(null);
                        toDoDescription.setError(null);

                        final String titleText = title.getText().toString();
                        final String descriptionText = toDoDescription.getText().toString();
                        final String remindText = remind.getText().toString();

                        boolean cancel = false;
                        View focusView = null;

                        // Check for a valid email address.
                        if (TextUtils.isEmpty(titleText)) {
                            title.setError(getString(R.string.error_field_required));
                            focusView = title;
                            cancel = true;
                        }

                        else  if (TextUtils.isEmpty(descriptionText)) {
                            toDoDescription.setError(getString(R.string.error_field_required));
                            focusView = toDoDescription;
                            cancel = true;
                        }

                        if (cancel) {
                            focusView.requestFocus();
                        }
                        else {
                            save_task(titleText, descriptionText, remindText);

                        }
                    }
                }
        );

        return view;
    }

    private void save_task(String title, String remind, String description){

        ToDo newToDo = new ToDo(title, null, description, remind);

        Controller newController = new Controller();
        newController.setTask(newToDo);
        getFragmentManager().popBackStack();


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
