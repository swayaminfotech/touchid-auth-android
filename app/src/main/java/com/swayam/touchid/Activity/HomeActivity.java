package com.swayam.touchid.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.swayam.touchid.R;
import com.swayam.touchid.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    //Binding View
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}