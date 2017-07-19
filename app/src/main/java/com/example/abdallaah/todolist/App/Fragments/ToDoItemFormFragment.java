package com.example.abdallaah.todolist.App.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.abdallaah.todolist.App.Activities.LoginActivity;
import com.example.abdallaah.todolist.App.Model.ToDo;
import com.example.abdallaah.todolist.R;
import com.google.firebase.database.FirebaseDatabase;

//this fragment is used for creating new ToDoItems
public class ToDoItemFormFragment extends Fragment {
    private static final String TAG = "ToDoItemFormFragment";

    private OnFragmentInteractionListener mListener;
    private EditText title;
    private EditText toDoDescription;
    private EditText remind;
    private FloatingActionButton save;


    public ToDoItemFormFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                        //title and description set as required fields
                        title.setError(null);
                        toDoDescription.setError(null);

                        String titleText = title.getText().toString();
                        String descriptionText = toDoDescription.getText().toString();
                        String remindText = remind.getText().toString();
                        String formattedText = "";

                        boolean cancel = false;
                        View focusView = null;

                        // Check if title is empty
                        if (TextUtils.isEmpty(titleText)) {
                            title.setError(getString(R.string.error_field_required));
                            focusView = title;
                            cancel = true;
                        }

                        //check if description is empty
                        else  if (TextUtils.isEmpty(descriptionText)) {
                            toDoDescription.setError(getString(R.string.error_field_required));
                            focusView = toDoDescription;
                            cancel = true;
                        }

                        else if(!TextUtils.isEmpty(remindText) && remindText.length() != 8){
                            remind.setError(getString(R.string.date_format_incorrect));
                            focusView = toDoDescription;
                            cancel = true;
                        }



                        //if any one of those two fields are empty, cancel save and point out errors
                        if (cancel) {
                            focusView.requestFocus();
                        }
                        //title or description was not empty
                        else {
                            //saving function
                            if(remindText.length() == 8) {
                                formattedText = remindText.substring(0, 2) + "/" + remindText.substring(2, 4) + "/" + remindText.substring(4, 8);
                            }
                            save_task(titleText, descriptionText, formattedText);
                        }
                    }
                }
        );

        return view;
    }

    private void save_task(String title, String remind, String description){
        //giving id to ToDoItems based on the last highest digits available in the fire base which will give unique ids to each task
        int id;
        try {
            id = (ToDoListFragment.toDoList.get(ToDoListFragment.toDoList.size() - 1)).getId() + 1;

        }
        //this scenario only appears when user has deleted all toDoItems so 0 is a valid number
        catch (Exception e){
           id = 0;
        }

        //create the new ToDoItem
        ToDo newToDo = new ToDo(title, null, description, remind, id);

        //save in firebase
        FirebaseDatabase.getInstance().
                getReference("users").
                child(LoginActivity.current_user.getUid()).
                child("todolist").child(Integer.toString(id)).setValue(newToDo);

        //after saving go back to the ToDoItem lists
        getFragmentManager().popBackStack();
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
