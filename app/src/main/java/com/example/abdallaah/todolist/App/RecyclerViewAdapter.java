package com.example.abdallaah.todolist.App;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdallaah.todolist.App.Model.ToDo;
import com.example.abdallaah.todolist.R;

import java.util.List;

//adapter class for the recycler view
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ToDoHolder> {
    private List<ToDo> mToDoList;
    private Context mContext;

    public RecyclerViewAdapter(List<ToDo> mToDoList, Context context) {
        this.mToDoList = mToDoList;
        this.mContext = context;
    }

    @Override
    public ToDoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ToDoHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoHolder holder, int position) {

        if (mToDoList == null || mToDoList.size() == 0) {
            holder.thumbnail.setImageResource(R.drawable.todo);
            holder.title.setText(R.string.no_todo);
        } else {
            ToDo toDoItem = mToDoList.get(position);
            holder.thumbnail.setImageResource(R.drawable.tasks);
            holder.title.setText(toDoItem.getTitle());
            String remindData = toDoItem.getDateRemind();
            if (remindData.length() > 7) {
                remindData = "Reminder set on " + " " + remindData;
            } else {
                remindData = "No reminder set";
            }
            holder.dateRemind.setText(remindData);

        }

    }

    @Override
    public int getItemCount() {
        return ((mToDoList != null) && (mToDoList.size() > 0) ? mToDoList.size() : 0);
    }

    //holder class for recycler view
    static class ToDoHolder extends RecyclerView.ViewHolder {
        TextView title = null;
        TextView dateCreated = null;
        TextView dateRemind = null;
        ImageView thumbnail = null;

        public ToDoHolder(View itemView) {
            super(itemView);
            this.thumbnail = itemView.findViewById(R.id.thumbnail);
            this.title = itemView.findViewById(R.id.title);
            this.dateCreated = itemView.findViewById(R.id.date_created);
            this.dateRemind = itemView.findViewById(R.id.date_remind);
        }
    }
}

