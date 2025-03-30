package com.example.lutemon;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class CreateLutemonActivity extends AppCompatActivity {

    private EditText editTextName;
    private Spinner spinnerColor;
    private Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lutemon);

        editTextName = findViewById(R.id.editTextName);
        spinnerColor = findViewById(R.id.spinnerColor);
        buttonCreate = findViewById(R.id.buttonCreate);

        String[] colors = {"White", "Green", "Pink", "Orange", "Black"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adapter);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String color = spinnerColor.getSelectedItem().toString();

                if (name.isEmpty()) {
                    Toast.makeText(CreateLutemonActivity.this, "Enter a name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Lutemon lutemon = createLutemon(color, name);
                if (lutemon != null) {
                    Storage.getInstance().addLutemon(lutemon);
                    Toast.makeText(CreateLutemonActivity.this, color + " Lutemon created!", Toast.LENGTH_SHORT).show();
                    editTextName.setText("");
                }
            }
        });
    }

    private Lutemon createLutemon(String color, String name) {
        switch (color) {
            case "White": return new WhiteLutemon(name);
            case "Green": return new GreenLutemon(name);
            case "Pink": return new PinkLutemon(name);
            case "Orange": return new OrangeLutemon(name);
            case "Black": return new BlackLutemon(name);
            default: return null;
        }
    }
}
