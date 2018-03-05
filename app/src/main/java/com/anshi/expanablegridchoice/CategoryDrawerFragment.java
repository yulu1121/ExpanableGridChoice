package com.anshi.expanablegridchoice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.anshi.expanablegridchoice.utils.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by yulu on 2018/2/24.
 */

public class CategoryDrawerFragment extends Fragment {
    private Context context;
    private ExpandableListView mExpandView;
    private LinkedHashMap<String,List<String>> mExpandMap;
    private Button resetBtn;
    private Button completeBtn;
    private CategoryExpandAdapter categoryExpandAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    public static CategoryDrawerFragment getInstance(){
        return new CategoryDrawerFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.category_drawer_main,container,false);
        initView(view);
        expandListView();
        addEventListener();
        return view;
    }

    private void initView(View view) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.footer_layout,null);
        mExpandView = view.findViewById(R.id.category_drawer_list);
        mExpandView.addFooterView(view1);
        resetBtn = view1.findViewById(R.id.reset_btn);
        completeBtn = view1.findViewById(R.id.complete_btn);
        initData();
        categoryExpandAdapter = new CategoryExpandAdapter(context,mExpandMap);
        mExpandView.setAdapter(categoryExpandAdapter);
        mExpandView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    private void addEventListener(){
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryExpandAdapter.clear();

            }
        });
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Integer, String> checkedString = categoryExpandAdapter.getCheckedString();
                if (null!=checkedString){
                    Log.e("xxx",checkedString.toString());
                    Toast.makeText(context, checkedString.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 使组无法收缩
     * added by yulu 2017年2月21日 14:02:06
     */
    private void expandListView() {
        Object[] objects = mExpandMap.keySet().toArray();
        int size = objects.length ;
        for (int i = 0; i < size; i++) {
            mExpandView.expandGroup(i);
        }
    }

    private void initData(){
        mExpandMap = new LinkedHashMap<>();
        List<String> listOne = new ArrayList<>();
        listOne.add("翰代维");
        listOne.add("花花公子");
        listOne.add("啄木鸟");
        listOne.add("红蜻蜓");
        mExpandMap.put(Constants.CATEGORY_BRAND_KEY,listOne);
        List<String> listTwo = new ArrayList<>();
        listTwo.add("包邮");
        listTwo.add("赠送运费险");
        listTwo.add("消费者保障");
        listTwo.add("7天退/换货");
        mExpandMap.put(Constants.CATEGORY_SCALE_KEY,listTwo);
        List<String> listThree = new ArrayList<>();
        listThree.add("XXS");
        listThree.add("XS");
        listThree.add("S");
        listThree.add("M");
        listThree.add("均码");
        listThree.add("L");
        listThree.add("XL");
        listThree.add("2XL");
        listThree.add("XXL");
        mExpandMap.put(Constants.CATEGORY_SIZE_KEY,listThree);
        List<String> listFour = new ArrayList<>();
        listFour.add("1-58");
        listFour.add("58-114");
        listFour.add("114-222");
        mExpandMap.put(Constants.CATEGORY_PRICE_KEY,listFour);
        List<String> listFive = new ArrayList<>();
        listFive.add("珠三角");
        listFive.add("长三角");
        listFive.add("京津冀");
        mExpandMap.put(Constants.CATEGORY_LOCATION_KEY,listFive);
    }


}
