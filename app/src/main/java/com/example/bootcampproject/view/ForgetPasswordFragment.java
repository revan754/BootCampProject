package com.example.bootcampproject.view;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bootcampproject.R;
import com.example.bootcampproject.databinding.FragmentForgetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPasswordFragment extends Fragment {

    FragmentForgetPasswordBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginHereButton.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.action_forgetPasswordFragment_to_loginFragment));

        binding.resetButton.setOnClickListener(view1 -> {
            if (binding.editEmail.getText().toString().isEmpty())
                Toast.makeText(getContext(), "Lütfen Email Adresinizi Giriniz!", Toast.LENGTH_LONG).show();
            else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(binding.editEmail.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "şifre resetleme linki atıldı", Toast.LENGTH_LONG).show();
                                Navigation.findNavController(view1).navigate(R.id.action_forgetPasswordFragment_to_loginFragment);
                            } else {
                                Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }

        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigateUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);
    }
}
