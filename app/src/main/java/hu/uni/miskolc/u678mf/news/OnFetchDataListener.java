package hu.uni.miskolc.u678mf.news;

import java.util.List;

import hu.uni.miskolc.u678mf.news.models.NewsHeadlines;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);
}
