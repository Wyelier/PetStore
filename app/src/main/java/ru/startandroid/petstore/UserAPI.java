package ru.startandroid.petstore;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("user/{username}")
    Call<User> getUsers(@Path("username") String username);

    @GET("user/login")
    Call<User> login(@Query("username") String username, @Query("password") String password);

    @POST("user")
    Call<User> createUsers(@Body User user);

    Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("https://petstore.swagger.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
