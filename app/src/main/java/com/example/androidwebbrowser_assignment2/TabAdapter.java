package com.example.androidwebbrowser_assignment2;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class TabAdapter extends FragmentStateAdapter {
    private int tabCount = 1;
    private MainActivity mainActivity;

    public TabAdapter(@NonNull FragmentActivity fragmentActivity, List<String> tabTitles, MainActivity mainActivity) {
        super(fragmentActivity);
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // create a new fragment with the position as the tab number
        return TabFragment.newInstance(position + 1);
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }

    // Add new tab and increment tab count
    public void addNewTab() {
        tabCount++;
        mainActivity.addTabTitle("Tab " + tabCount);
        //notifyDataSetChanged();
    }

    // Remove tab and decrement tab count
    public void removeTab( int position) {
        tabCount--;
        mainActivity.removeTabTitle(position);
        //notifyDataSetChanged();
    }

}
