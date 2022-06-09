package com.example.bootcampproject.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bootcampproject.R;
import com.example.bootcampproject.databinding.FragmentLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private static final int RC_SIGN_IN = 9001;
    public FragmentLoginBinding binding;
    public FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;
    public View view1;
    public ProgressDialog pd;
    CallbackManager mCallbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(getContext());
        // google entegrasyonu
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        auth = FirebaseAuth.getInstance();

        // facebook
        FacebookSdk.sdkInitialize(getContext());
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.w("TAG", "onSuccess FacebookCallback: ");
                    }

                    @Override
                    public void onCancel() {
                        Log.w("TAG", "onCancel FacebookCallback: ");
                        Toast.makeText(getContext(), "Bir hata oluştu ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.w("TAG", "onError FacebookCallback: " + exception.getLocalizedMessage());
                        Toast.makeText(getContext(), "bir hata oluştu: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.editTextEmail.setCursorVisible(false);
        binding.editTextEmail.setRawInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser currentUser = auth.getCurrentUser();
        // kullanıcı giriş yapmış ise daha önce ve çıkış yapmamış ise direkt homeFragment'e yönlendir
        if (currentUser != null) {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
        }
        // facebook ile giriş
        LoginButton loginButton = binding.facebookButton;
        loginButton.setFragment(this);
        loginButton.setOnClickListener(view2 -> {
            loginButton.setReadPermissions("email", "public_profile");
            loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.w("TAG", "onSuccess registerCallback: ");
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    Log.w("TAG", "onCancel registerCallback: ");
                    Toast.makeText(getContext(), "Bir hata oluştu: ", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Log.w("TAG", "onError registerCallback: " + error.getLocalizedMessage());
                    Toast.makeText(getContext(), "Bir hata oluştu: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
        binding.fb.setOnClickListener(view22 -> loginButton.performClick());

        // email ile giriş
        binding.loginButton.setOnClickListener(view23 -> {

            if (binding.edittextPassword.getText().toString().isEmpty() || binding.editTextEmail.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Lütfen email ve şifrenizi giriniz!", Toast.LENGTH_LONG).show();
            } else {
                auth.signInWithEmailAndPassword(binding.editTextEmail.getText().toString(), binding.edittextPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.w("TAG", "onSuccess signInWithEmailAndPassword: ");
                        Navigation.findNavController(view23).navigate(R.id.action_loginFragment_to_homeFragment);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "onFailure signInWithEmailAndPassword: " + e.getLocalizedMessage());
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                });

            }

        });
        binding.forgetPasswordButton.setOnClickListener(view24 -> Navigation.findNavController(view24).navigate(R.id.action_loginFragment_to_forgetPasswordFragment));
        // google ile giriş
        binding.googleButton.setOnClickListener(view25 -> {
            view1 = view25;
            signInUsingGoogle();
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        Log.w("TAG", "onActivityResult: " + requestCode);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed
                Log.w("TAG", "GoogleSignIn: " + e.getLocalizedMessage());
                Toast.makeText(getContext(), "Login olunurken bir hata oluştu :" + e
                        .getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        showProgress("Lütfen Bekleyiniz");
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    Log.w("TAG", "onSuccess firebaseAuthWithGoogle");
                    Bundle bundle = new Bundle();
                    bundle.putString("email", Objects.requireNonNull(authResult.getUser()).getEmail());
                    Navigation.findNavController(view1).navigate(R.id.action_loginFragment_to_homeFragment, bundle);
                    pd.dismiss();
                }).addOnFailureListener(e -> {
                    Log.w("TAG", "onFailure firebaseAuthWithGoogle");
                    Toast.makeText(getContext(), "bir hata oluştu : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void signInUsingGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.w("TAG", "handleFacebookAccessToken");
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.w("TAG", "onSuccess handleFacebookAccessToken");
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_homeFragment);
            }
        }).addOnFailureListener(e -> {
            Log.w("TAG", "onFailure handleFacebookAccessToken: ", e);
            Toast.makeText(getContext(), "Bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        });
    }

    public void showProgress(String message) {
        pd.setMessage(message);
        pd.show();
    }

}