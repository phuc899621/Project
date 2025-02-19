package com.example.todolist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todolist.R;
import com.example.todolist.databinding.ActivityDashBoardBinding;
import com.example.todolist.databinding.AddTaskLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DashBoardActivity extends AppCompatActivity {
    ActivityDashBoardBinding binding;
    BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.cvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoardActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        AddTaskLayoutBinding addTaskBinding=AddTaskLayoutBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(addTaskBinding.getRoot());
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetDialog.isShowing()){
                    bottomSheetDialog.dismiss();
                }
                else {
                    bottomSheetDialog.show();
                }
            }
        });


    }
}