package com.garfield.alfred.robbin.event;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangcm on 2016/11/15.
 */

public class BookListEvent {
    private ArrayList<HashMap<String, String>> bookInfoList;

    public BookListEvent(ArrayList<HashMap<String, String>> bookInfoList) {
        super();
        this.bookInfoList = bookInfoList;
    }

    public ArrayList getBookInfoList() {
        return bookInfoList;
    }
}
