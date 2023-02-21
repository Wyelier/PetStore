package ru.startandroid.petstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText LoginEditText, PassEditText;
    private Button btnLog, btnReg;
    private ImageButton btnMyAcc;
    String userNameText, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        LoginEditText = (EditText) findViewById(R.id.editTextLogin);
        PassEditText = (EditText) findViewById(R.id.editTextPass);
        btnLog = (Button) findViewById(R.id.btnLogin);
        btnReg = (Button) findViewById(R.id.btnReg);
        btnMyAcc = (ImageButton) findViewById(R.id.btnLK);

        UserAPI userAPI = UserAPI.retrofit2.create(UserAPI.class);

        btnMyAcc.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        btnReg.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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
