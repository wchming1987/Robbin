package com.garfield.alfred.robbin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class BookInfoActivity extends AppCompatActivity {
    private SimpleDraweeView bookImage;
    private TextView bookName;
    private TextView bookGrade;
    private TextView bookSimpleInfo;

    private Button buyBtn;
    private Button borrowBtn;
    private Button ebookBtn;

    private String ISBN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 接收传入数据
        Bundle bundle = this.getIntent().getExtras();
        ISBN = bundle.getString("ISBN13");
        Toast.makeText(BookInfoActivity.this, "ISBN:"+ISBN, Toast.LENGTH_SHORT).show();

        // 获取页面元素
        bookImage = (SimpleDraweeView) findViewById(R.id.book_image);
        bookName = (TextView) findViewById(R.id.book_name);
        bookGrade = (TextView) findViewById(R.id.book_douban_grade);
        bookSimpleInfo = (TextView) findViewById(R.id.book_simple_info);

        buyBtn = (Button) findViewById(R.id.buy_btn);
        borrowBtn = (Button) findViewById(R.id.borrow_btn);
        ebookBtn = (Button) findViewById(R.id.ebook_btn);

        //  获取书籍列表
        String get_url = "http://192.168.200.146:12000/book/" + ISBN;

        RequestQueue queue = Volley.newRequestQueue(BookInfoActivity.this);
        StringRequest bookListRequest = new StringRequest(Request.Method.GET,
                get_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.length() == 0) {
                            Toast.makeText(BookInfoActivity.this, "该书的数据逃跑了，已经派出加菲猫去寻找，请耐心等待", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            JSONObject book = JSON.parseObject(response);

                            // 设置展示元素
                            bookImage.setImageURI(book.getString("image"));
                            bookName.setText(book.getString("name"));
                            bookGrade.setText(book.getString("doubanGrade"));
                            bookSimpleInfo.setText(String.format("%s/%s/%s", book.getString("author"), book.getString("publisher"), book.getString("pubdate")));

                            // 设置按钮状态
                            if (book.getBoolean("isBought")) {
                                buyBtn.setText("已购买");
                            }
                            if (book.getBoolean("isLending")) {
                                buyBtn.setText("已借出");
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

        // 配置按钮事件
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookInfoActivity.this, "加油哦，好好学习！", Toast.LENGTH_SHORT).show();

                String post_url = "http://192.168.200.146:12000/book/" + ISBN + "/buy";

                RequestQueue queue = Volley.newRequestQueue(BookInfoActivity.this);
                StringRequest bookListRequest = new StringRequest(Request.Method.POST,
                        post_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject book = JSON.parseObject(response);

                                    // 设置按钮状态
                                    if (book.getBoolean("isBought") == false) {
                                        buyBtn.setText("购买");
                                    } else if (book.getBoolean("isBought") == true) {
                                        buyBtn.setText("已购买");
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

            }
        });

        borrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookInfoActivity.this, "加油哦，好好学习！", Toast.LENGTH_SHORT).show();
            }
        });

        ebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookInfoActivity.this, "加油哦，好好学习！", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
