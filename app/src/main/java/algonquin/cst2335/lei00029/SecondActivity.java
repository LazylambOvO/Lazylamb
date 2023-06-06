package algonquin.cst2335.lei00029;

import static android.provider.Telephony.Mms.Part.FILENAME;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import algonquin.cst2335.lei00029.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        File file = new File(getFilesDir(), FILENAME);
        if(file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            binding.imageView.setImageBitmap(theImage);
        }

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        String savedPhoneNumber = prefs.getString("PhoneNumber", "");


        Intent nextPage = getIntent();

        String Email = nextPage.getStringExtra("Email");
        int age = nextPage.getIntExtra("AGE",0);

        binding.textView3.setText(Email);

        binding.callPhoneButton.setOnClickListener((v) -> {
            Intent call = new Intent(Intent.ACTION_DIAL);

            String phoneNumber = binding.editTextPhone.getText().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("PhoneNumber", phoneNumber);
            editor.apply();

            call.setData(Uri.parse("tel:" + phoneNumber));

            startActivity(call);
        });



        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override

                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            binding.imageView.setImageBitmap(thumbnail);

                            FileOutputStream fOut = null;

                            try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                                fOut.flush();

                                fOut.close();

                            }

                            catch (FileNotFoundException e)

                            { e.printStackTrace();

                            }catch (IOException e) {
                                e.printStackTrace();
                            }



                        }

                    }

                });


        binding.changePictureButton.setOnClickListener((v) -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            cameraResult.launch(cameraIntent);

        });

    }
}