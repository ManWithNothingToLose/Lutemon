package com.example.lutemon;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateLutemonActivity extends AppCompatActivity {

    private EditText editTextName;
    private RadioGroup radioGroupColor;
    private ImageView imageLutemonPreview;
    private Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lutemon);

        // Link UI components
        editTextName = findViewById(R.id.editTextName);
        radioGroupColor = findViewById(R.id.radioGroupColor);
        imageLutemonPreview = findViewById(R.id.imageLutemonPreview);
        buttonCreate = findViewById(R.id.buttonCreate);

        // Update preview image when color is selected
        radioGroupColor.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioWhite) {
                imageLutemonPreview.setImageResource(R.drawable.lutemon_white);
            } else if (checkedId == R.id.radioGreen) {
                imageLutemonPreview.setImageResource(R.drawable.lutemon_green);
            } else if (checkedId == R.id.radioPink) {
                imageLutemonPreview.setImageResource(R.drawable.lutemon_pink);
            } else if (checkedId == R.id.radioOrange) {
                imageLutemonPreview.setImageResource(R.drawable.lutemon_orange);
            } else if (checkedId == R.id.radioBlack) {
                imageLutemonPreview.setImageResource(R.drawable.lutemon_black);
            }
        });

        // Handle Create button click
        buttonCreate.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Determine selected color and create corresponding Lutemon
            int selectedId = radioGroupColor.getCheckedRadioButtonId();
            Lutemon newLutemon = null;
            if (selectedId == R.id.radioWhite) {
                newLutemon = new WhiteLutemon(name);
            } else if (selectedId == R.id.radioGreen) {
                newLutemon = new GreenLutemon(name);
            } else if (selectedId == R.id.radioPink) {
                newLutemon = new PinkLutemon(name);
            } else if (selectedId == R.id.radioOrange) {
                newLutemon = new OrangeLutemon(name);
            } else if (selectedId == R.id.radioBlack) {
                newLutemon = new BlackLutemon(name);
            }

            if (newLutemon != null) {
                // Add Lutemon to Storage with context
                Storage.getInstance().addLutemon(newLutemon, this);

                Toast.makeText(this,
                        "Lutemon " + name + " created and moved to Home!",
                        Toast.LENGTH_SHORT).show();

                finish();
            } else {
                Toast.makeText(this, "Please select a color", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
