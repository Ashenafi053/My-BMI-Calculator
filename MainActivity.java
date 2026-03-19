package com.example.bmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Added for Pro UI

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Elements
        EditText editWeight = findViewById(R.id.weightInput);
        EditText editHeight = findViewById(R.id.heightInput);
        Button btnCalc = findViewById(R.id.calculateBtn);
        TextView txtResult = findViewById(R.id.resultText);
        ImageView helpIcon = findViewById(R.id.helpIcon);
        CardView resultCard = findViewById(R.id.resultCard); // Linked to the new CardView

        // Help Icon Click Listener
        helpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("Help & Health Guide")
                        .setMessage("• Enter weight in KG and height in CM.\n" +
                                "• BMI is a general indicator, not a medical diagnosis.\n\n" +
                                "What if my BMI is high or low?\n" +
                                "Don't panic! Muscle mass or bone density can affect results. " +
                                "If you are concerned, watch the expert guide below for safe steps to reach a normal weight.")
                        .setPositiveButton("Close", null)
                        .setNeutralButton("Watch Guide (YouTube)", new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(android.content.DialogInterface dialog, int which) {
                                android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW,
                                        android.net.Uri.parse("https://www.youtube.com/watch?v=qamTh498AKc"));
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        // Calculate Button Click Listener
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strWeight = editWeight.getText().toString();
                String strHeight = editHeight.getText().toString();

                if (strWeight.isEmpty() || strHeight.isEmpty()) {
                    txtResult.setText("Please enter both values!");
                    return;
                }

                float weight = Float.parseFloat(strWeight);
                float height = Float.parseFloat(strHeight);

                if (height > 3) {
                    height = height / 100;
                }

                float bmi = weight / (height * height);

                String category;
                int colorRes;

                // Logic for Category and Card Color
                if (bmi < 18.5) {
                    category = "Underweight";
                    colorRes = android.R.color.holo_blue_light;
                } else if (bmi < 25) {
                    category = "Normal Weight";
                    colorRes = android.R.color.holo_green_light;
                } else if (bmi < 30) {
                    category = "Overweight";
                    colorRes = android.R.color.holo_orange_light;
                } else {
                    category = "Obese";
                    colorRes = android.R.color.holo_red_light;
                }

                // Update UI with color and result
                txtResult.setText("BMI: " + String.format("%.2f", bmi) + "\n" + category);
                resultCard.setCardBackgroundColor(getResources().getColor(colorRes));
            }
        });
    }
}