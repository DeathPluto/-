package com.zyxf.workdivision.bean.response;

import com.zyxf.workdivision.bean.Checkingin;

import java.util.List;

/**
 * Created by DeathPluto on 2015/5/23.
 */
public class CheckinginForm {
    public int current_page;
    public List<Checkingin> data;
    public int per_page;
    public int total;
    public String next_page_url;
    public String prev_page_url;
}
