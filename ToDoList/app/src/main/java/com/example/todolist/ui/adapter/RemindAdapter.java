package com.example.todolist.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.databinding.ItemRepeatBinding;

import java.util.List;

public class RemindAdapter extends ArrayAdapter<String> {
    public RemindAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
    }

    public void updateItems(List<String> items) {
        clear();
        addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RemindAdapter.ViewHolder holder;
        if (convertView == null) {
            ItemRepeatBinding binding = ItemRepeatBinding.inflate(
                    LayoutInflater.from(getContext()), parent, false);
            convertView = binding.getRoot();
            holder = new RemindAdapter.ViewHolder(binding);
            convertView.setTag(holder);
        }else {
            holder = (RemindAdapter.ViewHolder) convertView.getTag();
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
