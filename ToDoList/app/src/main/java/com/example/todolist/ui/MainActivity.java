package com.example.todolist.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.todolist.R;
import com.example.todolist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String HomeTag = "HomeFragment";
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, v.getPaddingBottom() +32 - systemBars.bottom );
            return insets;
        });

        binding.bnvBottomNavigation.setBackground(null);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        transaction.replace(R.id.fragmentLayout, new HomeFragment());

        transaction.addToBackStack(null);

        transaction.commit();
    }

}