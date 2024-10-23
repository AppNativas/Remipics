package com.example.remipics.interfaces;

import com.example.remipics.entities.LoginRequest;
import com.example.remipics.entities.LoginResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;

public interface ApiService {
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
