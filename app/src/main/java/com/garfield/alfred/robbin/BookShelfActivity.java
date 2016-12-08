package com.garfield.alfred.robbin;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.garfield.alfred.robbin.fragment.BookShelfFragment;


public class BookShelfActivity extends AppCompatActivity {
    static final String BOOK_TITLE = "title";
    static final String BOOK_AOTHER = "artist";
    static final String BOOK_IMAGE = "thumb_url";
    static final String BOOK_PUBLISHER = "publisher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 配置 tab
        TabLayout tab = (TabLayout) findViewById(R.id.bstab);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);

        // 设置 tab 标题
        List<String> listTitle = new ArrayList<String>();
        listTitle.add("书架");
        //listTitle.add("想读");

        // 设置 tab 页面
        List<Fragment> listFragment = new ArrayList<Fragment>();
        listFragment.add(new BookShelfFragment());
        //listFragment.add(new BookShelfFragment());

        // 关联 tab 页面
        TabPagerAdapder pagerAdapter = new TabPagerAdapder(BookShelfActivity.this.getSupportFragmentManager(), listTitle, listFragment);
        ViewPager viewPager = (ViewPager) findViewById(R.id.bsview);
        viewPager.setAdapter(pagerAdapter);
        tab.setupWithViewPager(viewPager);
    }

    /**
     * @author
     */
    class TabPagerAdapder extends FragmentPagerAdapter {
        private List<Fragment> list_fragment;                         // fragment 页面列表
        private List<String> list_Title;                              // tab 标题列表

        public TabPagerAdapder(FragmentManager fm, List<String> list_Title, List<Fragment> list_fragment) {
            super(fm);
            this.list_fragment = list_fragment;
            this.list_Title = list_Title;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_Title.size();
        }

        // 此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {
            return list_Title.get(position % list_Title.size());
        }
    }
}
