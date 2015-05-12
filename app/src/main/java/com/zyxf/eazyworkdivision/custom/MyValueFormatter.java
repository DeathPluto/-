package com.zyxf.eazyworkdivision.custom;

import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;

/**
 * Title :       com.zyxf.eazyworkdivision.custom                                     <br/>
 * Copyright :   金屏宝 Co. , Ltd.Copyright 2015,All rights reserved  <br/>
 *
 * @author :     Administrator                                            <br/>
 * @version :    1.0                                                <br/>
 * @since :      2015/5/12 12:29                                    <br/>
 * Description:  todo
 */
public class MyValueFormatter implements ValueFormatter {
    private DecimalFormat mFormat;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + "人";
    }
}
