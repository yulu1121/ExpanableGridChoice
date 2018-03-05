package com.anshi.expanablegridchoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.anshi.expanablegridchoice.utils.Constants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * Created by yulu on 2018/2/24.
 */

public class CategoryExpandAdapter extends BaseExpandableListAdapter {
    private LinkedHashMap<String,List<String>> mMap;
    private Context mContext;
    private CategoryExpandGridAdapter categoryExpandGridAdapter;

    public CategoryExpandAdapter(Context context,LinkedHashMap<String,List<String>> map){
        this.mContext = context;
        this.mMap = map;
    }

    @Override
    public int getGroupCount() {
        Set<String> keySet = mMap.keySet();
        return keySet.toArray().length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //此处需要返回1,否则会出现数据不对应
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        CategoryExpandViewHolder viewHolder;
        if (null==view){
            view = LayoutInflater.from(mContext).inflate(R.layout.category_expand_item,parent,false);
            viewHolder = new CategoryExpandViewHolder(view);
        }else {
            viewHolder = (CategoryExpandViewHolder) view.getTag();
        }
        Object[] objects = mMap.keySet().toArray();
        String key = (String) objects[groupPosition];
        switch (key){
            case Constants.CATEGORY_BRAND_KEY:
                viewHolder.textView.setText("品牌");
                break;
            case Constants.CATEGORY_PRICE_KEY:
                viewHolder.textView.setText("价格");
                break;
            case Constants.CATEGORY_LOCATION_KEY:
                viewHolder.textView.setText("地区");
                break;
            case Constants.CATEGORY_SCALE_KEY:
                viewHolder.textView.setText("优惠");
                break;
            case Constants.CATEGORY_SIZE_KEY:
                viewHolder.textView.setText("尺码");
                break;
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        CategoryExpandChildViewHolder childViewHolder;
        if (null==view){
            view = LayoutInflater.from(mContext).inflate(R.layout.category_child_item,parent,false);
            childViewHolder = new CategoryExpandChildViewHolder(view);
        }else {
            childViewHolder = (CategoryExpandChildViewHolder) view.getTag();
        }
        Object[] objects = mMap.keySet().toArray();
        String key = (String) objects[groupPosition];
        List<String> list = mMap.get(key);
        categoryExpandGridAdapter = new CategoryExpandGridAdapter(list,mContext,groupPosition);
        childViewHolder.gridView.setAdapter(categoryExpandGridAdapter);
        return view;
    }
    //获取选择数据
    Map<Integer, String> getCheckedString(){
        if (null!=categoryExpandGridAdapter){
            return categoryExpandGridAdapter.getCheckedString();
        }
        return null;
    }
    //清空选择
     void clear(){
        if (null!=categoryExpandGridAdapter){
           categoryExpandGridAdapter.clearChecked();
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private class CategoryExpandViewHolder{
        TextView textView;
        CategoryExpandViewHolder(View view){
            textView = view.findViewById(R.id.category_expand_title);
            view.setTag(this);
        }
    }
    private class CategoryExpandChildViewHolder{
        CanScrollGridView gridView;
        CategoryExpandChildViewHolder(View view){
            gridView = view.findViewById(R.id.category_expand_grid_item);
            view.setTag(this);
        }
    }
}
