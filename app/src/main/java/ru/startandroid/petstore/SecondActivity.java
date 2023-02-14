package ru.startandroid.petstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        PetAPI petAPI = PetAPI.retrofit.create(PetAPI.class);

        BtnActv2.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        postBtn.setOnClickListener(v -> {
            sProgressBar.setVisibility(View.VISIBLE);

            String IdInput = sEditTextID.getText().toString();
            String Name = sEditTextName.getText().toString();
            String photoUrl = sEditTextUrl.getText().toString();
            List<String> PhotoUrls = new ArrayList<>();
            PhotoUrls.add(photoUrl);
            int ID = Integer.parseInt(IdInput);
//            Pet newPet = new Pet(ID, Name, PhotoUrls);
            Pet newPet = new Pet();
            newPet.setId(ID);
            newPet.setName(Name);
            newPet.setPhotoUrls(PhotoUrls);
            Call<Pet> Call = petAPI.createPets(newPet);

            Call.enqueue(new Callback<Pet>() {
                @Override
                public void onResponse(Call<Pet> call, Response<Pet> response) {
                    if(response.isSuccessful()) {
                        Pet createdPet = response.body();
                        sProgressBar.setVisibility(View.INVISIBLE);
                    } else {
                        ResponseBody errorBody = response.errorBody();
                        try {
                            Toast.makeText(SecondActivity.this, errorBody.string(),
                                    Toast.LENGTH_SHORT).show();
                            sProgressBar.setVisibility(View.INVISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<Pet> call, Throwable throwable) {
                    Toast.makeText(SecondActivity.this, "Что-то пошло не так " + throwable.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    sProgressBar.setVisibility(View.INVISIBLE);
                }
            });
        });
    }
}
