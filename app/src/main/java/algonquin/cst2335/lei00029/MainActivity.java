package algonquin.cst2335.lei00029;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import algonquin.cst2335.lei00029.databinding.ActivityMainBinding;

/**
 * this class is a simple password checker app
 * @author Yucong Lei
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    RequestQueue queue = null;
    Bitmap image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );


        //This part goes at the top of the onCreate function:
        queue = Volley.newRequestQueue(this);

        setContentView(binding.getRoot());

        binding.forecastButton.setOnClickListener( click ->{
            String cityName = binding.cityTextField.getText().toString();

            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName) + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";

            //this goes in the button click handler:
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    (response)->{

                try{
                    JSONObject coord = response.getJSONObject("coord");
                    JSONArray weatherArray = response.getJSONArray("weather");
                    JSONObject position0 = weatherArray.getJSONObject(0);

                    String description = position0.getString("description");

                    JSONObject mainObject = response.getJSONObject( "main" );

                    double current = mainObject.getDouble("temp");
                    double min = mainObject.getDouble("temp_min");
                    double max = mainObject.getDouble("temp_max");
                    int humidity = mainObject.getInt("humidity");

                    String iconName = position0.getString("icon");
                    String pictureURL = "http://openweathermap.org/img/w/" + iconName + ".png";


                    String pathname = getFilesDir() +"/"+iconName+".png";
                    File file = new File(pathname);
                    if (file.exists()) {
                        image = BitmapFactory.decodeFile(pathname);
                    } else {
                        ImageRequest imgReq = new ImageRequest(pictureURL, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                try {
                                    FileOutputStream fOut = null;
                                    fOut = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                                    image = bitmap;
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                    fOut.flush();
                                    fOut.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                (error) -> {});
                        queue.add(imgReq);
                    }




                    runOnUiThread( (  )  -> {

                        binding.tempView.setText("The current temperature is " + current);
                        binding.tempView.setVisibility(View.VISIBLE);

                        binding.maxTempView.setText("The max temperature is " + max);
                        binding.maxTempView.setVisibility(View.VISIBLE);

                        binding.minTempView.setText("The min temperature is " + min);
                        binding.minTempView.setVisibility(View.VISIBLE);

                        binding.humidityView.setText("The min temperature is " + humidity);
                        binding.humidityView.setVisibility(View.VISIBLE);

                        binding.icon.setImageBitmap(image);
                        binding.icon.setVisibility(View.VISIBLE);

                        binding.descriptionView.setText("The description is:"+description);
                        binding.descriptionView.setVisibility(View.VISIBLE);

                    });


                }catch (JSONException e){
                    e.printStackTrace();
                }
                    },
                    (error)->{
                        int i = 0;
                    });

            queue.add(request);
        });
    }
}