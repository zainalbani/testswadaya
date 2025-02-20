package com.zain.newslistswadaya.di

import com.zain.newslistswadaya.response.GetNewsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getNews(
        @Query("country")country: String = "us",
        @Query("apiKey") apiKey: String = "da6e42d6cf704ed1ad501370e3fbcee3",
    ): Call<GetNewsResponse>

    @GET("everything")
    fun searchNews(
        @Query("q")q: String,
        @Query("apiKey") apiKey: String = "da6e42d6cf704ed1ad501370e3fbcee3",
    ): Call<GetNewsResponse>

}