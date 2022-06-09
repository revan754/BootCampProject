package com.example.bootcampproject.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bootcampproject.R;
import com.example.bootcampproject.databinding.FragmentHomeBinding;
import com.example.bootcampproject.model.CoinsModel;
import com.example.bootcampproject.service.API;
import com.example.bootcampproject.service.RestInterface;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                gotoLogin(view);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);

    }

    private void gotoLogin(View view) {
        new AlertDialog.Builder(requireContext1())
                .setTitle("Uyarı")
                .setMessage("Uygulamadan çıkmak istediğinizden emin misiniz?")
                .setPositiveButton("Evet", (dialog, which) -> {

                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment);
                })
                .setNegativeButton("Iptal", null)
                .show();
    }
    private void getData() {

        Call<List<CoinsModel>> call = API.getInstance().getMyApi().getCoins();
        call.enqueue(new Callback<List<CoinsModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<CoinsModel>> call, Response<List<CoinsModel>> response) {

                System.out.println("resp" + response.body());
                if (response.isSuccessful()) {
                    int i = response.body() != null ? response.body().size() : 0;
                    while (i > 0) {
                        if (Objects.equals(response.body().get(i - 1).coin, "ETH")) {
                            binding.ethPrice.setText(String.valueOf(response.body().get(i - 1).price));
                            binding.ethPrice1.setText(String.valueOf(response.body().get(i - 1).price));
                            binding.ethName.setText(response.body().get(i - 1).coin + " - " + response.body().get(i - 1).name);
                        }
                        if (Objects.equals(response.body().get(i - 1).coin, "BTC")) {
                            binding.btcPrice.setText(String.valueOf(response.body().get(i - 1).price));
                            binding.btcPrice1.setText(String.valueOf(response.body().get(i - 1).price));
                            binding.btcName.setText(response.body().get(i - 1).coin + " - " + response.body().get(i - 1).name);
                        }
                        if (Objects.equals(response.body().get(i - 1).coin, "TNB")) {
                            binding.tnbPrice.setText(String.valueOf(response.body().get(i - 1).price));
                            binding.tnbPrice1.setText(String.valueOf(response.body().get(i - 1).price));
                            binding.tnbName.setText(response.body().get(i - 1).coin + " - " + response.body().get(i - 1).name);
                        }
                        if (Objects.equals(response.body().get(i - 1).coin, "ENG")) {
                            binding.engPrice.setText(String.valueOf(response.body().get(i - 1).price));
                            binding.engPrice1.setText(String.valueOf(response.body().get(i - 1).price));
                            binding.engName.setText(response.body().get(i - 1).coin + " - " + response.body().get(i - 1).name);
                        }
                        i--;

                    }
                }
            }

            @Override
            public void onFailure(Call<List<CoinsModel>> call, Throwable t) {
                Toast.makeText(getContext(),"Bir hata oluştu:"+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public final Context requireContext1() {
        Context context = getContext();
        if (context == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to a context.");
        }
        return context;
    }
}