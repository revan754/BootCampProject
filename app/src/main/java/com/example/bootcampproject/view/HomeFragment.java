package com.example.bootcampproject.view;

import static com.example.bootcampproject.view.MainActivity.dismissProgress;
import static com.example.bootcampproject.view.MainActivity.showProgress;
import static com.example.bootcampproject.view.MainActivity.showToast;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.bootcampproject.R;
import com.example.bootcampproject.databinding.FragmentHomeBinding;
import com.example.bootcampproject.model.CoinsModel;
import com.example.bootcampproject.retrofit.API;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    DecimalFormat formatter = new DecimalFormat("##.##");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProgress();
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
        getCoins();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                logout(view);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);

    }

    private void logout(View view) {
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

    private void getCoins() {

        Call<List<CoinsModel>> call = API.getInstance().getMyApi().getCoins();
        call.enqueue(new Callback<List<CoinsModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<CoinsModel>> call, Response<List<CoinsModel>> response) {
                if (response.isSuccessful()) {
                    String price = "" , coin_name = "";
                    System.out.println("resp" + response.body());
                    int i = response.body() != null ? response.body().size() : 0;
                    while (i > 0) {
                        price = getFormatedPrice(response.body().get(i - 1).getPrice());
                        coin_name = response.body().get(i - 1).getCoin() + " - " + response.body().get(i - 1).getName();
                        if (Objects.equals(response.body().get(i - 1).getCoin(), "ETH")) {
                            binding.ethPrice.setText(price);
                            binding.ethPrice1.setText(price);
                            binding.ethName.setText(coin_name);
                        }
                        if (Objects.equals(response.body().get(i - 1).getCoin(), "2MINERS ETH")) {
                            binding.btcPrice.setText(price);
                            binding.btcPrice1.setText(price);
                            binding.btcName.setText(coin_name);
                        }
                        if (Objects.equals(response.body().get(i - 1).getCoin(), "BTC")) {
                            binding.tnbPrice.setText(price);
                            binding.tnbPrice1.setText(price);
                            binding.tnbName.setText(coin_name);
                        }
                        if (Objects.equals(response.body().get(i - 1).getCoin(), "ANTPOOL BTC")) {
                            binding.engPrice.setText(price);
                            binding.engPrice1.setText(price);
                            binding.engName.setText(coin_name);
                        }
                        i--;
                    }
                    dismissProgress();
                } else {
                    showToast(getContext(), "Verileri çekerken bir hata oluştu");
                    dismissProgress();
                }
            }

            @Override
            public void onFailure(Call<List<CoinsModel>> call, Throwable t) {
                dismissProgress();
                showToast(getContext(), "Bir hata oluştu:" + t.getLocalizedMessage());
            }
        });
    }

    public String getFormatedPrice(Double price)
    {
        return "$" + formatter.format(price);
    }
    public final Context requireContext1() {
        Context context = getContext();
        if (context == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to a context.");
        }
        return context;
    }
}