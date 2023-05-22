package algonquin.cst2335.lei00029.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import algonquin.cst2335.lei00029.databinding.ActivityMainBinding;
import algonquin.cst2335.lei00029.data.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private MainViewModel model;

    private MutableLiveData<Boolean> drinkCoffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        

        variableBinding.mybutton.setOnClickListener(click ->
        {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });

        model.editString.observe(this, s ->{
            variableBinding.textview.setText("Your edit text has: " + s);
        });

        model.isSelected.observe(this, selected ->{
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switchButton.setChecked(selected);
        });

        variableBinding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.getIsSelected().postValue(isChecked);
        });

        variableBinding.radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.getIsSelected().postValue(isChecked);
        });

        variableBinding.switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.getIsSelected().postValue(isChecked);
        });


    }


}