package com.example.bookshelfapp.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookshelfapp.BuildConfig;
import com.example.bookshelfapp.data.model.book.BookResponse;
import com.example.bookshelfapp.data.network.ApiService;
import com.example.bookshelfapp.data.network.BookInterface;
import com.example.bookshelfapp.data.network.BookService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRepository {
    private BookInterface apiService;

    public BookRepository() {
        this.apiService = BookService.getApiService();
    }
    public LiveData<BookResponse> getBooks(String query){
        MutableLiveData<BookResponse> bookRes=new MutableLiveData<>();
        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        apiService.getBooks(params, BuildConfig.BOOK_API_KEY).enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if(response.isSuccessful()){
                    bookRes.setValue(response.body());
                }
                else {

                }

            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable throwable) {

            }
        });
        return bookRes;
    }
}
