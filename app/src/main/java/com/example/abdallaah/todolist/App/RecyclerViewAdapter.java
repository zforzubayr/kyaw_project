package com.example.abdallaah.todolist.App;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdallaah.todolist.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ToDoHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private List<ToDo> mToDoList;
    private Context mContext;

    public RecyclerViewAdapter(List<ToDo> mToDoList) {
        this.mToDoList = mToDoList;
    }

    @Override

    public ToDoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ToDoHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoHolder holder, int position) {
        ToDo toDoItem = mToDoList.get(position);
        holder.title.setText(toDoItem.getTitle());
        holder.dateCreated.setText(toDoItem.getDateCreated());
        holder.dateRemind.setText(toDoItem.getDateRemind());
    }

    @Override
    public int getItemCount() {
        return ((mToDoList != null )&&(mToDoList.size() > 0) ? mToDoList.size() : 0);
    }

    void loadNewData(List<ToDo> newList){
        mToDoList = newList;
        notifyDataSetChanged();
    }

    public ToDo getToDoItem(int position){
       return ((mToDoList != null )&&(mToDoList.size() > 0) ? mToDoList.get(position) : null);

    }

    static class ToDoHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "ToDoHolder";
        TextView title = null;
        TextView dateCreated = null;
        TextView dateRemind = null;

        public ToDoHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.dateCreated = itemView.findViewById(R.id.date_created);
            this.dateRemind = itemView.findViewById(R.id.date_remind);
        }
    }
}

