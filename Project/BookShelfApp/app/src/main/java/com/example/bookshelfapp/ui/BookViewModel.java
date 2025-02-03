package com.example.bookshelfapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookshelfapp.data.model.book.BookResponse;
import com.example.bookshelfapp.data.repository.BookRepository;

public class BookViewModel extends ViewModel {
    private BookRepository bookRepository;
    private LiveData<BookResponse> books;
    public BookViewModel() {
        this.bookRepository = new BookRepository();
    }
    public void getBooks(String query) {
        books = bookRepository.getBooks(query);
    }
    public LiveData<BookResponse> getBooks() {
        return books;
    }
}
