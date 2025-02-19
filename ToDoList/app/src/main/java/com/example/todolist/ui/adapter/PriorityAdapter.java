package com.example.todolist.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.todolist.R;
import com.example.todolist.databinding.ItemPriorityBinding;

import java.util.List;

public class PriorityAdapter extends ArrayAdapter<String> {


    public PriorityAdapter(@NonNull Context context, List<String> priorityItems) {
        super(context,0, priorityItems);
    }
    public void updateData(List<String> newData) {
        clear();
        addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            ItemPriorityBinding binding = ItemPriorityBinding.inflate(
                    LayoutInflater.from(getContext()), parent, false);
            convertView = binding.getRoot();
            holder = new ViewHolder(binding);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.binding.tvPriority.setText(getItem(position));
        switch (getItem(position)) {
            case "High":
                holder.binding.tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                holder.binding.ivPriority.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.red)
                ));
                break;
            case "Medium":
                holder.binding.tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
                holder.binding.ivPriority.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.orange)
                ));
                break;
            case "Low":
                holder.binding.tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                holder.binding.ivPriority.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.green)
                ));
                break;
            case "None":
                holder.binding.tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                holder.binding.ivPriority.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.grey)
                ));
                break;
            default:break;
        }
        return convertView;
    }
    private static class ViewHolder{
        private final ItemPriorityBinding binding;
        ViewHolder(ItemPriorityBinding binding){
            this.binding=binding;
        }
    }
}
