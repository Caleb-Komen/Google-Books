package com.example.books;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends ArrayAdapter {
    public MyAdapter( Context context,  List objects) {
        super(context, 0, objects);

    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);
        }

        Book book= (Book) getItem(position);

        ImageView imageView = convertView.findViewById(R.id.image_view);
        Glide.with(getContext()).load(Uri.parse(book.getImageUrl())).into(imageView);

        TextView bookTitle = convertView.findViewById(R.id.book_title);
        bookTitle.setText(book.getTitle());

        TextView bookAuthor = convertView.findViewById(R.id.book_author);
        bookAuthor.setText(book.getAuthor());

        TextView datePublished = convertView.findViewById(R.id.date_published);
        datePublished.setText(book.getDatePublished());

        return convertView;
    }
}
