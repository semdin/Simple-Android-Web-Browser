package com.example.androidwebbrowser_assignment2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TabFragment extends Fragment {
    private int tabNumber;

    public static TabFragment newInstance(int tabNumber) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt("tab_number", tabNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabNumber = getArguments().getInt("tab_number");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        TextView tabText = view.findViewById(R.id.tabText);
        tabText.setText("Tab " + tabNumber);

        Button newTabButton = view.findViewById(R.id.newTabButton);

        newTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add new tab and increment tab count
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).tabAdapter.addNewTab();
                }
            }
        });

        Button goButton = view.findViewById(R.id.goButton);


        goButton.setOnClickListener(v -> {
            WebView mywebview = view.findViewById(R.id.webView);
            EditText addressBar = view.findViewById(R.id.addressBar);

            mywebview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    String title = mywebview.getTitle();
                    if (title == null || title.isEmpty()) {
                        title = "Empty Page";
                    } else if (title.contains("about:blank")) {
                        title = "Empty Page";
                    }

                    tabText.setText(title);

                    // Update Tab Text on current tab
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).updateTabText(tabNumber - 1, title);
                    }


                }
            });

            WebSettings webSettings = mywebview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            String url = addressBar.getText().toString();
            mywebview.loadUrl(url);

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(addressBar.getWindowToken(), 0);
        });

        Button closeTabButton = view.findViewById(R.id.closeTabButton);
        closeTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove current tab and decrement tab count
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).tabAdapter.removeTab(tabNumber - 1);
                }
            }
        });

        return view;
    }

}
