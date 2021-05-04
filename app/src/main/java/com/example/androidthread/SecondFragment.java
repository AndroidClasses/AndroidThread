package com.example.androidthread;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidthread.databinding.FragmentSecondBinding;
import com.example.multilog.MultiLogger;

public class SecondFragment extends Fragment implements MultiLogger.Callback {

    private FragmentSecondBinding binding;

    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        new MultiLogger(3, this).run();
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void trigger(String name, String value) {
        stringBuilder.append(name).append(":").append(value).append("\n");
        applyTextViewLabel(binding.textviewSecond, stringBuilder.toString());
    }

    private synchronized void applyTextViewLabel(TextView textView, String label) {
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(label);
            }
        });
    }
}