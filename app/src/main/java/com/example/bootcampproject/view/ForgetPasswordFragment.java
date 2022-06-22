package com.example.bootcampproject.view;

import static com.example.bootcampproject.view.MainActivity.showToast;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootcampproject.R;
import com.example.bootcampproject.databinding.FragmentForgetPasswordBinding;
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
            // Geçerli bir email adresi girilip girilmediğini kontrol et
            if (checkEmail(binding.editEmail.getText().toString())) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(binding.editEmail.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                showToast(getContext(), "Mail'inize Şifre Yenileme Linki Gönderildi");
                                Navigation.findNavController(view1).navigate(R.id.action_forgetPasswordFragment_to_loginFragment);
                            } else {
                                showToast(getContext(), task.getException().getLocalizedMessage());
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

    public boolean checkEmail(String emailInput) {
        if (!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return true;
        } else {
            showToast(getContext(), "Lütfen Geçerli Bir Email Adresi Giriniz!");
            return false;
        }
    }

}
