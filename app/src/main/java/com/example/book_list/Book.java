package com.example.book_list;

import android.graphics.Bitmap;

public class Book {
    private String mTitle,mAuthor,mPublisher;
    private Bitmap mImg;

    public Book(String Title,String Author,String Publisher,Bitmap img){
        mTitle=Title;
        mAuthor=Author;
        mPublisher=Publisher;
        mImg=img;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthors() {
        return mAuthor;
    }

    public String getPublisher(){
        return mPublisher;
    }

    public Bitmap getImg() {
        return mImg;
    }


}
