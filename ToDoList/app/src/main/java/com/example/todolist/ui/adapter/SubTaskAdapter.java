package com.example.todolist.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Collections;
import java.util.List;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder>{
    private List<String> subTasks;
    private OnItemClickListener onItemClickListener;
    private OnTextChangeListener onTextChangeListener;
    private OnItemMoveListener onItemMoveListener;

    public SubTaskAdapter(
            List<String> subTasks,
            OnItemClickListener onItemClickListener,
            OnTextChangeListener onTextChangeListener,
            OnItemMoveListener onItemMoveListener){
        this.subTasks=subTasks;
        this.onItemClickListener=onItemClickListener;
        this.onTextChangeListener=onTextChangeListener;
        this.onItemMoveListener=onItemMoveListener;
    }


    public List<String> getSubTasks() {
        return subTasks;
    }
    public void updateData(List<String> subTasks){
        this.subTasks.clear();
        this.subTasks.addAll(subTasks);
        notifyDataSetChanged();
    }
    public void moveItem(int fromPosition, int toPosition){
        Collections.swap(subTasks,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        notifyItemChanged(fromPosition,"UPDATE");
        notifyItemChanged(toPosition,"UPDATE");
    }
    public OnItemMoveListener getOnItemMoveListener() {
        return onItemMoveListener;
    }

    @NonNull
    @Override
    public SubTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sub_task,parent,false);
        return new SubTaskAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskAdapter.ViewHolder holder, int position) {
        holder.tieSubTask.setText(subTasks.get(position));
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            holder.tieSubTask.setText(subTasks.get(position));
            holder.ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            });
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return subTasks.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextInputEditText tieSubTask;
        ImageView ivRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tieSubTask=itemView.findViewById(R.id.tieSubtask);
            ivRemove=itemView.findViewById(R.id.ivClose);
            tieSubTask.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        subTasks.set(position, charSequence.toString());
                        onTextChangeListener.onTextChange(charSequence.toString(),position);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public interface OnTextChangeListener{
        void onTextChange(String s,int position);
    }
    public interface OnItemMoveListener{
        void onItemMove(int fromPosition, int toPosition);
    }
}
