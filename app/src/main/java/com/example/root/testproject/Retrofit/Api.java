package com.example.root.testproject.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by root on 5/31/17.
 */

public interface Api {

    @GET("/users")
    Call<List<UserJson>> loadUsers();

}