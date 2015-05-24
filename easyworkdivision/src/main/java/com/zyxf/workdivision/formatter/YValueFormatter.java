package com.zyxf.workdivision.formatter;

import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by DeathPluto on 2015/5/24.
 */
public class YValueFormatter implements ValueFormatter {
    private DecimalFormat mFormat;

    public YValueFormatter() {
        this.mFormat = new DecimalFormat("###,###,###,##0");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + "人";
    }
}
