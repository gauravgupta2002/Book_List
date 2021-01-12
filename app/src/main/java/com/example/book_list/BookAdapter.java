package com.example.book_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> Books) {
        super(context, 0, Books);
    }

    //Returns a list item view that displays information about the book at the given position
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_book,parent,false);
        }

        Book currentBook = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentBook.getTitle());

        TextView author = (TextView) listItemView.findViewById(R.id.authors);
        author.setText(currentBook.getAuthors());

        TextView publisher = (TextView) listItemView.findViewById(R.id.publisher);
        publisher.setText(currentBook.getPublisher());

        ImageView BookImage = listItemView.findViewById(R.id.imag);
        View loadingIndicator = listItemView.findViewById(R.id.loading_indicator2);
        loadingIndicator.setVisibility(View.GONE);
        BookImage.setImageBitmap(currentBook.getImg());

        return listItemView;
    }
}
