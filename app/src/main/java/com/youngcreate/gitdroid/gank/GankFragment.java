package com.youngcreate.gitdroid.gank;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.youngcreate.gitdroid.R;
import com.youngcreate.gitdroid.commons.ActivityUtils;
import com.youngcreate.gitdroid.gank.model.GankItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 16-8-8.
 */
//此Fragment用来展示单日的干货数据
public class GankFragment extends Fragment implements GankPresenter.GankView {

    private static final String KEY_DATE = "key_date";

    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.content)
    ListView listView;
    @BindView(R.id.emptyView)
    FrameLayout emptyView;

    private GankAdapter adapter;
    private GankPresenter gankPresenter;

    private ActivityUtils activityUtils;
    private Unbinder unbinder;


    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;

    public static GankFragment getInstance(Date date) {
        GankFragment gankFragment = new GankFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATE, date);
        gankFragment.setArguments(args);
        return gankFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        calendar = Calendar.getInstance(Locale.CHINA);
        date = new Date(System.currentTimeMillis());
        gankPresenter = new GankPresenter(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gank, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        tvDate.setText(simpleDateFormat.format(date));
        //
        adapter = new GankAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GankItem gankItem = adapter.getItem(position);
                //
                activityUtils.startBrowser(gankItem.getUrl());
            }
        });
        //初始化执行业务
        gankPresenter.getGanks(date);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnFilter)
    public void showDataDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //更新日期，重新执行业务
            calendar.set(year, monthOfYear, dayOfMonth);
            date = calendar.getTime();
            tvDate.setText(simpleDateFormat.format(date));
            gankPresenter.getGanks(date);
        }
    };

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        YoYo.with(Techniques.FadeIn).duration(500).playOn(emptyView);
    }

    @Override
    public void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void setData(List<GankItem> gankItems) {
        adapter.setDatas(gankItems);
        adapter.notifyDataSetChanged();
    }
}
