package algonquin.cst2335.lei00029;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import algonquin.cst2335.lei00029.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    protected ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());

        Log.e(TAG,"In onCreate()");
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        String emailAddress = prefs.getString("LoginName", "");

        binding.emailAddress.setText(emailAddress);

        binding.loginButton.setOnClickListener((v) -> {
            Log.e(TAG,"You clicked the button");

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", binding.emailAddress.getText().toString());
            editor.apply();


            Intent nextPage = new Intent(this,SecondActivity.class);

            String whatIsTyped = binding.emailAddress.getText().toString();

            nextPage.putExtra("Email",whatIsTyped);
            nextPage.putExtra("AGE",25);

            startActivity(nextPage);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"In onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"In onDestroy()");
    }
}