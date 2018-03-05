package com.anshi.expanablegridchoice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * Created by yulu on 2018/2/24.
 */

public class CategoryExpandGridAdapter extends BaseAdapter{
    private List<String>  mData;
    private Context mContext;
    private int mGroupPosition;
    private Set<RadioButton> checkBoxList  = new HashSet<>();
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer,Set<RadioButton>> map= new HashMap<>();
    //使用静态确保数据全部被放置到集合中，set防止对象重复
    private static Map<Integer,Set<RadioButton>> radioButtonHashMap = new LinkedHashMap<>();
    //传递groupPostion,确定选择标签对应的key
    public CategoryExpandGridAdapter(List<String> list, Context context, int groupPosition){
        this.mData = list;
        this.mContext = context;
        this.mGroupPosition = groupPosition;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                    Bundle data = msg.getData();
                    Set<RadioButton> checkList = (Set<RadioButton>) data.getSerializable("checkList");
                    radioButtonHashMap.put(msg.arg1,checkList);
        }
    };
    @Override
    public int getCount() {
        return null==mData?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final GridViewHolder gridViewHolder;
        if (null==view){
            view = LayoutInflater.from(mContext).inflate(R.layout.category_expand_grid_item,parent,false);
            gridViewHolder = new GridViewHolder(view);
        }else {
            gridViewHolder = (GridViewHolder) view.getTag();
        }
        gridViewHolder.tag.setText(mData.get(position));
        gridViewHolder.tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Set<RadioButton> checkBoxes = map.get(mGroupPosition);
                if (checkBoxes!=null&&checkBoxes.size()>0){
                    for (RadioButton c:checkBoxes) {
                        if (c.isChecked()){
                            c.setChecked(false);
                        }
                    }
                }
                if (((RadioButton)v).isChecked()){
                    checkBoxList.add((RadioButton) v);
                    Message message = mHandler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("checkList", (Serializable)checkBoxList);
                    message.setData(bundle);
                    message.arg1 = mGroupPosition;
                    mHandler.sendMessage(message);
                }
                gridViewHolder.tag.setChecked(true);
                map.put(mGroupPosition,checkBoxList);

            }
        });
        return view;
    }

    Map<Integer,String> getCheckedString(){
        Map<Integer,String> strings = new LinkedHashMap<>();
        Set<Integer> integers = radioButtonHashMap.keySet();
        for (int id:integers) {
            Set<RadioButton> radioButtons = radioButtonHashMap.get(id);
            for (RadioButton radioButton:radioButtons) {
                if (radioButton.isChecked()){
                    strings.put(id,radioButton.getText().toString());
                }
            }
        }
        return strings;
    }

    void clearChecked(){
        Set<Integer> integers = radioButtonHashMap.keySet();
        for (int id:integers) {
            Set<RadioButton> radioButtons = radioButtonHashMap.get(id);
            for (RadioButton radioButton:radioButtons) {
                if (radioButton.isChecked()){
                    radioButton.setChecked(false);
                }
            }
        }
    }

    private class GridViewHolder{
        RadioButton tag;
        GridViewHolder(View view){
            tag = view.findViewById(R.id.catrgory_tag_rb);
            view.setTag(this);
        }
    }
}
