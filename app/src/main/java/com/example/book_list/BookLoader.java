package com.example.book_list;

import android.content.Context;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    //for log msgs
    private static final String LOG_TAG= BookLoader.class.getName();

    //Query url
    private String mUrl;

    public BookLoader(@NonNull Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    //on background thread
    @Nullable
    @Override
    public List<Book> loadInBackground() {
        if(mUrl==null){
            return null;
        }

        List<Book> books = null;
        try {
            books = QueryUtils.fetchBooksData(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }
}
