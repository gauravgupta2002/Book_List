package com.example.book_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static String URL;

    //Adapter for the list of books
    private BookAdapter mAdapter;
    //TextView that is displayed when the list is empty
    private TextView emptyText;
    private static  int BOOK_LOADER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText toSearch = findViewById(R.id.edit_text);
        String search=toSearch.getText().toString();

        if(search.isEmpty()||search==null){
            URL=BOOK_REQUEST_URL;
            BOOK_LOADER_ID=1;
        }
        else{
            URL=BOOK_REQUEST_URL+search;
            BOOK_LOADER_ID=2;
        }

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.recycle_view);

        emptyText = (TextView)findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyText);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this,new ArrayList<Book>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);
        bookListView.setSelector(R.drawable.b1);


        setConnectivty();

        Button SearchButton = findViewById(R.id.Search_Button);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchEditText=findViewById(R.id.edit_text);
                View loadingIndicator = findViewById(R.id.loading_indicator);
                mAdapter.clear();
                emptyText.setVisibility(View.GONE);
                loadingIndicator.setVisibility(View.VISIBLE);
                String forcesearch=searchEditText.getText().toString();
                if(!forcesearch.isEmpty()){
                   URL = BOOK_REQUEST_URL + forcesearch;
                    LoaderManager.getInstance(MainActivity.this).initLoader(2, null,MainActivity.this);
                }else{
                    LoaderManager.getInstance(MainActivity.this).initLoader(1, null,MainActivity.this);
                }
            }
        });


    }


    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i("FINAL_URI_PROCESSED",URL);
        return new BookLoader(this,URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        emptyText.setText(R.string.no_books);
        emptyText.setVisibility(View.VISIBLE);
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
    mAdapter.clear();
    }



    public void setConnectivty(){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = cM.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, null, this);

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyText.setText(R.string.no_internet);
        }
    }
}