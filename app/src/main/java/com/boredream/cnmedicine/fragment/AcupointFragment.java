package com.boredream.cnmedicine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.boredream.bdcodehelper.adapter.ListDropDownAdapter;
import com.boredream.bdcodehelper.entity.ListResponse;
import com.boredream.bdcodehelper.entity.PageIndex;
import com.boredream.bdcodehelper.listener.OnStringSelectListener;
import com.boredream.bdcodehelper.net.MultiPageRequest;
import com.boredream.bdcodehelper.present.MultiPageLoadPresent;
import com.boredream.bdcodehelper.utils.TitleBuilder;
import com.boredream.bdcodehelper.view.DropDownMenu;
import com.boredream.cnmedicine.R;
import com.boredream.cnmedicine.adapter.AcupointAdapter;
import com.boredream.cnmedicine.base.BaseFragment;
import com.boredream.cnmedicine.constants.CommonConstants;
import com.boredream.cnmedicine.entity.Acupoint;
import com.boredream.cnmedicine.net.HttpRequest;
import com.boredream.cnmedicine.net.SimpleSubscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;


public class AcupointFragment extends BaseFragment {

    private View view;
    private MultiPageLoadPresent multiPageLoadPresent;
    private ArrayList<Acupoint> datas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.frag_community, null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        new TitleBuilder(view).setTitleText(getString(R.string.tab2));

        initDropDownMenu();
    }

    private void initData() {
        loadData();
    }

    private DropDownMenu ddm;

    private String headers[] = {"所有经络", "所有功能"};
    private List<String> menuData1 = new ArrayList<>();
    private List<String> menuData2 = new ArrayList<>();
    private String curMenuData1 = headers[0];
    private String curMenuData2 = headers[1];
    private List<View> popupViews = new ArrayList<>();

    private void initDropDownMenu() {
        ddm = (DropDownMenu) view.findViewById(R.id.ddm);

        menuData1.add(headers[0]);
        menuData1.add("任脉");
        menuData1.add("督脉");
        menuData1.add("手厥阴心包经");
        menuData1.add("手太阳小肠经");
        menuData1.add("手太阴肺经");
        menuData1.add("手少阳三焦经");
        menuData1.add("手少阴心经");
        menuData1.add("手阳明大肠经");
        menuData1.add("足厥阴肝经");
        menuData1.add("足太阳膀胱经");
        menuData1.add("足太阴脾经");
        menuData1.add("足少阳胆经");
        menuData1.add("足少阴肾经");
        menuData1.add("足阳明胃经");
        addMenuListView(menuData1, new OnStringSelectListener() {
            @Override
            public void onStringSelect(String str) {
                curMenuData1 = str;
                loadData();
            }
        });

        menuData2.add(headers[1]);
        menuData2.add("利口舌咽喉穴");
        menuData2.add("利水通淋穴");
        menuData2.add("利目穴");
        menuData2.add("利耳穴");
        menuData2.add("利鼻穴");
        menuData2.add("安神穴");
        menuData2.add("平肝息风穴");
        menuData2.add("开窍苏厥穴");
        menuData2.add("止咳平喘化痰穴");
        menuData2.add("消食导滞穴");
        menuData2.add("清三焦热穴");
        menuData2.add("清心热穴");
        menuData2.add("清热解毒穴");
        menuData2.add("清肝胆热穴");
        menuData2.add("清肺热穴");
        menuData2.add("清胃肠热穴");
        menuData2.add("温里穴");
        menuData2.add("理气穴");
        menuData2.add("理血穴");
        menuData2.add("益气壮阳穴");
        menuData2.add("舒筋活络穴");
        menuData2.add("补阴穴");
        menuData2.add("袪风除湿穴");
        menuData2.add("解表穴");
        menuData2.add("调经止带穴");
        menuData2.add("通利诸窍穴");
        menuData2.add("空");

        addMenuListView(menuData2, new OnStringSelectListener() {
            @Override
            public void onStringSelect(String str) {
                curMenuData2 = str;
                loadData();
            }
        });

        View include_refresh_list = View.inflate(activity, R.layout.include_refresh_list, null);
        PageIndex pageIndex = new PageIndex(1, CommonConstants.COUNT_OF_PAGE);
        multiPageLoadPresent = new MultiPageLoadPresent(activity, include_refresh_list.findViewById(R.id.srl), pageIndex);

        //init context view
        include_refresh_list.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        //init dropdownview
        ddm.setDropDownMenu(Arrays.asList(headers), popupViews, include_refresh_list);
    }

    private void addMenuListView(final List<String> menuDatas, final OnStringSelectListener listener) {
        final ListView lv = (ListView) View.inflate(
                activity, R.layout.listview_dropdown_menu, null);
        final ListDropDownAdapter adapter = new ListDropDownAdapter(activity, menuDatas);
        lv.setAdapter(adapter);
        popupViews.add(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onStringSelect(menuDatas.get(position));
                }

                adapter.setCheckItem(position);
                ddm.setTabText(menuDatas.get(position));
                ddm.closeMenu();
            }
        });
    }

    private void loadData() {
        datas.clear();

        AcupointAdapter adapter = new AcupointAdapter(activity, datas);
        multiPageLoadPresent.load(adapter, datas,
                new MultiPageRequest<ListResponse<Acupoint>>() {
                    @Override
                    public Observable<ListResponse<Acupoint>> request(int page) {
                        return HttpRequest.getAcupoint(page,
                                curMenuData1.equals(headers[0]) ? null : curMenuData1,
                                curMenuData2.equals(headers[1]) ? null : curMenuData2);
                    }
                },
                new SimpleSubscriber<ListResponse<Acupoint>>(activity) {
                    @Override
                    public void onNext(ListResponse<Acupoint> response) {
                        //
                    }
                });
    }
}
