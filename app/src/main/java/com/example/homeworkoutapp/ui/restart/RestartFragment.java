package com.example.homeworkoutapp.ui.restart;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.homeworkoutapp.databinding.FragmentRestartBinding;

public class RestartFragment extends Fragment {

    private RestartViewModel restartViewModel;
    private FragmentRestartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        restartViewModel =
                new ViewModelProvider(this).get(RestartViewModel.class);

        binding = FragmentRestartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRestart;
        restartViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}