package com.boredream.cnmedicine.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.boredream.bdcodehelper.adapter.MoreRecyclerAdapter;
import com.boredream.bdcodehelper.entity.SettingItem;
import com.boredream.bdcodehelper.utils.DisplayUtils;
import com.boredream.bdcodehelper.utils.TitleBuilder;
import com.boredream.bdcodehelper.utils.UserInfoKeeper;
import com.boredream.bdcodehelper.view.DividerItemDecoration;
import com.boredream.bdcodehelper.view.EmptyItemDecoration;
import com.boredream.cnmedicine.R;
import com.boredream.cnmedicine.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private RecyclerView rv_more;
    private MoreRecyclerAdapter adapter;
    private List<SettingItem> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(activity, R.layout.frag_more, null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        new TitleBuilder(view).setTitleText(getString(R.string.tab4));

        rv_more = (RecyclerView) view.findViewById(R.id.rv_more);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                activity, LinearLayoutManager.VERTICAL, false);
        rv_more.setLayoutManager(linearLayoutManager);
        // 每个item之间的分割线
        rv_more.addItemDecoration(new DividerItemDecoration(activity));
    }

    private void initData() {
        items = new ArrayList<>();

        items.add(new SettingItem(
                R.mipmap.ic_launcher,
                "我的收藏",
                null,
                -1
        ));
        items.add(new SettingItem(
                R.mipmap.ic_launcher,
                "关于应用",
                null,
                -1
        ));
        items.add(new SettingItem(
                R.mipmap.ic_launcher,
                "检查更新",
                null,
                -1
        ));

        // 每组item之间的分割间隔
        rv_more.addItemDecoration(new EmptyItemDecoration(
                new Integer[]{0}, DisplayUtils.dp2px(activity, 16)));

        adapter = new MoreRecyclerAdapter(items, this);
        adapter.setUser(UserInfoKeeper.getInstance(getActivity()).getCurrentUser());
        rv_more.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == -1) {
            // 给特殊的用户信息header位置设为了position=-1的item click事件
            // intent2Activity(UserInfoEditActivity.class);
        } else if (position >= 0) {

        }
    }
}
