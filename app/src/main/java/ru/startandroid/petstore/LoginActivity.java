package ru.startandroid.petstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText LoginEditText;
    private EditText PassEditText;
    private Button btnLog;
    private Button btnReg;
    String userNameText;
    String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        LoginEditText = findViewById(R.id.editTextLogin);
        PassEditText = findViewById(R.id.editTextPass);
        btnLog = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnReg);

        UserAPI userAPI = UserAPI.retrofit2.create(UserAPI.class);

        btnReg.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        btnLog.setOnClickListener(v -> {

            userNameText = LoginEditText.getText().toString();
            userPassword = PassEditText.getText().toString();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            Call<User> Call = userAPI.login(userNameText, userPassword);

            Call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        Toast.makeText(LoginActivity.this, "Вход успешно выполнен",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        ResponseBody errorBody = response.errorBody();
                        try {
                            Toast.makeText(LoginActivity.this, errorBody.string(),
                                    Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Что-то пошло не так " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
