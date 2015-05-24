package com.zyxf.workdivision.formatter;

import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by DeathPluto on 2015/5/24.
 */
public class XValueFormatter implements ValueFormatter {
    private DecimalFormat mFormat;

    public XValueFormatter() {
        this.mFormat = new DecimalFormat("###,###,###,##0");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value);
    }
}
