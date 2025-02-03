package com.example.bookshelfapp.data.network;



import com.example.bookshelfapp.data.model.book.BookResponse;
import com.example.bookshelfapp.data.repository.BookRepository;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface BookInterface {
    @GET("volumes")
    Call<BookResponse> getBooks(@QueryMap(encoded = true) Map<String,String> query, @Query("key") String key);

}
