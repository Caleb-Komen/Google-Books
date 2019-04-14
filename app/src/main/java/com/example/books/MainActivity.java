package com.example.books;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String JSON_RESPONSE_URL = "https://www.googleapis.com/books/v1/volumes?q=pride+prejudice&maxResults=5&printType=books";
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find a reference to the list view
        listView = findViewById(R.id.data_list_view);

        //instantiate the adapter
        adapter = new MyAdapter(getApplicationContext(),new ArrayList<Book>());

        //set the adapter with the list view
        listView.setAdapter(adapter);

        new BookAsyncTask().execute(JSON_RESPONSE_URL);

        //set onClickListener to each item in the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Book book = (Book) adapterView.getItemAtPosition(i);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(book.getUrl()));

                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }

            }
        });

    }

    public class BookAsyncTask extends AsyncTask<String, Void, List<Book>>{

        @Override
        protected List<Book> doInBackground(String... url) {
            List<Book> books = new ArrayList<>();
            if(url[0]==null && TextUtils.isEmpty(url[0])){
                return null;
            }

            books = QueryUtils.fetchBookData(url[0]);
            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            //clear the adapter of any previous data
            adapter.clear();

            if(books!=null)
                adapter.addAll(books);
        }
    }
}
