package com.example.books;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    public static URL createUrl(String url){
        URL myUrl = null;

        try {
            myUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return myUrl;
        }

        public static String makeHttpRequest(URL url){

        String jResponse = null;
        HttpURLConnection httpURLConnection;

        //return early if url is null
        if(url==null){
            return jResponse;
        }

        //make a request
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.connect();

                //read the input stream and return a jsonResponse
                InputStream inputStream = httpURLConnection.getInputStream();
                jResponse = readInputStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return jResponse;
        }

        public static String readInputStream(InputStream inputStream) throws IOException {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String data;
            String message="";

            while ((data = bufferedReader.readLine()) != null){
                message += data;
            }

            return message;
        }

        public static List<Book> extractJResponse(String jResponse){

            List<Book> books = new ArrayList<>();
            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(jResponse);

                if(jsonObject != null){
                JSONArray jsonArray = jsonObject.optJSONArray("items");

                //loop through the objects in the json array
                for(int i = 0; i<jsonArray.length();i++) {

                    JSONObject object = jsonArray.optJSONObject(i);
                    JSONObject myObject = object.optJSONObject("volumeInfo");
                    String title = myObject.optString("title");
                    JSONArray array = myObject.optJSONArray("authors");

                    String author = null;
                    for(int j = 0; j<array.length(); j++){
                        author = array.optString(j);
                    }

                    String datePublished = myObject.optString("publishedDate");
                    JSONObject anotherObject = myObject.optJSONObject("imageLinks");
                    String imageLink = anotherObject.optString("smallThumbnail");
                    String url = myObject.optString("previewLink");

                    books.add(new Book(author, title, imageLink, datePublished,url));

                }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return books;
        }

        public static List<Book> fetchBookData(String url){
            URL myUrl = createUrl(url);
            List<Book> bookList = new ArrayList<>();

            String jResponse =  makeHttpRequest(myUrl);

            bookList = extractJResponse(jResponse);

            return bookList;
        }
    }

