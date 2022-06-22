package com.example.bootcampproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.bootcampproject.R;


public class MainActivity extends AppCompatActivity {

    public static ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pd = new ProgressDialog(this);
    }

    public static void showToast(Context context, String msj) {
        Toast.makeText(context, msj, Toast.LENGTH_SHORT).show();
    }

    public static void showProgress() {
        if (pd != null) {
            pd.setMessage("Lütfen Bekleyiniz..");
            pd.show();
        }
    }

    public static void dismissProgress() {
        if (pd != null)
            pd.dismiss();
    }
}