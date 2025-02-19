package com.example.todolist.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<String> items;
    private int selectedItem=RecyclerView.NO_POSITION;//-1
    private String selectedName;

    public CategoryAdapter(List<String> items) {
        this.items = items;
        selectedName="";
    }

    public String getSelectedName() {
        if(selectedName.isEmpty()){
            return "";
        }
        else return selectedName;
    }
    public void updateData(List<String> items){
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category,parent,false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        holder.btnItems.setText(items.get(position));
        if (selectedItem==position){
            holder.btnItems.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.green));
            holder.btnItems.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.white));
        }else {
            holder.btnItems.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.white));
            holder.btnItems.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.grey));
        }
        holder.btnItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) return;
                selectedItem=holder.getAdapterPosition();
                selectedName=holder.btnItems.getText().toString().trim();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        MaterialButton btnItems;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnItems=itemView.findViewById(R.id.mbtnItem);
        }
    }

}
