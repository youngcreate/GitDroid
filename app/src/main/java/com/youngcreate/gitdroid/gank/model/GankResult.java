package com.youngcreate.gitdroid.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by Administrator on 16-8-8.
 */
public class GankResult {

    //种类
    private List<String> category;
    //
    private boolean error;
    //
    private Results results;

    public List<String> getCategory() {
        return category;
    }

    public boolean isError() {
        return error;
    }

    public Results getResults() {
        return results;
    }

    public static class Results {

        @SerializedName("Android")
        private List<GankItem> androidItems;

        public List<GankItem> getAndroidItems() {
            return androidItems;
        }
    }
}
