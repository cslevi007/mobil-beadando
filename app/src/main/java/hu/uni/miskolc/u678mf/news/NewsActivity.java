package hu.uni.miskolc.u678mf.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import hu.uni.miskolc.u678mf.R;
import hu.uni.miskolc.u678mf.news.models.NewsApiResponse;
import hu.uni.miskolc.u678mf.news.models.NewsHeadlines;

public class NewsActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1, b2, b3, b4, b5, b6, b7;
    SearchView searchView;
    String countryForApiByCurrentLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        if(getResources().getConfiguration().locale.getLanguage() == "hu") {
            countryForApiByCurrentLanguage = "hu";
        } else {
            countryForApiByCurrentLanguage = "us";
        }

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle(getResources().getString(R.string.fetchingNews));
                dialog.show();

                RequestManager manager = new RequestManager(NewsActivity.this);
                manager.getNewsHeadlines(listener, "general", query, countryForApiByCurrentLanguage);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle(getResources().getString(R.string.fetchingNews));
        dialog.show();

        b1 = findViewById(R.id.btn_1);
        b2 = findViewById(R.id.btn_2);
        b3 = findViewById(R.id.btn_3);
        b4 = findViewById(R.id.btn_4);
        b5 = findViewById(R.id.btn_5);
        b6 = findViewById(R.id.btn_6);
        b7 = findViewById(R.id.btn_7);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null, countryForApiByCurrentLanguage);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()) {
                Toast.makeText(NewsActivity.this, getResources().getString(R.string.noDataFoundToast), Toast.LENGTH_SHORT).show();
            } else {
                showNews(list);
            }
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {
            Toast.makeText(NewsActivity.this, getResources().getString(R.string.errorOccurredToast), Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.news_recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(NewsActivity.this, DetailsActivity.class).putExtra("data", headlines));
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category = button.getHint().toString();

        dialog.setTitle(getResources().getString(R.string.fetchingNews));
        dialog.show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, category, null, countryForApiByCurrentLanguage);
    }
}