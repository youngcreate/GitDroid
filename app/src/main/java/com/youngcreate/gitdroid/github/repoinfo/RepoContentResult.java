package com.youngcreate.gitdroid.github.repoinfo;

import com.google.gson.annotations.SerializedName;

/**
 * 获取README响应结果
 * Created by Administrator on 16-8-2.
 */
public class RepoContentResult {

    @SerializedName("encoding")
    private String encoding;

    @SerializedName("content")
    private String content;

    public String getEncoding() {
        return encoding;
    }

    public String getContent() {
        return content;
    }
}
