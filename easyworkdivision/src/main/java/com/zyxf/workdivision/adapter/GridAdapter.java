package com.zyxf.workdivision.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyxf.workdivision.R;
import com.zyxf.workdivision.base.BaseAdapter;
import com.zyxf.workdivision.bean.GridItem;

import java.util.List;

/**
 * Created by DeathPluto on 2015/5/19.
 */
public class GridAdapter extends BaseAdapter<GridItem> {

    public GridAdapter(List<GridItem> list) {
        super(list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridItem item = getItem(position);
        TextView textView = (TextView) View.inflate(parent.getContext(), R.layout.item_gridview, null);
        textView.setText(item.name);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, item.resId, 0, 0);
        return textView;
    }
}
