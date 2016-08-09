package com.youngcreate.gitdroid.gank;

import com.youngcreate.gitdroid.gank.model.GankItem;
import com.youngcreate.gitdroid.gank.model.GankResult;
import com.youngcreate.gitdroid.gank.network.GankClient;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 16-8-8.
 */
public class GankPresenter {

    private Call<GankResult> call;
    private GankView gankView;

    public interface GankView {

        void showEmptyView();

        void hideEmptyView();

        void showMessage(String msg);

        void setData(List<GankItem> gankItems);

    }

    public GankPresenter(GankView gankView) {
        this.gankView = gankView;
    }

    //通过日期，获取每日干货数据
    public void getGanks(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        call = GankClient.getInstance().getDailyData(year, month, day);
        call.enqueue(callBack);


    }

    private Callback<GankResult> callBack = new Callback<GankResult>() {
        @Override
        public void onResponse(Call<GankResult> call, Response<GankResult> response) {
            GankResult gankResult = response.body();
            if (gankResult == null) {
                gankView.showMessage("unknown error");
                return;
            }
            //没有数据的情况
            if (gankResult.isError()
                    || gankResult.getResults() == null
                    || gankResult.getResults().getAndroidItems() == null
                    || gankResult.getResults().getAndroidItems().isEmpty()) {
                gankView.showEmptyView();
                return;
            }
            List<GankItem> gankItems = gankResult.getResults().getAndroidItems();
            //将获取到的今日干货数据交付给视图
            gankView.hideEmptyView();
            gankView.setData(gankItems);

        }

        @Override
        public void onFailure(Call<GankResult> call, Throwable t) {
            gankView.showMessage("Error:" + t.getMessage());
        }
    };

}
