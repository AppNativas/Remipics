package com.example.remipics.interfaces;

import com.example.remipics.entities.ApiResponse;
import com.example.remipics.entities.LoginRequest;
import com.example.remipics.entities.RegisterRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;

public interface ApiUserService {

    @POST("api/auth/login")
    Call<ApiResponse> login(@Body LoginRequest loginRequest);

    @POST("api/auth/register")
    Call<ApiResponse> register(@Body RegisterRequest registerRequest);

}
