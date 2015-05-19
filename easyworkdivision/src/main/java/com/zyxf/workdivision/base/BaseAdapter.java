package com.zyxf.workdivision.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by DeathPluto on 2015/5/19.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    private List<T> list;

    public BaseAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
