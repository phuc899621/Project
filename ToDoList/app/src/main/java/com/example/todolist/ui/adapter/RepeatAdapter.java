package com.example.todolist.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.todolist.R;
import com.example.todolist.databinding.ItemPriorityBinding;
import com.example.todolist.databinding.ItemRepeatBinding;

import java.util.List;

public class RepeatAdapter extends ArrayAdapter<String> {
    public RepeatAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
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
            ItemRepeatBinding binding = ItemRepeatBinding.inflate(
                    LayoutInflater.from(getContext()), parent, false);
            convertView = binding.getRoot();
            holder = new ViewHolder(binding);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.binding.tvRepeat.setText(getItem(position));
        return convertView;
    }
    private static class ViewHolder {
        private final ItemRepeatBinding binding;

        ViewHolder(ItemRepeatBinding binding) {
            this.binding = binding;
        }
    }
}
