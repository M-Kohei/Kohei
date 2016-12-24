package com.example.matu.kobu_prototype;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.matu.kobu_prototype.fragment.BluetoothConnectFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.activity_main, new BluetoothConnectFragment());
        transaction.commit();
    }
}
