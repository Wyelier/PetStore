package ru.startandroid.petstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mTextView;
    private EditText mEditText;
    private ImageView mImageView;
    private Button getBtn;


    String[] layouts = {"Создать", "Посмотреть созданного питомца"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String>mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, layouts);
        setContentView(R.layout.activity_main);
        // дописать из https://github.com/Wyelier/MultiScreen/blob/master/app/src/main/java/com/example/multiscreen/MainActivity.java


        mProgressBar = findViewById(R.id.progressBar);
        mTextView = findViewById(R.id.textView);
        mEditText = findViewById(R.id.editText);
        mImageView = findViewById(R.id.imageView);
        getBtn = findViewById(R.id.button);

        mProgressBar.setVisibility(View.INVISIBLE);

        PetAPI petAPI = PetAPI.retrofit.create(PetAPI.class);
        getBtn.setOnClickListener(v -> {

            mProgressBar.setVisibility(View.VISIBLE);

            Call<Pet> Call = petAPI.getPets(mEditText.getText().toString());

            Call.enqueue(new Callback<Pet>() {
                @Override
                public void onResponse(Call<Pet> call, Response<Pet> response) {
                    if (response.isSuccessful()) {
                        Pet pet = response.body();
                        String text = "Вашего питомца зовут " + pet.getName();
                        mTextView.setText(text);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Picasso.with(MainActivity.this)
                                .load(pet.getPhotoUrls().get(0))
                                .resize(600, 600)
                                .into(mImageView);
                    } else {
                        ResponseBody errorBody = response.errorBody();
                        try {
                            Toast.makeText(MainActivity.this, errorBody.string(),
                                    Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.INVISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Pet> call, Throwable throwable) {
                    Toast.makeText(MainActivity.this, "Что-то пошло не так " + throwable.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            });
        });

    }


}