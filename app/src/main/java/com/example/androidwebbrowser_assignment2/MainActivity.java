package com.example.androidwebbrowser_assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Log;

import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;


public class MainActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public ViewPager2 viewPager;
    public TabAdapter tabAdapter;
    private List<String> tabTitles = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabTitles.add("Tab 1");
        tabAdapter = new TabAdapter(this, tabTitles, this);
        viewPager.setAdapter(tabAdapter);

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        // Available Memory in MB
        long availableMemory = memoryInfo.availMem / (1024 * 1024);

        // Calculate OffscreenPageLimit as 10% of Available Memory
        int offscreenPageLimit = (int) (availableMemory / 10);

        // Set OffscreenPageLimit to ViewPager2
        viewPager.setOffscreenPageLimit(offscreenPageLimit);

        // TabLayout and ViewPager2 binding
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabTitles.get(position))).attach();


    }

    // Update Tab Text after Go! button is clicked
    public void updateTabText(int position, String newText) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        if (tab != null) {
            tabTitles.set(position, newText);
            tabAdapter.notifyDataSetChanged();
        }
    }

    public void addTabTitle(String title) {
        tabTitles.add(title);
        tabAdapter.notifyDataSetChanged();
    }

}