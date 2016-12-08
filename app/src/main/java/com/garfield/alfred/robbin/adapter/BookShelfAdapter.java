package com.garfield.alfred.robbin.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import com.facebook.drawee.view.SimpleDraweeView;

import com.garfield.alfred.robbin.BookInfoActivity;
import com.garfield.alfred.robbin.R;


/**
 * Created by wangcm on 2016/11/15.
 */
public class BookShelfAdapter extends BaseAdapter {
    private Fragment fragment;
    private List list;

    public BookShelfAdapter(Fragment fragment, List<HashMap<String, ?>> list) {
        this.fragment = fragment;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("convertView is null?" + (null == convertView));

        // 获取数据信息
        HashMap book = (HashMap<String, Object>) list.get(position);

        // 配置 ViewHolder
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(fragment.getContext());
            convertView = inflater.inflate(R.layout.book_list_item, null);

            System.out.println("ISBN13:" + book.get("isbn13"));

            viewHolder = new ViewHolder();

            viewHolder.ISBN13 = (String) book.get("isbn13");

            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.artist);
            viewHolder.list_image = (SimpleDraweeView) convertView.findViewById(R.id.list_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取资源地址
        TextView title = (TextView) convertView.findViewById(R.id.title); // 书名
        TextView artist = (TextView) convertView.findViewById(R.id.artist); // 书籍信息
        SimpleDraweeView dreweeView = (SimpleDraweeView) convertView.findViewById(R.id.list_image); // 封面

        // 配置资源属性
        title.setText(book.get("name").toString());
        artist.setText(String.format("%s/%s/%s", book.get("author"), book.get("publisher"), book.get("pubdate")));
        dreweeView.setImageURI(book.get("image").toString());

        /*
        // 给item 中的 title 添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent book_intent = new Intent(view.getContext(), BookInfoActivity.class);

                Bundle book_bundle = new Bundle();
                book_bundle.putString("ISBN13", viewHolder.ISBN13);
                book_intent.putExtras(book_bundle);

                view.getContext().startActivity(book_intent);
            }
        });

        // 给item 中的 artist 添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
        viewHolder.artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent book_intent = new Intent(view.getContext(), BookInfoActivity.class);

                Bundle book_bundle = new Bundle();
                book_bundle.putString("ISBN13", viewHolder.ISBN13);
                book_intent.putExtras(book_bundle);

                view.getContext().startActivity(book_intent);
            }
        });

        // 给item 中的 list_image 添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
        viewHolder.list_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent book_intent = new Intent(view.getContext(), BookInfoActivity.class);

                Bundle book_bundle = new Bundle();
                book_bundle.putString("ISBN13", viewHolder.ISBN13);
                book_intent.putExtras(book_bundle);

                view.getContext().startActivity(book_intent);
            }
        });
        */

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent book_intent = new Intent(view.getContext(), BookInfoActivity.class);

                Bundle book_bundle = new Bundle();
                book_bundle.putString("ISBN13", viewHolder.ISBN13);
                book_intent.putExtras(book_bundle);

                view.getContext().startActivity(book_intent);
            }
        });

        return convertView;
    }

    //提取出来方便点
    public final class ViewHolder {
        public String ISBN13;

        public TextView title;
        public TextView artist;
        public SimpleDraweeView list_image;
    }


}
