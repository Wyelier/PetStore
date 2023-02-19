package ru.startandroid.petstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText LoginEditText;
    private EditText PassEditText;
    private EditText FirstNEditText;
    private EditText LastNEditText;
    private Button btntoLog;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_layout);


        LoginEditText = findViewById(R.id.editTextLogin);
        FirstNEditText = findViewById(R.id.editTextFirstN);
        LastNEditText = findViewById(R.id.editTextLastN);
        PassEditText = findViewById(R.id.editTextPass);

        btntoLog = findViewById(R.id.btntoLog);
        btnRegister = findViewById(R.id.btnRegister);

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Ваш ID");

        
        UserAPI userAPI = UserAPI.retrofit2.create(UserAPI.class);

        btntoLog.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        btnRegister.setOnClickListener(v -> {
            Random random = new Random();
            int id = random.nextInt(1000) + 1;
            String Id = UUID.randomUUID().toString();
            String Login = LoginEditText.getText().toString();
            String FirtsN = FirstNEditText.getText().toString();
            String LastN = LastNEditText.getText().toString();
            String Pass = PassEditText.getText().toString();
            builder.setMessage("Ваш ID: " + id);
            if (PassEditText.length() > 8)
            {
                User newUser = new User();
                newUser.setId(id);
                newUser.setUsername(Login);
                newUser.setFirstName(FirtsN);
                newUser.setLastName(LastN);
                newUser.setPassword(Pass);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                Call<User> Call = userAPI.createUsers(newUser);
                Call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User createdUser = response.body();
                        } else {
                            ResponseBody errorBody = response.errorBody();
                            try {
                                Toast.makeText(RegisterActivity.this, errorBody.string(),
                                        Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Что-то пошло не так " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                builder.setTitle("Предупреждение");
                builder.setMessage("Пароль должен иметь более 8 символов");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
