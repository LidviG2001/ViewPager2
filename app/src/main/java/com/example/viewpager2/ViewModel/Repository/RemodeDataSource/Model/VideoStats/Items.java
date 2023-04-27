package com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items {

    @SerializedName("statistics")
    @Expose
    private Statistics statistics;

    @SerializedName("snippet")
    @Expose
    private Snippets snippets;

    public void setSnippet(Snippets snippets){
        this.snippets = snippets;
    }

    public Snippets getSnippet(){
        return snippets;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

}