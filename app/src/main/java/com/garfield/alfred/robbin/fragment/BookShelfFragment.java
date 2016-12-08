package com.garfield.alfred.robbin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.garfield.alfred.robbin.R;
import com.garfield.alfred.robbin.adapter.BookShelfAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author
 */
public class BookShelfFragment extends Fragment {
    private ArrayList bookList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bookList = new ArrayList<HashMap<String, Object>>();

        //  获取书籍列表
        String get_url = "http://192.168.200.146:12000/books";

        RequestQueue queue = Volley.newRequestQueue(inflater.getContext());
        StringRequest bookListRequest = new StringRequest(Request.Method.GET,
                get_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = JSON.parseArray(response);
                            for (Object object : jsonArray) {
                                JSONObject jsonObject = (JSONObject) object;
                                HashMap<String, Object> book = new HashMap<String, Object>();
                                for (HashMap.Entry entry : jsonObject.entrySet()) {
                                    if (!entry.getKey().toString().equals("_id")) {
                                        book.put((String) entry.getKey(), entry.getValue());
                                    }
                                }
                                bookList.add(book);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("***************访问出错了！！！***************");
                    }
                }
        );
        queue.add(bookListRequest);

        View view = inflater.inflate(R.layout.book_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.book_list);
        listView.setAdapter(new BookShelfAdapter(this, bookList));

        return view;
    }
}
