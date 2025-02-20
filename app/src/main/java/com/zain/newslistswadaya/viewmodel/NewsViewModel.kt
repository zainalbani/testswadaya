package com.zain.newslistswadaya.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.zain.newslistswadaya.di.ApiService
import com.zain.newslistswadaya.response.BaseResponse
import com.zain.newslistswadaya.response.ErrorResponse
import com.zain.newslistswadaya.response.GetNewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val client: ApiService
) : ViewModel() {

    val newsResult: MutableLiveData<BaseResponse<GetNewsResponse>> = MutableLiveData()

    fun getNews() {
        newsResult.value = BaseResponse.Loading()
        client.getNews().enqueue(object : Callback<GetNewsResponse> {
            override fun onResponse(
                call: Call<GetNewsResponse>,
                response: Response<GetNewsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    newsResult.value = BaseResponse.Success(responseBody)
                } else {
                    val errorBody = response.errorBody()
                    if (errorBody != null) {
                        val errorResponse =
                            Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                        val errorMessage = errorResponse.message
                        newsResult.value = BaseResponse.Error(errorMessage)
                    } else {
                        newsResult.value = BaseResponse.Error("Unknown error occurred")
                    }
                }
            }

            override fun onFailure(call: Call<GetNewsResponse>, t: Throwable) {
                newsResult.value = BaseResponse.Error(t.message)
            }
        })
    }

    val searchResult: MutableLiveData<BaseResponse<GetNewsResponse>> = MutableLiveData()

    fun searchNews(q: String) {
        searchResult.value = BaseResponse.Loading()
        client.searchNews(q).enqueue(object : Callback<GetNewsResponse> {
            override fun onResponse(
                call: Call<GetNewsResponse>,
                response: Response<GetNewsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    searchResult.value = BaseResponse.Success(responseBody)
                } else {
                    val errorBody = response.errorBody()
                    if (errorBody != null) {
                        val errorResponse =
                            Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                        val errorMessage = errorResponse.message
                        searchResult.value = BaseResponse.Error(errorMessage)
                    } else {
                        searchResult.value = BaseResponse.Error("Unknown error occurred")
                    }
                }
            }

            override fun onFailure(call: Call<GetNewsResponse>, t: Throwable) {
                searchResult.value = BaseResponse.Error(t.message)
            }
        })
    }


}