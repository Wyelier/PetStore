package ru.startandroid.petstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private EditText mEditTextID;
    private EditText mEditTextUrl;
    private EditText mEditTextName;
    private ImageView mImageView;
    private Button postBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_pet);


    }
}
