package ru.startandroid.petstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private ProgressBar sProgressBar;
    private EditText sEditTextID;
    private EditText sEditTextName;
    private EditText sEditTextUrl;
    private Button postBtn;
    private Button BtnActv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_pet);

        sProgressBar = findViewById(R.id.progressBar2);
        sEditTextID = findViewById(R.id.editTextID);
        sEditTextName = findViewById(R.id.editTextName);
        sEditTextUrl = findViewById(R.id.editTextURL);

        postBtn = findViewById(R.id.button2);
        BtnActv2 = findViewById(R.id.btnActv2);

        sProgressBar.setVisibility(View.INVISIBLE);

        BtnActv2.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        postBtn.setOnClickListener(v -> {

        });
    }
}
